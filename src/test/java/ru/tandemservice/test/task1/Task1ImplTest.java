package ru.tandemservice.test.task1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;

public class Task1ImplTest {

    @Test
    public void splitTest1() { /* abcd */
        var actual = Task1Impl.split("abcd");
        List<Task1Impl.Pair<String, Boolean>> expected = new ArrayList<>();
        expected.add(new Task1Impl.Pair<>("abcd", false));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void splitTest2() { /* 1234 */
        var actual = Task1Impl.split("1234");
        List<Task1Impl.Pair<String, Boolean>> expected = new ArrayList<>();
        expected.add(new Task1Impl.Pair<>("1234", true));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void splitTest3() { /* abcd1234asd12312233as - [abcd, 1234, asd, 12312233, as] */
        var actual = Task1Impl.split("abcd1234asd12312233as");
        List<Task1Impl.Pair<String, Boolean>> expected = new ArrayList<>();
        expected.add(new Task1Impl.Pair<>("abcd", false));
        expected.add(new Task1Impl.Pair<>("1234", true));
        expected.add(new Task1Impl.Pair<>("asd", false));
        expected.add(new Task1Impl.Pair<>("12312233", true));
        expected.add(new Task1Impl.Pair<>("as", false));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void notEmptyStringCompareTest1() { /* abcd abcd 0 */
        Assert.assertEquals(
                0,
                Task1Impl.notEmptyCellValueCompare("abcd", "abcd")
        );
    }

    @Test
    public void notEmptyStringCompareTest2() { /* abcd123 abcd123 0 */
        Assert.assertEquals(
                0,
                Task1Impl.notEmptyCellValueCompare("abcd123", "abcd123")
        );
    }

    @Test
    public void notEmptyStringCompareTest3() { /* 123abcd 123abcd 0 */
        Assert.assertEquals(
                0,
                Task1Impl.notEmptyCellValueCompare("123abcd", "123abcd")
        );
    }

    @Test
    public void notEmptyStringCompareTest4() { /* 123abcd 123dcba -1 */
        Assert.assertTrue(Task1Impl.notEmptyCellValueCompare("123abcd", "123dcba") < 0);
    }

    @Test
    public void notEmptyStringCompareTest5() { /* 123abcd345 123abcd 1 */
        Assert.assertTrue(Task1Impl.notEmptyCellValueCompare("123abcd345", "123abcd") > 0);
    }

    @Test
    public void notEmptyStringCompareTest6() { /* 123abcd345 123dcba -1 */
        Assert.assertTrue(Task1Impl.notEmptyCellValueCompare("123abcd345", "123dcba") < 0);
    }

    @Test
    public void cellValueCompareTest1() { /* null null 0 */
        Assert.assertEquals(
                0,
                Task1Impl.cellValueCompare(null, null)
        );
    }

    @Test
    public void cellValueCompareTest2() { /* null empty -1 */
        Assert.assertEquals(
                -1,
                Task1Impl.cellValueCompare(null, "")
        );
    }

    @Test
    public void cellValueCompareTest3() { /* null abc -1 */
        Assert.assertEquals(
                -1,
                Task1Impl.cellValueCompare(null, "abc")
        );
    }

    @Test
    public void cellValueCompareTest4() { /* empty null 1 */
        Assert.assertEquals(
                1,
                Task1Impl.cellValueCompare("", null)
        );
    }

    @Test
    public void cellValueCompareTest5() { /* empty empty 0 */
        Assert.assertEquals(
                0,
                Task1Impl.cellValueCompare("", "")
        );
    }

    @Test
    public void cellValueCompareTest6() { /* empty abc -1 */
        Assert.assertEquals(
                -1,
                Task1Impl.cellValueCompare("", "abc")
        );
    }

    @Test
    public void cellValueCompareTest7() { /* abc null 1 */
        Assert.assertEquals(
                1,
                Task1Impl.cellValueCompare("abc", null)
        );
    }

    @Test
    public void cellValueCompareTest8() { /* abc empty 1 */
        Assert.assertEquals(
                1,
                Task1Impl.cellValueCompare("abc", "")
        );
    }

    @Test
    public void numberCompare() {
        Assert.assertTrue(Task1Impl.numberComparison("123123123123123", "123123123123") > 0);
    }

    @Test
    public void numberCompare2() {
        Assert.assertTrue(Task1Impl.numberComparison("123123123123", "123123123123123" ) < 0);
    }

}
