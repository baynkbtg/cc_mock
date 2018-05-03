package com.mock.web;

import com.alibaba.fastjson.JSONObject;
import com.mock.dto.BaseResult;
import com.mock.pojo.MockInfo;
import com.mock.service.MockService;
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
//    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

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
            @RequestParam(value = "json", required = true) String json,
            @RequestParam(value = "method", required = false) String method) throws IOException {

        MockInfo mockInfo = new MockInfo();
        mockInfo.setAlias(alias);

        int firSplit = url.indexOf(":");
        String proto = url.substring(0, firSplit);
        String path = "";

        //根据接口协议做不同处理
        if (proto.equals("http") || proto.equals("https")) {
            URL urlobj = new URL(url);
            proto = urlobj.getProtocol();
            String domain = urlobj.getHost();
            path = urlobj.getPath();
            mockInfo.setDomain(domain);
            mockInfo.setIdenKey(idenKey);
            mockInfo.setIdenVal(idenVal);
        } else if (proto.equals("rpc")){
            path = url.substring(firSplit + 1);
            mockInfo.setMethod(method);
        }else {
            return new BaseResult<Object>(false, "不支持该协议");
        }
        mockInfo.setPath(path);
        mockInfo.setProto(proto);
        mockInfo.setPath(path);
        mockInfo.setJson(json);

        //判断是否已存在
        MockInfo isMockExist=null;
        try {
            if (proto.equals("rpc")){
                isMockExist=this.mockService.queryByPath(path);
            } else {
                isMockExist=this.mockService.queryByPath(path);
            }

            if(isMockExist == null){
                mockService.insert(mockInfo);
                return new BaseResult<Object>(true, "成功添加一条记录！");
            } else {
                return new BaseResult<Object>(false, "mock已存在");
            }
        } catch (Exception e) {
            return new BaseResult<Object>(false, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "queryByPath")
    public JSONObject queryByPath(HttpServletRequest request,
            @RequestParam(value = "path", required = false) String path,
                                  @RequestParam(value = "proto", required = false, defaultValue = "http") String proto,
                                  @RequestParam(value = "mname", required = false) String mname) throws ServletException, IOException {

        JSONObject jsonExpected = new JSONObject();
        MockInfo mockInfo=this.mockService.queryByPath(path);
        String idenKey = mockInfo.getIdenKey();
        String idenVal = mockInfo.getIdenVal();
        String methodName = mockInfo.getMethod();

        if (proto.equals("rpc")) {
            if (!(methodName == null || "".equals(methodName))) {
                Enumeration enu=request.getParameterNames();
                while(enu.hasMoreElements()){
                    String paraName=(String)enu.nextElement();
                    if(paraName.equals(mname)){
                        String reqVal = request.getParameter(paraName);
                        if(reqVal.equals(methodName)){
                            jsonExpected = JSONObject.parseObject(mockInfo.getJson());
                        }
                    }
                }
                return jsonExpected;
            }
        } else {
            if (!(idenKey == null || "".equals(idenKey))) {
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
                return jsonExpected;
            }
        }
        jsonExpected = JSONObject.parseObject(mockInfo.getJson());
        return jsonExpected;
    }
}