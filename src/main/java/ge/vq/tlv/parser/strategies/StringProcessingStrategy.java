package ge.vq.tlv.parser.strategies;

import com.google.common.base.Preconditions;
import ge.vq.tlv.structure.StringTypeValue;
import ge.vq.tlv.structure.TLVAbstractData;
import ge.vq.tlv.utils.BiteCodeUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static ge.vq.tlv.constants.CommonConstants.DATA_SIZE_BYTES_LENGTH;
import static ge.vq.tlv.constants.CommonConstants.WINDOWS_1251_ENCODING;
import static ge.vq.tlv.utils.BiteCodeUtils.readValue;


/**
 * String type processing strategy
 */
public class StringProcessingStrategy implements AbstractValueParserStrategy {

    @Override
    public void encode(List<Byte> buffer, TLVAbstractData input) throws UnsupportedEncodingException {
        StringTypeValue stringInput = (StringTypeValue) input;
        int keyLength = getKeyLength(stringInput);
        Preconditions.checkArgument(!(keyLength > 0xFFFF), "Serialize exceeded string length");
        short dataSize = (short) keyLength;
        appendBuffer(buffer, stringInput, dataSize);
    }

    @Override
    public StringTypeValue decode(byte[] decoded, int offset, String key, int dataSize) throws UnsupportedEncodingException {
        String value = new String(readValue(decoded, offset, dataSize), WINDOWS_1251_ENCODING);
        return new StringTypeValue(key, value);
    }

    void appendBuffer(List<Byte> buffer, StringTypeValue input, short dataSize) throws UnsupportedEncodingException {
        BiteCodeUtils.appendBytes(buffer, BiteCodeUtils.shortToLEBytes(dataSize), DATA_SIZE_BYTES_LENGTH);
        BiteCodeUtils.appendBytes(buffer, input.getValue().getBytes(WINDOWS_1251_ENCODING), dataSize);
    }

    int getKeyLength(StringTypeValue stringInput) throws UnsupportedEncodingException {
        return stringInput.getValue().getBytes(WINDOWS_1251_ENCODING).length;
    }
}
