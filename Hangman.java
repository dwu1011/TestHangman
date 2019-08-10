package Hangman;

import java.io.IOException;
import java.util.*;
import acm.program.ConsoleProgram;
import java.awt.*;

public class Hangman extends ConsoleProgram{
    int lives = 8;
    private HangmanCanvas canvas;
    @Override
    public void init() {
        canvas = new HangmanCanvas();
        add(canvas);
    }
    public void run(){
        canvas.reset();
        char guess;
        boolean check = false;
        int place = 0;


        HangmanLexicon lexicon = new HangmanLexicon();
        Random Random = new Random();
        Hangman hangman = new Hangman();

        String word = null;
        word = lexicon.getWord(Random.nextInt(lexicon.getWordCount()));
        char[] wordShow = new char[word.length()];
        ArrayList<Character> guesses = new ArrayList<>();
        ArrayList<Integer> occur;

        for(int i =0;i<wordShow.length;i++){
            wordShow[i] = '-';
        }
        while(lives > 0){
            canvas.displayWord(hangman.print(wordShow));
            for(char i:wordShow){
                print(i);
            }
            println();
            println("What is your guess?");
            guess = readLine().toUpperCase().charAt(0);
            place = word.indexOf(guess);
            if(place != -1){//if the guess is in the word
                occur = hangman.findOccur(guess, word);
                for(int i = 0; i<occur.size();i++){
                    wordShow[occur.get(i)] = word.charAt(occur.get(i));
                }
            }else if(hangman.check(guess,guesses) != -1){
                println("You have already guessed this character");
            }else{
                println("You guessed wrong");
                println("You have "+lives+" lives");
                lives --;
            }
            for(char i:wordShow){//check if there are still words to guess
                if(i == '-'){
                    check = true;
                }
            }
            if(check != true){
                println("You Won!");
                check = true;
                break;
            }
            check = false;
        }
        if(check != true){
            println("You Lost");
            println("The right word was "+word);
        }


    }
    private int check(char i, ArrayList<Character> guesses){
        for(int f =0;f<guesses.size();f++){
            if(guesses.get(f) == i){
                return f;
            }
        }
        return -1;
    }
    private ArrayList<Integer> findOccur(char i, String f){
        ArrayList<Integer> arr = new ArrayList<>();
        for(int g =0;g< f.length();g++){
            if(f.charAt(g) == i) {
                arr.add(g);
            }
        }
        return arr;
    }
    private String print(char[] arr){
        char[] printchar = new char[arr.length  * 2 -1];
        for(int i  =0;i<printchar.length; i ++){
            if(i%2 != 0){
                printchar[i] = ' ';
            }else{
                printchar[i] = arr[i/2];
            }
        }
        return new String(printchar);
    }
    public static void main(String[] args){
        Hangman hangman = new Hangman();
        hangman.start();
    }
}
