package com.project.game.boggle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Container {

    private static final String DICTIONARY = "english-dictionary.txt";
    private static final String HIGHSCORES = "highscores.txt";

    private HashMap dictionary;            // Dictionary of valid words
    private ArrayList<String> solution;
    private ArrayList<HashMap<String, Integer>> highscores; // List of highscores
    // need dictionary data type to hold highscores and the player name
    private HashMap<String, Integer> highscoresDic;

    private ArrayList<String> wordList;    // List of words

    private String word;     // Word
    private int wordScore;   // Word score
    private int player;      // Player #
    private int playerScore; // Player score
    private String user;     // Username


    public HashMap getDictionary() { return dictionary; }
    public void setDictionary(HashMap dictionary) { this.dictionary = dictionary; }
    public ArrayList<String> getSolution() { return solution; }
    public void setSolution(ArrayList<String> solution) { this.solution = solution; }

//    public ArrayList<HashMap<String, Integer>> setHighscores(){}
    // each element in ArrayList is a HashMap
    // each HashMap contain one key(player name) value(player score) pair
    public ArrayList<HashMap<String, Integer>> getHighscores() { return highscores; }
    // this function will update the highscore
    // this array list only contain 10 elements.
    // have to delete last element when there a new highscore appear
    public void updateHighscores(HashMap<String, Integer> oneHighscore) {
        if(this.highscores == null){
            this.highscores = new ArrayList<HashMap<String, Integer>>();
        }

        if(this.highscores.isEmpty()){
            highscores.add(oneHighscore);
        }
        else{
            int indexToAdd = findIndexToAdd(oneHighscore);
            if(indexToAdd != -1){
                highscores.add(indexToAdd, oneHighscore);
                while(highscores.size() > 10){
                    highscores.remove(highscores.size()-1);
                }
            }
        }
    }

    // find the index to insert a map into array list.
    // if value is less all elements in array list return -1
    // it will return index number when it find a spot to insert
    public int findIndexToAdd(HashMap<String, Integer> oneHighscore){
        Iterator index = oneHighscore.keySet().iterator();
        String key = (String)index.next();
        int valueToAdd = oneHighscore.get(key);
        int listLength = highscores.size();
        for(int i = 0; i < listLength; i++){
            Iterator listIndex = highscores.get(i).keySet().iterator();
            String listKey = (String)listIndex.next();
            int listValue = highscores.get(i).get(listKey);
            if(valueToAdd > listValue){
                return i;
            }
        }
        return -1;
    }

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
    public void setHighscoresDic(String user, int playerScore){
        this.highscoresDic =  new HashMap <String, Integer>();
        this.highscoresDic.put(user, playerScore);
    }
    public HashMap<String, Integer> getHighscoresDic(){return this.highscoresDic;}

    private static final Container container = new Container();
    public static Container getInstance() { return container; }
}
