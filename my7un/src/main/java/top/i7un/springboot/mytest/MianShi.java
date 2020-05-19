package top.i7un.springboot.mytest;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * Created by Noone on 2020-05-13.
 */
public class MianShi {

    public void test(){

    }

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "baidu");
        map.put("2", "ali");
        map.put("3", "tengxun");

        Set<Map.Entry<String, String>> entries = map.entrySet();
//        entries.removeIf(entry -> entry.getValue().equalsIgnoreCase("ali"));
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                if ("ali".equalsIgnoreCase(next.getValue())){
                    iterator.remove();
                }
            }
        String s = JSONObject.toJSONString(map);
        System.out.println(s);
    }
}
