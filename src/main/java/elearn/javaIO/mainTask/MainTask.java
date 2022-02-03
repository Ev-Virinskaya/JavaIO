package elearn.javaIO.mainTask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTask {
    private static Path path;

    public static void main(String[] args) {
        path = Paths.get("E:\\учеба\\java\\tutorials\\annotations");
        //path = Paths.get("e:/elearn/JavaIO/data/MainTaskFile.txt");
        if (Files.isRegularFile(path)) {
            printFileContext(path);
        } else if (Files.isDirectory(path)) {
            writeDirectoryContextToFile("e:/elearn/JavaIO/data/MainTask.txt");
        }
    }

    public static void printFileContext(Path path) {
        long amountFiles = 0;
        double averageLengthFileName = 0;
        long amountFolders = 0;
        Pattern pattern = Pattern.compile("(.+\\.[A-Za-z0-9]{1,10}\\b)");
        Matcher matcher = pattern.matcher("");
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String nextLine;

            while ((nextLine = reader.readLine()) != null) {
                matcher.reset(nextLine);
                if(matcher.find()){
                    amountFiles++;
                    averageLengthFileName += nextLine.length();
                } else {
                    amountFolders++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Количество папок: " + amountFolders);
        System.out.println("Количество файлов: " + amountFiles);
        System.out.println("Среднее количество файлов в папке: " + (amountFiles/amountFolders));
        System.out.println("Средняя длинна названия файла: " + averageLengthFileName/amountFiles);
    }

    public static void writeDirectoryContextToFile(String fileName) {
        Path resultFile = Paths.get(fileName);
        if (!Files.exists(resultFile)) {
            try {
                Files.createFile(resultFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter writer = new FileWriter(resultFile.toFile())) {
            for (String string : getTreeList()) {
                writer.write(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTreeList() {
        MyFileVisitor myFileVisitor = new MyFileVisitor();
        try {
            Files.walkFileTree(path, myFileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myFileVisitor.getFilenames();
    }

}
