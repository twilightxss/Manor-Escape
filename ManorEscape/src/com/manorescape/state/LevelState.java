package com.manorescape.state;

import java.awt.Graphics2D;
import java.awt.Point;

import com.manorescape.tilemap.TileMapManager;
import com.manorescape.entity.Weapon;

public abstract class LevelState
{
    protected LevelManager lm;
    protected TileMapManager tmm;
    
    public LevelState(LevelManager lm)
    {
        this.lm = lm;
    }
    
    //These abstract methods will be implemented in subclasses
    public abstract void keyPressed(int keyCode);
    public abstract void keyReleased(int keyCode);
    public abstract void mouseClicked(Point mouseClick);
    public abstract void update();
    public abstract void draw(Graphics2D g);
}