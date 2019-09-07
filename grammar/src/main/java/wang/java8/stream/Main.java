package wang.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        // 生成流
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        // 串行流
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        // limit 用于打出指定数量的流
        new Random().ints().limit(10).forEach(System.out::println);

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // map方法 获取对应的平方数
        List<Integer> squaresList = numbers.stream().map(i -> i*i).distinct().collect(Collectors.toList());

        // filter 方法
        // 获取空字符串的数量
        long count = strings.stream().filter(string -> string.isEmpty()).count();

        // sorted
        new Random().ints().limit(10).sorted().forEach(System.out::println);

        // 并行 获取空字符串的数量
        long cnt = strings.parallelStream().filter(string -> string.isEmpty()).count();
    }
}
