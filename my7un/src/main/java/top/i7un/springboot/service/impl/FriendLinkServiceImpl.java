package top.i7un.springboot.service.impl;

import top.i7un.springboot.constant.CodeType;
import top.i7un.springboot.mapper.FriendLinkMapper;
import top.i7un.springboot.model.FriendLink;
import top.i7un.springboot.service.FriendLinkService;
import top.i7un.springboot.utils.DataMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:  Noone
 * @Date: 2019/5/16 17:09
 * Describe:
 */
@Service
@Slf4j
public class FriendLinkServiceImpl implements FriendLinkService {

    @Autowired
    FriendLinkMapper friendLinkMapper;

    @Override
    public DataMap addFriendLink(FriendLink friendLink) {
        int id = friendLinkMapper.findIsExistByBlogger(friendLink.getBlogger());
        if(id == 0){
            friendLinkMapper.save(friendLink);
            return DataMap.success(CodeType.ADD_FRIEND_LINK_SUCCESS)
                    .setData(friendLink.getId());
        } else {
            return DataMap.fail(CodeType.FRIEND_LINK_EXIST);
        }
    }

    @Override
    public DataMap getAllFriendLink() {
        List<FriendLink> links = friendLinkMapper.getAllFriendLink();
        return DataMap.success().setData(links);
    }

    @Override
    public DataMap updateFriendLink(FriendLink friendLink, int id) {
        friendLinkMapper.updateFriendLink(friendLink, id);
        return DataMap.success(CodeType.UPDATE_FRIEND_LINK_SUCCESS);
    }

    @Override
    public DataMap deleteFriendLink(int id) {
        friendLinkMapper.deleteFriendLinkById(id);
        return DataMap.success(CodeType.DELETE_FRIEND_LINK_SUCCESS);
    }

    @Override
    public DataMap getFriendLink() {
        List<FriendLink> links = friendLinkMapper.getAllFriendLink();
        return DataMap.success().setData(links);
    }
}
