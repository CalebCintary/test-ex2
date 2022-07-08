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

    @Override
    public void assignNumbers(final List<IElement> elements) {
        AtomicInteger max = new AtomicInteger(elements.get(0).getNumber());
        elements.forEach(element -> max.set(Math.max(element.getNumber(), max.get())));


        do {
            int differenceIndex = 0;
            while (elements.get(differenceIndex).getNumber() == differenceIndex) {
                if (++differenceIndex >= elements.size()) {
                    break;
                }
            }

            if (differenceIndex >= elements.size()) {
                break;
            }

            int rabbitPointer = elements.get(differenceIndex).getNumber();
            elements.get(differenceIndex).setupNumber(max.incrementAndGet());
            while (true) {
                int nextRabbitPointer = elements.get(rabbitPointer).getNumber();
                elements.get(rabbitPointer).setupNumber(rabbitPointer);
                if (nextRabbitPointer >= elements.size()) {
                    break;
                } else {
                    rabbitPointer = nextRabbitPointer;
                }

            }
        } while (true);
    }

}
