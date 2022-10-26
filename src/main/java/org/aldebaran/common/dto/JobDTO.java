package org.aldebaran.common.dto;

/**
 * JobDTO.
 *
 * @author Alejandro
 *
 */
public class JobDTO {

    private String applicationName;
    private String baMapStringCallableB64;

    /** Constructor for JobDTO. */
    public JobDTO() {
    }

    /**
     * Constructor for JobDTO.
     *
     * @param applicationName
     *            applicationName
     * @param baMapStringCallableB64
     *            baMapStringCallableB64
     */
    public JobDTO(final String applicationName, final String baMapStringCallableB64) {
        this.applicationName = applicationName;
        this.baMapStringCallableB64 = baMapStringCallableB64;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(final String applicationName) {
        this.applicationName = applicationName;
    }

    public String getBaMapStringCallableB64() {
        return baMapStringCallableB64;
    }

    public void setBaMapStringCallableB64(final String baMapStringCallableB64) {
        this.baMapStringCallableB64 = baMapStringCallableB64;
    }

}
