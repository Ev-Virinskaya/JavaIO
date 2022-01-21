package elearn.JavaIO;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.FileReader;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class OptionalTasks {
    public static void main(String[] args) {

        try {
            //createFileWithRandomNumbers(makeNewDir(), 10);
            // replacePublicWithPrivate(new Scanner(System.in).nextLine(), "public", "private");
            //writeStringReverseOrder(makeNewDir(), "e:/elearn/JavaIO/src/main/java/elearn/JavaIO/TestClass.java");
            //replaceLowercaseWithUppercase("e:/elearn/JavaIO/src/main/java/elearn/JavaIO/TestClass.java");
//          changeStudentsLastName("e:/elearn/JavaIO/data/Book1.xlsx");
            deleteWords("e:/text.txt");
            //deleteComments("e:/elearn/JavaIO/src/main/java/elearn/JavaIO/TestClass.java");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String makeNewDir() {
        String absolutPathNewDir = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String pathNameDirectory = reader.readLine();
            File newDirectory = new File(pathNameDirectory);
            if (!newDirectory.exists()) {
                newDirectory.mkdirs();
            }
            absolutPathNewDir = newDirectory.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return absolutPathNewDir;
    }

    public static void rewriteFile(List<String> list, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : list) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    //task One
    public static void createFileWithRandomNumbers(String folderOptionalTasks, int dataSize) throws IOException {
        File newFile = new File(folderOptionalTasks + "/TaskOne.txt");

        try (FileWriter writer = new FileWriter(newFile)) {

            for (int i = 0; i < dataSize; i++) {
                String str = Math.round(Math.random() * 10) + System.lineSeparator();
                writer.write(str);
                System.out.print(str);
            }
        }

        int[] array = new int[dataSize];
        try (BufferedReader reader = new BufferedReader(new FileReader(newFile))) {
            while (reader.ready()) {
                for (int i = 0; i < array.length; i++) {
                    array[i] = Integer.parseInt(reader.readLine().trim());
                    System.out.println(array[i]);
                }
            }
            Arrays.sort(array);
        }

        try (FileWriter sortedWriter = new FileWriter(newFile)) {
            for (int randomInt : array) {
                sortedWriter.write(randomInt + System.lineSeparator());
            }
        }
    }

    //task Two
    public static void replacePublicWithPrivate(String fileName, String oldString, String newString) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        List<String> lines = new ArrayList<>();
        while (reader.ready()) {
            lines.add(reader.readLine().replace(oldString, newString));
        }
        reader.close();
        rewriteFile(lines, fileName);
    }

    //task Tree
    public static void writeStringReverseOrder(String dir, String fileName) throws IOException {
        File fileReverseOrder = new File(dir + "/TaskTree.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileReverseOrder));
             BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.ready()) {
                StringBuilder reverseString = new StringBuilder();
                char[] line = reader.readLine().toCharArray();
                for (int i = line.length - 1; i >= 0; i--) {
                    reverseString.append(line[i]);
                }
                writer.write(reverseString.toString());
                writer.newLine();
            }
        }
    }

    //task Four
    public static void replaceLowercaseWithUppercase(String fileName) throws IOException {
        List<String> tempList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.ready()) {
                String newLine = reader.readLine();
                String[] line = newLine.split("\\W");
                for (String str : line) {
                    if (str.length() > 2) {
                        newLine = newLine.replace(str, str.toUpperCase());
                    }
                }
                tempList.add(newLine);
            }
        }
        rewriteFile(tempList, fileName);
    }

    //task Seven
    public static void deleteWords(String fileName) throws IOException {
        Pattern pattern = Pattern.compile("(\\b\\w{3,5}\\b)");
        List<String> tempList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            Matcher matcher = pattern.matcher("");
            String line;
            while ((line = reader.readLine()) != null) {
                matcher.reset(line);
                int count = 0;
                while (matcher.find()) {
                        count++;
                }
                if (count % 2 == 0) {
                    line = matcher.replaceAll("");
                } else {
                    for (int i = 1; i < count; i++) {
                        line = matcher.replaceFirst("");
                        matcher.reset(line);
                    }
                }
                tempList.add(line);
            }
        }
        rewriteFile(tempList, fileName);
    }

    //task Nine
    public static void deleteComments(String fileName) throws IOException{
        List<String> tempList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String newLine;
        }

        rewriteFile(tempList, fileName);
    }
}


