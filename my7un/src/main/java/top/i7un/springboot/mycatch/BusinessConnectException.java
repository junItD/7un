package top.i7un.springboot.mycatch;


/**
 * Created by ZhaoJun on 2019/1/16.
 */
public class BusinessConnectException extends RuntimeException {

    ResultCode resultCode;


    public ResultCode getResultCode() {
        return resultCode;
    }

    public BusinessConnectException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
}
