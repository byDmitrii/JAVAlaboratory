package com.company;

/**
 * Класс для вычисления итераций преобразования комплексных чисел
 */
public class ComplexNums {
    private double x;
    private double y;
    public double Zreal;
    public double Zimaginary;

    /**
     * Конструктор, устанавливающий значения по умолчанию
     */
    public ComplexNums(double x, double y) {
        this.x = x;
        this.y = y;
        this.Zreal = 0;
        this.Zimaginary = 0;
    }

    /**
     * Метод, осуществляющий подсчёт
     */
    public void Iteration() {
        double Zrealupdated = (Zreal * Zreal) - (Zimaginary * Zimaginary) + x;
        double Zimaginaryupdated = 2 * Zreal * Zimaginary + y;
        Zreal = Zrealupdated;
        Zimaginary = Zimaginaryupdated;
    }
}