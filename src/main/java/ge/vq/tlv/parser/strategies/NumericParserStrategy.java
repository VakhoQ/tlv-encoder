package ge.vq.tlv.parser.strategies;

import ge.vq.tlv.structure.NumericTypeValue;
import ge.vq.tlv.structure.TLVAbstractData;
import ge.vq.tlv.utils.BiteCodeUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.nio.ByteOrder;
import java.util.List;

import static ge.vq.tlv.constants.CommonConstants.DATA_SIZE_BYTES_LENGTH;
import static ge.vq.tlv.structure.UnsignedDataTypes.*;
import static ge.vq.tlv.utils.BiteCodeUtils.*;

/**
 * @author vakhtang.koroghlishvili
 *         numeric type processing strategy
 */
public class NumericParserStrategy implements AbstractValueParserStrategy {

    @Override
    public void encode(List<Byte> buffer, TLVAbstractData input) {
        NumericTypeValue numericInput = (NumericTypeValue) input;
        BigInteger numTypeValue = numericInput.getValue();
        Pair<byte[], Short> numTypePair = buildNumericTypeBuffer(numTypeValue);
        short dataSize = numTypePair.getRight();
        appendBytes(buffer, shortToLEBytes(dataSize), DATA_SIZE_BYTES_LENGTH);
        appendBytes(buffer, numTypePair.getLeft(), dataSize);
    }

    @Override
    public NumericTypeValue decode(byte[] decoded, int offset, String key, int dataSize) {

        long value = 0;
        if (dataSize == 1) {
            value = decoded[offset] & 0xFF;
            return new NumericTypeValue(key, value);
        } else if (dataSize == 2) {
            value = BiteCodeUtils.getSignedShort(readValue(decoded, offset, 2), ByteOrder.LITTLE_ENDIAN);
            return new NumericTypeValue(key, value);
        } else if (dataSize == 4) {
            value = BiteCodeUtils.getSignedInt(readValue(decoded, offset, 4), ByteOrder.LITTLE_ENDIAN);
            return new NumericTypeValue(key, value);
        } else if (dataSize == 8) {
            BigInteger uLongValue = BiteCodeUtils.getSignedLong(readValue(decoded, offset, 8), ByteOrder.LITTLE_ENDIAN);
            return new NumericTypeValue(key, uLongValue);
        } else {
            throw new RuntimeException("Too big value!");
        }
    }

    /**
     * Detects how many byte does the value needs
     *
     * @param value - value of TLV protocol
     * @return return  value and data size.
     */
    private Pair<byte[], Short> buildNumericTypeBuffer(BigInteger value) {

        byte[] buffer = new byte[8];

        if (isLessOrEqualThen(value, UBYTE.MAX_VALUE())) {
            buffer[0] = value.byteValue();
            return Pair.of(buffer, UBYTE.BUFFER_SIZE());
        } else if (isLessOrEqualThen(value, USHORT.MAX_VALUE())) {
            return Pair.of(shortToLEBytes(value.shortValue()), USHORT.BUFFER_SIZE());
        } else if (isLessOrEqualThen(value, UINT.MAX_VALUE())) {
            return Pair.of(intToLEBytes(value.intValue()), UINT.BUFFER_SIZE());
        } else if (isLessOrEqualThen(value, ULONG.MAX_VALUE())) {
            return Pair.of(longToLBytes(value.longValue()), ULONG.BUFFER_SIZE());
        } else {
            throw new RuntimeException("Too big value");
        }

    }


    /**
     * detects if concrete number can be stored in maxValue
     *
     * @param value    - value of TLV
     * @param maxValue - max value to determine how many byte does it need
     * @return true if value <= maximum value
     */
    private boolean isLessOrEqualThen(BigInteger value, String maxValue) {
        BigInteger maxValueBigInteger = new BigInteger(maxValue, 16);
        return value.compareTo(maxValueBigInteger) == -1 || value.compareTo(maxValueBigInteger) == 0;
    }

}
