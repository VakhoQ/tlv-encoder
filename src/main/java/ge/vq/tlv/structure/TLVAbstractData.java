package ge.vq.tlv.structure;

/**
 * @author vakhtang.koroghlishvili
 */
public abstract class TLVAbstractData {

    private TLVType type;

    private String key;

    public TLVType getType() {
        return type;
    }

    void setType(TLVType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    void setKey(String key) {
        this.key = key;
    }

    public abstract String getStringValue();

    @Override
    public String toString() {
        return String.format("key: %s. value: %s. ", getKey(), getStringValue());
    }
}