package org.aldebaran.common.dto;

import java.util.List;

/**
 * RedistributionOfJARsDTO.
 *
 * @author Alejandro
 *
 */
public class RedistributionOfJARsDTO {

    private String applicationName;
    private List<FileDTO> listFileDTO;
    private List<String> listLocationAgents;
    private int numLocationsToSend;

    /** Constructor for RedistributionOfJARsDTO. */
    public RedistributionOfJARsDTO() {
    }

    /**
     * Constructor for RedistributionOfJARsDTO.
     *
     * @param applicationName
     *            applicationName
     * @param listFileDTO
     *            listFileDTO
     * @param listLocationAgents
     *            listLocationAgents
     * @param numLocationsToSend
     *            numLocationsToSend
     */
    public RedistributionOfJARsDTO(final String applicationName, final List<FileDTO> listFileDTO, final List<String> listLocationAgents,
            final int numLocationsToSend) {

        this.applicationName = applicationName;
        this.listFileDTO = listFileDTO;
        this.listLocationAgents = listLocationAgents;
        this.numLocationsToSend = numLocationsToSend;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(final String applicationName) {
        this.applicationName = applicationName;
    }

    public List<FileDTO> getListFileDTO() {
        return listFileDTO;
    }

    public void setListFileDTO(final List<FileDTO> listFileDTO) {
        this.listFileDTO = listFileDTO;
    }

    public List<String> getListLocationAgents() {
        return listLocationAgents;
    }

    public void setListLocationAgents(final List<String> listLocationAgents) {
        this.listLocationAgents = listLocationAgents;
    }

    public int getNumLocationsToSend() {
        return numLocationsToSend;
    }

    public void setNumLocationsToSend(final int numLocationsToSend) {
        this.numLocationsToSend = numLocationsToSend;
    }

    /**
     * Clones the RedistributionOfJARsDTO but with a new listLocationAgents.
     *
     * @param listLocationAgentsNew
     *            listLocationAgentsNew
     * @return the cloned RedistributionOfJARsDTO
     */
    public RedistributionOfJARsDTO cloneWithOtherAgents(final List<String> listLocationAgentsNew) {
        return new RedistributionOfJARsDTO(applicationName, listFileDTO, listLocationAgentsNew, numLocationsToSend);
    }

}
