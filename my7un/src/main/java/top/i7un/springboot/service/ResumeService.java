package top.i7un.springboot.service;

import top.i7un.springboot.model.Work;
import top.i7un.springboot.utils.DataMap;

/**
 * Created by Noone on 2020/6/4.
 */
public interface ResumeService {
    DataMap findAllwork();

    DataMap getWorkRecordByWorkId(String workId);

    Work getworkById(long workId);

    Boolean getRightIdFlag(Long workId);
}
