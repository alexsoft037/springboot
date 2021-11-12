package xin.xiaoer.modules.mobile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.utils.TxtChapterReader;
import xin.xiaoer.entity.TextChapter;
import xin.xiaoer.modules.mobile.bean.ResponseBean;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("mobile/chapter")
@ApiIgnore
public class APICharpterReaderController {

//    @RequestMapping(value = "/read")
//    public ResponseBean read(@RequestParam Map<String, Object> params) {
//        TxtChapterReader chapterReader=new TxtChapterReader();
//        ArrayList<TextChapter> matches = new ArrayList<>();
//        try
//        {
//            String filePath = "F:\\WorkData\\xiaoer\\doc\\末日之神速大师.txt";
//            matches = chapterReader.readerChapters(filePath);
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return new ResponseBean(false, "success", null, matches);
//    }
}
