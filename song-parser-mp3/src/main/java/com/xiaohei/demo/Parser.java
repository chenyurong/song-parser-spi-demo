package com.xiaohei.demo;

import com.chenshuyi.demo.ParserManager;

/**
 * @author chenyr
 * @date 2018.04.19
 */
public class Parser extends Mp3Parser implements com.chenshuyi.demo.Parser {
    static
    {
        try
        {
            ParserManager.registerParser(new Parser());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Can't register parser!");
        }
    }
}
