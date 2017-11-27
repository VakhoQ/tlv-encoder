package ge.vq.tlv.constants;

/**
 * @author vakhtang.koroghlishvili
 */
public class CommonConstants {

    /**
     * Encoding for ASCII 0-255 byte
     */
    public static final String WINDOWS_1251_ENCODING = "Windows-1251";


    /**
     * Encoding for UTF16 LE
     */
    public static final String UTF16_ENCODING = "UTF-16LE";

    /**
     * Data size can be 1,2,4,8. In order to save the values use 2 byte
     * because of the protocol specification.
     * {@link ge.vq.tlv.structure.UnsignedDataTypes}
     */
    public static final int DATA_SIZE_BYTES_LENGTH = 2;


}
