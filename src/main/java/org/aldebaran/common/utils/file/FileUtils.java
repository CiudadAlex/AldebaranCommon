package org.aldebaran.common.utils.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.aldebaran.common.dto.FileDTO;

/**
 * FileUtils.
 *
 * @author Alejandro
 *
 */
public final class FileUtils {

    private FileUtils() {
    }

    /**
     * Returns the file content.
     *
     * @param file
     *            file
     * @return the file content
     */
    public static byte[] getFileContent(final File file) throws IOException {

        try (final FileInputStream fis = new FileInputStream(file)) {

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (fis.available() > 0) {
                final int b = fis.read();
                baos.write(b);
            }

            return baos.toByteArray();
        }
    }

    /**
     * Finds recursively all the files with a given extension under the given
     * path.
     *
     * @param path
     *            path
     * @param extension
     *            extension
     * @return list of files with a given extension under the given path
     */
    public static List<File> findFilesWithExtensionRecursively(final String path, final String extension) {

        final File dir = new File(path);
        return findFilesWithExtensionRecursively(dir, extension);
    }

    private static List<File> findFilesWithExtensionRecursively(final File dir, final String extension) {

        final List<File> listJARs = new ArrayList<File>();

        final File[] arrayFile = dir.listFiles();

        if (arrayFile == null) {
            return listJARs;
        }

        for (final File file : arrayFile) {

            if (file.isDirectory()) {

                final List<File> listJARsSubDir = findFilesWithExtensionRecursively(file, extension);
                listJARs.addAll(listJARsSubDir);

            } else if (file.isFile()) {

                if (file.getName().toLowerCase().endsWith("." + extension)) {
                    listJARs.add(file);
                }
            }
        }

        return listJARs;
    }

    /**
     * Builds the FileDTO from a file.
     *
     * @param file
     *            file
     * @return the FileDTO
     * @throws IOException
     */
    public static FileDTO buildFileDTO(final File file) throws IOException {

        final byte[] content = FileUtils.getFileContent(file);
        final String contentB64 = Base64.getEncoder().encodeToString(content);

        final FileDTO fileDTO = new FileDTO(file.getName(), contentB64);

        return fileDTO;
    }

    /**
     * Builds the file from a FileDTO.
     *
     * @param fileDTO
     *            fileDTO
     * @param pathToStore
     *            directory to store the file
     * @return the file
     * @throws IOException
     */
    public static File buildFile(final FileDTO fileDTO, final String pathToStore) throws IOException {

        final String name = fileDTO.getName();
        final String contentB64 = fileDTO.getContentB64();
        final byte[] content = Base64.getDecoder().decode(contentB64);

        return buildFile(pathToStore, name, content);
    }

    /**
     * Builds the file.
     *
     * @param pathToStore
     *            pathToStore
     * @param name
     *            name
     * @param content
     *            content
     * @return the file
     * @throws IOException
     */
    public static File buildFile(final String pathToStore, final String name, final byte[] content) throws IOException {

        final File file = createEmptyFile(pathToStore, name);

        try (final FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
        }

        return file;
    }

    /**
     * Builds and empty file.
     *
     * @param pathToStore
     *            pathToStore
     * @param name
     *            name
     * @return the file
     * @throws IOException
     */
    public static File createEmptyFile(final String pathToStore, final String name) throws IOException {

        final File dir = new File(pathToStore);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        final File file = new File(pathToStore + File.separatorChar + name);

        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

}
