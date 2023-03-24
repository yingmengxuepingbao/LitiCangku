package com.penghaisoft.framework.util;

public class BaseService {

  protected Resp success() {
    Resp resp = new Resp();
    resp.setCode("0");
    return resp;
  }

  protected Resp success(String msg) {
    Resp resp = new Resp();
    resp.setCode("0");
    resp.setMsg(msg);
    return resp;
  }

  protected Resp fail(String msg) {
    Resp resp = new Resp();
    resp.setCode("1");
    resp.setMsg(msg);
    return resp;
  }
}
