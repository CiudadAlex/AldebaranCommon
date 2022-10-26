package org.aldebaran.common.utils.jar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.aldebaran.common.dto.FileDTO;
import org.aldebaran.common.dto.RedistributionOfJARsDTO;
import org.aldebaran.common.utils.file.FileUtils;
import org.aldebaran.common.utils.lists.ListsUtils;
import org.aldebaran.common.utils.log.TimeProgressLogger;
import org.aldebaran.common.utils.run.Callable;
import org.aldebaran.common.utils.run.ParallelExecutor;
import org.aldebaran.common.utils.serviceinvokers.AgentServiceInvoker;

/**
 * JarManagementUtils.
 *
 * @author Alejandro
 *
 */
public final class JarManagementUtils {

    private JarManagementUtils() {
    }

    /**
     * Prepares the JARs to send.
     *
     * @param applicationName
     *            applicationName
     * @param path
     *            path
     * @param listLocationAgents
     *            listLocationAgents
     * @param numLocationsToSend
     *            numLocationsToSend
     * @return list of JAR files
     * @throws IOException
     */
    public static RedistributionOfJARsDTO prepareJARsToSend(final String applicationName, final String path,
            final List<String> listLocationAgents, final int numLocationsToSend) throws IOException {

        final List<File> listJARs = findJARsRecursively(path);

        final List<FileDTO> listFileDTO = new ArrayList<FileDTO>();

        for (final File jar : listJARs) {

            final FileDTO fileDTO = FileUtils.buildFileDTO(jar);
            listFileDTO.add(fileDTO);
        }

        return new RedistributionOfJARsDTO(applicationName, listFileDTO, listLocationAgents, numLocationsToSend);
    }

    /**
     * Stores the JARs received.
     *
     * @param redistributionOfJARsDTO
     *            redistributionOfJARsDTO
     * @param pathToStore
     *            pathToStore
     * @return the list of files stored
     * @throws IOException
     */
    public static List<File> storeJARsReceived(final RedistributionOfJARsDTO redistributionOfJARsDTO, final String pathToStore)
            throws IOException {

        final List<File> listFile = new ArrayList<File>();

        for (final FileDTO fileDTO : redistributionOfJARsDTO.getListFileDTO()) {

            final File file = FileUtils.buildFile(fileDTO, pathToStore);
            listFile.add(file);
        }

        return listFile;
    }

    private static List<File> findJARsRecursively(final String path) {
        return FileUtils.findFilesWithExtensionRecursively(path, "jar");
    }

    /**
     * Redistributes the Jars.
     *
     * @param password
     *            password
     * @param redistributionOfJARsDTO
     *            redistributionOfJARsDTO
     * @throws Exception
     */
    public static void redistributeJARs(final String password, final RedistributionOfJARsDTO redistributionOfJARsDTO) throws Exception {

        final List<String> listLocationAgentsTotal = redistributionOfJARsDTO.getListLocationAgents();

        if (listLocationAgentsTotal.isEmpty()) {
            return;
        }

        final int numLocationsToSend = redistributionOfJARsDTO.getNumLocationsToSend();

        final List<Callable<Void>> listJob = new ArrayList<Callable<Void>>();

        final List<List<String>> partitionLocationAgents = ListsUtils.splitListInPortions(listLocationAgentsTotal, numLocationsToSend);

        final TimeProgressLogger progress = new TimeProgressLogger(partitionLocationAgents.size());

        for (final List<String> listLocationAgents : partitionLocationAgents) {

            final Callable<Void> job = () -> {

                redistributeJARsToAgent(password, redistributionOfJARsDTO, listLocationAgents);
                progress.stepFinishedAndPrintProgress();
                return null;
            };

            listJob.add(job);
        }

        ParallelExecutor.execute(10, listJob);
    }

    private static void redistributeJARsToAgent(final String password, final RedistributionOfJARsDTO redistributionOfJARsDTO,
            final List<String> listLocationAgents) {

        String locationToSend = listLocationAgents.get(0);
        final List<String> listLocationAgentsToSend = listLocationAgents.size() == 1 ? new ArrayList<String>()
                : listLocationAgents.subList(1, listLocationAgents.size());

        if (!locationToSend.contains(":")) {
            // Adds the agent default port
            locationToSend = locationToSend + ":8080";
        }

        final RedistributionOfJARsDTO redistributionOfJARsToSend = redistributionOfJARsDTO.cloneWithOtherAgents(listLocationAgentsToSend);
        AgentServiceInvoker.redistributeJars(password, redistributionOfJARsToSend, locationToSend);
    }

    /**
     * Returns the agents not up to date.
     *
     * @param password
     *            password
     * @param applicationName
     *            applicationName
     * @param hash
     *            hash
     * @param listLocationAgents
     *            listLocationAgents
     * @return the agents not up to date
     * @throws Exception
     */
    public static List<String> getAgentsNotUpToDate(final String password, final String applicationName, final Integer hash,
            final List<String> listLocationAgents) throws Exception {

        final List<Callable<String>> listJob = new ArrayList<Callable<String>>();

        final TimeProgressLogger progress = new TimeProgressLogger(listLocationAgents.size());

        for (final String locationAgent : listLocationAgents) {

            final Callable<String> job = () -> {

                final String locationIfNotUpToDate = checkAndReturnAgentIfNotUpToDate(password, applicationName, hash, locationAgent);
                progress.stepFinishedAndPrintProgress();
                return locationIfNotUpToDate;
            };

            listJob.add(job);
        }

        final List<String> listLocationAgentsNotUpToDate = ParallelExecutor.execute(10, listJob);

        return ListsUtils.filterList(listLocationAgentsNotUpToDate, s -> s != null);
    }

    private static String checkAndReturnAgentIfNotUpToDate(final String password, final String applicationName, final Integer hash,
            final String locationAgent) {

        final boolean isUpToDate = AgentServiceInvoker.checkJarsUpToDate(password, applicationName, hash, locationAgent);

        if (!isUpToDate) {
            return locationAgent;
        } else {
            return null;
        }
    }

}
