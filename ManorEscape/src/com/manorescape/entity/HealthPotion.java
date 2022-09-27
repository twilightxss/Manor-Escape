package com.manorescape.entity;

import com.manorescape.tilemap.TileMapManager;

import java.awt.Graphics2D;
import java.util.HashMap;

public class HealthPotion extends GameObject
{
    private boolean isCollected = false;
    
    private int value;
    
    private HashMap<String, Sprite> animatedSprite = new HashMap(); /// Store Animation
    
    public HealthPotion()
    {
        super();
        
        x = 0;
        y = 0;
        
        value = 10;
    }
    
    public HealthPotion(TileMapManager tmm, int x, int y, int val)
    {
        super(tmm);
        
        this.x = x;
        this.y = y;
        
        value = val;
    }
    
    @Override
    public void update()
    {
        
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        if(!isCollected)
        {
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
    
    public void setCollected(boolean collected)
    {
        isCollected = collected;
    }
    
    public boolean getCollected()
    {
        return isCollected;
    }
    
    public int getValue()
    {
        return value;
    }
}
