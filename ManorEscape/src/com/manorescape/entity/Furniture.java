package com.manorescape.entity;

import com.manorescape.tilemap.TileMapManager;

import java.awt.Graphics2D;
import java.util.HashMap;

public class Furniture extends GameObject
{
    private HashMap<String, Sprite> animatedSprite = new HashMap(); /// Store Animation
    
    private String type;
    
    public Furniture()
    {
        super();
        
        x = 0;
        y = 0;
        
        type = null;
    }
    
    public Furniture(TileMapManager tmm, int x, int y, String type)
    {
        super(tmm);
        
        this.x = x;
        this.y = y;
        
        this.type = type;
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
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getType()
    {
        return type;
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
