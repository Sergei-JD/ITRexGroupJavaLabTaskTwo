package com.itrex.task.two;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LabyrinthReader {

    public void input(String fileName, ArrayList<Integer> labyrinthSize, StringBuilder labyrinthString) {

        String s;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            s = reader.readLine();
            Pattern pat = Pattern.compile("\\d+");
            Matcher matcher = pat.matcher(s);

            while (matcher.find()) {
                labyrinthSize.add(Integer.parseInt(matcher.group()));
            }

            while ((s = reader.readLine()) != null) {
                labyrinthString.append(s + "\n");
            }

        } catch (Exception e) {
            System.err.println("BufferedReader error!");
        }
    }
}
