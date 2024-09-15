package ru.easyUm;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        Set<Integer> numbers = new HashSet<>();
        addRandomNumbers(numbers);

        CompletableFuture.supplyAsync(() -> {
            Set<Integer> updatedNumber = addRandomNumbers(numbers);
            System.out.println("Размер списка внутри supplyAsync: " + updatedNumber.size());
            return numbers;
        })/*.thenAcceptAsync(updatedNumbers -> {
            updatedNumbers.clear();
            System.out.println("Лист пуст, его размер: " + updatedNumbers.size());
        })*/.thenAccept(updatedNumbers -> {
            updatedNumbers.clear();
            System.out.println("Лист пуст, его размер: " + updatedNumbers.size());
        });

        System.out.println("Размер листа в конце main:" + numbers.size());
    }

    public static Set<Integer> addRandomNumbers(Set<Integer> numbers) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int number = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
            numbers.add(number);
        }
        return numbers;
    }
}