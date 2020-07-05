package com.techupstudio.profanity_checker.base;

import com.techupstudio.utils.io.ReadWritableFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Lyen
 */
public class StringHasher {
    private TreeNode root;
    private int badWordStart;
    private int badWordEnd;
    private boolean isSuspicionFound;
    private boolean[] asteriskMark;

    public StringHasher(Object[] wordsList) {
        root = new TreeNode();
        buildDictionaryTree(wordsList);
    }

    public StringHasher(File wordsFile) throws IOException {
        root = new TreeNode();
        buildDictionaryTree(wordsFile);
    }


    public void buildDictionaryTree(Object[] wordList) {
        for (Object word : wordList) {
            addToTree(word.toString().toLowerCase().trim(), 0, root);
        }
    }

    public void buildDictionaryTree(File file) throws IOException {
        ReadWritableFile readWritableFile = new ReadWritableFile(file);
        buildDictionaryTree(readWritableFile.readEnumerate().values().toArray());
    }

    /**
     * @param badWordLine
     * @param characterIndex : index of each letter in a bad word
     * @param node           that iterates through the tree
     */
    private void addToTree(String badWordLine, int characterIndex, TreeNode node) {
        if (characterIndex < badWordLine.length()) {
            Character c = badWordLine.charAt(characterIndex);
            if (!node.containsChild(c)) {
                node.addChild(c);
            }
            node = node.getChildByLetter(c);
            // check if this is the last letter
            if (characterIndex == (badWordLine.length() - 1)) {
                // mark this letter as the end of a bad word
                node.setEnd(true);
            } else {
                // add next letter
                addToTree(badWordLine, characterIndex + 1, node);
            }
        }
    }


    /**
     * @param userInput
     * @return string with bad words filtered
     */
    public String process(String userInput) {
        init(userInput.length());
        // for each character in a bad word
        for (int i = 0; i < userInput.length(); i++) {
            searchAlongTree(userInput, i, root);
        }
        return applyAsteriskMark(userInput);
    }

    private void init(int length) {
        asteriskMark = new boolean[length];
        for (int i = 0; i < length; i++) {
            asteriskMark[i] = false;
        }
        badWordStart = -1;
        badWordEnd = -1;
        isSuspicionFound = false;
    }

    private void searchAlongTree(String pUserInput, int characterIndex,
                                 TreeNode node) {
        if (characterIndex < pUserInput.length()) {
            // get the corresponding letter
            Character letter = pUserInput.charAt(characterIndex);
            if (node.containsChild(letter)) {
                // find a word whose first letter is equal to one of the bad
                // words' first letter
                if (!isSuspicionFound) {
                    isSuspicionFound = true;
                    badWordStart = characterIndex;
                }
                // if this is the final letter of a bad word
                if (node.getChildByLetter(letter).isEnd()) {
                    badWordEnd = characterIndex;
                    markAsterisk(badWordStart, badWordEnd);
                }
                node = node.getChildByLetter(letter);
                searchAlongTree(pUserInput, characterIndex + 1, node);
            } else {
                // initialize some parameters
                isSuspicionFound = false;
                badWordStart = -1;
                badWordEnd = -1;
            }
        }
    }

    /**
     * Replace some of the letters in userInput as * according to asteriskMark
     *
     * @param userInput
     * @return string with bad words filtered
     */
    private String applyAsteriskMark(String userInput) {
        StringBuilder filteredBadWords = new StringBuilder(userInput);
        for (int i = 0; i < asteriskMark.length; i++) {
            if (asteriskMark[i] == true) {
                filteredBadWords.setCharAt(i, '*');
            }
        }
        return filteredBadWords.toString();
    }

    /**
     * Identify the letters of userInput that should be marked as "*"
     *
     * @param badWordStart
     * @param badWordEnd
     */
    private void markAsterisk(int badWordStart, int badWordEnd) {
        for (int i = badWordStart; i <= badWordEnd; i++) {
            asteriskMark[i] = true;
        }
    }

    public boolean isMatch(String sentence) {
        return false;
    }

    public String[] getMatchedWords(String sentence) {
        return new String[0];
    }
}
