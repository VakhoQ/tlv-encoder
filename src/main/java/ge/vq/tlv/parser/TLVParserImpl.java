package ge.vq.tlv.parser;

import com.google.common.primitives.Bytes;
import ge.vq.tlv.parser.strategies.AbstractValueParserContext;
import ge.vq.tlv.structure.TLVAbstractData;
import ge.vq.tlv.utils.BiteCodeUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static ge.vq.tlv.constants.CommonConstants.WINDOWS_1251_ENCODING;
import static ge.vq.tlv.utils.BiteCodeUtils.*;

/**
 * @author vakhtang.koroghlishvili
 */
public class TLVParserImpl implements TLVParser {


    @Override
    public byte[] encode(List<TLVAbstractData> inputs) throws UnsupportedEncodingException {

        List<Byte> result = new ArrayList<>();

        for (TLVAbstractData input : inputs) {

            byte type = (byte) input.getType().getValue();
            result.add(type);
            byte tagNameLength = (byte) input.getKey().getBytes(WINDOWS_1251_ENCODING).length;
            int uTagNameLength = toUnsignedInt(tagNameLength);

            checkArgument(!(uTagNameLength > 0xFF), "Serialize tag name limit exceeded");

            result.add(tagNameLength);
            Arrays.stream(wrapBytes(input.getKey().getBytes(WINDOWS_1251_ENCODING))).forEach(result::add);

            AbstractValueParserContext processingContext = new AbstractValueParserContext(type);
            processingContext.getStrategy().encode(result, input);

        }
        return Bytes.toArray(result);
    }


    @Override
    public List<TLVAbstractData> decode(byte[] decoded) throws UnsupportedEncodingException {

        List<TLVAbstractData> encodedList = new ArrayList<>();
        int offset = 0;

        while (offset != decoded.length) {

            checkArgument(!(offset + 1 > decoded.length), "Deserialize the packet of data is not complete");
            byte type = decoded[offset];
            offset += 1;
            checkArgument(!(offset + 1 > decoded.length), "Deserialize the packet of data is not complete");

            int nameSize = decoded[offset] & 0xFF;
            offset += 1;
            checkArgument(!(offset + nameSize > decoded.length), "Deserialize the packet of data is not complete");

            String key = new String(readValue(decoded, offset, nameSize), WINDOWS_1251_ENCODING);
            offset += nameSize;
            checkArgument(!(offset + 2 > decoded.length), "Deserialize the packet of data is not complete");

            int dataSize = BiteCodeUtils.getSignedShort(readValue(decoded, offset, 2), ByteOrder.LITTLE_ENDIAN);
            offset += 2;

            checkArgument(!(offset + dataSize > decoded.length), "Deserialize the packet of data is not complete");

            AbstractValueParserContext processingContext = new AbstractValueParserContext(type);
            TLVAbstractData encodedData = processingContext.getStrategy().decode(decoded, offset, key, dataSize);
            encodedList.add(encodedData);
            offset += dataSize;
        }

        return encodedList;
    }


}
