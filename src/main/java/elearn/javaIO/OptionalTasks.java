package elearn.javaIO;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OptionalTasks {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        try {
            createFileWithRandomNumbers(fileName, 10);
            replacePublicWithPrivate(fileName,  "private","public");
            writeStringReverseOrder(fileName, "src/main/java/elearn/javaIO/mainTask/MainTask.java");
            replaceLowercaseWithUppercase(fileName);
            deleteWords(fileName);
            deleteComments(fileName);
            replaceWords(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rewriteFile(List<String> list, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String line : list) {
                writer.write(line);
                writer.write("\n");
            }
        }
    }

    //task One
    //Создать и заполнить файл случайными целыми числами.
    // Отсортировать содержимое файла по возрастанию.
    public static void createFileWithRandomNumbers(String fileName, int dataSize) throws IOException {
        File newFile = new File(fileName);
        StringWriter writer = new StringWriter();
        int[] array = new int[dataSize];

        for (int i = 0; i < dataSize; i++) {
            writer.append(String.valueOf(Math.round(Math.random() * 10)))
                    .append(System.lineSeparator());
        }
        String[] strArray = writer.toString().split(System.lineSeparator());
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.parseInt(strArray[i]);
        }
        Arrays.sort(array);
        if (!newFile.exists()) {
            newFile.createNewFile();
        }

        try (FileWriter sortedWriter = new FileWriter(newFile)) {
            for (int randomInt : array) {
                sortedWriter.write(randomInt + System.lineSeparator());
            }
        }
    }

    //task Two
    //Прочитать текст Java-программы и все слова public в объявлении атрибутов и методов класса
    // заменить на слово private.
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
    //Прочитать текст Java-программы
    // и записать в другой файл в обратном порядке символы каждой строки.
    public static void writeStringReverseOrder(String fileToWrite, String fileToRead) throws IOException {
        File fileReverseOrder = new File(fileToWrite);
        try (FileWriter writer = new FileWriter(fileReverseOrder);
             BufferedReader reader = new BufferedReader(new FileReader(fileToRead))) {
            while (reader.ready()) {
                StringBuilder reverseString = new StringBuilder();
                char[] line = reader.readLine().toCharArray();
                for (int i = line.length - 1; i >= 0; i--) {
                    reverseString.append(line[i]);
                }
                writer.write(reverseString.toString());
                writer.write("\n");
            }
        }
    }

    //task Four
    //Прочитать текст Java-программы и в каждом слове длиннее двух символов
    // все строчные символы заменить прописными.
    public static void replaceLowercaseWithUppercase(String fileName) throws IOException {
        List<String> tempList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.ready()) {
                String newLine = reader.readLine();
                String[] line = newLine.trim().split("\\W");
                for (String str : line) {
                    if (str.length() > 2) {
                        newLine = newLine.replaceFirst(str, str.toUpperCase());
                    }
                }
                tempList.add(newLine);
            }
        }
        rewriteFile(tempList, fileName);
    }

    //task Seven
    // Из файла удалить все слова, содержащие от трех до пяти символов,
    // но при этом из каждой строки должно быть удалено только максимальное четное количество таких слов.
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
    // Из текста Java-программы удалить все виды комментариев.
    public static void deleteComments(String fileName) throws IOException {

        List<String> tempList = new ArrayList<>();
        Pattern pattern = Pattern.compile("(/\\*.+\\*/|//.+$)");
        Matcher matcher = pattern.matcher("");
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String newLine;
            while (reader.ready()) {
                newLine = findMultilineComment(reader);
                matcher.reset(newLine);
                while (matcher.find()) {
                    newLine = matcher.replaceAll("");
                }
                if (!matcher.find()) {
                    tempList.add(newLine);
                }
            }
        }
        rewriteFile(tempList, fileName);
    }

    public static String findMultilineComment(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String newLine = reader.readLine();
        builder.append(newLine);
        if (newLine.contains("/*") && !newLine.contains("*/")) {
            while (!(newLine = reader.readLine()).contains("*/")) {
                builder.append(" ").append(newLine);
            }
            builder.append(" ").append(newLine);
        }
        return builder.toString();
    }

    //task ten
    //Прочитать строки из файла и поменять местами первое и последнее слова в каждой строке.
    public static void replaceWords(String fileName) throws IOException {
        List<String> newString = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.ready()) {
                String[] nextLine = reader.readLine().trim().split(" ");
                String newFirstWord = nextLine[nextLine.length - 1];
                nextLine[nextLine.length - 1] = nextLine[0];
                nextLine[0] = newFirstWord;
                StringBuilder builder = new StringBuilder();
                for (String word : nextLine) {
                    builder.append(word).append(" ");
                }
                newString.add(builder.toString().trim());
            }
        }
        rewriteFile(newString, fileName);
    }
}

