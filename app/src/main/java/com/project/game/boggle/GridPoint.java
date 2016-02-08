package com.project.game.boggle;

/**
 * Created by Muna on 2/7/16.
 */
public class GridPoint {

    //the X and Y positions
    public int x;
    public int y;


    /*
     * the default constructor
     */
    GridPoint(){
        x = -1;
        y = -1;
    }


    /*
     * the constructor which passes the X and Y positions
     */
    GridPoint(int i, int j){
        x = i;
        y = j;
    }
}
