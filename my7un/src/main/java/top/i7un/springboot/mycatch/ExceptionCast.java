package top.i7un.springboot.mycatch;


/**
 * Created by ZhaoJun on 2019/1/16.
 */
public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        throw new BusinessConnectException(resultCode);
    }
}
