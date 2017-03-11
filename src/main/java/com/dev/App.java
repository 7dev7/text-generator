package com.dev;

public class App {
    public static void main(String[] args) {
        TextController textController = new TextController();
        String base = textController.readTextFromFile("test.txt");
        if (base == null) {
            System.out.println("Base is null. Please, check text file");
            return;
        }
        MarkovChain markovChain = new MarkovChain(base);
        System.out.println(markovChain.generateText());
    }
}
