package com.manorescape.entity;

import com.manorescape.tilemap.TileMapManager;

import java.awt.Graphics2D;
import java.util.HashMap;

public class Enemy extends GameObject
{
    private double xSpeed = 1;
    private double ySpeed = 1;
    
    private int damage;
    private int health;
    
    private boolean isAlive = true;
    
    private boolean recovering;
    private int recoverTimer = 0;
    
    private HashMap<String, Sprite> animatedSprite = new HashMap(); /// Store Animation
    
    public Enemy()
    {
        x = 0;
        y = 0;
        damage = 10;
        health = 50;
    }
    
    public Enemy(TileMapManager tmm, int x, int y, int dmg)
    {
        super(tmm);
        
        this.x = x;
        this.y = y;
        damage = dmg;
        health = 50;
    }
    
    @Override
    public void update()
    {
        checkTileMapCollision(x,y);
        //turn();
        
        if(recovering)
        {
            recoverTimer++;
            
            if(recoverTimer >= 30)
            {
                setRecovering(false);
                resetRecoverTimer();
            }
        }
    }
    
    public void turn(double px, double py)
    {
        if(dx == 0 || px > x)
        {
            MOVE_LEFT = false;
            moveRight(true);
        }
        
        if(dx == 0 || px < x)
        {
            MOVE_RIGHT = false;
            moveLeft(true);
        }
        
        if(dy == 0 || py > y)
        {
            MOVE_UP = false;
            moveDown(true);
        }
        
        if(dy == 0 || py < y)
        {
            MOVE_DOWN = false;
            moveUp(true);
        }
    }
    
    public void moveLeft(boolean move)
    {
        if(move == true)
        {
            MOVE_LEFT = true;
            STANDING = false;
            dx = -xSpeed;
        }
        else
        {
            MOVE_LEFT = false;
            STANDING = true;
            dx = 0;
        }
    }
    
    public void moveRight(boolean move)
    {
        if(move == true)
        {
            MOVE_RIGHT = true;
            STANDING = false;
            dx = xSpeed;
        }
        else
        {
            MOVE_RIGHT = false;
            STANDING = true;
            dx = 0;
        }
    }
    
    public void moveUp(boolean move)
    {
        if(move == true)
        {
            MOVE_UP = true;
            STANDING = false;
            dy = -ySpeed;
        }
        else
        {
            MOVE_UP = false;
            STANDING = true;
            dy = 0;
        }
    }
    
    public void moveDown(boolean move)
    {
        if(move == true)
        {
            MOVE_DOWN = true;
            STANDING = false;
            dy = ySpeed;
        }
        else
        {
            MOVE_DOWN = false;
            STANDING = true;
            dy = 0;
        }
    }
    
    public void collisionFurniture(Furniture[] furniture)
    {
        for(Furniture f: furniture)
        {
            if(collidesWith(f))
            {
                if(x > f.getX())
                {
                    dx = 0;
                    x += xSpeed;
                }
                else if(x < f.getX())
                {
                    dx = 0;
                    x -= xSpeed;
                }

                if(y > f.getY())
                {
                    dy = 0;
                    y += ySpeed;
                }
                else if(y < f.getY())
                {
                    dy = 0;
                    y -= ySpeed;
                }
            }
        }
    }
    
    public boolean isAlive()
    {
        return isAlive;
    }
    
    public void setAlive(boolean alive)
    {
        isAlive = alive;
    }
    
    public boolean isRecovering()
    {
        return recovering;
    }
    
    public void resetRecoverTimer()
    {
        recoverTimer = 0;
    }
    
    public void setRecovering(boolean recover)
    {
        recovering = recover;
    }
    
    public int getHealth()
    {
        return health;
    }
    
    public void setHealth(int newHealth)
    {
        health = newHealth;
    }
    
    public int getDamage()
    {
        return damage;
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        if(isAlive)
        {
            if(MOVE_RIGHT)
                sprite = animatedSprite.get("RIGHT");
            else if(MOVE_LEFT)
                sprite = animatedSprite.get("LEFT");
            else if(MOVE_UP)
                sprite = animatedSprite.get("UP");
            else if(MOVE_DOWN)
                sprite = animatedSprite.get("DOWN");
            else
                sprite = animatedSprite.get("STATIC");
        
        super.draw(g);
        } 
    }
    
    public void setAnimation(String direction, Sprite s)
    {
        animatedSprite.put(direction, s);
        
        if(direction.equals("STATIC"))
        {
            sprite = s;
            cHeight = sprite.getHeight();
            cWidth = sprite.getWidth();
        }
    }
}
