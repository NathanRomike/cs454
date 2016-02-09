package com.project.game.boggle;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Muna on 2/8/16.
 */
public class BoggleTest {
    @BeforeClass
    public static void readyFramework() {
        System.out.println("Testing of this class beging...");
    }


    @AfterClass
    public static void closeFramework() {
        System.out.println("Testing is over.");
    }


    @Test
    public void testGeneratedGridSize() throws Exception {
        System.out.println("testing boggle grid size");
        assertEquals("The size of the generated grid != 16", 16, BoardGenerator.getRandomDice().size());

    }

    @Test
    public void test() throws Exception {
        System.out.println("testing boggle grid size");
        Container.getInstance().setWord("hello");
        System.out.println(Container.getInstance().getPlayerScore());
        //assertEquals("The size of the generated grid != 16", 16, );
    }


}
