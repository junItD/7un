package top.i7un.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import top.i7un.springboot.model.Work;

import java.util.List;

/**
 * Created by Noone on 2020/6/4.
 */
@Mapper
@Repository
public interface WorkMapper {

    List<Work> selectAllWork();

//    @Select("select * from t_work")
    List<Work> selectWork();
}
