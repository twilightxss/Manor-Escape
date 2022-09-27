package com.manorescape.state;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;

import com.manorescape.main.LevelPanel;
import com.manorescape.tilemap.TileMapManager;
import com.manorescape.tilemap.TileMap;
import com.manorescape.entity.Player;
import com.manorescape.entity.Sprite;
import com.manorescape.sprite.SpriteLoader;
import com.manorescape.entity.Weapon;
import com.manorescape.entity.Enemy;
import com.manorescape.entity.Furniture;

public class DiningRoom extends LevelState
{
    private BufferedImage background;
    private Player p;
    private Weapon w;
    private Enemy e;
    
    private Furniture furniture[];
    private final int FURNITURE_NUMBER = 6;
    private final int xCoords[] = {250, 314, 378, 442, 275, 425};
    private final int yCoords[] = {300, 300, 300, 300, 225, 225};
    private final String furnitureType[] = {"table", "table" , "table", "table", "table", "table"};
    
    private TileMapManager tmm;
    private TileMap tm;
    
    public DiningRoom(LevelManager lm)
    {
        super(lm);
        tmm = new TileMapManager();
        
        p = new Player(tmm, 100, 300);
        w = new Weapon(tmm, p.getX(), p.getY(), 10, true); 
        e = new Enemy(tmm, 600, 150, 10);
        furniture = new Furniture[FURNITURE_NUMBER];
        
        for(int i = 0; i < furniture.length; i++)
        {
            furniture[i] = new Furniture(tmm, xCoords[i], yCoords[i], furnitureType[i]);
        }
        
        loadPlayerResources();
        loadTreasureResources();
        loadEnemyResources();
        loadFurnitureResources();
        init();
    }
    
    public void loadPlayerResources()
    {
        Sprite s;
        SpriteLoader sp = new SpriteLoader("/images/player");
        
        s = sp.loadFileSequence("protag_right", 1, "png");
        p.setAnimation("STATIC", s);
        
        s = sp.loadFileSequence("protag_left", 1, "png");
        p.setAnimation("STATIC_LEFT", s);
        
        s = sp.loadFileSequence("protag_back", 1, "png");
        p.setAnimation("STATIC_UP", s);
        
        s = sp.loadFileSequence("protag_front", 1, "png");
        p.setAnimation("STATIC_DOWN", s);
        
        s = sp.loadFileSequence("protag_right", 4, "png");
        p.setAnimation("RIGHT", s);
        
        s = sp.loadFileSequence("protag_left", 4, "png");
        p.setAnimation("LEFT", s);
        
        s = sp.loadFileSequence("protag_back", 4, "png");
        p.setAnimation("UP", s);
        
        s = sp.loadFileSequence("protag_front", 4, "png");
        p.setAnimation("DOWN", s);
    }
    
    public void loadTreasureResources()
    {
        Sprite s;
        SpriteLoader sp = new SpriteLoader("/images/collectibles");
        
        s = sp.loadFileSequence("weapon", 1, "png");
        w.setAnimation("STATIC", s);
        
        p.setWeapon(w);
    }
    
    public void loadEnemyResources()
    {
        Sprite s;
        SpriteLoader sp = new SpriteLoader("/images/monsters");
        
        s = sp.loadFileSequence("slime", 1, "png");
        e.setAnimation("STATIC", s);
        
        s = sp.loadFileSequence("slime", 3, "png");
        e.setAnimation("RIGHT", s);
        
        s = sp.loadFileSequence("slime", 3, "png");
        e.setAnimation("LEFT", s);
        
        s = sp.loadFileSequence("slime", 3, "png");
        e.setAnimation("UP", s);
        
        s = sp.loadFileSequence("slime", 3, "png");
        e.setAnimation("DOWN", s);
    }
    
    public void loadFurnitureResources()
    {
        for(int i = 0; i < furniture.length; i++)
        {
            switch(furniture[i].getType())
            {
                case "table":
                    Sprite s;
                    SpriteLoader sp = new SpriteLoader("/images/furniture");
        
                    s = sp.loadFileSequence("table", 1, "png");
                    furniture[i].setAnimation("STATIC", s);
                    break;
                default:
                    break;
            }
        }
    }
    
    public void init()
    {
        try
        {
            background = ImageIO.read(getClass().getResourceAsStream("/images/temp_bg.png"));
        }catch(Exception EX)
            {
                System.out.println("Error loading level 2 background image");
            }
        
        System.out.println("Dining Room loaded");
    }

    @Override
    public void keyPressed(int keyCode)
    {
        if(keyCode == KeyEvent.VK_A)
        {
            p.moveLeft(true);
        }
        
        if(keyCode == KeyEvent.VK_D)
        {
            p.moveRight(true);
        }
        
        if(keyCode == KeyEvent.VK_W)
        {
            p.moveUp(true);
        }
        
        if(keyCode == KeyEvent.VK_S)
        {
            p.moveDown(true);
        }
        
        if(keyCode == KeyEvent.VK_SPACE)
        {
            p.attack();
        }
        
        if(keyCode == KeyEvent.VK_O)
        {
            lm.nextState();
        }
        
        if(keyCode == KeyEvent.VK_I)
        {
            lm.previousState();
        }
    }

    @Override
    public void keyReleased(int keyCode)
    {
        if(keyCode == KeyEvent.VK_A)
        {
            p.moveLeft(false);
        }
        
        if(keyCode == KeyEvent.VK_D)
        {
            p.moveRight(false);
        }
        
        if(keyCode == KeyEvent.VK_W)
        {
            p.moveUp(false);
        }
        
        if(keyCode == KeyEvent.VK_S)
        {
            p.moveDown(false);
        }
    }

    @Override
    public void mouseClicked(Point mouseClick)
    {
        
    }

    @Override
    public void update()
    {
        p.update();
        p.collisionEnemy(e);
        p.collisionWeapon(w);
        p.collisionFurniture(furniture);
        
        w.update();
        w.collisionEnemy(e);

        e.update();
        e.turn(p.getX(), p.getY());
        e.collisionFurniture(furniture);
        
        lose();
        
        tmm.setCameraPosition(LevelPanel.PANEL_WIDTH/2 - p.getX(), LevelPanel.PANEL_HEIGHT/2 - p.getY());
    }
    
    public void lose()
    {
        if(p.getHealth() <= 0)
        {
            lm.goToState(lm.LOSE_SCREEN);
        }
    }

    @Override
    public void draw(Graphics2D g)
    {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, LevelPanel.PANEL_WIDTH, LevelPanel.PANEL_WIDTH);
        
        Graphics2D g2d = (Graphics2D) g;
        

        //draw tilemap
        tmm.draw(g);
        
        g2d.drawImage(background, 0,0, null);
        
        // draw entities
        p.draw(g);
        w.draw(g);
        
        for(Furniture f: furniture)
        {
            f.draw(g);
        }
        
        e.draw(g);

    }
    
    public void setPlayer(int prevHealth, Weapon prevWeapon)
    {
        p.setHealth(prevHealth);
        p.setWeapon(prevWeapon);
    }
    
    public int getPlayerHP()
    {
        return p.getHealth();
    }
    
    public Weapon getPlayerWeapon()
    {
        return p.getWeapon();
    }
}
