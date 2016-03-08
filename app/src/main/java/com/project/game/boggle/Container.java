package com.project.game.boggle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Container {
    private static final String DICTIONARY = "english-dictionary.txt";
    private static final String HIGHSCORES = "highscores.txt";

    private HashMap dictionary;
    private ArrayList<String> solution;
    private static List<Character> board = new ArrayList<Character>();

    private ArrayList<HashMap<String, Integer>> highscores;
    private HashMap<String, Integer> highscoresDic;

    private ArrayList<String> wordList;
    private ArrayList<String> otherPlayer_wordList = new ArrayList<String>();

    private String word;
    private int wordScore;
    private int player;
    private int playerScore;
    private String user;
    private int valid;

    private boolean isPlayer1Done;
    private boolean isPlayer2Done;
    private boolean isMaster = false;
    private int otherPlayerScore;

    private boolean isCutthroat = false;
    private boolean wordMatch = false;

    public void setPlayer1Done(boolean val) {this.isPlayer1Done = val ;}
    public boolean getPlayer1Done() {return isPlayer1Done;}
    public void setPlayer2Done(boolean val) {this.isPlayer2Done = val ;}
    public boolean getPlayer2Done() {return isPlayer2Done;}
    public void setIsMaster(boolean val) {this.isCutthroat = val ;}
    public boolean getIsMaster() {return isMaster;}
    public void setOtherPlayerScore(int i) {this.otherPlayerScore=i; }
    public int getOtherPlayerScore() {return otherPlayerScore;}
    public void setIsCutthroat(boolean val) {this.isCutthroat = val ;}
    public boolean getIsCutthroat() {return isCutthroat;}

    public void setWordMatch(boolean val) {this.wordMatch = val ;}
    public boolean getWordMatch() {return wordMatch ;}


    public void setBoard(List<Character> board) { this.board = board; }
    public static List<Character> getBoard() { return board; }

    public static String getHIGHSCORES() { return HIGHSCORES; }
    public HashMap getDictionary() { return dictionary; }
    public void setDictionary(HashMap dictionary) { this.dictionary = dictionary; }
    public ArrayList<String> getSolution() { return solution; }
    public void setSolution(ArrayList<String> solution) { this.solution = solution; }
    public ArrayList<HashMap<String, Integer>> getHighscores() { return highscores; }
    public void setHighscores(ArrayList<HashMap<String, Integer>> highscores) {
        this.highscores = highscores;
    }

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

    // TODO - VALID USED FOR TWO-PLAYER COMMUNICATION
    public void setValid(int valid) { this.valid = valid; }
    public int getValid() { return this.valid; }
    public void resetValid() { this.valid = -1; }

    public ArrayList<String> getWordList() { return wordList; }
    public void setWordList(ArrayList<String> wordList) { this.wordList = wordList; }

    public ArrayList<String> getOtherPlayer_WordList() { return otherPlayer_wordList; }
    public void addToOtherPlayer_WordList(String str) { this.otherPlayer_wordList.add(str); }

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

    public void reset() {
        solution = null;
        board = new ArrayList<Character>();
        highscores = null;
        highscoresDic = null;
        wordList = null;
        word = null;
        wordScore = 0;
        player = 0;
        playerScore = 0;
        //user = null;
        valid = 0;
    }

    private static final Container container = new Container();
    public static Container getInstance() { return container; }
}
