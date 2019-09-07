package wang.java8;

import java.util.Comparator;
import java.util.List;

public class GrammarTest {

    // 使用 java 7 排序
    private void sortUsingJava7(List<String> names){
        names.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
    }

    // 使用 java 8 排序
    private void sortUsingJava8(List<String> names){
        names.sort(String::compareTo);
    }
}
