package com.project.game.boggle;

/**
 * Created by Muna on 2/7/16.
 */

import java.io.BufferedReader;
import java.io.DataInputStream;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.HashMap;


public class Dictionary {

    //HashMap that stores the dictionary
    private static HashMap dictionary;
    private static ArrayList<String> hashInput;


    /*
		 * initialize the dictionary variable and parse the dictionary
		 * file to add all words in the dictionary file to the
		 * dictionary HashMap
		 */
    public Dictionary() throws Exception {
        dictionary = new HashMap();
       dictionary = Container.getInstance().getDictionary();
       // dictionary = new HashMap<String,ArrayList<String>>();
       //parseData(dictFile);
    }


    /*
     * This will parse the dictionary text file and store all of the wordList
     * from the word file that are three letters long or greater into a list.
     */
    public void parseData(InputStream dicFile) throws Exception{


        try {
            String temp;
            String key = "";
            String nextKey;


            dictionary = new HashMap();


            InputStream data = dicFile;

            DataInputStream input = new DataInputStream(data);

            BufferedReader br = new BufferedReader(new InputStreamReader(input));


            while ((temp = br.readLine()) != null) {

                if (temp.length() >= 3) {

                    nextKey = temp.substring(0, 2);

                    if (nextKey.equals(key)) {
                        hashInput.add(temp);
                    } else {

                        dictionary.put(key, hashInput);
                        key = nextKey;
                        hashInput = new ArrayList<String>();
                        hashInput.add(temp);
                    }

                }

            }
            dictionary.put(key, hashInput);
            input.close();

        }

        catch (Exception fnfe)
        {
            System.out.println(fnfe);
        }

    }




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
