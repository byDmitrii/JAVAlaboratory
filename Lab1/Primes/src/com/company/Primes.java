package com.company;
// Класс выводит те значения, которые метод IsPrime посчитал простыми
public class Primes {
    public static void main(String[] args) {
        for (int i = 2; i <= 100; i++) {
            if (IsPrime(i))
                System.out.println(i + " - простое число");
        }
    }
    // Метод определяет является ли аргумент простым числом или нет
    public static boolean IsPrime(int n){
        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;
        return true;
    }
}
