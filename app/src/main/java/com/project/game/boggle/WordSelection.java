package com.app.myapplication;

import android.widget.TextView;

/**
 * Created by Muna on 1/23/16.
 */
public class WordSelection {

    public static void highlight(TextView textView, Character ch)
    {

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
}
