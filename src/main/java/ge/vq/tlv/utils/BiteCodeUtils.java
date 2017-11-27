package ge.vq.tlv.utils;


import com.google.common.primitives.UnsignedInts;
import com.google.common.primitives.UnsignedLong;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author vakhtang.koroghlishvili
 */
public class BiteCodeUtils {



    /**
     * byte value to short
     *
     * @param value byte value in signed format
     * @return unsigned byte
     */
    public static short toUnsignedShort(byte value) {
        return (short) (value & 0xFF);
    }

    /**
     * byte to unsigned byte
     *
     * @param value byte value in signed format
     * @return unsigned byte
     */
    public static int toUnsignedInt(byte value) {
        return (value & 0xFF);
    }

    /**
     * returns signed short value written in int
     *
     * @param buff  - 2 byte
     * @param order - lintel endian/ big endian
     * @return signed short
     */
    public static int getSignedShort(byte[] buff, ByteOrder order) {
        ByteBuffer buffer = ByteBuffer.wrap(buff);
        buffer.order(order);
        return buffer.getShort() & 0xFFFF;
    }


    /**
     * returns Signed int value written in long. <br>
     * singed int & & 0xFFFFFFFF = unsigned int value
     *
     * @param buff  4 byte array
     * @param order lintel endian/ big endian
     * @return unsigned int
     */
    public static long getSignedInt(byte[] buff, ByteOrder order) {
        ByteBuffer buffer = ByteBuffer.wrap(buff);
        buffer.order(order);
        return UnsignedInts.toLong(buffer.getInt());
    }

    public static String toBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++) {
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        }
        return sb.toString();
    }

    public static byte[] longToLBytes(long i) {
        return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(i).array();
    }

    public static byte[] longToBBytes(long i) {
        return ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putLong(i).array();
    }

    public static byte[] intToLEBytes(int i) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
    }


    public static byte[] intToBEBytes(int i) {
        return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(i).array();
    }


    public static byte[] shortToLEBytes(Short sh) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(sh).array();
    }


    public static byte[] shortToBEBytes(Short sh) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(sh).array();
    }

    public static byte[] byteToLEBytes(byte b) {
        return ByteBuffer.allocate(1).order(ByteOrder.LITTLE_ENDIAN).put(b).array();
    }


    public static BigInteger getSignedLong(byte[] buff, ByteOrder order) {
        ByteBuffer buffer = ByteBuffer.wrap(buff);
        buffer.order(order);
        return UnsignedLong.fromLongBits(buffer.getLong()).bigIntegerValue();
    }

    public static short getShortFromByteArray(byte[] buff, ByteOrder order) {
        ByteBuffer buffer = ByteBuffer.wrap(buff);
        buffer.order(order);
        return buffer.getShort();
    }

    public static Byte[] wrapBytes(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }

    public static void appendBytes(List<Byte> destination, byte[] src, int length) {
        for (int i = 0; i < length; i++) {
            destination.add(src[i]);
        }
    }

    public static byte[] readValue(byte[] input, int offset, int size) {
        byte[] valueBuffer = new byte[size];
        IntStream.range(0, size).forEach(i -> valueBuffer[i] = input[offset + i]);
        return valueBuffer;
    }

    public static String bytesToHex(byte[] input) {
        StringBuilder builder = new StringBuilder();
        for (byte byteValue : input) {
            builder.append(String.format("%02x", byteValue));
        }
        return builder.toString();
    }

    public static String byteToMD5(byte[] plaintext) {
        return DigestUtils.md5Hex(plaintext);
    }

}

