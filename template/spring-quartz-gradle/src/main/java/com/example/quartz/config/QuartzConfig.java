package com.example.quartz.config;

import com.example.quartz.job.MyJob;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.Set;

@Configuration
public class QuartzConfig {

    private final DataSource dataSource;

    public QuartzConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void init() {
        SpringConnectionProvider.springDataSource = dataSource;
    }

    @Bean
    public Scheduler scheduler() throws Exception {
        Properties props = new Properties();

        props.setProperty("org.quartz.scheduler.instanceName", "QuartzScheduler");
        props.setProperty("org.quartz.scheduler.instanceId", "AUTO");

        props.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        props.setProperty("org.quartz.threadPool.threadCount", "5");

        props.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        props.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
        props.setProperty("org.quartz.jobStore.useProperties", "true");
        props.setProperty("org.quartz.jobStore.tablePrefix", "qrtz_");
        props.setProperty("org.quartz.jobStore.dataSource", "myDS");

        props.setProperty("org.quartz.dataSource.myDS.connectionProvider.class", "com.example.quartz.config.SpringConnectionProvider");

        StdSchedulerFactory factory = new StdSchedulerFactory(props);
        Scheduler scheduler = factory.getScheduler();

        JobDetail job = JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob")
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(job)
                .withIdentity("myTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?"))
                .build();

        Set<Trigger> triggerSet = Set.of(trigger);
        scheduler.scheduleJob(job, triggerSet,true);

        scheduler.start();
        return scheduler;
    }
}