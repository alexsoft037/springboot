package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.utils.EnumBean;
import xin.xiaoer.entity.Area;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.service.AreaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("mobile/area")
@ApiIgnore
public class APIAreaController {

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "normalList/{parentAreaId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean normalList(@PathVariable String parentAreaId) {
        List<EnumBean> list = new ArrayList<>();
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("state", StateEnum.ENABLE.getCode());
        paraMap.put("parentAreaId", parentAreaId);
        List<Area> areaList = areaService.getAreaListByIsShow(paraMap);
        if (areaList != null && areaList.size() > 0) {
            for (int i = 0; i < areaList.size(); i++) {
                EnumBean bean = new EnumBean();
                bean.setValue(areaList.get(i).getAreaName());
                bean.setCode(areaList.get(i).getAreaId());
                list.add(bean);
            }
        }
        return new ResponseBean(false,"success", null, list);
    }
}
