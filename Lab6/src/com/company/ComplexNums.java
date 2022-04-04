package com.company;


//Класс для вычисления итераций преобразования комплексных чисел

public class ComplexNums {
    private double x;
    private double y;
    public double Zreal;
    public double Zimaginary;


    //Конструктор значений по умолчанию

    public ComplexNums(double x, double y) {
        this.x = x;
        this.y = y;
        this.Zreal = 0;
        this.Zimaginary = 0;
    }

    //Методы, осуществляющий подсчёт

    public void IterationMandelbrot() {
        double Zrealupdated = (Zreal * Zreal) - (Zimaginary * Zimaginary) + x;
        double Zimaginaryupdated = 2 * Zreal * Zimaginary + y;
        Zreal = Zrealupdated;
        Zimaginary = Zimaginaryupdated;
    }
    public void IterationTricorn() {
        double Zrealupdated = (Zreal * Zreal) - (Zimaginary * Zimaginary) + x;
        double Zimaginaryupdated = -2 * Zreal * Zimaginary + y;
        Zreal = Zrealupdated;
        Zimaginary = Zimaginaryupdated;
    }
    public void IterationBShip() {
        double Zrealupdated = (Zreal * Zreal) - (Zimaginary * Zimaginary) + x;
        double Zimaginaryupdated = Math.abs(2 * Zreal * Zimaginary) + y;
        Zreal = Zrealupdated;
        Zimaginary = Zimaginaryupdated;
    }
}