package myjava.exercise;

import myjava.exercise.model.Person;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StreamEx {
    private static final String PEN_EMOJI = "\uD83D\uDD8A";
    public static final String ROCKET_EMOJI = "\uD83D\uDE80";

    public static void main(String[] args) {
        List<Integer> testList1 = List.of(1, 2, 3, 4, 5);
        List<String> testList2 = List.of("Hello", "World", "Java", "Stream");
        List<Integer> testList3 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> testList4 = List.of(1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5);
        List<String> testList5 = List.of("@hello", "#world", "normal", "$sign", "text");
        List<String> testList6 = List.of("apple","cherry", "banana", "date", "elderberry");
        List<Person> testList7 = List.of(new Person("John", 26), new Person("Ana", 28));
        List<Person> testList8 = List.of(new Person("John", 26), new Person("Ana", 28), new Person("John", 30));
        List<List<String>> testList9 = Arrays.asList(
                Arrays.asList("apple", "banana"),
                Arrays.asList("orange", "kiwi"),
                Arrays.asList("grape", "watermelon")
        );
        // List.of :      ver  > 9| ptu bất biến             | notnull  | tối ưu tiết kiệm bộ nhớ| empty list| ko thêm/xóa|
        // Arrays.asList: ver  > 5| ptu có thể thay đổi value| able null| ko tối ưu              |  not empty| ko thêm/xóa|

        System.out.println("Test 🪄:  OUTPUT 🔬 </>");
//        System.out.println(ROCKET_EMOJI + " average: " + calculateAverage(testList1));
//        System.out.println(ROCKET_EMOJI + " convert Lowercase: " + convertUpperToLower(testList2));
//        System.out.println(ROCKET_EMOJI + " sum Even: " + sumEven(testList3));
        System.out.println(ROCKET_EMOJI + " remove duplicate: "+ removeDuplicates(testList4));
//        System.out.println(ROCKET_EMOJI + " count specific string: " + countNumberOfStringStartWithSpecificLetter(testList5));
//        System.out.println(ROCKET_EMOJI + " sort descending:");
//        printList(sortDescending(testList6));
//        System.out.println(ROCKET_EMOJI + " find min:" + findMin(testList1));
//        System.out.println(ROCKET_EMOJI + " find min2:" + findMin2(testList1));
//        System.out.println(ROCKET_EMOJI + " find second min:" + findSecondSmallest(testList4));
//        System.out.println(ROCKET_EMOJI + " convert to Map: " + convertToMap(testList7));
//        System.out.println(ROCKET_EMOJI + " group by name: " + groupByName(testList8));
//        System.out.println(ROCKET_EMOJI + " flatMap: " + convertUsingFlatMap(testList9));

    }

    public static double calculateAverage(List<Integer> list) {
        return list.stream().mapToInt(i -> i).average().orElse(0);
    }

    public static List<String> convertUpperToLower(List<String> list) {
        return list.stream().map(String::toLowerCase).toList();
    }

    public static Integer sumEven(List<Integer> list) {
        return list.stream().filter(i -> i % 2 == 0).reduce(0, Integer::sum);
    }

    public static <T> void printList(List<T> list) {
        list.stream().forEach(i -> System.out.printf("%s ", i));
    }

    public static List<Integer> removeDuplicates(List<Integer> list) {
        return list.stream().distinct().sorted().collect(Collectors.toList());
    }

    public static long countNumberOfStringStartWithSpecificLetter(List<String> list) {
        // "^[\\W]": Kiểm tra chuỗi bắt đầu với bất kỳ ký tự không phải chữ cái hoặc chữ số.
        // "^[!@#$%^]": Chỉ kiểm tra các chuỗi bắt đầu với một trong các ký tự đặc biệt !, @, #, $, %, ^.
        Pattern pattern = Pattern.compile("^[\\W].*");
        return list.stream().filter(s -> pattern.matcher(s).find()).count();
    }

    public static List<String> sortDescending(List<String> list) {
        return list.stream().sorted(Comparator.reverseOrder()).toList();
    }

    public static int findMin(List<Integer> list) {
        return list.stream().min(Integer::compare).orElse(0);
    }

    public static int findMin2(List<Integer> list) {
        return list.stream().mapToInt(i -> i).min().orElse(0);
    }

    public static int findSecondSmallest(List<Integer> list) {
        return list.stream().distinct().sorted().skip(1).findFirst().orElse(0);
    }

    public static Map<String, Integer> convertToMap(List<Person> list) {
        return list.stream().collect(Collectors.toMap(Person::getName, Person::getAge));
    }

    public static Map<String, List<Person>> groupByName(List<Person> list) {
        return list.stream()
                .collect(Collectors.groupingBy(Person::getName));
    }

    public static List<String> convertUsingFlatMap(List<List<String>> list) {
        return list.stream()
               .flatMap(List::stream)
               .toList();
    }
}
