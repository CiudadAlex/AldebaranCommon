package org.aldebaran.common.utils.os;

/**
 * OSUtils.
 *
 * @author Alejandro
 *
 */
public final class OSUtils {

    private static OS os;

    private OSUtils() {
    }

    /**
     * Retuns the Operating System.
     *
     * @return the Operating System
     */
    public static OS getOS() {

        if (os == null) {

            final String operSys = System.getProperty("os.name").toLowerCase();

            if (operSys.contains("win")) {
                os = OS.WINDOWS;

            } else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
                os = OS.LINUX;

            } else if (operSys.contains("mac")) {
                os = OS.MAC;

            } else if (operSys.contains("sunos")) {
                os = OS.SOLARIS;
            }
        }

        return os;
    }
}
