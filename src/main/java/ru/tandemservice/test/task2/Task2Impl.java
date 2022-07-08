package ru.tandemservice.test.task2;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>Задание №2</h1>
 * Реализуйте интерфейс {@link IElementNumberAssigner}.
 *
 * <p>Помимо качества кода, мы будем обращать внимание на оптимальность предложенного алгоритма по времени работы
 * с учетом скорости выполнения операции присвоения номера:
 * большим плюсом (хотя это и не обязательно) будет оценка числа операций, доказательство оптимальности
 * или указание области, в которой алгоритм будет оптимальным.</p>
 */
public class Task2Impl implements IElementNumberAssigner {

    // ваша реализация должна работать, как singleton. даже при использовании из нескольких потоков.
    public static final IElementNumberAssigner INSTANCE = new Task2Impl();

    public static IElementNumberAssigner getInstance() {
        return INSTANCE;
    }

    /**
     * <p>Алгоритм исполняется за примерно линейное время.</p>
     * <p>Использование операций {@link IElement#setupNumber(int)} стремится к n + k. Если исходные номера состоят
     * из промежутка [0;n] то k -> 0. Если же промежуток [0;{2, 3}n] то (при n = 1000) k -> {200, 300}. При увеличении
     * коэффициента при n k -> 0</p>
     * <p>Исходя из всего выше сказанного алгоритм работает с наибольшей эффективностью когда исходный список состоит из
     *  [0; n] или если представленные значения сильно отличаются от [0; n]</p>
     *
     * @param elements элементы, которым нужно выставить номера
     */
    @Override
    public void assignNumbers(final List<IElement> elements) {
        // Заранее вычисляется максимальный номер для замен
        AtomicInteger max = new AtomicInteger(elements.get(0).getNumber());
        elements.forEach(element -> max.set(Math.max(element.getNumber(), max.get())));

        // offset для старта с не идущих по порядку элементов
        int offset = 0;

        do {
            int differenceIndex = offset;
            while (elements.get(differenceIndex).getNumber() == differenceIndex) { // Поиск первого элемента идущего не
                if (++differenceIndex >= elements.size()) {                        // по порядку
                    offset = differenceIndex - 1;
                    break;
                }
            }

            if (differenceIndex >= elements.size()) { // Если поиск указывает на последний или дальше, то завершаем
                break;
            }

            int rabbitPointer = elements.get(differenceIndex).getNumber(); // Прыгающий указатель для замен.
            // Нестандартная ситуация. Оказалось, что значение в ячейке не входит в размер списка.
            if (rabbitPointer >= elements.size()) {
                // Ищем такую ячейку, что она хранит значение, которое должно быть тут.
                boolean changed = false;
                for (int i = 0; i < elements.size(); ++i) {
                    // Если такая ячейка нашлась, то в одной из следующих строк, происходит замена в этой точке
                    // на номер, которого точно нет в списке, а rabbitPointer, указываем на точку различия с
                    // нестандартной ситуацией, что закрывает её в последствии в основном проходе, и быстро выкидывает
                    // на новую итерацию.
                    if (elements.get(i).getNumber() == differenceIndex) {
                        rabbitPointer = differenceIndex;
                        differenceIndex = i;
                        changed = true;
                        break;
                    }
                }
                if (!changed) { // Если такой ячейки не нашлось, то просто заменяем на нужное значение
                    elements.get(differenceIndex).setupNumber(differenceIndex);
                    continue;
                }
            }

            // В первой точке различия ставим номер, которого гарантированно нет в списке.
            elements.get(differenceIndex).setupNumber(max.incrementAndGet());
            while (true) {
                // Запоминаем значение в следующей ячейке
                int nextRabbitPointer = elements.get(rabbitPointer).getNumber();
                // В лучшем случае, rabbitPointer указывает на ячейку, с номером, которых хранился в differentIndex
                // ячейке. Соответственно, так как его там уже нет, смело ставим в rabbitPointer ячейку правильное
                // значение.
                elements.get(rabbitPointer).setupNumber(rabbitPointer);
                // Если следующее значение указателя, больше чем возможно, то выходим с цикла и ищем новое различие
                // иначе прыгаем в следующую ячейку и повторяем операцию
                if (nextRabbitPointer >= elements.size()) {
                    break;
                } else {
                    rabbitPointer = nextRabbitPointer;
                }

            }
        } while (true);
    }

}
