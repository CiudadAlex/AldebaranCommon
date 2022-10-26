package org.aldebaran.common.utils.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ProcessUtils.
 *
 * @author Alejandro
 *
 */
public final class ProcessUtils {

    private ProcessUtils() {
    }

    /**
     * Logs the process ourput.
     *
     * @param proc
     *            the process
     * @throws IOException
     */
    public static void logProcessOutput(final Process proc) throws IOException {

        final BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        final BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        // Read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        // Read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
    }
}
