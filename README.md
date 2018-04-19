# Song Parser Demo of Java SPI

song-parser-spi-demo is a simple example of Java SPI usage. It can reinfoce your understanding of Java SPI mechanism.

## Background

As we know, there is many kinds of song format, a song can be MP3 or MP4 or even RMBV format. We want to build a framework to parse the song information which contains song name, song author, song elasped time etc. If we don't use Java SPI mechanism, we can develop the framework like this:

```
public class ParseUtil{
    public static Song parseMp3Song(byte[] data){
        //parse song according to mp3 data format
    }
}
```

people who use the framwork write the code below to get the song infomation:

```
Song song = ParseUtil.parseMp3Song(data);
```

and if there is another song which is stored by mp4 format, we need to upgrade our framework and release a new version, and the framework now like this:

```
public class ParseUtil{
    public Song parseMp3Song(byte[] data){
        //parse song according to mp3 data format
    }
    public Song parseMp4Song(byte[] data){
        //parse song according to mp4 data format
    }
}

```

and people who use it need to change their code like this:

```
//this song is mp3 format type, call the parseMp3Song() method.
Song song = ParseUtil.parseMp3Song(data);
//this song is mp4 format type, call the parseMp4Song() method.
Song song = ParseUtil.parseMp4Song(data);
```

everytime we add a new song format parser, we need to release a new version of the framework, and everyone who want to use our new version need to upgrade the framework. What's more, people who use the framework need to know the song format and call the corresponding method. It's so unconvenient!

## Better Framework with SPI

but if we use Java SPI mechanism, everytime we add a new song format parser, people who use the Song Parser Framework don't need to upgrade the framework and don't need to change any of their code. 

when there is only one kinds of format type, for example, mp3 format type. we write the code below to parse the song information.

```
Song song = ParserManager.getSong(data);    //song stored with mp3 format
```

and if we want to parse song with mp4 format, we don't need to change the calling method, the code parse the song information is still like:

```
Song song = ParserManager.getSong(data);    //song stored with mp3 format
```

what we need to do is import the mp4 Parser dependency, and the Java SPI mechanism will find out all the parser and auto search the right parser to parse the song. 

```
<dependency>
    <groupId>com.xiaoshu.demo</groupId>
    <artifactId>song-parser-mp4</artifactId>
    <version>1.0.0</version>
</dependency>
```

It's really awesome! 

we don't need to change any of our code but just import the Parser dependency and it will return the right result. 

Let's go inside the Song Parser Framework and see how it works.

## Framework Structure

This project contains four separate modules which can divide into three main part.

* "song-parser" module, define the unified interaface that every parser will use.
* "song-parser-mp3" module, define the mp3 parser.
* "song-parser-demo" module, provide a quicks start example, you can just run the "com.chenshuyi.demo.App.main" and result comes.

## how to add a new Parser

if you want to add a new song Parser, for example a Parser to parse RMVB format type song. You need to create a new project and add the dependency of "song-parser":

```
<dependency>
    <groupId>com.chenshuyi.demo</groupId>
    <artifactId>song-parser</artifactId>
    <version>1.0.0</version>
</dependency>
```

and then create a class called "Parser" in the root of package:

```
package com.anonymous.demo;

import com.chenshuyi.demo.ParserManager;

/**
 * @author chenyr
 * @date 2018.04.19
 */
public class Parser extends RmvbParser implements com.chenshuyi.demo.Parser {
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
```

and then create the RmvbParser which do the data parse work:

```
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
```

finanly add a description file `resources/META-INF/services/com.chenshuyi.demo.Parser`:

```
com.anonymous.demo.Parser
```

and then you import the new mp4 Parser in module "song-parser-demo":

```
<dependency>
    <groupId>com.anonymous.demo</groupId>
    <artifactId>song-parser-rmvb</artifactId>
    <version>1.0.0</version>
</dependency>
```

and then try to parse rmvb format song:

```
Song song = ParserManager.getSong(data);    //parse rmvb song 
```

and it works !

```
Name:《Wonderful U》
Author:AGA
Time:240
Format:rmvb
```

for more detail, you can dive into the code and thanks for watching.

if it helps you understand the Java SPI, please give me a start, thank you.
