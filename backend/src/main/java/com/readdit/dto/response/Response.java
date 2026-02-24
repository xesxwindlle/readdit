package com.readdit.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Response {

    private Integer code;
    private String flag;
    private List<String> errorMsgs = new ArrayList<>();
    private List<String> warningMsgs = new ArrayList<>();
    private Object data;

    // === FACTORY METHODS ===

    public static Response success() {
        Response resp = new Response();
        resp.code = 1;
        resp.flag = "success";
        return resp;
    }

    public static Response success(Object object) {
        Response resp = new Response();
        resp.code = 1;
        resp.flag = "success";
        resp.data = object;
        return resp;
    }

    public static Response warning() {
        Response resp = new Response();
        resp.code = 1;
        resp.flag = "warning";
        return resp;
    }

    public static Response warning(String msg, Object object) {
        Response resp = new Response();
        resp.code = 1;
        resp.flag = "warning";
        resp.warningMsgs.add(msg);
        resp.data = object;
        return resp;
    }

    public static Response error(String msg) {
        Response resp = new Response();
        resp.code = 0;
        resp.flag = "error";
        resp.errorMsgs.add(msg);
        return resp;
    }

}