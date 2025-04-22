package myjava.exercise;

class CoffeeOrder implements Runnable {
    private final String customerName;

    public CoffeeOrder(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public void run() {
        System.out.println(customerName + " đang được phục vụ bởi " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000); // Giả lập thời gian pha cà phê
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(customerName + " đã nhận cà phê ☕ từ " + Thread.currentThread().getName());
    }
}

public class MultiThreadEx {
    public static void main(String[] args) {
        System.out.println("\uD83C\uDFEA  Quán cà phê bắt đầu mở cửa!");

        // Tạo thread pool với 3 barista
        java.util.concurrent.ExecutorService baristas = java.util.concurrent.Executors.newFixedThreadPool(3);

        // Giả lập 5 khách đến đặt cà phê
        String[] customers = {"An", "Bình", "Chi", "Dũng", "Em"};

        for (String name : customers) {
            CoffeeOrder order = new CoffeeOrder(name);
            baristas.submit(order); // Giao order cho barista xử lý
        }

        baristas.shutdown(); // Đóng quán sau khi nhận hết order
    }
}
