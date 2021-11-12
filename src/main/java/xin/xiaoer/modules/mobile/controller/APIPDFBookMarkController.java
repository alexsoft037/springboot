package xin.xiaoer.modules.mobile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.utils.Const;
import xin.xiaoer.common.utils.PDFChapterReader;
import xin.xiaoer.common.utils.PathUtil;
import xin.xiaoer.modules.mobile.bean.ResponseBean;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("mobile/pdf")
@ApiIgnore
public class APIPDFBookMarkController {

//    @RequestMapping(value = "/getBookMarks")
//    public ResponseBean banner(@RequestParam Map<String, Object> params) throws Exception {
//        String filePath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD + "test.pdf";
//        PDFChapterReader chapterReader = new PDFChapterReader();
//        ArrayList<Map<String, Object>> allChapters = chapterReader.readerChapters(filePath);
//
//        return new ResponseBean(false,"success", null, allChapters);
//    }
}
