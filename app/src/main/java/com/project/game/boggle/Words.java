package com.project.game.boggle;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;


public class Words extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_words);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.7));

        Drawable drawable = new ColorDrawable(Color.WHITE);
        drawable.setAlpha(190);
        getWindow().setBackgroundDrawable(drawable);

        loadWords();
    }

    public void loadWords() {
        Container container = Container.getInstance();
        ArrayList<String> wordsList = container.getWordList();
        TextView title = (TextView) findViewById(R.id.words_list);
        TextView list = (TextView) findViewById(R.id.words_found);


        if (wordsList.isEmpty()) {
            title.setText(getResources().getString(R.string.words_list_empty));
        } else {
            title.setText(getResources().getString(R.string.words_list_not_empty));

            Iterator words = wordsList.iterator();

            while (words.hasNext()) {
                String word = (String) words.next();
                word = word + "\n";

                list.append(word);
            }
        }
    }
}
