package elearn.JavaIO.MainTask;

import java.io.*;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.List;

public class MainTask {
    private static Path path;

    public static void main(String[] args) {
        path = Paths.get(args[0]);
        if (Files.isRegularFile(path)) {
            printFileContext(path);
        } else if (Files.isDirectory(path)) {
            writeDirectoryContextToFile();
        }
    }

    public static void printFileContext(Path path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                System.out.println(nextLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDirectoryContextToFile() {
        Path resultFile = Paths.get(path.toString() + "/directoryContext.txt");
        if (!Files.exists(resultFile)) {
            try {
                Files.createFile(resultFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(resultFile.toFile()))) {
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
            Files.walkFileTree(path, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, myFileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myFileVisitor.getFilenames();
    }

}
