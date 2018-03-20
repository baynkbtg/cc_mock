package com.mock.web;

import com.mock.dto.BaseResult;
import com.mock.pojo.MockInfo;
import com.mock.service.MockService;
import com.mock.util.MockUtil;
import com.mock.util.ResolveURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.Produces;
import java.io.IOException;
import java.util.List;
import java.util.Map;


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
    @RequestMapping(value = "execute")
    public BaseResult<Object> execute(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "expectation") String expectation) throws IOException {
        //解析URL
        String proto = ResolveURL.read(url).get("proto");
        String domain = ResolveURL.read(url).get("domain");
        String context = ResolveURL.read(url).get("context");

        List<MockInfo> mockInfos = mockService.query();
        boolean checkResult = false;
        for (MockInfo mockInfo : mockInfos) {
            if (mockInfo.getUrl().equals(context)) {
                return new BaseResult<Object>(false, "该接口已经存在mock数据，无法添加。若有需要，请直接修改该数据！");
            }
            if (mockInfo.getDomain().equals(domain)) {
                checkResult = true;
            }
        }
        /**
         * 判断该域名是否已在Nginx server模块中配置，若未配置，则在Nginx的server模块自动添加一个server_name
         */
        if (checkResult == false) {
            MockUtil.updateDomain(domain);
        }

        //向JavaBean注入属性值
        MockInfo mockInfo = new MockInfo();
        mockInfo.setName(name);
        mockInfo.setProto(proto);
        mockInfo.setDomain(domain);
        mockInfo.setUrl(context);

        mockInfo.setExpectation(expectation);

        try {
            //向远程服务器发送命令,并获取moco端口号和文件名
            Map<String, String> map = MockUtil.configNginx(mockInfo.getUrl(), mockInfo.getExpectation(), mockInfo.getProto());
            mockInfo.setFileName(map.get("fileName"));
            mockService.insert(mockInfo);
            return new BaseResult<Object>(true, "成功添加一条记录！");
        } catch (Exception e) {
            return new BaseResult<Object>(false, e.getMessage());
        }
    }

    @RequestMapping(value = "insert")
    public ModelAndView insert(@RequestParam(value = "proto") String proto,
                               @RequestParam(value = "domain") String domain,
                               @RequestParam(value = "url") String url,
                               @RequestParam(value = "expectation") String expectation) {
        MockInfo mockInfo = new MockInfo();
        mockInfo.setProto(proto);
        mockInfo.setDomain(domain);
        mockInfo.setUrl(url);
        mockInfo.setExpectation(expectation);
        mockService.insert(mockInfo);
        return new ModelAndView("mock", null);
    }
}
