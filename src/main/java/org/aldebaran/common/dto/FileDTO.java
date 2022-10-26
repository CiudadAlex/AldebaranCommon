package org.aldebaran.common.dto;

/**
 * FileDTO.
 *
 * @author Alejandro
 *
 */
public class FileDTO {

    private String name;
    private String contentB64;

    /** Constructor for FileDTO. */
    public FileDTO() {
    }

    /**
     * Constructor for FileDTO.
     *
     * @param name
     *            name
     * @param contentB64
     *            contentB64
     */
    public FileDTO(final String name, final String contentB64) {
        this.name = name;
        this.contentB64 = contentB64;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getContentB64() {
        return contentB64;
    }

    public void setContentB64(final String contentB64) {
        this.contentB64 = contentB64;
    }

}
