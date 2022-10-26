package org.aldebaran.common.utils.run;

/**
 * StringAndObjectDTO.
 *
 * @author Alejandro
 *
 */
public class IdAndResultDTO {

    private final String id;
    private final Object result;

    /**
     * Constructor for IdAndResultDTO.
     *
     * @param id
     *            id
     * @param result
     *            result
     */
    public IdAndResultDTO(final String id, final Object result) {
        this.id = id;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public Object getResult() {
        return result;
    }

}
