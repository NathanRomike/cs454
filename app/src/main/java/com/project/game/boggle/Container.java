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
}
