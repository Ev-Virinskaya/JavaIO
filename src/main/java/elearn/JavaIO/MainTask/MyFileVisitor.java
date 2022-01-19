package elearn.JavaIO.MainTask;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MyFileVisitor extends SimpleFileVisitor<Path> {

    private List<String> filenames = new ArrayList<>();


    public List<String> getFilenames() {
        return filenames;
    }

    protected MyFileVisitor() {
        super();
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (Files.isDirectory(dir)) {
            filenames.add(String.format("|-----%s\n", dir.getFileName()));
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (Files.isRegularFile(file)) {
            filenames.add(String.format("|     %s\n", file.getFileName().toString()));
        }
        return FileVisitResult.CONTINUE;
    }
}
