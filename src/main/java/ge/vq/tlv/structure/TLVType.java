package ge.vq.tlv.structure;

/**
 * @author vakhtang.koroghlishvili
 */
public enum TLVType {

    NUMERIC(1),
    STRING(2),
    BLOB(3),
    ENCODEDBLOB(4),
    UNICODE_STRING(5);


    private final int type;

    TLVType(int type) {
        this.type = type;
    }

    public int getValue() {
        return type;
    }

}
