package ge.vq.tlv.parser;


import ge.vq.tlv.structure.TLVAbstractData;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author vakhtang.koroghlishvili
 *         TLV parser to decode/encode data
 */
public interface TLVParser {

    /**
     * Encodes list of tlv structure
     *
     * @param input TLV data structure
     * @return encoded byte array
     * @throws UnsupportedEncodingException
     */
    byte[] encode(List<TLVAbstractData> input) throws UnsupportedEncodingException;

    /**
     * decodes byte array into tlv structure
     *
     * @param input decoded byte array
     * @return list of TLV data structure
     * @throws UnsupportedEncodingException
     */
    List<TLVAbstractData> decode(byte[] input) throws UnsupportedEncodingException;

}
