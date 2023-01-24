package com.epam.fastfilemover;

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
import java.nio.file.StandardCopyOption;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;

public class FastFileMover {
    public static boolean moveWithFileStreams(String from, String to) {
        File copied = new File("src/test/resources/copiedWithIo.txt");
        try (
                InputStream in = new FileInputStream(from);
                OutputStream out = new FileOutputStream(to)) {
            int r = in.read();
            while (r != -1) {
                out.write(r);
                r = in.read();
            }
            System.out.println("The file was successfully copied with File Streams");
            return true;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }

    public static boolean moveWithFileStreamsWithBuffer(String from, String to) {
        try (
                InputStream in = new BufferedInputStream(new FileInputStream(from));
                OutputStream out = new BufferedOutputStream(new FileOutputStream(to))) {
            byte[] buffer = new byte[100];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
            System.out.println("The file was successfully copied with File Streams with buffer");
            return true;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }

    public static boolean moveWithNio(String from, String to) {
        try {

            Files.copy(new File(from).toPath(), new File(to).toPath(),
                    StandardCopyOption.REPLACE_EXISTING, COPY_ATTRIBUTES);
            System.out.println("The file was successfully copied with Nio2");
            return true;

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }

    public static boolean moveWithFileChannel(String from, String to) {
        try (
                RandomAccessFile fromRandomAccessFile = new RandomAccessFile(from, "rw");
                FileChannel fromChannel = fromRandomAccessFile.getChannel();
                RandomAccessFile toRandomAccessFile = new RandomAccessFile(to, "rw");
                FileChannel toChannel = toRandomAccessFile.getChannel();
        ) {
            ByteBuffer fromBuffer = ByteBuffer.allocate(100);
            ByteBuffer toBuffer = ByteBuffer.allocate(100);
            int r = fromChannel.read(fromBuffer);
            while (r != -1) {
                fromBuffer.flip();
                while (fromBuffer.hasRemaining()) {
                    byte get = fromBuffer.get();
                    toBuffer.put(get);
                }
                toBuffer.flip();
                toChannel.write(toBuffer);
                fromBuffer.clear();
                toBuffer.clear();
                r = fromChannel.read(fromBuffer);
            }
            System.out.println("The file was successfully copied with FileChannel");
            return true;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }
}
