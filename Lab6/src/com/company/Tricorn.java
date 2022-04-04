package com.company;
import java.awt.geom.Rectangle2D;

// Подкласс

public class Tricorn extends FractalGenerator {
    // Константа с максимальным числом итераций
    public static final int MAX_ITERATIONS = 2000;

    /* Метод позволяет генератору фракталов определить наиболее "интересную область"
    комплексной области конкретного фрактала */
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }

    //
    public int numIterations(double x, double y)
    {
        /*
        Вычислить Zn = Zn-1 ^ 2 + c, где значения представляют собой комплексные числа, представленные
        по zreal и zimaginary, Z0 = 0, а c - особая точка в
        фрактале, который мы отображаем (заданный x и y)=> класс COMPLEXNUMS. Это повторяется
        до Z ^ 2> 4 (абсолютное значение Z больше 2) или максимум
        достигнуто количество итераций.
        */
        int iteration = 0;
        ComplexNums cnum = new ComplexNums(x, y);
        while (iteration < MAX_ITERATIONS && ((cnum.Zreal * cnum.Zreal) + (cnum.Zimaginary * cnum.Zimaginary)) < 4)
        {
            cnum.IterationTricorn();
            iteration += 1;
        }
        // Если достигнут максимум итераций , возвращам -1
        if (iteration == MAX_ITERATIONS){
            return -1;
        }
        else {
            return iteration;
        }
    }
    public String toString() {
        return "Tricorn";
    }
}