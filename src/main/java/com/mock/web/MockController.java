package com.mock.web;

import com.mock.dto.BaseResult;
import com.mock.pojo.MockInfo;
import com.mock.service.MockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.net.URL;

/**
 * Created by qilong.chen on 2018/3/20.
 */
@Controller
@RequestMapping(value = "mock")
public class MockController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockService mockService;

    @ResponseBody
    @Produces("application/json;charset=UTF-8")
    @RequestMapping(value = "insert")
    public BaseResult<Object> execute(
            @RequestParam(value = "alias", required = false) String alias,
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "json") String json) throws IOException {
        //解析URL
        URL urlobj = new URL(url);
        String proto = urlobj.getProtocol();
        String domain = urlobj.getHost();
        String path = urlobj.getPath();
        String query = urlobj.getQuery();

        //向JavaBean注入属性值
        MockInfo mockInfo = new MockInfo();
        mockInfo.setAlias(alias);
        mockInfo.setProto(proto);
        mockInfo.setDomain(domain);
        mockInfo.setPath(path);

        mockInfo.setJson(json);

        try {
            mockService.insert(mockInfo);
            return new BaseResult<Object>(true, "成功添加一条记录！");
        } catch (Exception e) {
            return new BaseResult<Object>(false, e.getMessage());
        }
    }

    @ResponseBody
        @RequestMapping(value = "queryByPath")
//    public String queryByUrl(@RequestParam(value = "path") String path) {
//        String expectation = mockService.queryByPath(path);
//        return expectation;
//    }

    public void queryByPath(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
//        return "success!";

//        return request;
//        String requestUrl =request.getRequestURL().toString();
//        return requestUrl;
        String queryString = request.getQueryString();
        String requestURI = request.getRequestURI();

        String requestURL = request.getRequestURL().toString();

        // 全路径 什么都有的那种
        String fullPath = requestURL + "?" + queryString;


        System.out.println("参数部分:" + queryString);
        System.out.println("请求uri:" + requestURI);
        System.out.println("请求url:" + requestURL);
        System.out.println("全路径:" + fullPath);

    }


}