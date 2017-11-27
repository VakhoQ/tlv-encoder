package ge.vq.tlv

import ge.vq.tlv.parser.TLVParser
import ge.vq.tlv.parser.TLVParserImpl
import ge.vq.tlv.structure.BlobTypeValue
import ge.vq.tlv.structure.NumericTypeValue
import ge.vq.tlv.structure.StringTypeValue
import spock.lang.Specification

class TTVDecoderSpec extends Specification {


    def "should decode string byte array"() {
        given:
        def encoder = new TLVParserImpl()

        when:
        List<StringTypeValue> result = encoder.decode(input as byte[])

        then:
        result.first().key == key
        result.first().value == value
        noExceptionThrown()

        where:
        key    | value      | input
        'k'    | 'v'        | [2, 1, 107, 1, 0, 118]
        'MTID' | 'HSFT0010' | [2, 4, 77, 84, 73, 68, 8, 0, 72, 83, 70, 84, 48, 48, 49, 48]
    }


    def "should decode unicode string byte array"() {
        given:
        TLVParser encoder = new TLVParserImpl()

        when:
        List<StringTypeValue> result = encoder.decode(input)

        then:
        result.first().key == key
        result.first().value == value
        noExceptionThrown()

        where:
        key    | value | input
        'MTID' | 'ა'   | [5, 4, 77, 84, 73, 68, 2, 0, -48, 16] as byte[]

    }


    def "should decode numeric byte array"() {
        given:
        TLVParser encoder = new TLVParserImpl()

        when:
        List<NumericTypeValue> result = encoder.decode(input as byte[])

        then:
        result.first().key == 'MTID'
        result.first().value == value

        where:
        value                | input
        1                    | [1, 4, 77, 84, 73, 68, 1, 0, 1]
        255                  | [1, 4, 77, 84, 73, 68, 1, 0, -1]
        256                  | [1, 4, 77, 84, 73, 68, 2, 0, 0, 1]
        65535                | [1, 4, 77, 84, 73, 68, 2, 0, -1, -1]
        65536                | [1, 4, 77, 84, 73, 68, 4, 0, 0, 0, 1, 0]
        4294967295           | [1, 4, 77, 84, 73, 68, 4, 0, -1, -1, -1, -1]
        4294967296           | [1, 4, 77, 84, 73, 68, 8, 0, 0, 0, 0, 0, 1, 0, 0, 0]
        18446744073709551615 | [1, 4, 77, 84, 73, 68, 8, 0, -1, -1, -1, -1, -1, -1, -1, -1]
    }


    def "should decode blob"() {

        given:
        TLVParser encoder = new TLVParserImpl()

        when:
        List<BlobTypeValue> result = encoder.decode(input as byte[])


        then:
        result.first().key == key
        result.first().value == value


        where:
        key            | value                               | input
        "key"          | [97, 98, 99, 100]                   | [3, 3, 107, 101, 121, 4, 0, 97, 98, 99, 100]
        "123asd"       | [119, 104]                          | [3, 6, 49, 50, 51, 97, 115, 100, 2, 0, 119, 104]
        'MTID'         | 'ა'                                 | [5, 4, 77, 84, 73, 68, 2, 0, -48, 16]
        '1234'         | 'ჰ'                                 | [5, 4, 49, 50, 51, 52, 2, 0, -16, 16]
        'a1'           | 'ЖжЙйЦцЯя'                          | [5, 2, 97, 49, 16, 0, 22, 4, 54, 4, 25, 4, 57, 4, 38, 4, 70, 4, 47, 4, 79, 4]
        '!@#$%^&*()_+' | 'aVCდ!!ჭჰ@#$%^&*()_+=-/?><:;[]{}Юэ' | [5, 12, 33, 64, 35, 36, 37, 94, 38, 42, 40, 41, 95, 43, 66, 0, 97, 0, 86, 0, 67, 0, -45, 16, 33, 0, 33, 0, -19, 16, -16, 16, 64, 0, 35, 0, 36, 0, 37, 0, 94, 0, 38, 0, 42, 0, 40, 0, 41, 0, 95, 0, 43, 0, 61, 0, 45, 0, 47, 0, 63, 0, 62, 0, 60, 0, 58, 0, 59, 0, 91, 0, 93, 0, 123, 0, 125, 0, 46, 4, 77, 4]

    }


}