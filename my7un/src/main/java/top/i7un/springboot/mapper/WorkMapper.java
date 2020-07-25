package top.i7un.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import top.i7un.springboot.model.Work;
import top.i7un.springboot.model.WorkRecord;

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

    List<WorkRecord> getWorkRecordByWorkId(Long workId);

    Work getworkById(long workId);

    Work getTwoParams(String postName ,String department ,String project);

    Integer getRightIdFlag(Long workId);
}
