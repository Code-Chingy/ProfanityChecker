package com.techupstudio.games.CyberBullyingAwarenessGame.Base.ProfanityChecker;

import com.techupstudio.utils.GeneralUtils.Funcs;

import java.io.File;
import java.io.IOException;

public class ProfanityHasher extends StringHasher {

    public ProfanityHasher() throws IOException {
        super(new File("C:\\Users\\Otc_Chingy\\IdeaProjects\\JavaConsoleApplications\\GameCenter\\src\\com\\techupstudio\\games\\CyberBullyingAwarenessGame\\Base\\ProfanityChecker\\bad-words-list\\bad-single-words.txt"));
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


        while (userInput.contains("v")){
            int index = userInput.indexOf("v");
            if (index - 1 > -1){
                Character previous_char =  userInput.charAt(index-1);
                if (Funcs.findIn("b c d f g h j k l m n p q r s t v w x y z".split(" "), previous_char.toString())){
                    userInput = userInput.replaceFirst("v", "u");
                }
            }
        }

        userInput = userInput.toLowerCase().replaceAll("[^a-zA-Z ]", "");

        return super.process(userInput);
    }
}
