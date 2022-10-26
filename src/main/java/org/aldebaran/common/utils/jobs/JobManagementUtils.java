package org.aldebaran.common.utils.jobs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.aldebaran.common.dto.FileDTO;
import org.aldebaran.common.utils.file.FileUtils;
import org.aldebaran.common.utils.log.AppLogger;

/**
 * JobManagementUtils.
 *
 * @author Alejandro
 *
 */
public final class JobManagementUtils {

    private static final String SERIALIZED_JOB_FILEMANE = "serialized_job.bin";

    private static final String SERIALIZED_RESULT_FILEMANE = "serialized_result.bin";

    private static final String INITIATED_JOB_FILEMANE = "initiated.job";

    private static final String ENDED_JOB_FILEMANE = "ended.job";

    private static final String FAILED_JOB_FILEMANE = "failed.job";

    private static final String REPLACEMENT = "####";
    private static final String PROGRESS_EXTENSION = ".progress";

    private static final String PROGRESS_FILEMANE_PATTERN = REPLACEMENT + PROGRESS_EXTENSION;

    private JobManagementUtils() {
    }

    /**
     * Stores the initialization of a job.
     *
     * @param pathJobSerialization
     *            pathJobSerialization
     * @param baB64
     *            baB64
     * @throws IOException
     */
    public static void storeInitializationOfJob(final String pathJobSerialization, final String baB64) throws IOException {

        clearFiles(pathJobSerialization);

        FileUtils.createEmptyFile(pathJobSerialization, INITIATED_JOB_FILEMANE);

        final FileDTO fileDTO = new FileDTO(SERIALIZED_JOB_FILEMANE, baB64);
        FileUtils.buildFile(fileDTO, pathJobSerialization);
    }

    /**
     * Reads job.
     *
     * @param pathJobSerialization
     *            pathJobSerialization
     * @return the serialized job
     * @throws IOException
     */
    public static byte[] readJob(final String pathJobSerialization) throws IOException {

        final File file = new File(pathJobSerialization + File.separatorChar + SERIALIZED_JOB_FILEMANE);
        return FileUtils.getFileContent(file);
    }

    /**
     * Reads the result.
     *
     * @param pathJobSerialization
     *            pathJobSerialization
     * @return the result
     * @throws IOException
     */
    public static FileDTO readResult(final String pathJobSerialization) throws IOException {

        final File file = new File(pathJobSerialization + File.separatorChar + SERIALIZED_RESULT_FILEMANE);
        return FileUtils.buildFileDTO(file);
    }

    /**
     * Reads the failure.
     *
     * @param pathJobSerialization
     *            pathJobSerialization
     * @return the failure
     * @throws IOException
     */
    public static FileDTO readFailure(final String pathJobSerialization) throws IOException {

        final File file = new File(pathJobSerialization + File.separatorChar + FAILED_JOB_FILEMANE);
        return FileUtils.buildFileDTO(file);
    }

    /**
     * Stores the end of a job.
     *
     * @param pathJobSerialization
     *            pathJobSerialization
     * @param ba
     *            byte array of the content
     * @throws IOException
     */
    public static void storeEndOfJob(final String pathJobSerialization, final byte[] ba) throws IOException {

        FileUtils.buildFile(pathJobSerialization, SERIALIZED_RESULT_FILEMANE, ba);

        FileUtils.createEmptyFile(pathJobSerialization, ENDED_JOB_FILEMANE);
    }

    /**
     * Stores the failure of a job.
     *
     * @param pathJobSerialization
     *            pathJobSerialization
     * @param exception
     *            exception
     * @throws IOException
     */
    public static void storeFailedJob(final String pathJobSerialization, final Exception exception) throws IOException {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(baos, true);
        exception.printStackTrace(printStream);

        printStream.flush();

        FileUtils.buildFile(pathJobSerialization, FAILED_JOB_FILEMANE, baos.toByteArray());
    }

    /**
     * Stores the progress.
     *
     * @param pathJobSerialization
     *            pathJobSerialization
     * @param progress
     *            progress
     * @throws IOException
     */
    public static void storeProgress(final String pathJobSerialization, final int progress) throws IOException {

        final String progressFilename = PROGRESS_FILEMANE_PATTERN.replace(REPLACEMENT, "" + progress);
        FileUtils.createEmptyFile(pathJobSerialization, progressFilename);

        final File dir = new File(pathJobSerialization);

        for (final File file : dir.listFiles()) {

            final String fileName = file.getName();

            if (fileName.endsWith(PROGRESS_EXTENSION) && !fileName.equals(progressFilename)) {
                file.delete();
            }
        }
    }

    /**
     * Reads the progress.
     *
     * @param pathJobSerialization
     *            pathJobSerialization
     * @return the progress
     */
    public static int readProgress(final String pathJobSerialization) throws IOException {

        final File dir = new File(pathJobSerialization);

        for (final File file : dir.listFiles()) {

            final String fileName = file.getName();

            if (fileName.endsWith(PROGRESS_EXTENSION)) {

                try {
                    return Integer.parseInt(fileName.replace(PROGRESS_EXTENSION, ""));

                } catch (final Exception e) {
                    AppLogger.logError("Error reading the progress", e);
                }
            }
        }

        return 0;
    }

    /**
     * Returns if the job is initiated.
     *
     * @param pathJobSerialization
     *            pathJobSerialization
     * @return if the job is initiated
     */
    public static boolean isJobStarted(final String pathJobSerialization) {
        return existsFile(pathJobSerialization, INITIATED_JOB_FILEMANE);
    }

    /**
     * Returns if the job is ended.
     *
     * @param pathJobSerialization
     *            pathJobSerialization
     * @return if the job is ended
     */
    public static boolean isJobEnded(final String pathJobSerialization) {
        return existsFile(pathJobSerialization, ENDED_JOB_FILEMANE);
    }

    /**
     * Returns if the job is failed.
     *
     * @param pathJobSerialization
     *            pathJobSerialization
     * @return if the job is failed
     */
    public static boolean isJobFailed(final String pathJobSerialization) {
        return existsFile(pathJobSerialization, FAILED_JOB_FILEMANE);
    }

    private static boolean existsFile(final String pathJobSerialization, final String fileName) {

        final File file = new File(pathJobSerialization + File.separatorChar + fileName);
        return file.exists();
    }

    private static void clearFiles(final String pathJobSerialization) {

        final File dir = new File(pathJobSerialization);
        final File[] arrFile = dir.listFiles();

        if (arrFile == null) {
            return;
        }

        for (final File f : arrFile) {
            f.delete();
        }
    }
}
