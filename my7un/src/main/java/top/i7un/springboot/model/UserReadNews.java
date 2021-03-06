package top.i7un.springboot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author:  Noone
 * @Date: 2019/5/6 15:39
 * Describe: 用户评论留言未读数
 */
@Data
public class UserReadNews implements Serializable{

    /**
     * 留言+评论未读数
     */
    private int allNewsNum;

    /**
     * 评论未读数
     */
    private int commentNum;

    /**
     * 留言未读数
     */
    private int leaveMessageNum;

    public UserReadNews(int allNewsNum, int commentNum, int leaveMessageNum) {
        this.allNewsNum = allNewsNum;
        this.commentNum = commentNum;
        this.leaveMessageNum = leaveMessageNum;
    }
}
