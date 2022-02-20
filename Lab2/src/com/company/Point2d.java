package com.company;

public class Point2d {
    private double xPointCoord; //Координаты Х
    private double yPointCoord; //Координаты Y

    //Конструктор инициализации
    public Point2d(double x, double y) {
        xPointCoord = x;
        yPointCoord = y;
    }

    //Конструктор по умолчанию
    public Point2d() {
        //Вызов конструктора с двумя параметрами и определение источника
        this(0.0, 0.0);
    }

    //Возвращение координаты X
    public double getX() {
        return xPointCoord;
    }

    //Возвращение координаты Y
    public double getY() {
        return yPointCoord;
    }

    //Отдаем значение:
    //Установка значения координаты Х
    public void setX(double value) {
        xPointCoord = value;
    }

    //Установка значения координаты Y
    public void setY(double value) {
        yPointCoord = value;
    }

    //Вычисляем расстояние между двумя точками
    public double distanceTo(Point2d p) {
        double x2 = p.getX();
        double y2 = p.getY();

        double x = this.xPointCoord;
        double y = this.yPointCoord;

        double dist = Math.sqrt(Math.pow((x2 - x), 2) + Math.pow((y2 - y), 2));
        return dist;
    }

    public static boolean equalsPoints(Point2d p, Point2d p2) {
        double x = p.getX();
        double y = p.getY();

        double x2 = p2.getX();
        double y2 = p2.getY();

        if ((x == x2) && (y == y2))
            return true;
        else return false;
    }
}
