package com.manorescape.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Sprite
{
    private int stepCount;
    private int animationSpeed;
    private int currentFrame;
    private int numberOfFrames;
    private boolean isAnimated;
    private ArrayList<BufferedImage> animation;
    private int width;
    private int height;
       
    public Sprite(int animspeed)
    {
        stepCount = 0;
        animationSpeed = animspeed;
        currentFrame = 0;
        numberOfFrames = 0;
        isAnimated = false;
        animation = new ArrayList();
    }
    
    public void addFrame(BufferedImage frame)
    {
        if(frame != null)
        {
            animation.add(frame);
            numberOfFrames = animation.size();
            
            if(numberOfFrames > 1)
                isAnimated = true;                  
        }        
        
        update();
    }
    
    public void addFrame(String frameURI)
    {   
        BufferedImage tempImage = null;
        
        try
        {
            tempImage = ImageIO.read(getClass().getResourceAsStream(frameURI));
        }catch(IOException ex)
        {
            System.err.println("Error adding frame to animation");
            System.err.println("URI Provided: " + frameURI);
        }
        
        animation.add(tempImage);
        numberOfFrames = animation.size();
        
        if(numberOfFrames > 1)
            isAnimated = true;
    }
    
    public BufferedImage getSprite()
    {
        return isAnimated ? getNextFrame() : animation.get(0);
    }
    
    private void update()
    {
        width = animation.get(0).getWidth();
        height = animation.get(0).getHeight();
    }
    
    private BufferedImage getNextFrame()
    {
        BufferedImage frame = animation.get(currentFrame);
        
        stepCount++;
        
        if(stepCount % animationSpeed == 0)
            currentFrame++;
        
        if(currentFrame == numberOfFrames)
            currentFrame = 0;
        
        return frame;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    { 
        return height;
    }
}
