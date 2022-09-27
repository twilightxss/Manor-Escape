package com.manorescape.tilemap;

public class TileMap
{
    
    // Entrance Hall Map
    
    
    // Bedroom Map
    private final int[][] map2 = 
    {
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        {2, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
    };
    
    public TileMap()
    {
        
    }
    
    
    
    public int[][] getMap(int currentState)
    {
        switch(currentState)
        {
            case 0:
                return map2;
            case 1:
                return map2;
            default:
                return null;
        }
    }
}
