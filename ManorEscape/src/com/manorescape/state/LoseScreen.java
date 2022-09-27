package com.manorescape.state;

import com.manorescape.main.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

public class LoseScreen extends LevelState
{
//    private Button playGameButton;
//    private Button aboutButton;
    
    public LoseScreen(LevelManager lm)
    {
        super(lm);
        
//        Sprite playSprite = new Sprite(0);
//        playSprite.addFrame("/images/level2/buttons/start_1.png");
//        
//        playGameButton = new Button(300, 300);
//        playGameButton.setSprite(playSprite);
//        
//        Sprite abtSprite = new Sprite(0);
//        abtSprite.addFrame("/images/level2/buttons/about_1.png");
//        
//        aboutButton = new Button(300,375);
//        aboutButton.setSprite(abtSprite);
    }
    
    @Override
    public void keyPressed(int keyCode)
    {
        if(keyCode == KeyEvent.VK_SPACE)
        {
            System.exit(0);
        }
    }
    
    @Override
    public void keyReleased(int keyCode)
    {
        
    }
    
    @Override
    public void mouseClicked(Point mouseClick)
    {
//        if(playGameButton.clickedInside(mouseClick))
//        {
//            lm.nextState();
//        }
//        
//        if(aboutButton.clickedInside(mouseClick))
//        {
//            System.out.println("You clicked the about button");
//        }
    }
    
    @Override
    public void update()
    {
        
    }
    
    @Override
    public void draw(Graphics2D g)
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_WIDTH);
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        
        g.drawString("You Died", 300, 100);
        
        g.drawString("Press Space to exit", 300, 400);
        
//        playGameButton.draw(g);
//        aboutButton.draw(g);
    }
}
