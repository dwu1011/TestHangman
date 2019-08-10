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
        boolean check;
        int place;


        HangmanLexicon lexicon = new HangmanLexicon();
        Random random = new Random();
        Hangman hangman = new Hangman();

        String word;
        word = lexicon.getWord(random.nextInt(lexicon.getWordCount()));
        String wordShow = new String(word);
        for (int i = 0; i < word.length(); ++i) {
          wordShow += '-';
        }
        Set<Character> guesses = new HashSet<>();
        ArrayList<Integer> occur;

        while (lives > 0) {
            canvas.displayWord(hangman.print(wordShow));
            println(wordShow);
            println("\nWhat is your guess?");
            guess = readLine().toUpperCase().charAt(0);
            if (guesses.contains(guess)) {
		println("You have already gueese this character!");
                continue;
            }
            guesses.add(guess);
            place = word.indexOf(guess);
            if (place != -1) {//if the guess is in the word
                occur = hangman.findOccur(guess, word);
                for(int i = 0; i < occur.size(); i++){
                    wordShow[occur.get(i)] = word.charAt(occur.get(i));
                }
            } else {
                println("You guessed wrong");
                println("You have "+lives+" lives");
                lives --;
            }
            won = (wordShow.indexOf('-') == -1); 
            if(won){
                println("You Won!");
                break;
            }
        }
        if (!won) {
            println("You Lost");
            println("The right word was "+word);
        }
    }

    private ArrayList<Integer> findOccur(char c, String f){
        ArrayList<Integer> arr = new ArrayList<>();
        for(int g =0; g < f.length(); g++){
            if(f.charAt(g) == c) {
                arr.add(g);
            }
        }
        return arr;
    }

    private String print(char[] arr) {
        char[] printchar = new char[arr.length  * 2 - 1];
        for (int i = 0;i < printchar.length; i++) {
            if (i % 2 != 0){
                printchar[i] = ' ';
            } else {
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
