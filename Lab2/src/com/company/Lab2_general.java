package com.company;
import java.util.Scanner;

public class Lab2_general {

    public static void main(String[] args) {
        Point3d p = null, p1 = null, p2 = null;

        char name_coordinates[] = {'x', 'y', 'z'};
        int number_coordinates[] = new int[3];

        Scanner inp = new Scanner(System.in);
        System.out.println("Введите координаты трёх точек:");

        for(int i=1; i<4; i++){
            for (int j=0; j<3; j++) {
                System.out.println(i + ":" + name_coordinates[j]);
                number_coordinates[j] = inp.nextInt();
            }
            switch(i){
                case 1:
                    p = new Point3d(number_coordinates[0],number_coordinates[1],number_coordinates[2]);
                    break;
                case 2:
                    p1 = new Point3d(number_coordinates[0],number_coordinates[1],number_coordinates[2]);
                    break;
                case 3:
                    p2 = new Point3d(number_coordinates[0],number_coordinates[1],number_coordinates[2]);
                    break;
            }
        }
        if (Point3d.equalsPoints(p, p1) || Point3d.equalsPoints(p2, p1) || Point3d.equalsPoints(p, p2)){
            System.out.println("Вы ввели одинаковые координаты (данные некорректны)");
        }
        else {
            String result = String.format("%.2f", computeArea(p, p1, p2));
            System.out.printf("Площадь треугольника = " + result);
        }
    }

    //Для вычесления площади треугольника, образованного тремя точками
    public static double computeArea(Point3d p, Point3d p2, Point3d p3){

        //вычисляем стороны треугольника
        double a = p.distanceTo(p2);
        double b = p2.distanceTo(p3);
        double c = p3.distanceTo(p);

        //формула полупериметра
        double semi_per = (a + b + c) / 2.;
        //формула площаль Герона
        double square_herons = Math.sqrt((semi_per * (semi_per - a) * (semi_per - b) * (semi_per - c)));

        return square_herons;
    }
}
