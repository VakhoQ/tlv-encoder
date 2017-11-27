package ge.vq.tlv.structure;


import java.math.BigInteger;

/**
 * @author vakhtang.koroghlishvili
 * <p>
 * 64 Bit unsigned structure
 */
public class NumericTypeValue extends TLVAbstractData {

    public NumericTypeValue(String key, int value) {
        this(key, BigInteger.valueOf(value));
    }

    public NumericTypeValue(String key, long value) {
        this(key, BigInteger.valueOf(value));
    }


    public NumericTypeValue(String key, BigInteger value) {
        this(key, value, TLVType.NUMERIC);
    }

    private NumericTypeValue(String key, BigInteger value, TLVType type) {
        super();
        this.value = value;
        this.setType(type);
        this.setKey(key);
    }

    private BigInteger value;

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    @Override
    public String getStringValue() {
        return this.getValue().toString();
    }
}
