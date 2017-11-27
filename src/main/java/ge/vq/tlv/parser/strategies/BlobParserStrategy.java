package ge.vq.tlv.parser.strategies;

import com.google.common.base.Preconditions;
import ge.vq.tlv.structure.BlobTypeValue;
import ge.vq.tlv.structure.TLVAbstractData;
import ge.vq.tlv.utils.BiteCodeUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static ge.vq.tlv.constants.CommonConstants.DATA_SIZE_BYTES_LENGTH;
import static ge.vq.tlv.utils.BiteCodeUtils.readValue;


/**
 * blob type strategy
 */
public class BlobParserStrategy implements AbstractValueParserStrategy {

    @Override
    public void encode(List<Byte> buffer, TLVAbstractData input) throws UnsupportedEncodingException {
        BlobTypeValue blobTypeValue = (BlobTypeValue) input;
        short dataSize = ((short) getValueLength(blobTypeValue));
        Preconditions.checkArgument(!(dataSize > 0xFFFF), "Serialize exceeded string length");
        BiteCodeUtils.appendBytes(buffer, BiteCodeUtils.shortToLEBytes(dataSize), DATA_SIZE_BYTES_LENGTH);
        BiteCodeUtils.appendBytes(buffer, blobTypeValue.getValue(), dataSize);
    }

    @Override
    public TLVAbstractData decode(byte[] decoded, int offset, String key, int dataSize) {
        return new BlobTypeValue(key, readValue(decoded, offset, dataSize), false);
    }

    private int getValueLength(BlobTypeValue blobValue) throws UnsupportedEncodingException {
        return blobValue.getValue().length;
    }
}
