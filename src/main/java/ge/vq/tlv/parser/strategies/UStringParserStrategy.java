package ge.vq.tlv.parser.strategies;

import ge.vq.tlv.structure.StringTypeValue;
import ge.vq.tlv.utils.BiteCodeUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static ge.vq.tlv.constants.CommonConstants.DATA_SIZE_BYTES_LENGTH;
import static ge.vq.tlv.constants.CommonConstants.UTF16_ENCODING;
import static ge.vq.tlv.utils.BiteCodeUtils.appendBytes;
import static ge.vq.tlv.utils.BiteCodeUtils.readValue;

/**
 * @author vakhtang.koroghlishvili
 *         unsigned string processing strategy
 */
public class UStringParserStrategy extends StringProcessingStrategy {

    @Override
    public StringTypeValue decode(byte[] decoded, int offset, String key, int dataSize) throws UnsupportedEncodingException {
        String value = new String(readValue(decoded, offset, dataSize), "UTF-16LE");
        return new StringTypeValue(key, value);
    }


    @Override
    protected void appendBuffer(List<Byte> buffer, StringTypeValue input, short dataSize) throws UnsupportedEncodingException {
        appendBytes(buffer, BiteCodeUtils.shortToLEBytes(dataSize), DATA_SIZE_BYTES_LENGTH);
        appendBytes(buffer, input.getValue().getBytes(UTF16_ENCODING), dataSize);
    }

    protected int getKeyLength(StringTypeValue stringInput) throws UnsupportedEncodingException {
        return stringInput.getValue().getBytes(UTF16_ENCODING).length;
    }
}
