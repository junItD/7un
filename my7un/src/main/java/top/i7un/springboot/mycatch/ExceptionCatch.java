//package top.i7un.springboot.mycatch;
//
//import com.google.common.collect.ImmutableMap;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * Created by ZhaoJun on 2019/1/16.
// * 统一异常捕获类
// */
//@ControllerAdvice
//@Slf4j
//public class ExceptionCatch {
//
//
//    private static ImmutableMap<Class<? extends Throwable>,ResultCode> immutableMap;
//    static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();
//    static {
//        builder.put(NullPointerException.class, CommonCode.NULLPOINTEREXCEPTION)
//                .put(NumberFormatException.class,CommonCode.NUMBERFORMATEXCEPTION);
//    }
//    @ExceptionHandler(BusinessConnectException.class)
//    @ResponseBody
//    public ResponseResult businessException(BusinessConnectException businessConnectException){
//        log.warn("catch exception:{}",businessConnectException.getResultCode().message());
//        ResultCode resultCode = businessConnectException.getResultCode();
//        return new ResponseResult(resultCode);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ResponseResult exception(Exception exception){
//        log.warn("catch exception:{}",exception);
//        if (immutableMap ==null){
//            immutableMap =  builder.build();
//        }
//        ResultCode resultCode = immutableMap.get(exception.getClass());
//        if (null != resultCode){
//            return new ResponseResult(resultCode);
//        }
//        return new ResponseResult(CommonCode.SERVER_ERROR);
//    }
//}
