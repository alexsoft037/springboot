package xin.xiaoer.common.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import xin.xiaoer.entity.TextChapter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtChapterReader {

    private ArrayList<String> fileNames;

    public TxtChapterReader()
    {
        fileNames=new ArrayList<String>();
    }

    public ArrayList<TextChapter> readerChapters(String filePath) throws Exception{

       Charset charset = new CharsetDetector().detectCharset ( filePath );
        InputStreamReader fileUtf8 = new InputStreamReader(new FileInputStream(filePath), charset);
        BufferedReader bufferedReader = new BufferedReader(fileUtf8);
        bufferedReader.readLine();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line=bufferedReader.readLine())!=null){
//            String next = scanner.next();
            sb.append(line);
            sb.append(System.lineSeparator());
        }
//        scanner.close();
        String outString = sb.toString();
        outString = new String(outString.getBytes(), "UTF-8");
        outString += "第完结章";
//        byte ptext[] = outString.getBytes("UTF8");
//        outString = StringUnicodeEncoderDecoder.encodeStringToUnicodeSequence(outString);
//        String pattern = "/(.*)(\\x{7b2c})(\\s*)([\\x{96f6}\\x{4e00}\\x{4e8c}\\x{4e09}\\x{56db}\\x{4e94}\\x{516d}\\x{4e03}\\x{516b}\\x{4e5d}\\x{5341}\\x{767e}\\x{5343}\\x{5b8c}\\x{7ed3} 0-9]+)(\\s*)([\\x{7ae0}\\x{8282}]+)(.*)(\\s*)/u";
        String pattern = "/(.*)(s*)(第)(s*)([零一二三四五六七八九十百千完结 0-9]+)(s*)([章节]+)(.*)(s*)/u";
//        String pattern = "(第)(s*)([零一二三四五六七八九十百千完结 0-9]+)(s*)([章节]+)";
//        String pattern = "/(.*)(s*)(第)(s*)([零一二三四五六七八九十百千完结 0-9]+)(s*)([章节]+)(.*)/u";
//        String pattern = "/第[零一二三四五六七八九十百千完结 0-9]*[章节]/i";
//        String pattern = "/第[0-9一二兩三四五六七八九十百千萬]*[章]/i";

        ArrayList<String> matches= Pcre.preg_match_all(pattern, outString);
        ArrayList<TextChapter> chapterArrayList = new ArrayList<>();
        int key = 0;
        TextChapter lastChapter = new TextChapter();
        lastChapter.setTextCount(0);
        for (String match: matches){
            int j = key + 1;
            TextChapter chapter = new TextChapter();
//            System.out.println(match);
            if (j == matches.size()){
                break;
            }
            String contentPattern = match.replace("*", "(.*)")+"(.*)"+matches.get(j).replace("*", "(.*)");
            String content = Pcre.preg_match(contentPattern, outString.replace(System.lineSeparator(), ""), true);
            content = content.replace(match, "").replace(matches.get(j),"");
            chapter.setContent(content);
            String title = Pcre.preg_match("/([\\x{7ae0}\\x{8282}]+)(\\s*)(.*)(\\s*)/u", match, true);
            String chapterTxt = Pcre.preg_match("(第)(s*)([零一二三四五六七八九十百千完结 0-9]+)(s*)([章节]+)", match, true);
//            System.out.println(chapterTxt);
            title = title.replace("\\n", "");
            title = title.replace(title.substring(0, 1), "").trim();
            chapter.setChapterIndex(j);
            chapter.setChapter(chapterTxt);
            chapter.setChapterTitle(title);
            chapter.setTextCount(content.length());
            lastChapter.setTextCount(lastChapter.getTextCount() + content.length());
//            System.out.println(content);
//            System.out.println(title);
            chapterArrayList.add(chapter);
            key ++;
        }
        lastChapter.setChapterIndex(key+1);
//        chapterArrayList.add(lastChapter);
        return chapterArrayList;
    }

    private String escapeNonAscii(String str) {

        StringBuilder retStr = new StringBuilder();
        for(int i=0; i<str.length(); i++) {
            int cp = Character.codePointAt(str, i);
            int charCount = Character.charCount(cp);
            if (charCount > 1) {
                i += charCount - 1; // 2.
                if (i >= str.length()) {
                    throw new IllegalArgumentException("truncated unexpectedly");
                }
            }

            if (cp < 128) {
                retStr.appendCodePoint(cp);
            } else {
                retStr.append(String.format("\\x{%x}", cp));
            }
        }
        return retStr.toString();
    }
    public static void main(String[] args) {
        TxtChapterReader chapterReader=new TxtChapterReader();
        try
        {
            String filePath= "F:\\WorkData\\xiaoer\\doc\\test.txt";
            chapterReader.readerChapters(filePath);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
