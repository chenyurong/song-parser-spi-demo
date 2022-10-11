# Java SPI 例子：歌曲解析器

song-parser-api-demo 是 Java SPI 使用的一个简单示例。它可以加强你对 Java SPI 机制的理解。

## 背景

众所周知，歌曲格式有很多种，一首歌曲可以是MP3或MP4，甚至是RMBV格式。 我们想构建一个框架来解析歌曲信息，包括歌曲名称、歌曲作者、歌曲经过时间等。如果我们不使用Java SPI机制，我们可以这样开发框架：

```
public class ParseUtil{
    public static Song parseMp3Song(byte[] data){
        //parse song according to mp3 data format
    }
}
```

使用框架的人编写下面的代码来获取歌曲信息：

```
Song song = ParseUtil.parseMp3Song(data);
```

如果还有一首mp4格式的歌曲，我们需要升级我们的框架并发布一个新版本，现在的框架是这样的：

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

使用它的人需要像这样更改他们的代码：

```
//this song is mp3 format type, call the parseMp3Song() method.
Song song = ParseUtil.parseMp3Song(data);
//this song is mp4 format type, call the parseMp4Song() method.
Song song = ParseUtil.parseMp4Song(data);
```

每次我们添加一个新的歌曲格式解析器，我们都需要发布一个新版本的框架，每个想要使用我们新版本的人都需要升级框架。 更重要的是，使用框架的人需要知道歌曲格式并调用相应的方法。 太不方便了！

## 更好的框架与 SPI

但是如果我们使用 Java SPI 机制，每次我们添加一个新的歌曲格式解析器，使用歌曲解析器框架的人不需要升级框架，也不需要改变他们的任何代码。

当只有一种格式类型时，例如 mp3 格式类型。 我们编写下面的代码来解析歌曲信息。

```
Song song = ParserManager.getSong(data);    //song stored with mp3 format
```

而如果我们想解析mp4格式的歌曲，我们不需要改变调用方法，解析歌曲信息的代码还是这样的：

```
Song song = ParserManager.getSong(data);    //song stored with mp3 format
```

我们需要做的是导入 mp4 Parser 依赖，Java SPI 机制会找出所有的解析器并自动搜索正确的解析器来解析歌曲。

```
<dependency>
    <groupId>com.xiaoshu.demo</groupId>
    <artifactId>song-parser-mp4</artifactId>
    <version>1.0.0</version>
</dependency>
```

真是太棒了！

我们不需要更改任何代码，只需导入 Parser 依赖项，它将返回正确的结果。

让我们进入 Song Parser Framework，看看它是如何工作的。

## 框架结构

该项目包含四个独立的模块，可分为三个主要部分。

* "song-parser" 模块，定义每个解析器将使用的统一接口。
* “song-parser-mp3”模块，定义mp3解析器。
* "song-parser-demo" 模块，提供快速启动示例，您只需运行 "com.chenshuyi.demo.App.main" 即可获得结果。

## 如何添加新的解析器

如果要添加新的歌曲解析器，例如解析 RMVB 格式类型歌曲的解析器。 您需要创建一个新项目并添加“song-parser”的依赖项：

```
<dependency>
    <groupId>com.chenshuyi.demo</groupId>
    <artifactId>song-parser</artifactId>
    <version>1.0.0</version>
</dependency>
```

然后在包的根目录中创建一个名为“Parser”的类：

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

然后创建执行数据解析工作的 RmvbParser：

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

最后添加一个描述文件`resources/META-INF/services/com.chenshuyi.demo.Parser`：

```
com.anonymous.demo.Parser
```

然后在模块“song-parser-demo”中导入新的 mp4 Parser：

```
<dependency>
    <groupId>com.anonymous.demo</groupId>
    <artifactId>song-parser-rmvb</artifactId>
    <version>1.0.0</version>
</dependency>
```

然后尝试解析rmvb格式的歌曲：

```
Song song = ParserManager.getSong(data);    //parse rmvb song 
```

成功了！

```
Name:《Wonderful U》
Author:AGA
Time:240
Format:rmvb
```

有关更多详细信息，您可以深入研究代码并感谢您的观看。

如果它有助于您理解Java SPI，请给我一个收藏，谢谢。