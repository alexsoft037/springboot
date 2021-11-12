
package xin.xiaoer.common.utils;

import java.io.File;
import java.nio.ByteBuffer;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.io.BufferedInputStream;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharacterCodingException;

/**
 * @author R. Yahiaoui
 * <p>
 * Use : new CharsetDetector()
 * .detectCharset( filePath ) ;
 */

public class CharsetDetector {

    public Charset detectCharset(String pathFile) {

        String[] charsetsToBeTested = {"UTF-8",
                "GB2312",
                "GBK",
                "LATIN1",
                "BIG5",
                "CP936",
                "UTF-16",
                "ISO-8859-7",
                "ISO-8859-1",
                "ISO-8859-2",
                "ISO-8859-4",
                "US-ASCII",
                "windows-1250",
                "windows-1251",
                "windows-1252",
                "windows-1253",
                "windows-1254",
                "windows-1257",
                "UTF-16BE",
                "UTF-32",
                "UTF-16LE",
                "UTF-32BE",
                "UTF-32LE",
                "ISO-8859-5",
                "ISO-8859-7",
                "ISO-8859-9",
                "ISO-8859-13",
                "ISO-8859-15",
                "x-UTF-32BE-BOM",
                "x-UTF-32LE-BOM",
                "x-UTF-16LE-BOM",
                "x-IBM874",
                "x-IBM737",
                "IBM00858",
                "IBM437",
                "IBM775",
                "IBM850",
                "IBM852",
                "IBM855",
                "IBM857",
                "IBM862",
                "IBM866",
                "KOI8-R",
                "KOI8-U",
        };
        Charset charset = null;

        for (String charsetName : charsetsToBeTested) {
            charset = detectCharset(new File(pathFile),
                    Charset.forName(charsetName));
            if (charset != null) {
                break;
            }
        }

        return charset;
    }

    private Charset detectCharset(File f, Charset charset) {
        try {
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(f));

            CharsetDecoder decoder = charset.newDecoder();
            decoder.reset();

            byte[] buffer = new byte[512];
            boolean identified = false;
            while ((input.read(buffer) != -1) && (!identified)) {
                identified = identify(buffer, decoder);
            }

            input.close();

            if (identified) {
                return charset;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    private boolean identify(byte[] bytes, CharsetDecoder decoder) {
        try {
            decoder.decode(ByteBuffer.wrap(bytes));
        } catch (CharacterCodingException e) {
            return false;
        }
        return true;
    }
}
