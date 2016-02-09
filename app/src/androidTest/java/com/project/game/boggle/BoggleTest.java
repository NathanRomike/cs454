package com.project.game.boggle;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Muna on 2/8/16.
 */
public class BoggleTest  extends AppCompatActivity {


    @BeforeClass
    public static void readyFramework() {
        System.out.println("Testing of this class beging...");
    }


    @AfterClass
    public static void closeFramework() {
        System.out.println("Testing is over.");
    }





}
