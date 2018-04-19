package com.anonymous.demo;

import com.chenshuyi.demo.Song;

import java.util.Arrays;

/**
 * @author chenyr
 * @date 2018.04.19
 */
public class RmvbParser implements com.chenshuyi.demo.Parser {

    public final byte[] FORMAT = "RMVB".getBytes();

    public final int FORMAT_LENGTH = FORMAT.length;

    @Override
    public Song parse(byte[] data) throws Exception{
        if (!isDataCompatible(data)) {
            throw new Exception("data format is wrong.");
        }
        //parse data by rmvb format type
        return new Song("AGA", "rmvb", "《Wonderful U》", 240L);
    }

    private boolean isDataCompatible(byte[] data) {
        byte[] format = Arrays.copyOfRange(data, 0, FORMAT_LENGTH);
        return Arrays.equals(format, FORMAT);
    }
}