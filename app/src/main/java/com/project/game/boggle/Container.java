package com.project.game.boggle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Container {

    private static final String DICTIONARY = "english-dictionary.txt";
    private static final String HIGHSCORES = "highscores.txt";

    private HashMap dictionary;            // Dictionary of valid words
    private ArrayList<Integer> highscores; // List of highscores
    private ArrayList<String> wordList;    // List of words

    private String word;     // Word
    private int wordScore;   // Word score
    private int player;      // Player #
    private int playerScore; // Player score
    private String user;     // Username


    public HashMap getDictionary() { return dictionary; }
    public void setDictionary(HashMap dictionary) { this.dictionary = dictionary; }
    public ArrayList<Integer> getHighscores() { return highscores; }
    public void setHighscores(ArrayList<Integer> highscores) { this.highscores = highscores; }
    public ArrayList<String> getWordList() { return wordList; }
    public void setWordList(ArrayList<String> wordList) { this.wordList = wordList; }
    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }
    public int getWordScore() { return wordScore; }
    public void setWordScore(int wordScore) { this.wordScore = wordScore; }
    public int getPlayer() { return player; }
    public void setPlayer(int player) { this.player = player; }
    public int getPlayerScore() { return playerScore; }
    public void setPlayerScore(int playerScore) { this.playerScore = playerScore; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }


    private static final Container container = new Container();
    public static Container getInstance() { return container; }

    /*
  * This will check the entire list of wordList to see if the given word is valid
  */
    public boolean isReal(String input){

        if(input.length() < 3){
            return false;
        }

        String sub = input.substring(0, 2);
        if(dictionary.containsKey(sub)){
            ArrayList<String> wordLists = (ArrayList<String>) dictionary.get(sub);
            if(wordLists.contains(input)){
                return true;
            }
        }

        return false;
    }



    /*
     * This will check the entire list of wordList to see if the given string
     * is part of a word
     */
    public boolean isPartOfAWord(String input){

        if(input.length() == 1){
            return true;
        }

        String sub = input.substring(0, 2);
        if(dictionary.containsKey(sub)){
            ArrayList<String> wordLists = (ArrayList<String>) dictionary.get(sub);
            for(int i = 0; i < wordLists.size(); ++i){
                int subSize = input.length();
                int wordSize = wordLists.get(i).length();
                if(subSize <= wordSize){
                    String compare = wordLists.get(i).substring(0, subSize);
                    if(input.equals(compare)){
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
