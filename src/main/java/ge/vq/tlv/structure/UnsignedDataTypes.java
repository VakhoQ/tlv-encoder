package ge.vq.tlv.structure;

/**
 * @author vakhtang.koroghlishvili
 *         Unsigned numbers maximum size and length of byte array for this number.
 *         Hex values are represented in String format.
 */
public enum UnsignedDataTypes {

    UBYTE("FF", 1),
    USHORT("FFFF", 2),
    UINT("FFFFFFFF", 4),
    ULONG("FFFFFFFFFFFFFFFF", 8);

    private final String maxSize;
    private final int byteAllocation;

    UnsignedDataTypes(String maxSize, int byteAllocation) {
        this.maxSize = maxSize;
        this.byteAllocation = byteAllocation;
    }

    public String MAX_VALUE() {
        return maxSize;
    }

    public short BUFFER_SIZE() {
        return (short) byteAllocation;
    }
}
