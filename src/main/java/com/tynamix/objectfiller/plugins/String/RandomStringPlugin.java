package com.tynamix.objectfiller.plugins.String;

import com.tynamix.objectfiller.plugins.RandomizerPlugin;

import java.util.concurrent.ThreadLocalRandom;

public class RandomStringPlugin implements RandomizerPlugin<String> {

    /// <summary>
    /// The count of words which will be generated
    /// </summary>
    private final int wordCount;

    /// <summary>
    /// The word min length.
    /// </summary>
    private final int wordMinLength;

    /// <summary>
    /// The word max length.
    /// </summary>
    private final int wordMaxLength;

    /// <summary>
    /// Initializes a new instance of the <see cref="MnemonicString"/> class.
    /// </summary>
    public RandomStringPlugin() {
        this(1);
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="MnemonicString"/> class.
    /// </summary>
    /// <param name="wordCount">
    /// The count of words which will be generated
    /// </param>
    public RandomStringPlugin(int wordCount) {
        this(wordCount, 3, 15);
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="MnemonicString"/> class.
    /// </summary>
    /// <param name="wordCount">
    /// The count of words which will be generated
    /// </param>
    /// <param name="wordMinLength">
    /// The word min length.
    /// </param>
    /// <param name="wordMaxLength">
    /// The word max length.
    /// </param>
    public RandomStringPlugin(int wordCount, int wordMinLength, int wordMaxLength) {
        this.wordCount = wordCount;
        this.wordMinLength = wordMinLength;
        this.wordMaxLength = wordMaxLength;
    }

    public String createValue() {
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        char[] consonants = {'w', 'r', 't', 'z', 'p', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'c', 'v', 'b', 'n', 'm'};
        int wordLength = ThreadLocalRandom.current().nextInt(this.wordMinLength, this.wordMaxLength + 1);

        String result = "";

        for (int i = 0; i < this.wordCount; i++) {
            String currentWord = "";

            boolean nextIsVowel = ThreadLocalRandom.current().nextBoolean() == true;

            boolean upperLetter = i == 0 || ThreadLocalRandom.current().nextInt(0, 2) == 1;

            for (int j = 0; j < wordLength; j++) {
                char currentChar;
                if (nextIsVowel) {
                    currentChar = vowels[ThreadLocalRandom.current().nextInt(0, vowels.length)];
                } else {
                    currentChar = consonants[ThreadLocalRandom.current().nextInt(0, consonants.length)];
                }


                currentWord += upperLetter ? Character.toUpperCase(currentChar) : currentChar;
                upperLetter = false;
                nextIsVowel = !nextIsVowel;
            }

            result += currentWord + " ";
        }

        return result.trim();
    }
}
