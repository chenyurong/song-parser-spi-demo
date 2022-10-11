package com.xiaoshu.demo;

import com.chenshuyi.demo.Parser;
import com.chenshuyi.demo.ParserManager;
import com.chenshuyi.demo.Song;

import java.util.Arrays;

/**
 * @author chenyr
 * @date 2018.04.19
 */
public class Mp4Parser implements Parser {

    public final byte[] FORMAT = "MP4".getBytes();

    public final int FORMAT_LENGTH = FORMAT.length;

    static
    {
        try
        {
            ParserManager.registerParser(new Mp4Parser());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Can't register parser!");
        }
    }

    @Override
    public byte[] getByteFormate() {
        return this.FORMAT;
    }


    @Override
    public Song parse(byte[] data) throws Exception{
        if (!isDataCompatible(data)) {
            throw new Exception("data format is wrong.");
        }
        //parse data by mp3 format type
        return new Song("陈楚生", "mp4", "《有没有人曾告诉你》", 320L);
    }

    private boolean isDataCompatible(byte[] data) {
        byte[] format = Arrays.copyOfRange(data, 0, FORMAT_LENGTH);
        return Arrays.equals(format, FORMAT);
    }
}
