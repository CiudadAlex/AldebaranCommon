package org.aldebaran.common.utils.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

/**
 * SerializationUtils.
 *
 * @author Alejandro
 *
 */
public final class SerializationUtils {

    private SerializationUtils() {
    }

    /**
     * Serializes object in Base64.
     *
     * @param obj
     *            object
     * @return object serialized in Base64
     * @throws Exception
     */
    public static String serializeObjectToBase64(final Object obj) throws Exception {

        final byte[] baObj = serializeObject(obj);

        final String baObjB64 = Base64.getEncoder().encodeToString(baObj);
        return baObjB64;
    }

    /**
     * Serializes object.
     *
     * @param obj
     *            object
     * @return object serialized
     * @throws Exception
     */
    public static byte[] serializeObject(final Object obj) throws Exception {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(obj);
        oos.flush();

        final byte[] baObj = baos.toByteArray();

        oos.close();
        baos.close();
        return baObj;
    }

    /**
     * Deserializes the object from Base64.
     *
     * @param <T>
     *            type of object
     * @param baObjB64
     *            object serialized in Base64
     * @return the original Object
     * @throws Exception
     */
    public static <T> T deserializeObjectFromBase64(final String baObjB64) throws Exception {

        final byte[] baObj = Base64.getDecoder().decode(baObjB64);

        return deserializeObjectFromByteArray(baObj);
    }

    /**
     * Deserializes the object from Base64.
     *
     * @param <T>
     *            type of object
     * @param baObj
     *            object serialized
     * @return the original Object
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserializeObjectFromByteArray(final byte[] baObj) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baObj))) {
            return (T) ois.readObject();
        }
    }
}
