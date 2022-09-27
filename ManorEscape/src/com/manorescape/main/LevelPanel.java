package com.manorescape.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import com.manorescape.state.LevelManager;

/**
 *
 * @author James
 */
public class LevelPanel extends JPanel implements Runnable, KeyListener, MouseListener
{
    // Refactored - changed name to reflect that this is the size of the panel
    public static final int PANEL_WIDTH =  Game.WINDOW_WIDTH;// - 10;
    public static final int PANEL_HEIGHT = Game.WINDOW_HEIGHT;// - 10;
    
    private final double FPS = 60; // Number of frames per second we want to generate
    private final double TARGET_UPDATE_TIME = 1000/FPS; // Number of milliseconds to elapse between updates
  
    private BufferedImage screenBuffer; // Off screen image - draw into this and copy to the screen
    private Graphics2D graphics; // Graphics context for drawing into the off screen image
    private Thread gameLoop = null; // Thread - this allows us to run the game level in a separate thread
          
    //Game States
    private volatile boolean isRunning;
    private volatile boolean isPaused;
    
    // Level manager object
    LevelManager lm;  
    
    public LevelPanel()
    {
        super();
        
        initPanel();
        
        //default states
        isRunning = true;
        isPaused = false;
        
        lm = new LevelManager();
    }
   
    /**
     * Initialises the Panel's Swing properties
     */
    private void initPanel()
    {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        requestFocus();
    }
    
    /**
     * Initialises the off screen graphics drawing context
     * Everything drawn off screen is drawn to an image - the graphics context
     * is taken from the image and assigned to a context that is passed to
     * relevant objects in the game.
     */
    private void initGraphics()
    {
        screenBuffer = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D) screenBuffer.getGraphics();
    }

    /**
     * This method starts the main game loop in a separate thread
     * the thread is stored as an attribute so that it can be exposed through
     * the public interface if appropriate.
     */
    public void startGame()
    {
        initGraphics();
        
        gameLoop = new Thread(this);
        gameLoop.start();
    }
    
    /**
     * This method provides the main game loop - the game continues while it is running
     * 
     */
    private void gameLoop()
    {
        double startTime;
        double finishTime;
        double deltaT;
        double waitT;
                
       //Simple game loop implementation - should even out display to 60 FPS
       //
        while(isRunning)
        {
            // Record the start of the update process in nanoseconds
            startTime = System.nanoTime();
            
            if(!isPaused)
            {
                // Level update and redraw
                lm.update();
                lm.draw(graphics);
                // Repaint the screen
            
                repaint();
                //Record the end of  the update process
                finishTime = System.nanoTime();
                //Find the amount of time taken
                deltaT = finishTime - startTime;
                // frames per second calculation - 
                //System.out.println("Frame drawing time: " + Math.round(drawTime));                           
                //The Thread wait time is the Target time - the deltaT converted to milliseconds
                waitT = TARGET_UPDATE_TIME - deltaT / 1000000;
                // If we have a very small wait time, then set to 5 as a minimum
                if(waitT < 5)
                    waitT = 5;               
                
                //System.out.println("Wait: " + waitT);
                
                // Finally - cause the thread to pause for the wait time
                // This operation can cause an exception so we enclose in
                // a Try..Catch block
                try
                {
                    Thread.sleep((long)waitT);
                }catch(InterruptedException e)
                { 
                    e.printStackTrace();
                }
            }
            requestFocus();
        }
    }
    
    /**
     * This method starts the process of updating the level by calling
     * the level manager's update method
     */
    public void levelUpdate()
    {
        lm.update();
    }
    
    /**
     * This method starts the process of off screen drawing by calling the level
     * manager's updateScreenBuffer method
     */
    public void draw()
    {
        lm.draw(graphics);
    }
    
    /**
     * Draw the game on screen - at this stage all that is required is to
     * copy the off screen buffer - the image on to the panel using drawImage
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        g.drawImage(screenBuffer,0,0, PANEL_WIDTH, PANEL_HEIGHT, null );
    }
    
    @Override
    public void keyTyped(KeyEvent e) 
    {

    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        /**
         * Catch a pause keypress
         */        
       if(e.getKeyCode() == KeyEvent.VK_P)
        {
            isPaused = isPaused == false;
            System.out.println("Paused...");       
        }
       
       if(e.getKeyCode() == KeyEvent.VK_Q)
       {
           System.out.println("Quit the level or  the game");
       }
       
       lm.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        lm.keyReleased(e.getKeyCode());
    }

    @Override
    public void run() 
    {
        gameLoop();
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        lm.clicked(e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
        
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
       
    }
}
