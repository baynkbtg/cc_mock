package com.mock.web;

import com.alibaba.fastjson.JSONObject;
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
import javax.ws.rs.Produces;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

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
            @RequestParam(value = "idenKey", required = false) String idenKey,
            @RequestParam(value = "idenVal", required = false) String idenVal,
            @RequestParam(value = "json") String json) throws IOException {
        //解析URL
        URL urlobj = new URL(url);
        String proto = urlobj.getProtocol();
        String domain = urlobj.getHost();
        String path = urlobj.getPath();

        //向JavaBean注入属性值
        MockInfo mockInfo = new MockInfo();
        mockInfo.setAlias(alias);
        mockInfo.setProto(proto);
        mockInfo.setDomain(domain);
        mockInfo.setPath(path);
        mockInfo.setJson(json);
        mockInfo.setIdenKey(idenKey);
        mockInfo.setIdenVal(idenVal);

        try {
            mockService.insert(mockInfo);
            return new BaseResult<Object>(true, "成功添加一条记录！");
        } catch (Exception e) {
            return new BaseResult<Object>(false, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "queryByPath")
    public JSONObject queryByPath(HttpServletRequest request,
            @RequestParam(value = "path", required = false) String path) throws ServletException, IOException {

        //获取传入的参数，里面包含有被mock接口的查询参数
//        String getQueryString =request.getQueryString();
//        System.out.println("getQueryString:"+ getQueryString);

//        Enumeration enu=request.getParameterNames();
//        while(enu.hasMoreElements()){
//            String paraName=(String)enu.nextElement();
//            System.out.println(paraName+": "+request.getParameter(paraName));
//        }
//        System.out.println(request.getParameter("orderNo"));

        JSONObject jsonExpected = new JSONObject();

        //根据被mock接口的uri匹配并返回期望
        MockInfo mockInfo=this.mockService.queryByPath(path);
        String idenKey = mockInfo.getIdenKey();
        String idenVal = mockInfo.getIdenVal();
        if(idenKey != null){
            Enumeration enu=request.getParameterNames();
            while(enu.hasMoreElements()){
                String paraName=(String)enu.nextElement();
                if(paraName.equals(idenKey)){
                    String reqVal = request.getParameter(paraName);
                    if(reqVal.equals(idenVal)){
                        jsonExpected = JSONObject.parseObject(mockInfo.getJson());
                    }
                }
            }
        }

//        JSONObject jsonExpected = JSONObject.parseObject(expected);
        return jsonExpected;

    }

}