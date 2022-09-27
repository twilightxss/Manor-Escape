package com.manorescape.position;

public class Vector2D 
{
    double x;
    double y;
    double dx;
    double dy;
    
    public Vector2D()
    {
        
    }
    
    public void addVector(Vector2D positionVector)
    {
        this.x += positionVector.getX();
        this.y += positionVector.getY();
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public double getX()
    {
        return x;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public double getY()
    {
        return this.y;
    }
    
    public void setDisplacementX(double dx)
    {
        this.dx = dx;
    }
    
    public double getDisplacementX()
    {
        return dx;
    }
    
    public void setDisplacementY(double dy)
    {
        this.dy = dy;
    }
    
    public double getDisplacementY()
    {
        return this.dy;
    }
    
}