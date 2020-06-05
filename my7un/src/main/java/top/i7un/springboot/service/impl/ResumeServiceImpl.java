package top.i7un.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.i7un.springboot.mapper.WorkMapper;
import top.i7un.springboot.model.Work;
import top.i7un.springboot.service.ResumeService;
import top.i7un.springboot.utils.DataMap;

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
        return DataMap.success().setData(works);
    }
}