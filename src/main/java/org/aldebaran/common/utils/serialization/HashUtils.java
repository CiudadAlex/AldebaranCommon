package org.aldebaran.common.utils.serialization;

import java.util.List;

import org.aldebaran.common.dto.FileDTO;
import org.aldebaran.common.dto.RedistributionOfJARsDTO;
import org.aldebaran.common.utils.lists.ListsUtils;
import org.aldebaran.common.utils.text.TextUtils;

/**
 * HashUtils.
 *
 * @author Alejandro
 *
 */
public final class HashUtils {

    private HashUtils() {
    }

    /**
     * Returns the hash.
     *
     * @param redistributionOfJARsDTO
     *            redistributionOfJARsDTO
     * @return the hash
     */
    public static int getHash(final RedistributionOfJARsDTO redistributionOfJARsDTO) {

        final List<FileDTO> listFileDTO = redistributionOfJARsDTO.getListFileDTO();

        final StringBuilder sb = new StringBuilder();

        ListsUtils.orderListLong(listFileDTO, f -> (long) f.getName().hashCode(), true);
        final String concatFileInfo = TextUtils.concatList(listFileDTO, "|", f -> f.getName() + "&" + f.getContentB64().hashCode());
        sb.append(concatFileInfo);

        return sb.toString().hashCode();
    }
}
