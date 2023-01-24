package com.epam.fastfilemover;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.in;
import static java.lang.System.out;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Command(
        name = "fast-file-mover",
        description = "Fast-file-mover for command line ",
        version = "fast-file-mover 1.0",
        mixinStandardHelpOptions = true
)
public class App implements Callable<Integer> {

    public App() {
    }

    public static void main(String[] args) {
        System.exit(
                new CommandLine(new App())
                        .setCaseInsensitiveEnumValuesAllowed(true)
                        .execute(args)
        );
    }

    @Option(names = {"-mfs", "--move-files-streams"},
            description = "FastFileMover uses FileStreams")
    private boolean isUseFileStreams;

    @Option(names = {"-mfsb", "--move-files-streams-with-buffer"},
            description = "FastFileMover uses FileStreams")
    private boolean isUseFileStreamsWithBuffer;

    @Option(names = {"-mfc", "--move-file-channel"},
            description = "FastFileMover uses FileChannel")
    private boolean isUseFileChannel;

    @Option(names = {"-mnio", "--move-nio"},
            description = "FastFileMover uses NIO 2")
    private boolean isUseNIO;

    @Parameters(index = "0",
            description = "File for moving")
    private String from;

    @Parameters(index = "1",
            description = "Path for moving")
    private String to;

    @Override
    public Integer call() throws Exception {
        out.println("Start");
        int vaidatePathCopyTo = isFileOrDirectory(to);
        if (isFileOrDirectory(from) == 0 && vaidatePathCopyTo!= -1 ) {
            if (vaidatePathCopyTo == 1) {
                String newPathForTo= to + "/" + new File(from).getName();
                File file = new File(newPathForTo);
                to = newPathForTo;
            }
            if (isUseFileStreams) {
                FastFileMover.moveWithFileStreams(from, to);
            }
            if (isUseFileStreamsWithBuffer) {
                FastFileMover.moveWithFileStreamsWithBuffer(from, to);
            }
            if (isUseFileChannel) {
                FastFileMover.moveWithFileChannel(from, to);
            }
            if (isUseNIO) {
                FastFileMover.moveWithNio(from, to);
            }
            if (!(isUseFileStreams||isUseFileStreamsWithBuffer||isUseFileChannel||isUseNIO)){
                FastFileMover.moveWithFileStreams(from, to);
            }

        } else {
            out.println("Invalid parameters");
        }

        return 1;
    }



    public int isFileOrDirectory(String path) {
        File f = new File(path);
        if (f.exists()) {
            return Files.isDirectory(f.toPath()) ? 1 : 0;
        } else return -1;
    }
}
