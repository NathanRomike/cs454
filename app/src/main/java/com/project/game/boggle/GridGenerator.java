package com.project.game.boggle;

/**
 * Created by Muna on 1/21/16.
 */


import android.widget.TextView;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class GridGenerator {

    public static List<Character> getRandomDice() {
        List<Character> dieList = new ArrayList<>(16);

        String[] strings = {
                "DEILRX", "DELRVY", "DISTTY", "EEGHNW", "EEINSU", "EHRTVW", "EIOSST", "ELRTTY",
                "HIMNUQ", "HLNNRZ", "AAEEGN", "ABBJOO", "ACHOPS", "AFFKPS", "AOOTTW", "CIMOTU",
        };
        Random r = new Random();
        int cube_side;

        for (int i = 0; i < 16; i++) {
            cube_side = r.nextInt(6); //j to select one side of each cube
            dieList.add(strings[i].toCharArray()[cube_side]);

        }
        //to shuffle the elements of a collection
        Collections.shuffle(dieList);

        return dieList;
    }

    public static void alphaImg(TextView tv, Character alphabet) {
        switch(alphabet.charValue()) {
            case 'A':
                tv.setBackgroundResource(R.drawable.grida);
                break;
            case 'B':
                tv.setBackgroundResource(R.drawable.gridb);
                break;
            case 'C':
                tv.setBackgroundResource(R.drawable.gridc);
                break;
            case 'D':
                tv.setBackgroundResource(R.drawable.gridd);
                break;
            case 'E':
                tv.setBackgroundResource(R.drawable.gride);
                break;
            case 'F':
                tv.setBackgroundResource(R.drawable.gridf);
                break;
            case 'G':
                tv.setBackgroundResource(R.drawable.gridg);
                break;
            case 'H':
                tv.setBackgroundResource(R.drawable.gridh);
                break;
            case 'I':
                tv.setBackgroundResource(R.drawable.gridi);
                break;
            case 'J':
                tv.setBackgroundResource(R.drawable.gridj);
                break;
            case 'K':
                tv.setBackgroundResource(R.drawable.gridk);
                break;
            case 'L':
                tv.setBackgroundResource(R.drawable.gridl);
                break;
            case 'M':
                tv.setBackgroundResource(R.drawable.gridm);
                break;
            case 'N':
                tv.setBackgroundResource(R.drawable.gridn);
                break;
            case 'O':
                tv.setBackgroundResource(R.drawable.grido);
                break;
            case 'P':
                tv.setBackgroundResource(R.drawable.gridp);
                break;
            case 'Q':
                tv.setBackgroundResource(R.drawable.gridq);
                break;
            case 'R':
                tv.setBackgroundResource(R.drawable.gridr);
                break;
            case 'S':
                tv.setBackgroundResource(R.drawable.grids);
                break;
            case 'T':
                tv.setBackgroundResource(R.drawable.gridt);
                break;
            case 'U':
                tv.setBackgroundResource(R.drawable.gridu);
                break;
            case 'V':
                tv.setBackgroundResource(R.drawable.gridv);
                break;
            case 'W':
                tv.setBackgroundResource(R.drawable.gridw);
                break;
            case 'X':
                tv.setBackgroundResource(R.drawable.gridx);
                break;
            case 'Y':
                tv.setBackgroundResource(R.drawable.gridy);
                break;
            case 'Z':
                tv.setBackgroundResource(R.drawable.gridz);
                break;
        }
    }
}
