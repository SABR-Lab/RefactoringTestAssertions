package org.example;

public class Test {
    public static void main(String[] args) {
        System.out.println("Starting test...");
        for (int i = 0; i < 1000; i++) {
            String s = "test" + i;
        }
        System.out.println("Test complete.");
    }
}
