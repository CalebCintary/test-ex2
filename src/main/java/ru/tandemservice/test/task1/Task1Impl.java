package ru.tandemservice.test.task1;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Задание №1</h1>
 * Реализуйте интерфейс {@link IStringRowsListSorter}.
 *
 * <p>Мы будем обращать внимание в первую очередь на структуру кода и владение стандартными средствами java.</p>
 */
public class Task1Impl implements IStringRowsListSorter {

    // ваша реализация должна работать, как singleton. даже при использовании из нескольких потоков.
    public static final IStringRowsListSorter INSTANCE = new Task1Impl();

    private Task1Impl() {

    }

    public static IStringRowsListSorter getInstance() {
        return INSTANCE;
    }

    @Override
    public void sort(final List<String[]> rows, final int columnIndex) {
        rows.sort((arr1, arr2) -> cellValueCompare(arr1[columnIndex], arr2[columnIndex]));
    }

    /**
     * Вспомогательный класс пары значений.
     * Использую lombok чтобы не писать тривиальный код
     * @param <T>
     * @param <K>
     */
    @Getter
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Pair<T, K> {
        private final T first;
        private final K second;
    }

    static int cellValueCompare(final String left, final String right) {
        // Если первый операнд null, то он меньше всего что не null.
        if (left == null) {
            return (right == null ? 0 : -1);
        // Если первый операнд isEmpty, то он больше null, равен empty и меньше !isEmpty.
        } else if (left.isEmpty()) {
            return (right == null ? 1 : (right.isEmpty() ? 0 : -1));
        // Если первый операнд !isEmpty, то он больше null и isEmpty.
        } else {
            return (right == null ? 1 : (right.isEmpty() ? 1 : notEmptyCellValueCompare(left, right)));
        }
    }

    static int notEmptyCellValueCompare(@NonNull final String left,
                                               @NonNull final String right) {
        // Выделяем подстроки
        var aList = split(left);
        var bList = split(right);

        // В задаче не описана ситуация, в которой не совпадает количество подстрок, поэтому заполню
        // меньший список пустыми подстроками.
        if (aList.size() != bList.size()) {
            for (int i = aList.size(); i < bList.size(); ++i) {
                aList.add(new Pair<>("", false));
            }
            for (int i = bList.size(); i < aList.size(); ++i) {
                bList.add(new Pair<>("", false));
            }
        }

        // Основной проход по подстрокам
        int i = 0;
        Pair<String, Boolean> a, b;
        do { // Проходим до первой не совпадающей подстроки
            a = aList.get(i);
            b = bList.get(i++);
        } while (a.getFirst().equals(b.getFirst()) && i < aList.size());

        // Second - признак того, что подстрока число.
        // Пожертвую немного памяти, взамен замедления при проверках на try catch от parseInt
        if (a.getSecond() && b.getSecond()) {
            return numberComparison(a.getFirst(), b.getFirst());
        } else {
            // Проверки на empty, когда не совпадает количество подстрок.
            // В случае если есть empty он считается меньшим.
            if (!a.getFirst().isEmpty()) {
                if (!b.getFirst().isEmpty()) {
                    return a.getFirst().compareTo(b.getFirst()); // Сравнение по порядку в кодировке, ибо другого      \
                } else return 1;                                 // способа задано не было                             \
            } else return (b.getFirst().isEmpty() ? 0 : -1);
        }
    }

    /**
     * Разделяет строку на подстроки из чисел и оставшихся частей
     * @param s не пустая строка, которую необходимо разделить
     * @return массив подстрок
     */
    static List<Pair<String, Boolean>> split(@NonNull final String s) {
        List<Pair<String, Boolean>> result = new ArrayList<>();

        // Флаг числа. Если true, значит сейчас идет проход по подстроке из цифр. \
        // Базовое значение определяется первым символом                          \
        boolean digitFlag = (Character.isDigit(s.charAt(0)));
        StringBuilder current = new StringBuilder(); // Нынешняя подстрока
        for (Character c : s.toCharArray()) {
            if (Character.isDigit(c) != digitFlag) { // Если значение флага не совпадает с типом символа, то выгружаем \
                result.add(new Pair<>(current.toString(), digitFlag)); // подстроку и начинаем новую                   \
                digitFlag = !digitFlag;
                current = new StringBuilder();
            }
            current.append(c);
        }

        result.add(new Pair<>(current.toString(), digitFlag)); // Добавление последней подстроки

        return result;
    }

    /**
     * Выполняет сравнение двух строк, представляющих собой числа.
     * <p>Позволяет производить сравнение таких чисел, что они больше чем размерность Int. Пределом сравнения является
     *     размер стека, так как метод рекурсивен</p>
     * @param a левый операнд сравнения
     * @param b правый операнд сравнения
     * @return значение <0, 0 или >0 если левый операнд меньше правого, равен или больше соответственно.
     */
    static int numberComparison(final String a, final String b) {
        int aInt, bInt;
        try {
            aInt = Integer.parseInt(a); // Попытка выполнить стандартное сравнение
            bInt = Integer.parseInt(b);
        } catch (NumberFormatException ex) { // Переполнение Int
            String newA = a;
            String newB = b;
            int length = a.length();

            if (a.length() != b.length()) {  // Уравнивание длины двух чисел
                length = Math.max(a.length(), b.length());
                newA = "0".repeat(length - a.length()) + a;
                newB = "0".repeat(length - b.length()) + b;
            }

            int result = numberComparison( // Разделение строк в половину и сравнение левых половин чисел.
                    newA.substring(0, length / 2 + 1),
                    newB.substring(0, length / 2 + 1)
            );

            if (result == 0) { // Если после сравнения левых половин, они оказались равны, то сравниваем правые половины
                result = numberComparison(
                        newA.substring(length / 2 + 1),
                        newB.substring(length / 2 + 1)
                );
            }

            return result;
        }

        return aInt - bInt;
    }

}
