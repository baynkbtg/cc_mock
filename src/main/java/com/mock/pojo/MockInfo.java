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
    private String path;
    private String json;
    private String idenkey;
    private String idenval;

    public Long getId() {
        return id;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getIdenKey() {
        return idenkey;
    }

    public void setIdenKey(String idenKey) {
        this.idenkey = idenKey;
    }

    public String getIdenVal() {
        return idenval;
    }

    public void setIdenVal(String idenVal) {
        this.idenval = idenVal;
    }
}

