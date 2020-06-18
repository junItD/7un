package top.i7un.springboot.mycatch;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.i7un.springboot.constant.CodeType;
import top.i7un.springboot.utils.JsonResult;

/**
 * Created by ZhaoJun on 2019/1/16.
 * 统一异常捕获类
 */
@ControllerAdvice
@Slf4j
public class QunExceptionCatch {


    private static ImmutableMap<Class<? extends Throwable>,CodeType> immutableMap;
    static ImmutableMap.Builder<Class<? extends Throwable>,CodeType> builder = ImmutableMap.builder();
    static {
        builder.put(NullPointerException.class, CodeType.NULLPOINTEREXCEPTION)
                .put(ArithmeticException.class,CodeType.ARITHMETICEXCEPTION)
                .put(NumberFormatException.class,CodeType.NUMBERFORMATEXCEPTION);
    }
    @ExceptionHandler(BusinessConnectException.class)
    @ResponseBody
    public ResponseResult businessException(BusinessConnectException businessConnectException){
        log.warn("catch exception:{}",businessConnectException.getResultCode().message());
        ResultCode resultCode = businessConnectException.getResultCode();
        return new ResponseResult(resultCode);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String exception(Exception exception){
        log.warn("catch exception:{}",exception);
        if (immutableMap ==null){
            immutableMap =  builder.build();
        }
        CodeType resultCode = immutableMap.get(exception.getClass());
        if (null != resultCode){
            return JsonResult.fail(resultCode).toJSON();
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


}
