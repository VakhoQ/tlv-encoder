package ge.vq.tlv.structure;

/**
 * @author vakhtang.koroghlishvili
 */
public class StringTypeValue extends TLVAbstractData {

    private String value;

    public StringTypeValue(String key, String value) {
        this(key, value, TLVType.STRING);
    }

    public StringTypeValue(String key, String value, TLVType type) {
        super();
        this.setKey(key);
        this.setValue(value);
        this.setType(type);
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getStringValue() {
        return this.getValue();
    }
}
