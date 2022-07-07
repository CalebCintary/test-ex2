package ru.tandemservice.test.task1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SortTest {

    private static List<String[]> randomSource = new ArrayList<>();

    static {
        randomSource.add(new String[] {"1", "nikita", "abc"});
        randomSource.add(new String[] {"2", "alexei", "123abc"});
        randomSource.add(new String[] {"3", "nikolai", "abcd123"});
        randomSource.add(new String[] {"4", "sergei", "abcd123"});
        randomSource.add(new String[] {"5", "anna", "abcd213"});
        randomSource.add(new String[] {"6", "artem", "123abc156"});
        randomSource.add(new String[] {"7", "maxim", "123abc236"});
    }

    public void printSource(List<String[]> source) {
        source.forEach(strings -> {
            for (int i = 0; i < source.get(0).length; ++i) {
                System.out.print(strings[i] + "\t");
            }
            System.out.println();
        });
    }

    @Test
    public void sortTest() {
        printSource(randomSource);
        IStringRowsListSorter task1 = Task1Impl.getInstance();
        task1.sort(randomSource, 2);

        System.out.println();

        printSource(randomSource);
    }
}
