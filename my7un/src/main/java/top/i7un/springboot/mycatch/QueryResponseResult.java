package top.i7un.springboot.mycatch;

import lombok.Data;
import lombok.ToString;

/**
 * Created by ZhaoJun on 2019/1/21.
 */
@Data
@ToString
public class QueryResponseResult<T extends Object> extends ResponseResult {

    T data;

    public QueryResponseResult(ResultCode resultCode,T data){
        super(resultCode);
        this.data = data;
    }

    public QueryResponseResult(){};
}
