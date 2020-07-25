package top.i7un.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.i7un.springboot.mapper.WorkMapper;
import top.i7un.springboot.model.Work;
import top.i7un.springboot.model.WorkRecord;
import top.i7un.springboot.service.ResumeService;
import top.i7un.springboot.utils.DataMap;

import java.util.Date;
import java.util.List;

/**
 * Created by Noone on 2020/6/4.
 */
@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private WorkMapper workMapper;

    @Override
    public DataMap findAllwork() {
        List<Work> works = workMapper.selectAllWork();
        works.stream().filter(work -> work.getEndTime() == null).forEach(work -> work.setEndTime(new Date()));
        return DataMap.success().setData(works);
    }

    @Override
    public DataMap getWorkRecordByWorkId(String workId) {
        List<WorkRecord> list = workMapper.getWorkRecordByWorkId(Long.valueOf(workId));
        return DataMap.success().setData(list);
    }

    @Override
    public Work getworkById(long workId) {
        return workMapper.getworkById(workId);
    }

    @Override
    public Boolean getRightIdFlag(Long workId) {
        Integer rightIdFlag = workMapper.getRightIdFlag(workId);
        return null == rightIdFlag;
    }

}
