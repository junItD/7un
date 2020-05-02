package top.i7un.springboot.service;

import top.i7un.springboot.model.DailySpeech;
import top.i7un.springboot.utils.DataMap;

/**
 * @author: zhangocean
 * @Date: 2018/11/28 15:33
 * Describe: 藏心阁-今日
 */
public interface TodayService {

    DataMap publishISay(DailySpeech dailySpeech);

    DataMap getTodayInfo(int rows, int pageNum);

}
