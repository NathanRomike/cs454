package com.project.game.boggle;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
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
        System.out.println("TEST: the score of a correct word of 1 or 2 letters");

        int i = 0;
        Container.getInstance().setPlayerScore(0);
        Container.getInstance().setWord("I");
        i+=OnePlayer.computeScore(Container.getInstance().getWord().length());
        Container.getInstance().setWord("hi");
        i+=OnePlayer.computeScore(Container.getInstance().getWord().length());

        assertEquals("The score for a correct word with length 1 or 2 should be 0!"
                , 0, Container.getInstance().getPlayerScore());


    }


    @Test
    public void testScore() throws Exception {
        System.out.println("TEST: the score increases as the user submit a new word");
         int i = 0;
        Container.getInstance().setPlayerScore(0);
        Container.getInstance().setWord("I");
        i+=OnePlayer.computeScore(Container.getInstance().getWord().length());
        Container.getInstance().setWord("hi");
        i+=OnePlayer.computeScore(Container.getInstance().getWord().length());
        Container.getInstance().setWord("see");
        i+=OnePlayer.computeScore(Container.getInstance().getWord().length());
        Container.getInstance().setWord("tree");
        i+=OnePlayer.computeScore(Container.getInstance().getWord().length());
        Container.getInstance().setWord("north");
        i+=OnePlayer.computeScore(Container.getInstance().getWord().length());
        Container.getInstance().setWord("banana");
        i+=OnePlayer.computeScore(Container.getInstance().getWord().length());
        Container.getInstance().setWord("messman");
        i+=OnePlayer.computeScore(Container.getInstance().getWord().length());
        Container.getInstance().setWord("abashing");
        i+=OnePlayer.computeScore(Container.getInstance().getWord().length());

        assertEquals("The total score should be 23!"
                , 23, Container.getInstance().getPlayerScore());

    }



    @Test
    public void testWordLen3() throws Exception {
        System.out.println("TEST: the score of a correct word of 3 letters");

        Container.getInstance().setWord("see");
        Container.getInstance().setPlayerScore(0);

        assertEquals("The score for a correct word with length 3 should be 1!"
                , 1, OnePlayer.computeScore(Container.getInstance().getWord().length()));

    }

    @Test
    public void testWordLen4() throws Exception {
        System.out.println("TEST: the score of a correct word of 4 letters");

        Container.getInstance().setWord("tree");
        Container.getInstance().setPlayerScore(0);

        assertEquals("The score for a correct word with length 4 should be 1!"
                , 1, OnePlayer.computeScore(Container.getInstance().getWord().length()));

    }

    @Test
    public void testWordLen5() throws Exception {
        System.out.println("TEST: the score of a correct word of 5 letters");

        Container.getInstance().setWord("north");
        Container.getInstance().setPlayerScore(0);

        assertEquals("The score for a correct word with length 5 should be 2!"
                , 2, OnePlayer.computeScore(Container.getInstance().getWord().length()));

    }

    @Test
    public void testWordLen6() throws Exception {
        System.out.println("TEST: the score of a correct word of 6 letters");

        Container.getInstance().setWord("banana");
        Container.getInstance().setPlayerScore(0);

        assertEquals("The score for a correct word with length 5 should be 3!"
                , 3, OnePlayer.computeScore(Container.getInstance().getWord().length()));

    }

    @Test
    public void testWordLen7() throws Exception {
        System.out.println("TEST: the score of a correct word of 7 letters");

        Container.getInstance().setWord("messman");
        Container.getInstance().setPlayerScore(0);

        assertEquals("The score for a correct word with length 5 should be 1!"
                , 5, OnePlayer.computeScore(Container.getInstance().getWord().length()));

    }

    @Test
    public void testWordLen8() throws Exception {
        System.out.println("TEST: the score of a correct word of 8 letters");

        Container.getInstance().setWord("abashing");
        Container.getInstance().setPlayerScore(0);

        assertEquals("The score for a correct word with length 5 should be 11!"
                , 11, OnePlayer.computeScore(Container.getInstance().getWord().length()));

    }

    @Test
    public void testWordLen9() throws Exception {
        System.out.println("TEST: the score of a correct word of 9 letters or more");

        Container.getInstance().setWord("abarticular");
        Container.getInstance().setPlayerScore(0);

        assertEquals("The score for a correct word with length 5 should be 11 !"
                , 11, OnePlayer.computeScore(Container.getInstance().getWord().length()));

    }

    @Test
    public void testGeneratedGridSize() throws Exception {
        System.out.println("testing boggle board size");
        assertEquals("The size of the generated grid != 16", 16, BoardGenerator.getRandomDice().size());

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
    public void testSubmittignWord() throws Exception {
        System.out.println("TEST: submitting word");

        InputStream  r= this.getClass().getClassLoader().getResourceAsStream("english-dictionary.txt");
        Dictionary dict = new Dictionary(r);

        HashMap dictionary = new HashMap();
        ArrayList<String> wordList = new ArrayList<>();

        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(("english-dictionary.txt"))));
            String word;

            while ((word = bf.readLine()) != null) {
                dictionary.put(word, word);
            }

            Container.getInstance().setDictionary(dictionary);
            Container.getInstance().setWordList(wordList);

            OnePlayer.submitWord("I");
            OnePlayer.submitWord("three");
            OnePlayer.submitWord("tree");
            OnePlayer.submitWord("tree");
            System.out.println(wordList.toString());
        }
    catch (Exception e) {
        System.out.println("");
        System.out.println(e);

    }

        assertTrue("The user can not submit the same word more than one time, the expected array size is 2",
                wordList.size()==2);


    }








    @Test
    public void testHighSore() throws Exception {
        System.out.println("testing high-score list");
        //in this part we need to verify that any new score will affects the hig-score list
        //assertEquals("The size of the generated grid != 16", 16, BoardGenerator.getRandomDice().size());

    }

    @Test
    public void testValidMove() throws Exception {
        System.out.println("testing valid move part");
        // in this test we need to make sure that the user performed the right move
        //assertEquals("The size of the generated grid != 16", 16, BoardGenerator.getRandomDice().size());

    }

    @Test
    public void testGeneratedGridContent() throws Exception {
        System.out.println("testing boggle board content");

        //here we need to test that the content of the board is letters not null or #
        // + we need to make sure that non of the 16 letters is empty!
        //assertEquals("The size of the generated grid != 16", 16, BoardGenerator.getRandomDice().size());

    }




}
