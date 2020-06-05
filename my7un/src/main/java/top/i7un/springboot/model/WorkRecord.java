package top.i7un.springboot.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by Noone on 2020/6/4.
 */
@Data
public class WorkRecord {

        private Long id;
        private Long workId;
        private String project;
        private String projetDetail;
        private Date beginTime;
        private Date endTime;
}
