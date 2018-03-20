package com.mock.pojo;

import java.io.Serializable;

/**
 * Created by qilong.chen on 2018/3/19.
 */
public class MockInfo implements Serializable {
    private Long id;
    private String alias;
    private String proto;
    private String domain;
    private String url;
    private String json;
    private String fileName;

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getProto() {
        return proto;
    }

    public void setProto(String proto) {
        this.proto = proto;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String expectation) {
        this.json = json;
    }
}

