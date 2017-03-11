package com.dev;

public class Application {

    public static void main(String[] args) {
        FileTextController fileTextController = new FileTextController();
        String text = fileTextController.readTextFromFile("text.txt");
        MarkovChain markovChain = new MarkovChain(text);
        System.out.println(markovChain.generateText());
    }
}
