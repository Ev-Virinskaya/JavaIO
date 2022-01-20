package elearn.JavaIO.MainTask;



import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyFileVisitor extends SimpleFileVisitor<Path> {

    private final List<String> filenames = new ArrayList<>();

    public List<String> getFilenames() {
        return filenames;
    }

    protected MyFileVisitor() {
        super();
    }

    private int createDepth(Path path) {
        Pattern pattern = Pattern.compile("(\\\\)");
        Matcher matcher = pattern.matcher(path.toString());
        int count = 0;

        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private String formatFilesRepresentation(Path path) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < createDepth(path); i++) {
            builder.append("     ");
        }
        return builder.toString();
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if (Files.isDirectory(dir)) {
            filenames.add(formatFilesRepresentation(dir) + (String.format("|-----%s\n", dir.getFileName())));
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (Files.isRegularFile(file)) {
            filenames.add(formatFilesRepresentation(file) + String.format("|     %s\n", file.getFileName().toString()));
            System.out.println(file);
        }
        return FileVisitResult.CONTINUE;
    }
}
