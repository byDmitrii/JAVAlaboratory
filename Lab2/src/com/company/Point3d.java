package com.company;
//Трехмерный класс точки в евклидовом пространстве
public class Point3d {
    private double xPointCoord; //Координаты Х
    private double yPointCoord; //Координаты Y
    private double zPointCoord; //Координаты Z
    //Конструктор инициализации
    public Point3d (double x, double y, double z){
        xPointCoord = x;
        yPointCoord = y;
        zPointCoord = z;
    }
    //Конструктор по умолчанию
    public Point3d(){
        //Вызов конструктора с двумя параметрами и определение источника
        this(0.0, 0.0, 0.0);
    }
    //Возвращение координаты X
    public double getX(){
        return xPointCoord;
    }
    //Возвращение координаты Y
    public double getY(){
        return yPointCoord;
    }
    //Возвращение координаты Z
    public double getZ(){
        return zPointCoord;
    }
    //Установка значения координаты Х
    public void setX(double value){
        xPointCoord = value;
    }
    //Установка значения координаты Y
    public void setY(double value){
        yPointCoord = value;
    }
    //Установка значения координаты Z
    public void setZ(double value){
        zPointCoord = value;
    }
    //Вычисляем расстояние между двумя точками
    public double distanceTo (Point3d p){
        double x2 = p.getX();
        double y2 = p.getY();
        double z2 = p.getZ();

        double x = this.xPointCoord;
        double y = this.yPointCoord;
        double z = this.zPointCoord;

        double dist = Math.sqrt(Math.pow((x2-x), 2) + Math.pow((y2-y), 2) + Math.pow((z2-z), 2));
        return dist;
    }

    public static boolean equalsPoints (Point3d p, Point3d p2){
        double x = p.getX();
        double y = p.getY();
        double z = p.getZ();

        double x2 = p2.getX();
        double y2 = p2.getY();
        double z2 = p2.getZ();

        if ((x == x2) && (y == y2) && (z == z2))
            return true;
        else return false;

    }
}
