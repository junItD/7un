package top.i7un.springboot.service.impl;

import top.i7un.springboot.mapper.LeaveMessageLikesRecordMapper;
import top.i7un.springboot.model.LeaveMessageLikesRecord;
import top.i7un.springboot.service.LeaveMessageLikesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhangocean
 * @Date: 2018/7/16 15:32
 * Describe:
 */
@Service
public class LeaveMessageLikesRecordServiceImpl implements LeaveMessageLikesRecordService {

    @Autowired
    LeaveMessageLikesRecordMapper leaveMessageLikesRecordMapper;


    @Override
    public boolean isLiked(String pageName, int pId, int likeId) {

        return leaveMessageLikesRecordMapper.isLiked(pageName, pId, likeId) != null;
    }

    @Override
    public void insertLeaveMessageLikesRecord(LeaveMessageLikesRecord leaveMessageLikesRecord) {
        leaveMessageLikesRecordMapper.save(leaveMessageLikesRecord);
    }
}
