package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.entity.Category;
import xin.xiaoer.entity.CodeValue;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.service.CategoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/classroom")
@ApiIgnore
public class APIClassroomSubjectController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/subjectList", method = RequestMethod.GET)
    public ResponseBean subjectList(@RequestParam Map<String, Object> params) throws IOException {

        params.put("upperCode", "CRT");
        params.put("state", "1");
        List<Category> categoryList = categoryService.getList(params);
        List<Map<String, Object>> resultMap = new ArrayList<>();
        CloudStorageService cloudStorageService = OSSFactory.build();

        for (Category category: categoryList) {
            Map<String, Object> item = new HashMap<>();
            item.put("categoryName", category.getCategoryName());
            List<CodeValue> childrenList = categoryService.getCodeValues(category.getCategoryCode(), "1");
            for (CodeValue codeValue : childrenList){
                codeValue.setIcon(cloudStorageService.generatePresignedUrl(codeValue.getIcon()));
            }
            item.put("children", childrenList);
            resultMap.add(item);
        }

        return new ResponseBean(false,"success", null, resultMap);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseBean categories() throws IOException {

        //查询列表数据
        Map<String, Object> params = new HashMap<>();

        params.put("upperCode", "CRT");
        params.put("state", "1");
        List<Category> categories = categoryService.getList(params);
        List<CodeValue> codeValues = new ArrayList<>();
        for (Category category : categories) {
            List<CodeValue> subCategories = categoryService.getCodeValues(category.getCategoryCode(), "1");
            codeValues.addAll(subCategories);
        }

        return new ResponseBean(false, "success", null, codeValues);
    }

    @RequestMapping(value = "/categories/{upperCode}", method = RequestMethod.GET)
    public ResponseBean categories(@PathVariable("upperCode") String upperCode) throws IOException {

        //查询列表数据
        Map<String, Object> params = new HashMap<>();
        params.put("upperCode", upperCode);
        params.put("state", "1");

        List<CodeValue> subCategories = categoryService.getCodeValues(upperCode, "1");

        CloudStorageService cloudStorageService = OSSFactory.build();
        for (CodeValue codeValue: subCategories){
            codeValue.setIcon(cloudStorageService.generatePresignedUrl(codeValue.getIcon()));
        }

        return new ResponseBean(false, "success", null, subCategories);
    }
}
