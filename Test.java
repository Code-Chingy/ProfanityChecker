package com.techupstudio.profanity_checker;

import com.techupstudio.profanity_checker.base.ProfanityFilter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {
        File single = new File("src/com/techupstudio/profanity_checker/bad-words-list/bad-single-words.txt");
        File group = new File("src/com/techupstudio/profanity_checker/bad-words-list/bad-group-words.txt");
        ProfanityFilter filter = new ProfanityFilter(single, group);
        List<String> x = filter.getAllMatches("sh!t i think i love java");
        System.out.println(x);
    }

}
