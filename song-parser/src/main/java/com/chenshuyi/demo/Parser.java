package com.chenshuyi.demo;

/**
 * @author chenyr
 * @date 2018.04.19
 */
public interface Parser {

    byte[] getByteFormate();

    Song parse(byte[] data) throws Exception;
}
