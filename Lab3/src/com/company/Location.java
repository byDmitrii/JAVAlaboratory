package com.company;

/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
public class Location
{
    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;


    /** Creates a new location with the specified integer coordinates. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Creates a new location with coordinates (0, 0). **/
    public Location()
    {
        this(0, 0);
    }

    public boolean equals(Object obj){
        // Проверка приналежности obj к классу Location
        if (obj instanceof Location){
            Location other = (Location) obj;
            if (xCoord == other.xCoord && yCoord == other.yCoord){
                return true;
            }
        }
        return false;
    }
    public int hashCode(){
        int base = 17;
        base = 37 * base + xCoord;
        base = 37* base + yCoord;
        return base;
    }
}