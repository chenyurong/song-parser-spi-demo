package com.xiaohei.demo;

import com.chenshuyi.demo.Song;
import com.chenshuyi.demo.Parser;

import java.util.Arrays;

/**
 * @author chenyr
 * @date 2018.04.19
 */
public class Mp3Parser implements Parser {

    public final byte[] FORMAT = "MP3".getBytes();

    public final int FORMAT_LENGTH = FORMAT.length;

    @Override
    public Song parse(byte[] data) throws Exception{
        if (!isDataCompatible(data)) {
            throw new Exception("data format is wrong.");
        }
        //parse data by mp3 format type
        return new Song("刘千楚", "mp3", "《北京东路的日子》", 220L);
    }

    private boolean isDataCompatible(byte[] data) {
        byte[] format = Arrays.copyOfRange(data, 0, FORMAT_LENGTH);
        return Arrays.equals(format, FORMAT);
    }
}
