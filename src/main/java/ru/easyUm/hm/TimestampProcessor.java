package ru.easyUm.hm;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;


public class TimestampProcessor {

    public static void main(String[] args) {
        List<LocalDateTime> timestamps = getTimestamps();

        //Создаем множество CompletableFuture для вычисления разницы между каждой парой временных меток
        AtomicLong totalTimeDiff = new AtomicLong(0L);
        CompletableFuture<Void> parallelCalculations = CompletableFuture.runAsync(() -> {
            List<CompletableFuture<BigInteger>> timeDiffFutures = new ArrayList<>();
            for (int i = 0; i < timestamps.size() - 1; i++) {
                LocalDateTime previousTimestamp = timestamps.get(i);
                LocalDateTime currentTimestamp = timestamps.get(i + 1);
                CompletableFuture<BigInteger> timeDiffFuture = CompletableFuture.supplyAsync(() -> {
                    Instant start = Instant.from(previousTimestamp.atZone(ZoneOffset.UTC));
                    Instant end = Instant.from(currentTimestamp.atZone(ZoneOffset.UTC));
                    long diff = Duration.between(start, end).abs().toSeconds();
                    return BigInteger.valueOf(diff);
                });
                timeDiffFutures.add(timeDiffFuture);
            }

            CompletableFuture.allOf(timeDiffFutures.toArray(new CompletableFuture[0])).join();

            //Объединить результаты в одно целое и вычислить среднюю разница
            for (CompletableFuture<BigInteger> future : timeDiffFutures) {
                totalTimeDiff.addAndGet(future.join().longValue());
            }
        });

        parallelCalculations.join();

        //Получаем результат в консоль
        double averageTimeDiff = totalTimeDiff.get() / timestamps.size();
        System.out.printf("Average time difference: %.2f%n", averageTimeDiff);
    }

    private static List<LocalDateTime> getTimestamps() {
        return List.of(LocalDateTime.MIN, LocalDateTime.MAX, LocalDateTime.now());
    }


















    /*
    предлагается написать программу, которая будет работать с набором временных меток и выполнять следующие задачи:

Чтение данных: Программа должна читать временные метки из метода-поставщика временных меток в формате LocalDateTime.

Выполнение расчетов: На основании полученных данных необходимо определить среднюю разницу временных меток.

Параллельные вычисления: Нужно реализовать механизм параллельных вычислений с использованием CompletableFuture.
Каждая пара временных мерок должна обрабатываться в отдельном потоке, чтобы минимизировать время выполнения программы.

Объединение результатов: После завершения всех вычислений результаты должны быть выведены в консоль
Отладка и оптимизация: В процессе нужно обратить внимание на потенциальные узкие места и оптимизировать код для повышения производительности.
     */
}