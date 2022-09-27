/**
 * Game Object
 * 
 * Serves as a base class for characters and other game objects
 */
package com.manorescape.entity;
import com.manorescape.tilemap.TileMapManager;
import com.manorescape.tilemap.Tile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;

/**
 * Superclass for all objects that can take part in the game
 * 
 * @author James
 */
public class GameObject 
{    
    //Object X and Y Coordinates
    protected double x;
    protected double y;
    
   // Displacement from current X and Y
    protected double dx;
    protected double dy;
    
    // Collision box width and height
    // 
    protected int cWidth;
    protected int cHeight;
    
    //Collision directions
    // These values are set to true if a collision
    // is detected in any of the directions
    protected boolean cTopLeft;
    protected boolean cBottomRight;
    protected boolean cTopRight;
    protected boolean cBottomLeft;
    
    protected Sprite sprite;
      
    // Player States
    // Simple implementation of a state machine
    protected boolean STANDING;
    protected boolean MOVE_LEFT;
    protected boolean MOVE_RIGHT;
    protected boolean MOVE_UP;
    protected boolean MOVE_DOWN;
    protected boolean JUMP;
    protected boolean FALLING;
    
    protected boolean FACE_LEFT;
    protected boolean FACE_RIGHT;
    protected boolean FACE_UP;
    protected boolean FACE_DOWN;
    
    protected TileMapManager tmm;
    
    private BufferedImage image1 = null;
    private BufferedImage image2 = null;
    
    public GameObject()
    {
        this.tmm = null;
    }
            
    public GameObject(TileMapManager tmm)
    {
        this.tmm = tmm;
    }

    /**
     * This method is called to update the GameObject's state in relation to the game
     * 
     */
    public void update()
    {
        
    }
    
    public void getCorners(double x, double y)
    {
        int left = (int) (x - cWidth / 2) / TileMapManager.TILE_SIZE;
        int right = (int) (x + cWidth / 2 - 1) / TileMapManager.TILE_SIZE;
        int top = (int) (y - cHeight / 2 ) / TileMapManager.TILE_SIZE;
        int bottom = (int) (y + cHeight / 2 - 1) / TileMapManager.TILE_SIZE;
           
        int topLeft = tmm.getTileAt(top, left).getType();
        int topRight = tmm.getTileAt(top, right).getType();
        int bottomLeft = tmm.getTileAt(bottom, left).getType();
        int bottomRight = tmm.getTileAt(bottom, right).getType();
        
        cTopLeft = topLeft == Tile.TYPE_BLOCKED;
        cTopRight = topRight == Tile.TYPE_BLOCKED;
        cBottomLeft = bottomLeft == Tile.TYPE_BLOCKED;
        cBottomRight = bottomRight == Tile.TYPE_BLOCKED;
    }
   
    /**
     * This method will check the top and bottom right and left coordinates
     * of the sprite so that any collisions are flagged by the boolean collision
     * values
     * @param x
     * @param y 
     */
    public void checkTileMapCollision(double x, double y)
    {
        double checkX;
        double checkY;
        double tempX;
        double tempY;
        int currentRow;
        int currentCol;
        
        currentCol = (int) this.x / TileMapManager.TILE_SIZE;
        currentRow = (int) this.y / TileMapManager.TILE_SIZE;
        
        checkX = this.x + dx;
        checkY = this.y + dy;
       
        tempX = this.x;
        tempY = this.y;
 
        getCorners(this.x, checkY);
        
        if(dy < 0)
        {
            if(cTopRight || cTopLeft)
            {
                dy = 0;
                //System.out.println("Collision Top");
                tempY = currentRow * TileMapManager.TILE_SIZE + cHeight / 2;
            }else
                tempY += dy;
        }
        
        if(dy > 0)
        {
            if(cBottomLeft || cBottomRight)
            {
                //System.out.println("Collision Bottom");
                dy = 0;
                tempY = (currentRow + 1) * TileMapManager.TILE_SIZE - cHeight / 2;
                FALLING = false;
            }else
                tempY += dy;
        }
        
        getCorners(checkX, this.y); 
        
        if(dx < 0)
        {
            if(cTopLeft || cBottomLeft)
            {
                dx = 0;
                //System.out.println("Collision Left");
                tempX = currentCol * TileMapManager.TILE_SIZE + cWidth / 2;
            }else
                tempX += dx;
        }
           
        if(dx > 0)
        {
            if(cTopRight || cBottomRight)
            {
                dx = 0;
                //System.out.println("Collision Right");
                tempX = (currentCol + 1) * TileMapManager.TILE_SIZE - cWidth / 2;
            } else         
                tempX += dx;
        }
        
        if(!FALLING)
        {
            getCorners(x, checkY + 1);
            if(!cBottomLeft && !cBottomRight)
            {
                FALLING = true;
            }
        }
        
       this.x= tempX;        
       this.y= tempY; 

    }
        
    /**
     * This method determines if the current object has an intersection with another
     * gameobject.
     * 
     * @param obj
     * @return 
     */
    public boolean intersects(GameObject obj)
    {
        Rectangle r1 = getBounds(); // The current game object
        Rectangle r2 = obj.getBounds(); // The object we're checking
                
        return r1.intersects(r2);
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle((int)x, (int)y , cWidth, cHeight);
    }
        
    /**
     * Pass the graphics canvas from the GUI to the GameObject so that it 
     * can draw itself into the correct place.
     * Sub classes can override this if appropriate
     * @param g 
     */
    public void draw(Graphics2D g)
    {
        // Basic drawing - draw the sprite at current X and Y
        if(sprite != null)
            g.drawImage(sprite.getSprite(), (int)(x + tmm.getCameraX()) - (cWidth/2), (int)(y + tmm.getCameraY()) - (cHeight/2), null);
        
        /*if(image1!= null)
            g.drawImage(image1, 300, 10, null);
        
        if(image2 != null)
            g.drawImage(image2, 300, 10, null);*/
    }
        
    /**
     * Returns true if the game object intersects another game object
     * @param g
     * @return 
     */
    public boolean collidesWith(GameObject g)
    {     
        boolean collision = false;
        int x1, y1;
        int x2, y2;
        
        // Get the centre of each rectangle - measure the distance between the centrepoints
        x1 = (int) getBounds().getX() + (int)(getBounds().getWidth()/2);
        y1 = (int) getBounds().getY() + (int)(getBounds().getHeight()/2);
        
        x2 = (int) g.getBounds().getX() + (int) (getBounds().getWidth()/2);
        y2 = (int) g.getBounds().getY() + (int) (getBounds().getHeight()/2);
        
        /**
         * Pixel check is expensive, so only carry it out if the distance between sprites is less than 92
         * 
         * Replace this with an implementation of a quadtree
         */
        
        double x1x2 = Math.abs(x2 - x1);
        double y1y2 = Math.abs(y2 - y1);
        
        int distance = (int)Math.hypot(x1x2, y1y2);
        
        if(distance < 80) // TODO Change this value to something sprite related parameterise this value
        {
               collision = pixelCollisionCheck(g, g.getBounds(), getBounds());
        }
        
        //System.out.println("Collision: " + collision);
        
        return collision;
    }
    
    public boolean pixelCollisionCheck(GameObject other, Rectangle r1, Rectangle r2)
    {
        int topCornerX;
        int bottomCornerX;
        int topCornerY;
        int bottomCornerY;
        
        topCornerY = (r1.y > r2.y) ? r1.y : r2.y;
        bottomCornerY = ((r1.y + r1.height) > (r2.y + r2.height)) ? r1.y + r1.height : r2.y + r2.height;
        
        topCornerX = (r1.x > r2.x)? r1.x : r2.x;
        bottomCornerX = ((r1.width + r1.x) > (r2.width + r2.x)) ? r1.x + r1.height: r2.x + r2.height;
        
        int height = bottomCornerY - topCornerY;
        int width = bottomCornerX - topCornerX;
        
        int[] pixels1 = new int[width * height];
        int[] pixels2 = new int[width * height];
        
        PixelGrabber pg1 = new PixelGrabber(other.getSprite().getSprite(), topCornerX - (int)other.getX(), (int) topCornerY - (int) other.getY(), width, height, pixels1, 0, width);
        PixelGrabber pg2 = new PixelGrabber(getSprite().getSprite(), topCornerX - (int)getX(), (int) topCornerY - (int) getY(), width, height, pixels2, 0, width);
        
        try
        {
            pg1.grabPixels();
            pg2.grabPixels();            
        } catch(InterruptedException ex)
        {
            System.err.println("Error in Pixel Collision Check");
            System.err.println("Cannot Grab Pixels");
            System.err.println(ex.getMessage());
        }
        
        /*image1 = new BufferedImage(pg1.getWidth(), pg1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        image1.setRGB(0, 0, pg1.getWidth(), pg1.getHeight(), pixels1, 0, pg1.getWidth());
        
        image2 = new BufferedImage(pg1.getWidth(), pg1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        image2.setRGB(0, 0, pg2.getWidth(), pg2.getHeight(), pixels2, 0, pg1.getWidth());*/
        
        for(int i = 0; i < pixels1.length; i++)
        {
            int t1 = (pixels1[i] >>> 24) & 0xFF;
            int t2 = (pixels2[i] >>> 24) & 0xFF;
            
            if(t1 > 0 && t2 > 0)
                return true;
        }
        
        return false;
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;        
    }
    
    public void setX(double newX)
    {
        x = newX;
    }
    public void setY(double newY)
    {
        y = newY;
    }
    
    public Sprite getSprite()
    {
        return sprite;
    }
    
    public void setSprite(Sprite s)
    {
        sprite = s;
        
        cWidth = sprite.getWidth();
        cHeight = sprite.getHeight();
    }
}
