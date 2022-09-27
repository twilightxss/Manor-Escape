package com.manorescape.entity;

import java.awt.Graphics2D;
import java.util.HashMap;

import com.manorescape.tilemap.TileMapManager;

public class Player extends GameObject
{
    // Attributes
    private int health;
    private final int healthCap;
    private int attack;
    private double ySpeed = 3;
    private double xSpeed = 3;
    
    private boolean recovering;
    private int recoverTimer = 0;
    
    private boolean hasKey = false;
    
    private Weapon weapon = new Weapon();
    
    private HashMap<String, Sprite> animatedSprite = new HashMap(); /// Store Animation

    // Default Player class constructor
    // Calls default constructor from GameObject
    public Player()
    {
        super();
        
        x = 200;
        y = 450;
        health = 10;
        healthCap = health;
    }
    
    // Overloaded Player class constructor
    // Calls overloaded constructor from GameObject
    public Player(TileMapManager tmm, double x, double y)
    {
        super(tmm);
        
        this.x = x;
        this.y = y;
        health = 10;
        healthCap = health;
        
        FACE_RIGHT = true;
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
    
    @Override
    public void update()
    {
        checkTileMapCollision(x,y);
        
        
        // post-hit grace period timer
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
    
    public void collisionEnemy(Enemy e)
    {
        if(intersects(e) && e.isAlive() && !recovering)
        {
            int health = getHealth() - 1;//e.getDamage();
            setHealth(health);
            
            System.out.println("Collided with Enemy");
            System.out.println("HP = " + getHealth());
            
            setRecovering(true);
        }
    }
    
    public void collisionWeapon(Weapon w)
    {
        if(collidesWith(w) && !w.getCollected())
        {
            System.out.println("Picked up the " + w.getName());
            setWeapon(w);
            w.setCollected(true);
        }
    }
    
    public void collisionKey(Key k)
    {
        if(collidesWith(k) && !k.getCollected())
        {
            System.out.println("Collected Key");
            k.setCollected(true);
            setKey(true);
        }
    }
    
    public void collisionHealthPotion(HealthPotion h)
    {
        if(collidesWith(h) && !h.getCollected())
        {
            if(health < healthCap)
            {
                System.out.println("Recovered health");
                setHealth(health + h.getValue());
                if(health > healthCap)
                    health = healthCap;
                h.setCollected(true);
            }
            else
            {
                System.out.println("Already Max Health!");
            }
        }
    }
    
    public void collisionFurniture(Furniture[] furniture)
    {
        for(Furniture f: furniture)
        {
            if(collidesWith(f))
            {
                if(dx > 0)
                {
                    dx = 0;
                    x -= xSpeed;
                }
                else if(dx < 0)
                {
                    dx = 0;
                    x += xSpeed;
                }

                if(dy > 0)
                {
                    dy = 0;
                    y -= ySpeed;
                }
                else if(dy < 0)
                {
                    dy = 0;
                    y += ySpeed;
                }
            }
        }
    }
    
    public void attack()
    {
        // Finds direction to place sword
        
        if(FACE_LEFT)
        {
            weapon.setX(x - cWidth);
            weapon.setY(y);
        }
        else if(FACE_RIGHT)
        {
            weapon.setX(x + cWidth);
            weapon.setY(y);
        }
        else if(FACE_DOWN)
        {
            weapon.setX(x);
            weapon.setY(y + cHeight);
        }
        else if(FACE_UP)
        {
            weapon.setX(x);
            weapon.setY(y - cHeight);
        }
        
        weapon.resetAttackTimer();
        weapon.setAttacking(true);
        dy = 0;
        dx = 0;
    }
    
    public void moveLeft(boolean move)
    {
        if(move == true)
        {
            MOVE_LEFT = true;
            STANDING = false;
            dx = -xSpeed;
            
            FACE_LEFT = true;
            FACE_RIGHT = false;
            FACE_UP = false;
            FACE_DOWN = false;
            
            weapon.setAttacking(false);
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
            
            FACE_LEFT = false;
            FACE_RIGHT = true;
            FACE_UP = false;
            FACE_DOWN = false;
            
            weapon.setAttacking(false);
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
            
            FACE_LEFT = false;
            FACE_RIGHT = false;
            FACE_UP = true;
            FACE_DOWN = false;
            
            weapon.setAttacking(false);
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
            
            FACE_LEFT = false;
            FACE_RIGHT = false;
            FACE_UP = false;
            FACE_DOWN = true;
            
            weapon.setAttacking(false);
        }
        else
        {
            MOVE_DOWN = false;
            STANDING = true;
            dy = 0;
        }
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        if(MOVE_RIGHT)
            sprite = animatedSprite.get("RIGHT");
        else if(MOVE_LEFT)
            sprite = animatedSprite.get("LEFT");
        else if(MOVE_UP)
            sprite = animatedSprite.get("UP");
        else if(MOVE_DOWN)
            sprite = animatedSprite.get("DOWN");
        else if(STANDING)
        {
            if(FACE_RIGHT)
                sprite = animatedSprite.get("STATIC");
            if(FACE_LEFT)
                sprite = animatedSprite.get("STATIC_LEFT");
            if(FACE_UP)
                sprite = animatedSprite.get("STATIC_UP");
            if(FACE_DOWN)
                sprite = animatedSprite.get("STATIC_DOWN");
        }
        else
            sprite = animatedSprite.get("STATIC");
        
        super.draw(g);
    }
    
    public void setHealth(int newHealth)
    {
        health = newHealth;
    }
    
    public int getHealth()
    {
        return health;
    }
    
    public void setRecovering(boolean recover)
    {
        recovering = recover;
    }
    
    public boolean isRecovering()
    {
        return recovering;
    }
    
    public void resetRecoverTimer()
    {
        recoverTimer = 0;
    }
    
    public Weapon getWeapon()
    {
        return weapon;
    }
    
    public void setWeapon(Weapon newWeapon)
    {
        weapon = newWeapon;
    }
    
    public int getDamage()
    {
        return weapon.getDamage();
    }
    
    public boolean hasKey()
    {
        return hasKey;
    }
    
    public void setKey(boolean key)
    {
        hasKey = key;
    }
}
