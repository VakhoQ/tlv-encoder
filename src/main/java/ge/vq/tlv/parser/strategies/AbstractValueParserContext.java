package ge.vq.tlv.parser.strategies;

import static ge.vq.tlv.structure.TLVType.*;

/**
 * @author vakhtang.koroghlishvili
 *         Strategy context which decides which processing type strategy you need
 */
public class AbstractValueParserContext {

    private final byte type;

    public AbstractValueParserContext(byte type) {
        this.type = type;
    }

    public AbstractValueParserStrategy getStrategy() {

        if (Byte.toUnsignedInt(type) == NUMERIC.getValue()) {
            return new NumericParserStrategy();
        } else if (Byte.toUnsignedInt(type) == STRING.getValue()) {
            return new StringProcessingStrategy();
        } else if (Byte.toUnsignedInt(type) == UNICODE_STRING.getValue()) {
            return new UStringParserStrategy();
        } else if (Byte.toUnsignedInt(type) == BLOB.getValue() || Byte.toUnsignedInt(type) == ENCODEDBLOB.getValue()) {
            return new BlobParserStrategy();
        }
        throw new RuntimeException("Unknown Type detected");
    }

}
