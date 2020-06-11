package top.i7un.springboot.mycatch;

import lombok.ToString;

/**
 * Created by ZhaoJun on 2019/1/16.
 */
@ToString
public enum CommonCode implements ResultCode {

    SUCCESS(true,10000,"操作成功！"),
    FAIL(false,11111,"操作失败！"),
    INVALID_PARAM(false,10003,"非法参数！"),
    CATEGORY_PARAM_INVALID(false,10004,"请先填写二级分类"),
    REQUESTJDEXCETPION(false,10005,"调用京东接口异常"),
    CATEGORY_NULL_INVALID(false,10006,"请填写类目"),
    NULLPOINTEREXCEPTION(false,20001,"空指针异常！"),
    NUMBERFORMATEXCEPTION(false,20002,"类型转换异常，请检查参数！"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！");

    CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    boolean success;

    int code;

    String message;

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
