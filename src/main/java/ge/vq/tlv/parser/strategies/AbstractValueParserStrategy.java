package ge.vq.tlv.parser.strategies;

import ge.vq.tlv.structure.TLVAbstractData;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Abstract strategy for all type of structure
 */
public interface AbstractValueParserStrategy {

    /**
     * Encodes value
     *
     * @param buffer output buffer
     * @param input  input structure
     * @throws UnsupportedEncodingException
     */
    void encode(List<Byte> buffer, TLVAbstractData input) throws UnsupportedEncodingException;

    /**
     * @param decoded  decoded byte array which must be encoded
     * @param offset   offset number to access concrete array element
     * @param key      key represented in string
     * @param dataSize size of data
     * @return decoded structure
     * @throws UnsupportedEncodingException
     */
    TLVAbstractData decode(byte[] decoded, int offset, String key, int dataSize) throws UnsupportedEncodingException;


}
