package myjava.exercise;

public class StringEx {
    public static void main(String[] args) {
        String str = "Hello World!";
        System.out.println("Original String: " + str);
        System.out.println("Reversed String: " + reverseString(str));
        System.out.println(formatString(26, "Audi","Adam", "Eva"));
    }

    public static String reverseString(String str) {
//        StringBuilder: Dùng khi hiệu năng là ưu tiên và không cần xử lý đa luồng.
//        StringBuffer: Dùng khi tính an toàn trong môi trường đa luồng là quan trọng.
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }

    public static String formatString(int number, String carBrand, String ...str) {
        String joinedStr = String.join(",", str);
//        %s: Định dạng chuỗi.
//        %d: Định dạng số nguyên (integer).
//        %f: Định dạng số thập phân (floating-point).
//        %x: Định dạng số nguyên ở hệ thập lục phân (hexadecimal).
//        %n: Dòng mới (new line).
        return String.format("If has %.0f dollars %s will buy %d %s. %nThey love Java!", 100000000f,
                joinedStr, number, carBrand);
    }
}
