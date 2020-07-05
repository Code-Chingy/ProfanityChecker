package com.techupstudio.profanity_checker.base;

import com.techupstudio.utils.general.Funcs;

import java.io.File;
import java.io.IOException;

public class ProfanityHasher extends StringHasher {

    public ProfanityHasher() throws IOException {
        super(new File("src/com/techupstudio/profanity_checker/bad-words-list/bad-single-words.txt"));
    }

    @Override
    public String process(String userInput) {

        // don't forget to remove leetspeak, probably want to move this to its own function and use regex if you want to use this 

        userInput = userInput.replaceAll("1", "i");
        userInput = userInput.replaceAll("!", "i");
        userInput = userInput.replaceAll("3", "e");
        userInput = userInput.replaceAll("4", "a");
        userInput = userInput.replaceAll("@", "a");
        userInput = userInput.replaceAll("5", "s");
        userInput = userInput.replaceAll("7", "t");
        userInput = userInput.replaceAll("0", "o");
        userInput = userInput.replaceAll("9", "g");
        userInput = userInput.replaceAll("8", "ate");
        userInput = Funcs.replaceAll(userInput, "$", "S");
        userInput = Funcs.replaceAll(userInput, "+", "t");


        while (userInput.contains("v")) {
            int index = userInput.indexOf("v");
            if (index - 1 > -1) {
                char previous_char = userInput.charAt(index - 1);
                if (Funcs.findIn("a b c d f g h j k l m n p q r s t v w x y z".split(" "), Character.toString(previous_char))) {
                    userInput = userInput.replaceFirst("v", "u");
                }
            }
        }

        userInput = userInput.toLowerCase().replaceAll("[^a-zA-Z ]", "");

        return super.process(userInput);
    }
}
