package top.i7un.springboot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:  Noone
 * @Date: 2018/6/5 19:41
 * Describe: 权限
 */
@Data
@NoArgsConstructor
public class Role {

    private int id;

    private String name;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
