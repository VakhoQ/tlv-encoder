package ge.vq.tlv.parser.strategies;

import ge.vq.tlv.structure.BlobTypeValue;
import ge.vq.tlv.structure.TLVAbstractData;

import static ge.vq.tlv.utils.BiteCodeUtils.readValue;

/**
 * @author vakhtang.koroghlishvili
 *         unsigned blob processing strategy
 */
public class UBlobParserStrategy extends BlobParserStrategy {

    @Override
    public TLVAbstractData decode(byte[] decoded, int offset, String key, int dataSize) {
        return new BlobTypeValue(key, readValue(decoded, offset, dataSize), true);
    }

}
