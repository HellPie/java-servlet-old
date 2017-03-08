package dev.hellpie.school.java.servlet;

import dev.hellpie.school.java.servlet.server.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new Server(6990, "D:\\HTTPServer\\").run();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
