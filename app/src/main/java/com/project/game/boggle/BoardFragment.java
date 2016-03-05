package com.project.game.boggle;

/**
 * Created by Muna on 1/25/16.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import android.support.v7.app.AppCompatActivity;

import android.content.Context;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;



public class BoardFragment extends Fragment {


    private static Dictionary dictionary;

    private static List<Character> dieList;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle
                                     savedInstanceState) {






        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.content_board, container, false);
        Context context = getActivity();
        Resources r = context.getResources();
        String packageName = context.getPackageName();


        dieList = new ArrayList<Character>();

        if(Container.getInstance().getBoard().size() == 0) {
            this.setBoard(); // to generate a new board. dieList = BoardGenerator.getRandomDice();
        }
        else
        {
            dieList=Container.getInstance().getBoard();
        }


        //dieList=Container.getInstance().getBoard();

        if (Container.getInstance().getSolution().size() == 0)
        {
            try {
                dictionary = new Dictionary(getResources().openRawResource(R.raw.dictionary));



                //BoggleSolver.setBoard(dieList);
                BoggleSolver.setBoard(Container.getInstance().getBoard());
                BoggleSolver.boggleWordListSearch(dictionary);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        WordSelection.initQueue();

        final TextView[][] board = new TextView[4][4];
        final int rows = 4;
        final int cols = 4;
        int z = 0;
        int id;
        String key;

        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                key = "_" + x + y;
                id = r.getIdentifier(key, "id", packageName);
                board[x][y] = (TextView) view.findViewById(id);
                BoardGenerator.alphaImg(board[x][y], dieList.get(z));
                z++;
            }
        }

        z = 0;

        for (int y = 0; y < cols; y++) {
            for (int x = 0; x < rows; x++) {
                final TextView tile = board[x][y];
                final Character letter = dieList.get(z);

                tile.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if (WordSelection.validMove(tile, letter)) {
                                WordSelection.highlight(tile, letter);
                            }

                            return true;
                        }

                        return false;
                    }
                });

                z++;
            }
        }

        return view;
    }

    public static void setBoard()
    {
        dieList  = BoardGenerator.getRandomDice();
        Container.getInstance().setBoard(dieList);
    }
    public static List<Character> getBoard()
    {
        //return dieList;
        return Container.getInstance().getBoard();
    }




}