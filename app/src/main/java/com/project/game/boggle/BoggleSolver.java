package com.project.game.boggle;

/**
 * Created by Muna on 2/7/16.
 */

import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BoggleSolver extends AppCompatActivity {

    public static ArrayList<String> boggleWordList = new ArrayList<String>();

    static String[][] Board;
    public static Dictionary dictionary;


    public static void setBoard(List<Character> dice)
    {

        Board = new String[][]{{dice.get(0).toString(), dice.get(1).toString(), dice.get(2).toString(), dice.get(3).toString()},
                {dice.get(4).toString(), dice.get(5).toString(), dice.get(6).toString(), dice.get(7).toString()},
                {dice.get(8).toString(), dice.get(9).toString(), dice.get(10).toString(), dice.get(11).toString()},
                {dice.get(12).toString(), dice.get(13).toString(), dice.get(14).toString(), dice.get(15).toString()}};

    }


    private static String getLetter(int x, int y)
    {
        return Board[x][y];
    }


    public static void boggleWordListSearch(Dictionary dict) {
//initialize the board search path
        int[][] searchBoard = {{0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}};



        dictionary = dict;

        //call the boggleWordSearch function for every position on the board
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                boggleWordSearch("", j, i, searchBoard);
            }
        }

        // TODO [john] create popup window for solver
        for (int i = 0; i < boggleWordList.size(); i++) {
            String value = boggleWordList.get(i);
            System.out.println(value);
        }
    }


    /*
     * Recursive function that starts at one point in the grid and finds all
	 * words that can be highlighted starting at that point
	 */
    public static void boggleWordSearch(String word, int x, int y, int[][] path) {


        int[][] newPath = new int[4][4];
        copyPath(path, newPath);
        newPath[y][x] = 1;
        String newWord = word + getLetter(x, y).toLowerCase();



        if (dictionary.isReal(newWord)) {
            if (!boggleWordList.contains(newWord)) {
                boggleWordList.add(newWord);
            }

            ArrayList<GridPoint> movesList = nextWord(x, y, newPath);
            if (!(movesList.isEmpty())) {
                for (int i = 0; i < movesList.size(); ++i) {
                    GridPoint next = movesList.get(i);
                    boggleWordSearch(newWord, next.x, next.y, newPath);
                }
            }


        } else if (dictionary.isPartOfAWord(newWord)) {

            ArrayList<GridPoint> movesList = nextWord(x, y, newPath);
            if (!(movesList.isEmpty())) {
                for (int i = 0; i < movesList.size(); ++i) {
                    GridPoint next = movesList.get(i);
                    boggleWordSearch(newWord, next.x, next.y, newPath);
                }

            }
        }


    }


    /*
 * Uses the grid position and a 4X4 int array representing the path
 * already taken to find all possible grid positions the search
 * algorithm can take from the given position
 */
    private static ArrayList<GridPoint> nextWord(int x, int y, int[][] path) {

        ArrayList<GridPoint> moves = new ArrayList<GridPoint>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                int mx = x + j - 1;
                int my = y + i - 1;
                if ((mx >= 0 && mx < 4) && (my >= 0 && my < 4)) {
                    if (path[my][mx] == 0) {
                        GridPoint newMove = new GridPoint(mx, my);
                        moves.add(newMove);
                    }
                }
            }
        }
        return moves;
    }


    /*
         * preforms a deep copy on a 4X4 int array representing the path
         * the search algorithm takes through the boggle board
         */
    private static void copyPath(int [][] path, int [][] newPath) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                newPath[i][j] = path[i][j];
            }
        }
    }


}
