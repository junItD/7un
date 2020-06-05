package top.i7un.springboot.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Noone on 2020/6/4.
 */
@Data
public class Work {

      private Long id ;
      private String company;
      private String companyAbb;
      private String department;
      private String postName;
      @JSONField (format="yyyy-MM")
      private Date beginTime;
     @JSONField (format="yyyy-MM")
      private Date endTime;
      private String whyLeave;
      private List<WorkRecord> workRecordList;
}
