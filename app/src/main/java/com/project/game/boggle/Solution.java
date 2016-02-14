package com.project.game.boggle;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;


public class Solution extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_solution);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.6));

        Drawable drawable = new ColorDrawable(Color.WHITE);
        drawable.setAlpha(190);
        getWindow().setBackgroundDrawable(drawable);

        loadSolution();
    }

    public void loadSolution() {
        Container container = Container.getInstance();
        ArrayList<String> wordsList = container.getSolution();
        TextView solution = (TextView) findViewById(R.id.solution);

        Iterator words = wordsList.iterator();

        while (words.hasNext()) {
            String word = (String) words.next();
            word = word + "\n";

            solution.append(word);
        }
    }
}
