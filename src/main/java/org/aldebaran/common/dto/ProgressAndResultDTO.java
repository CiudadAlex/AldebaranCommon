package org.aldebaran.common.dto;

/**
 * ProgressAndResultDTO.
 *
 * @author Alejandro
 *
 */
public class ProgressAndResultDTO {

    private Integer progress;
    private String baMapStringObjectB64;
    private String baFailureDetailsB64;

    /** Constructor for ProgressAndResultDTO. */
    public ProgressAndResultDTO() {
    }

    /**
     * Constructor for ProgressAndResultDTO.
     *
     * @param progress
     *            progress
     * @param baMapStringObjectB64
     *            baMapStringObjectB64
     * @param baFailureDetailsB64
     *            baFailureDetailsB64
     */
    public ProgressAndResultDTO(final Integer progress, final String baMapStringObjectB64, final String baFailureDetailsB64) {
        this.progress = progress;
        this.baMapStringObjectB64 = baMapStringObjectB64;
        this.baFailureDetailsB64 = baFailureDetailsB64;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(final Integer progress) {
        this.progress = progress;
    }

    public String getBaMapStringObjectB64() {
        return baMapStringObjectB64;
    }

    public void setBaMapStringObjectB64(final String baMapStringObjectB64) {
        this.baMapStringObjectB64 = baMapStringObjectB64;
    }

    public String getBaFailureDetailsB64() {
        return baFailureDetailsB64;
    }

    public void setBaFailureDetailsB64(final String baFailureDetailsB64) {
        this.baFailureDetailsB64 = baFailureDetailsB64;
    }

}
