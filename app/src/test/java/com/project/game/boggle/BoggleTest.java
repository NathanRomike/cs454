package com.project.game.boggle;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Muna on 2/8/16.
 */
public class BoggleTest {




/*
    @Before
    public void setUp() throws Exception {
        System.out.print("TEST START... ");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("TEST COMPLETE.");
    }
*/


    @BeforeClass
    public static void readyFramework() {
        System.out.println("TEST STARTS...");
        Container.getInstance().setPlayerScore(0);
    }


    @AfterClass
    public static void closeFramework() {
        System.out.println("TEST END.");
    }

    @Test
    public void testWordLen012() throws Exception {
        // we need to test onSubmit method here

    }


    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    // here we need to test that the score increase
    // as the user submit a correct word rathar than
    // starting with score 0 ==> change the 7 test
    // cases you created to affect this kind of calc.
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////

    /*

    @Test
    public void testWordLen3() throws Exception {
        System.out.println("TEST: the score of a correct word of 3 letters");

        Container.getInstance().setWord("see");
        System.out.println();
        assertEquals("The score for a correct word with length 3 should be 1!"
                , 1, OnePlayer.score(Container.getInstance().getWord().length()));

    }

    @Test
    public void testWordLen4() throws Exception {
        System.out.println("TEST: the score of a correct word of 4 letters");

        Container.getInstance().setWord("tree");
        System.out.println();
        assertEquals("The score for a correct word with length 4 should be 1!"
                , 1, OnePlayer.score(Container.getInstance().getWord().length()));

    }

    @Test
    public void testWordLen5() throws Exception {
        System.out.println("TEST: the score of a correct word of 5 letters");

        Container.getInstance().setWord("north");
        System.out.println();
        assertEquals("The score for a correct word with length 5 should be 2!"
                , 2, OnePlayer.score(Container.getInstance().getWord().length()));

    }

    @Test
    public void testWordLen6() throws Exception {
        System.out.println("TEST: the score of a correct word of 6 letters");

        Container.getInstance().setWord("banana");
        System.out.println();
        assertEquals("The score for a correct word with length 5 should be 3!"
                , 3, OnePlayer.score(Container.getInstance().getWord().length()));

    }

    @Test
    public void testWordLen7() throws Exception {
        System.out.println("TEST: the score of a correct word of 7 letters");

        Container.getInstance().setWord("messman");
        System.out.println();
        assertEquals("The score for a correct word with length 5 should be 1!"
                , 5, OnePlayer.score(Container.getInstance().getWord().length()));

    }

    @Test
    public void testWordLen8() throws Exception {
        System.out.println("TEST: the score of a correct word of 8 letters");

        Container.getInstance().setWord("abashing");
        System.out.println();
        assertEquals("The score for a correct word with length 5 should be 11!"
                , 11, OnePlayer.score(Container.getInstance().getWord().length()));

    }

    @Test
    public void testWordLen9() throws Exception {
        System.out.println("TEST: the score of a correct word of 9 letters or more");

        Container.getInstance().setWord("abarticular");
        System.out.println();
        assertEquals("The score for a correct word with length 5 should be 11 !"
                , 11, OnePlayer.score(Container.getInstance().getWord().length()));

    }


    @Test
    public void testGeneratedGridSize() throws Exception {
        System.out.println("testing boggle board size");
        assertEquals("The size of the generated grid != 16", 16, BoardGenerator.getRandomDice().size());

    }

    @Test
    public void testGeneratedGridContent() throws Exception {
        System.out.println("testing boggle board content");

        //here we need to test that the content of the board is letters not null or #
        // + we need to make sure that non of the 16 letters is empty!
        //assertEquals("The size of the generated grid != 16", 16, BoardGenerator.getRandomDice().size());

    }

    @Test
    public void testBoggleSolver() throws Exception {
        System.out.println("testing boggle solver");

        try {
            List<Character> dieList = BoardGenerator.getRandomDice();


            BoggleSolver.setBoard(dieList);

            InputStream  r= this.getClass().getClassLoader().getResourceAsStream("english-dictionary.txt");
            Dictionary dict = new Dictionary(r);

            BoggleSolver.boggleWordListSearch(dict);

        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        assertTrue("The boggle board should contains 5 or more words /n" +
                        "and the generated board has less than 5 words",
                BoggleSolver.boggleWordList.size() > 5);

    }


    @Test
    public void testValidMove() throws Exception {
        System.out.println("testing valid move part");
        // in this test we need to make sure that the user performed the right move
        //assertEquals("The size of the generated grid != 16", 16, BoardGenerator.getRandomDice().size());

    }

    @Test
    public void testSubmittignWord() throws Exception {
        System.out.println("TEST: submitting word");
        //in this test we need to make sure that the submitted word is added to the list
        // we also need to make sure that duplicate words are not counted
        //assertEquals("The size of the generated grid != 16", 16, BoardGenerator.getRandomDice().size());

    }

    @Test
    public void testHighSore() throws Exception {
        System.out.println("testing high-score list");
        //in this part we need to verify that any new score will affects the hig-score list
        //assertEquals("The size of the generated grid != 16", 16, BoardGenerator.getRandomDice().size());

    }

*/


}
