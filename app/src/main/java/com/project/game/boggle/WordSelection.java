package com.project.game.boggle;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Muna on 1/23/16.
 */
public class WordSelection {
    public static int currX = 0;
    public static int currY = 0;
    public static Map letterQueue = null;
    public static Map tileQueue = null;

    public static void initQueue() {
        if (letterQueue == null) {
            letterQueue = new LinkedHashMap<String, String>();
            tileQueue = new LinkedHashMap<String, View>();
        } else {
            letterQueue.clear();
            tileQueue.clear();
        }
    }

    public static Boolean validMove(TextView tile, Character letter) {
        CharSequence coordinates = tile.getHint();
        int nextX = Character.getNumericValue(coordinates.charAt(0));
        int nextY = Character.getNumericValue(coordinates.charAt(1));
        int moveX = Math.abs(nextX - currX);
        int moveY = Math.abs(nextY - currY);
        String key = "" + nextX + nextY;

        if (letterQueue.isEmpty()) {
            currX = nextX;
            currY = nextY;

            String word = Container.getInstance().getWord();

            if (word == null) {
                word = "" + letter;
            } else {
                word += letter;
            }

            Container.getInstance().setWord(word);
            letterQueue.put(key, letter);
            tileQueue.put(key, tile);
            return true;
        } else if ((moveX == 0) && (moveY == 0)) {
            unhighlight(tile, letter);
            letterQueue.remove(key);
            tileQueue.remove(key);

            if (letterQueue.isEmpty()) {
                return false;
            }

            Iterator letters = letterQueue.keySet().iterator();
            String coors = null;
            int prevX;
            int prevY;

            while (letters.hasNext()) {
                coors = letters.next().toString();
            }

            prevX = Character.getNumericValue(coors.charAt(0));
            prevY = Character.getNumericValue(coors.charAt(1));

            currX = prevX;
            currY = prevY;

            return false;
        } else if ((moveX < 2) && (moveY < 2) && !(letterQueue.containsKey(key))) {
            currX = nextX;
            currY = nextY;

            String word = Container.getInstance().getWord();

            if (word == null) {
                word = "" + letter;
            } else {
                word += letter;
            }

            Container.getInstance().setWord(word);
            letterQueue.put(key, letter);
            tileQueue.put(key, tile);
            return true;
        } else {
            return false;
        }
    }

    public static Boolean unhighlightAll() {
        if (!letterQueue.isEmpty()) {
            Iterator keys = letterQueue.keySet().iterator();
            Iterator letters = letterQueue.values().iterator();

            while (keys.hasNext()) {
                unhighlight((TextView) tileQueue.get(keys.next()), (Character) letters.next());
            }

            letterQueue.clear();
            tileQueue.clear();
        }

        return true;
    }

    public static void highlight(TextView textView, Character ch) {
        switch (ch.charValue()) {
            case 'A': 
                textView.setBackgroundResource(R.drawable.higha);
                break;
            case 'B':
                textView.setBackgroundResource(R.drawable.highb);
                break;
            case 'C':
                textView.setBackgroundResource(R.drawable.highc);
                break;
            case 'D':
                textView.setBackgroundResource(R.drawable.highd);
                break;
            case 'E':
                textView.setBackgroundResource(R.drawable.highe);
                break;
            case 'F':
                textView.setBackgroundResource(R.drawable.highf);
                break;
            case 'G':
                textView.setBackgroundResource(R.drawable.highg);
                break;
            case 'H':
                textView.setBackgroundResource(R.drawable.highh);
                break;
            case 'I':
                textView.setBackgroundResource(R.drawable.highi);
                break;
            case 'J':
                textView.setBackgroundResource(R.drawable.highj);
                break;
            case 'K':
                textView.setBackgroundResource(R.drawable.highk);
                break;
            case 'L':
                textView.setBackgroundResource(R.drawable.highl);
                break;
            case 'M':
                textView.setBackgroundResource(R.drawable.highm);
                break;
            case 'N':
                textView.setBackgroundResource(R.drawable.highn);
                break;
            case 'O':
                textView.setBackgroundResource(R.drawable.higho);
                break;
            case 'P':
                textView.setBackgroundResource(R.drawable.highp);
                break;
            case 'Q':
                textView.setBackgroundResource(R.drawable.highq);
                break;
            case 'R':
                textView.setBackgroundResource(R.drawable.highr);
                break;
            case 'S':
                textView.setBackgroundResource(R.drawable.highs);
                break;
            case 'T':
                textView.setBackgroundResource(R.drawable.hight);
                break;
            case 'U':
                textView.setBackgroundResource(R.drawable.highu);
                break;
            case 'V':
                textView.setBackgroundResource(R.drawable.highv);
                break;
            case 'W':
                textView.setBackgroundResource(R.drawable.highw);
                break;
            case 'X':
                textView.setBackgroundResource(R.drawable.highx);
                break;
            case 'Y':
                textView.setBackgroundResource(R.drawable.highy);
                break;
            case 'Z':
                textView.setBackgroundResource(R.drawable.highz);
                break;
        }
    }

    public static void unhighlight(TextView tv, Character alphabet) {
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
