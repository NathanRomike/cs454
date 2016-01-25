package com.project.game.boggle;

/**
 * Created by Muna on 1/25/16.
 */

import android.view.MotionEvent;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import java.util.List;


public class GridFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle
                                     savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_main, container, false);

        final TextView textview1 = (TextView) view.findViewById(R.id.tv1);
        final TextView textview2 = (TextView) view.findViewById(R.id.tv2);
        final TextView textview3 = (TextView) view.findViewById(R.id.tv3);
        final TextView textview4 = (TextView) view.findViewById(R.id.tv4);
        final TextView textview5 = (TextView) view.findViewById(R.id.tv5);
        final TextView textview6 = (TextView) view.findViewById(R.id.tv6);
        final TextView textview7 = (TextView) view.findViewById(R.id.tv7);
        final TextView textview8 = (TextView) view.findViewById(R.id.tv8);
        final TextView textview9 = (TextView) view.findViewById(R.id.tv9);
        final TextView textview10 = (TextView) view.findViewById(R.id.tv10);
        final TextView textview11 = (TextView) view.findViewById(R.id.tv11);
        final TextView textview12 = (TextView) view.findViewById(R.id.tv12);
        final TextView textview13 = (TextView) view.findViewById(R.id.tv13);
        final TextView textview14 = (TextView) view.findViewById(R.id.tv14);
        final TextView textview15 = (TextView) view.findViewById(R.id.tv15);
        final TextView textview16 = (TextView) view.findViewById(R.id.tv16);

        List<Character> dieList =  GridGenerator.getRandomDice();

        final Character ch1 = dieList.get(0);
        final Character ch2 = dieList.get(1);
        final Character ch3 = dieList.get(2);
        final Character ch4 = dieList.get(3);
        final Character ch5 = dieList.get(4);
        final Character ch6 = dieList.get(5);
        final Character ch7 = dieList.get(6);
        final Character ch8 = dieList.get(7);
        final Character ch9 = dieList.get(8);
        final Character ch10 = dieList.get(9);
        final Character ch11 = dieList.get(10);
        final Character ch12 = dieList.get(11);
        final Character ch13 = dieList.get(12);
        final Character ch14 = dieList.get(13);
        final Character ch15 = dieList.get(14);
        final Character ch16 = dieList.get(15);


        GridGenerator.alphaImg(textview1, ch1);
        GridGenerator.alphaImg(textview2, ch2);
        GridGenerator.alphaImg(textview3, ch3);
        GridGenerator.alphaImg(textview4, ch4);
        GridGenerator.alphaImg(textview5, ch5);
        GridGenerator.alphaImg(textview6, ch6);
        GridGenerator.alphaImg(textview7, ch7);
        GridGenerator.alphaImg(textview8, ch8);
        GridGenerator.alphaImg(textview9, ch9);
        GridGenerator.alphaImg(textview10, ch10);
        GridGenerator.alphaImg(textview11, ch11);
        GridGenerator.alphaImg(textview12, ch12);
        GridGenerator.alphaImg(textview13, ch13);
        GridGenerator.alphaImg(textview14, ch14);
        GridGenerator.alphaImg(textview15, ch15);
        GridGenerator.alphaImg(textview16, ch16);

        textview1.setOnTouchListener(new View.OnTouchListener() {
                                         public boolean onTouch(View v, MotionEvent event) {

                                             WordSelection.highlight(textview1, ch1);
                                             return true;
                                         }
                                     }
        );


        textview2.setOnTouchListener(new View.OnTouchListener() {
                                         public boolean onTouch(View v, MotionEvent event) {
                                             WordSelection.highlight(textview2, ch2);
                                             return true;
                                         }
                                     }
        );

        textview3.setOnTouchListener(new View.OnTouchListener() {
                                         public boolean onTouch(View v, MotionEvent event) {

                                             WordSelection.highlight(textview3, ch3);
                                             return true;
                                         }
                                     }
        );


        textview4.setOnTouchListener(new View.OnTouchListener() {
                                         public boolean onTouch(View v, MotionEvent event) {

                                             WordSelection.highlight(textview4, ch4);
                                             return true;
                                         }
                                     }
        );



        textview5.setOnTouchListener(new View.OnTouchListener() {
                                         public boolean onTouch(View v, MotionEvent event) {

                                             WordSelection.highlight(textview5, ch5);
                                             return true;
                                         }
                                     }
        );


        textview6.setOnTouchListener(new View.OnTouchListener() {
                                         public boolean onTouch(View v, MotionEvent event) {

                                             WordSelection.highlight(textview6, ch6);
                                             return true;
                                         }
                                     }
        );


        textview7.setOnTouchListener(new View.OnTouchListener() {
                                         public boolean onTouch(View v, MotionEvent event) {

                                             WordSelection.highlight(textview7, ch7);
                                             return true;
                                         }
                                     }
        );


        textview8.setOnTouchListener(new View.OnTouchListener()
                                     {
                                         public boolean onTouch(View v, MotionEvent event) {

                                             WordSelection.highlight(textview8, ch8);
                                             return true;
                                         }
                                     }
        );


        textview9.setOnTouchListener(new View.OnTouchListener() {
                                         public boolean onTouch(View v, MotionEvent event) {

                                             WordSelection.highlight(textview9, ch9);
                                             return true;
                                         }
                                     }
        );


        textview10.setOnTouchListener(new View.OnTouchListener()
                                      {
                                          public boolean onTouch(View v, MotionEvent event) {

                                              WordSelection.highlight(textview10, ch10);
                                              return true;
                                          }
                                      }
        );


        textview11.setOnTouchListener(new View.OnTouchListener() {
                                          public boolean onTouch(View v, MotionEvent event) {

                                              WordSelection.highlight(textview11, ch11);
                                              return true;
                                          }
                                      }
        );


        textview12.setOnTouchListener(new View.OnTouchListener()
                                      {
                                          public boolean onTouch(View v, MotionEvent event) {

                                              WordSelection.highlight(textview12, ch12);
                                              return true;
                                          }
                                      }
        );


        textview13.setOnTouchListener(new View.OnTouchListener() {
                                          public boolean onTouch(View v, MotionEvent event) {

                                              WordSelection.highlight(textview13, ch13);
                                              return true;
                                          }
                                      }
        );


        textview14.setOnTouchListener(new View.OnTouchListener()
                                      {
                                          public boolean onTouch(View v, MotionEvent event) {

                                              WordSelection.highlight(textview14, ch14);
                                              return true;
                                          }
                                      }
        );


        textview15.setOnTouchListener(new View.OnTouchListener() {
                                          public boolean onTouch(View v, MotionEvent event) {

                                              WordSelection.highlight(textview15, ch15);
                                              return true;
                                          }
                                      }
        );


        textview16.setOnTouchListener(new View.OnTouchListener()
                                      {
                                          public boolean onTouch(View v, MotionEvent event) {

                                              WordSelection.highlight(textview16, ch16);
                                              return true;
                                          }
                                      }
        );


        return view;
    }
}
