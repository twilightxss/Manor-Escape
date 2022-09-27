package com.manorescape.main;

import javax.swing.JFrame;

/**
 * @author James
 */
public class Game
{ 
    public static final int WINDOW_WIDTH = 783; // Make this public so that other parts of the game can access
    public static final int WINDOW_HEIGHT = 676; // Make this public so that other parts of the game can access
    private static final String TITLE = "Manor Escape";

    private JFrame mainWindow;
    private LevelPanel lp;
    
    public Game()
    {       
        initGame();
        initComponents();
        initWindow();  
    }
    
    /**
     * Initialise the game related objects
     */
    private void initGame()
    {       
        lp = new LevelPanel();
        mainWindow = new JFrame();
    }
    
    /**
     * Initialise any other GUI components such as buttons or menus
     */
    private void initComponents()
    {  
        mainWindow.add(lp);
    }
    
    private void initWindow()
    {
        mainWindow.setTitle(Game.TITLE);
        mainWindow.setSize(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
    }
    
    public void start()
    {
        lp.startGame();
    }
    
    public static void main(String[] args) 
    {
        Game g = new Game();
        g.start();
    }
}