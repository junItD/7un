package top.i7un.springboot.service.impl;

import top.i7un.springboot.constant.CodeType;
import top.i7un.springboot.mapper.RewardMapper;
import top.i7un.springboot.model.Reward;
import top.i7un.springboot.service.RewardService;
import top.i7un.springboot.utils.DataMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:  Noone
 * @Date: 2019/7/14 15:45
 * Describe:
 */
@Service
@Slf4j
public class RewardServiceImpl implements RewardService {

    @Autowired
    private RewardMapper rewardMapper;

    @Override
    public DataMap save(Reward reward) {
        rewardMapper.save(reward);
        return DataMap.success(CodeType.ADD_REWARD_SUCCESS)
                .setData(reward.getId());
    }

    @Override
    public DataMap getRewardInfo() {
        List<Reward> rewardList = rewardMapper.getAllReward();
        return DataMap.success().setData(rewardList);
    }

    @Override
    public DataMap deleteReward(int id) {
        rewardMapper.deleteRewardById(id);
        return DataMap.success(CodeType.DELETE_REWARD_SUCCESS);
    }
}
