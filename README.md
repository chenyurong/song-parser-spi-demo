# Song Parser Demo of Java SPI

song-parser-spi-demo is a simple example of Java SPI usage. 

## Background

As we know, there is many kinds of song format, a song can be MP3 or MP4 or even RMBV format. We want to build a framework to parse the song information which contains song name, song author, song elasped time etc. If we don't use Java SPI mechanism, we can develop the framework like this:

```
public Song parseMp3Song(byte[] data){
    //parse song according to mp3 data format
}
```

and if there is another song which is stored by mp4 format, we need to upgrade our framework and release a new version, and the framework now like this:

```
public Song parseMp3Song(byte[] data){
    //parse song according to mp3 data format
}
public Song parseMp4Song(byte[] data){
    //parse song according to mp4 data format
}
```

everytime we add a new song format parser, we need to release a new version of the framework, and everyone who want to use our new version need to upgrade the framework. What's more, people who use our framework need to know the song format and call the corresponding method. It's so unconvenient!

but if we use Java SPI mechanism, everytime we add a new song format parser, people who use the Song Parser Framework don't need to upgrade the framework and don't need to change any of their code. All they need to do is add the new song format parser implementation, like below:

```
<dependency>
    <groupId>com.xiaoshu.demo</groupId>
    <artifactId>song-parser-mp4</artifactId>
    <version>1.0.0</version>
</dependency>
```

the code above add a mp4 format parser, and the Java SPI mechanism will find out all the parser and auto search the right parser to parse the song. 

```
Song song = ParserManager.getSong(mockSongData("MP3"));
Song song = ParserManager.getSong(mockSongData("MP4"));
```

It's really awesome! 

we don't need to change any of our code and it will return the right result. 

Let's go inside the Song Parser Framework and see how it works.

## quick start

To use the Song Parse Framework, you need to import the "song-parse" and the detailed Parse for example "song-parse-mp4" modules.

```
<dependency>
    <groupId>com.chenshuyi.demo</groupId>
    <artifactId>song-parser</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.xiaohei.demo</groupId>
    <artifactId>song-parser-mp3</artifactId>
    <version>1.0.0</version>
</dependency>
```

and then use ParserManager to parse the song:

```
Song song = ParserManager.getSong(mockSongData("MP4"));
System.out.println("Name:" + song.getName());
System.out.println("Author:" + song.getAuthor());
System.out.println("Time:" + song.getTime());
System.out.println("Format:" + song.getFormat());
```

and you can use ParserManager to parse the song that store as mp3 type:

```
Song song = ParserManager.getSong(mockSongData("MP3"));
System.out.println("Name:" + song.getName());
System.out.println("Author:" + song.getAuthor());
System.out.println("Time:" + song.getTime());
System.out.println("Format:" + song.getFormat());
```

you don't need to change any of the code, the program auto search the right Parser to parse the song data.

but if you search a song stored with unknow format type for example with RMVB format type, the program will throw out a Exception.

```
Song song = ParserManager.getSong(mockSongData("RMVB"));  // throw ParserNotFoundException
System.out.println("Name:" + song.getName());
System.out.println("Author:" + song.getAuthor());
System.out.println("Time:" + song.getTime());
System.out.println("Format:" + song.getFormat());
```

result from the console output:

```
Exception in thread "main" com.chenshuyi.demo.ParserNotFoundException: Can not find corresponding data:RMVB
```

## Framework Structure

This project contains four separate modules which can divide into three main part.

* "song-parser" module, define the unified interaface that every parser will use.
* "song-parser-mp3" module, define the mp3 parser.
* "song-parser-demo" module, provide a quicks start example, you can just run the "com.chenshuyi.demo.App.main" and result comes.

## how to add a new Parser

if you want to add a new song Parser, you just create a new project and add the dependency of "song-parser":

```
<dependency>
    <groupId>com.chenshuyi.demo</groupId>
    <artifactId>song-parser</artifactId>
    <version>1.0.0</version>
</dependency>
```

and then create a class called "Parser" in the root of package:

```
package com.xiaoshu.demo;

import com.chenshuyi.demo.ParserManager;

/**
 * @author chenyr
 * @date 2018.04.19
 */
public class Parser extends Mp4Parser implements com.chenshuyi.demo.Parser {
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

and then create the Mp4Parser which do the data parse work:

```
package com.xiaoshu.demo;

import com.chenshuyi.demo.Song;

import java.util.Arrays;

/**
 * @author chenyr
 * @date 2018.04.19
 */
public class Mp4Parser implements com.chenshuyi.demo.Parser {

    public final byte[] FORMAT = "MP4".getBytes();

    public final int FORMAT_LENGTH = FORMAT.length;

    @Override
    public Song parse(byte[] data) throws Exception{
        if (!isDataCompatible(data)) {
            throw new Exception("data format is wrong.");
        }
        //parse data by mp3 format type
        return new Song("Carpenters", "mp4", "《Yesterday Once More》", 320L);
    }

    private boolean isDataCompatible(byte[] data) {
        byte[] format = Arrays.copyOfRange(data, 0, FORMAT_LENGTH);
        return Arrays.equals(format, FORMAT);
    }
}
```

finanly add a description file `resources/META-INF/services/com.chenshuyi.demo.Parser`:

```
com.xiaoshu.demo.Parser
```

and then you import the new mp4 Parser in module "song-parser-demo":

```
<dependency>
    <groupId>com.xiaoshu.demo</groupId>
    <artifactId>song-parser-mp4</artifactId>
    <version>1.0.0</version>
</dependency>
```

and then try to parse mp4 format song:

```
```
