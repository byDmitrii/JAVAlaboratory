package com.company;
import java.awt.geom.Rectangle2D;

// Подкласс

public class Mandelbrot extends FractalGenerator
{
    // Константа с максимальным числом итераций
    public static final int MAX_ITERATIONS = 2000;

    /* Метод позволяет генератору фракталов определить наиболее "интересную область"
    комплексной области конкретного фрактала */
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    //
    public int numIterations(double x, double y)
    {
        int iteration = 0;
        ComplexNums cxnum = new ComplexNums(x, y);
        while (iteration < MAX_ITERATIONS && ((cxnum.Zreal * cxnum.Zreal) + (cxnum.Zimaginary * cxnum.Zimaginary)) < 4)
        {
            cxnum.Iteration();
            iteration++;
        }
        // Если достигнут максимум итераций , возвращам -1
        if (iteration == MAX_ITERATIONS){
            return -1;
        }
        else {
            return iteration;
        }
    }
}