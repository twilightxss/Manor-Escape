package com.manorescape.entity;

import com.manorescape.tilemap.TileMapManager;

import java.awt.Graphics2D;
import java.util.HashMap;

public class Weapon extends GameObject
{
    private int damage;
    private String name;
    
    private boolean isCollected;
    private boolean attacking;
    
    private int attackTimer;
    
    private HashMap<String, Sprite> animatedSprite = new HashMap(); /// Store Animation
    
    public Weapon()
    {
        super();
        
        damage = 1;
        name = "Broken Sword";
        
        isCollected = false;
        
        x = 0;
        y = 0;
    }
    
    public Weapon(TileMapManager tmm, double x, double y, int dmg)
    {
        super(tmm);
        
        damage = dmg;
        name = "Cooler Sword";
        
        isCollected = false;
        
        this.x = x;
        this.y = y;
    }
    
    public Weapon(TileMapManager tmm, double x, double y, int dmg, boolean collected)
    {
        super(tmm);
        
        damage = dmg;
        name = "Cooler Sword";
        
        this.x = x;
        this.y = y;
        
        isCollected = collected;
    }
    
    @Override
    public void update()
    {
        if(attacking)
        {
            attackTimer++;
            
            if(attackTimer == 25)
            {
                attacking = false;
            }
        }
    }
    
    public void collisionEnemy(Enemy e)
    {
        if(collidesWith(e) && e.isAlive() && !e.isRecovering())
        {
            if(isCollected && attacking)
            {
                System.out.println("Hit enemy for " + damage + " damage");
                e.setHealth(e.getHealth()-damage);
                e.setRecovering(true);

                if(e.getHealth() <= 0)
                    e.setAlive(false);
            }
        }
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        if(!isCollected)
        {
            super.draw(g);
        }
        
        if(isCollected && attacking)
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
    
    public int getDamage()
    {
        return damage;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setCollected(boolean collected)
    {
        isCollected = collected;
    }
    
    public boolean getCollected()
    {
        return isCollected;
    }
    
    public void setAttacking(boolean attack)
    {
        attacking = attack;
    }
    
    public void resetAttackTimer()
    {
        attackTimer = 0;
    }
}
