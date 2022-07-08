package ru.tandemservice.test.task2;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task2ImplTest {

    public static List<IElement> list = new ArrayList<>();

    public static ElementExampleImpl.Context context;

    public static final int ELEMENTS_COUNT = 1000;

    public static boolean fail = false;

    @BeforeEach
    public void listSetup() {
        context = new ElementExampleImpl.Context();
        list.clear();
        for (int i = 0; i < ELEMENTS_COUNT; ++i) {
            list.add(new ElementExampleImpl(context, i));
        }

        Collections.shuffle(list);
    }

//    @BeforeEach
//    public void listSetup() {
//        context = new ElementExampleImpl.Context();
//        list.add(new ElementExampleImpl(context, 5));
//        list.add(new ElementExampleImpl(context, 4));
//        list.add(new ElementExampleImpl(context, 3));
//        list.add(new ElementExampleImpl(context, 2));
//        list.add(new ElementExampleImpl(context, 1));
//        list.add(new ElementExampleImpl(context, 0));
//    }

    public void printList(List<?> l) {
        l.forEach(System.out::println);
    }

    @Test
    public void finalTest() {
        IElementNumberAssigner assigner = Task2Impl.getInstance();
        assigner.assignNumbers(Collections.unmodifiableList(list));

        boolean allGood = true;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).getNumber() != i) {
                allGood = false;
            }
        }
        System.out.println("operationsCount: " + context.getOperationCount());

        fail = !allGood;
        Assertions.assertTrue(allGood);
    }

    @AfterEach
    public void failPrint() {
        if (fail) {
            printList(list);
        }
    }


}
