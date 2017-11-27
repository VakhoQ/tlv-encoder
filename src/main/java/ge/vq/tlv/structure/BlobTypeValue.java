package ge.vq.tlv.structure;

/**
 * @author vakhtang.koroghlishvili
 */
public class BlobTypeValue extends TLVAbstractData {

    private final byte[] value;

    public BlobTypeValue(String key, byte[] value, boolean isEncoded) {
        this.setKey(key);
        this.value = value;
        if (isEncoded) {
            this.setType(TLVType.ENCODEDBLOB);
        } else {
            this.setType(TLVType.BLOB);
        }
    }

    public byte[] getValue() {
        return value;
    }

    @Override
    public String getStringValue() {
        return new String(this.getValue());
    }
}
