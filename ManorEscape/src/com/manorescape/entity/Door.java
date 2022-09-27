package com.manorescape.entity;

import com.manorescape.tilemap.TileMapManager;

import java.awt.Graphics2D;
import java.util.HashMap;

public class Door extends GameObject
{
    private HashMap<String, Sprite> animatedSprite = new HashMap(); /// Store Animation
    
    public Door()
    {
        super();
        
        x = 0;
        y = 0;
    }
    
    public Door(TileMapManager tmm, int x, int y)
    {
        super(tmm);
        
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void update()
    {
        
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        super.draw(g);
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
