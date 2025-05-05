### Kafka
#### I. Knowledge
_**Apache Kafka** là một hệ thống messaging phân tán, nhanh chóng và mạnh mẽ, được sử dụng rộng rãi trong các hệ thống xử lý dữ liệu thời gian thực. Kafka có khả năng xử lý lượng dữ liệu lớn, cung cấp một phương thức giao tiếp giữa các hệ thống trong một môi trường phân tán_

##### *. Các trường hợp sử dụng Kafka

- Xử lý các giao dịch tài chính trong thời gian thực
- Theo dõi và giám sát các phương tiện vận tải trong thời gian thực
- Thu thập và phân tích dữ liệu cảm biến
- Thu thập và phản ứng với các tương tác của khách hàng
- Theo dõi bệnh nhân trong bệnh viện
- Cung cấp nền tảng cho nền tảng dữ liệu, kiến ​​trúc hướng sự kiện và dịch vụ vi mô
- Thực hiện nhắn tin quy mô lớn
- Phục vụ như một bản ghi cam kết cho các hệ thống phân tán

##### 1. Cấu trúc Kafka:
_Kafka sử dụng một mô hình Publisher-Subscriber (Producer-Consumer) và có các thành phần chính như sau:_

`Producer`: _Đóng vai trò là nhà sản xuất dữ liệu, ghi dữ liệu vào các topic._

`Consumer`: _Đọc dữ liệu từ topic._

`Broker`: _Kafka chạy trên các server gọi là broker. Một Kafka cluster có thể có một hoặc nhiều broker. Mỗi broker lưu trữ một phần dữ liệu của topic._

`Topic`: _Là nơi lưu trữ dữ liệu. Producer ghi dữ liệu vào topic và Consumer đọc từ topic. Mỗi topic có thể có nhiều partition._

`Partition`: _Là đơn vị phân tán trong Kafka. Dữ liệu trong topic được chia thành các partition để phân tán trên các broker._

`Consumer Group`: Nhiều consumer có thể tham gia vào một nhóm (group) để chia sẻ công việc đọc dữ liệu từ các partition của topic.

`Zookeeper`: Là hệ thống quản lý các metadata của Kafka. Kafka sử dụng Zookeeper để duy trì trạng thái của cluster và các broker.

#### II. Setup

```text
//flow
Spring Boot App (port 9999)
├── /api/kafka/send?message=Hello      → Gửi message
├── @KafkaListener                      → Nghe message
└── Kafka UI (port 8080)               → Quan sát message đến topic
```

1. `Docker-compose`

```yaml
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    container_name: zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    container_name: kafka
    hostname: kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"     # Exposed for localhost access (Spring app)
      - "29092:29092"   # Internal communication
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_INTERNAL:PLAINTEXT
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092,PLAINTEXT_INTERNAL://0.0.0.0:29092
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092,PLAINTEXT_INTERNAL://kafka:29092
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT_INTERNAL
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: kafka-ui
    depends_on:
      - kafka
    ports:
      - "8080:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:29092
      KAFKA_CLUSTERS_0_ZOOKEEPER: zookeeper:2181
```

