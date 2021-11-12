package xin.xiaoer.modules.mobile.controller;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.help.entity.HelpCenter;
import xin.xiaoer.modules.help.service.HelpCenterService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.setting.entity.Contact;
import xin.xiaoer.modules.setting.service.ContactService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/")
@ApiIgnore
public class APIPageController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private HelpCenterService helpCenterService;

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ResponseBean contact(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

        String content = params.get("content").toString();
        String contactInfo = params.get("contactInfo").toString();
        String userId = params.get("userId").toString();

        Contact contact = new Contact();
        contact.setContent(content);
        contact.setContactInfo(contactInfo);
        contact.setState("1");
        contact.setCreateBy(Long.parseLong(userId));
        contactService.save(contact);

        return new ResponseBean(false, "success", null, null);
    }

    @RequestMapping(value = "contact/listData", method = RequestMethod.POST)
    public ResponseBean contactListData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = params.get("userId").toString();
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("createBy", userId);
        params.put("state", "1");
        Query query = new Query(params);

        List<Contact> contactList = contactService.getList(query);
        for (Contact contact : contactList) {
            contact.setContent(Jsoup.parse(contact.getContent()).text());
        }
        int total = contactService.getCount(query);

        PageUtils pageUtil = new PageUtils(contactList, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "contact/detail/{contactId}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseBean contactDetail(@PathVariable("contactId") Long contactId) {
        //查询列表数据

        Contact contact = contactService.get(contactId);

        return new ResponseBean(false, "success", null, contact);
    }

    @RequestMapping(value = "/aboutus", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean aboutUs(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据
        HelpCenter helpCenter = helpCenterService.getByCategory("ABOUTUS");

        return new ResponseBean(false, "success", null, helpCenter);
    }

    @RequestMapping(value = "/privacy", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean privacy(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据
        HelpCenter helpCenter = helpCenterService.getByCategory("PRIVACY");

        return new ResponseBean(false, "success", null, helpCenter);
    }

    @RequestMapping(value = "/registration", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean registration(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据
        HelpCenter helpCenter = helpCenterService.getByCategory("REGISTRATION");

        return new ResponseBean(false, "success", null, helpCenter);
    }
}