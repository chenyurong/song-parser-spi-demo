package com.chenshuyi.demo;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author chenyr
 * @date 2018.04.19
 */
public class ParserManager {

    private final static Map<String,ParserInfo> registeredParsers = new HashMap<>();

    static {
        loadInitialParsers();
        System.out.println("SongParser initialized");
    }

    private static void loadInitialParsers() {
        ServiceLoader<Parser> loadedParsers = ServiceLoader.load(Parser.class);
        Iterator<Parser> driversIterator = loadedParsers.iterator();
        try{
            while(driversIterator.hasNext()) {
                driversIterator.next();
            }
        } catch(Throwable t) {
            // Do nothing
        }
    }

    public static synchronized void registerParser(Parser parser) {
        registeredParsers.put(Arrays.toString(parser.getByteFormate()),new ParserInfo(parser));
    }

    public static Song getSong(byte[] data) {
        try {
            ParserInfo parserInfo =  registeredParsers.get(Arrays.toString(data));
            Song song = parserInfo.parser.parse(data);
            if (song != null) {
                return song;
            }
        } catch (Exception e) {
            //wrong parser, ignored it.
        }
        throw new ParserNotFoundException("10001", "Can not find corresponding data:" + new String(data));
    }
}
