//package top.i7un.springboot.utils;
//
//import com.alibaba.fastjson.JSONObject;
//import com.google.gson.JsonObject;
//import com.hsong.common.utils.date.DateUtils;
//import com.hsong.common.utils.json.JsonUtils;
//import com.hsong.web.wechatserver.dto.renwubao.*;
//import com.hsong.web.wechatserver.mybatis.entity.HsRwbRecord;
//import com.hsong.web.wechatserver.mybatis.service.IHsRwbRecordService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
///**
// * Created by Noone on 2020-09-17.
// */
//@Slf4j
//@Component
//public class AbstractTemplatePattern {
//
//    @Autowiredå
//    private IHsRwbRecordService iHsRwbRecordService;
//
//    static CloseableHttpClient httpClient;
//    static int connectRequestTimeout = 2000;
//    static int connectTimeout = 5000;
//    static int socketTimeout = 5000;
//    private HttpClientContext context = HttpClientContext.create();
//
//    static {
//        RequestConfig.custom()
//                .setConnectionRequestTimeout(connectRequestTimeout)
//                .setConnectTimeout(connectTimeout)
//                .setSocketTimeout(socketTimeout)
//                .build();
//
//        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
//        // Increase max total connection to 200
//        cm.setMaxTotal(5);
//        // Increase default max connection per route to 20
//        cm.setDefaultMaxPerRoute(5);
//        //启动空闲连接回收线程
//
//        httpClient = HttpClients.custom()
//                .setConnectionManager(cm)
//                .setKeepAliveStrategy(HttpClientConfigUtils.getKeepAliveStrategy())
//                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
//                .build();
//    }
//
//    @Autowired
//    private MailSendUtil mailSendUtil;
//    public final void getResult(){
//        login();
//        RwbResult data = getData();
//        insertDb(data);
//        SendMail(data);
//    }
//
//    private void SendMail(RwbResult data) {
//        StringBuilder content = new StringBuilder("<html><head></head><body><h2>任务宝数据</h2>");
//        content.append("<table border=\"5\" style=\"border:solid 1px #E8F2F9;font-size=14px;;font-size:18px;\">");
//        content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>活动名称</th><th>公众号</th><th>记录时间</th><th>昨日新增</th><th>昨日参与</th>" +
//                "<th>分享率</th><th>分享带来的粉丝数</th><th>分享K值</th><th>平均拉新人数</th>");
//        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
//        for (RwbPatchResult rwbPatchResult : data.getRwbPatchResultList()) {
//            content.append("<tr>");
//            content.append("<td>" + rwbPatchResult.getName() + "</td>"); //第一列
//            content.append("<td>" + rwbPatchResult.getSubscription() + "</td>");
//            content.append("<td>" + rwbPatchResult.getDate() + "</td>");
//            content.append("<td>" + rwbPatchResult.getYesterdayNewNum() + "</td>");
//            content.append("<td>" + rwbPatchResult.getYesterdayParticipationNum() + "</td>");
//            content.append("<td>" + decimalFormat.format(rwbPatchResult.getShareRate()) + "</td>");
//            content.append("<td>" + rwbPatchResult.getShareCount() + "</td>");
//            content.append("<td>" + decimalFormat.format(rwbPatchResult.getKRate()) + "</td>");
//            content.append("<td>" + rwbPatchResult.getAverageNewCount() + "</td>");
//            content.append("</tr>");
//        }
//        content.append("</table>");
//        content.append("</body></html>");
//        mailSendUtil.send("任务宝数据", content.toString());
//        log.info("=======任务宝发送邮件成功=====");
//    }
//
//    private void insertDb(RwbResult data) {
//        data.getRwbPatchResultList().forEach(rwbPatchResult -> {
//            HsRwbRecord hsRwbRecord = new HsRwbRecord();
//            BeanUtils.copyProperties(rwbPatchResult, hsRwbRecord);
//            iHsRwbRecordService.save(hsRwbRecord);
//        });
//    }
//
//    private LoginUser loginUser = null;
//    private HttpPost httpPost = null;
//    private CloseableHttpResponse response = null;
//    private HttpGet httpGet = null;
//    private RwbResult rwbResult = new RwbResult();
//    private List<RwbPatchResult> rwbPatchResults;
//    private void login() {
//        log.info("=======任务宝开始尝试登录========");
//        try {
//            context = rewubaoLogin("zhenqin.wang@hongsong.club", "cfcdb327876d5448891d77d14d659db1");
//            if (context == null) {
//                log.error("任务宝尝试登录失败登录失败");
//                return;
//            }
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            log.error("任务宝尝试登录失败登录失败");
//        }
//        log.info("=======任务宝登录成功 开始调用数据========");
//    }
//    public RwbResult getData(){
//        rwbPatchResults = new ArrayList<>();
//        Integer  partFansCount = 0;
//        Integer  newFansCount =0;
//        //抓取群数据总量统计
//        log.info("=========尝试抓取群数据总量统计=========");
//        httpGet = new HttpGet("https://www.94mxd.com/mxd/taskposter/stats/all/yesterday");
//        setRwbCommHeader(httpGet);
//        httpGet.setHeader("referer", "https://www.94mxd.com/index/dashboard");
//        try {
//            response = httpClient.execute(httpGet, context);
//            if (response != null) {
//                HttpEntity resEntity = response.getEntity();
//                if (resEntity != null) {
//                    String result = EntityUtils.toString(resEntity, "UTF-8");
//                    log.info("[HttpClient]: 抓取群数据总量统计，result = " + result);
//                    if(StringUtils.isNotBlank(result)) {
//                        RwbResultDto rwbResultDto = JsonUtils.jsonToObject(result, RwbResultDto.class);
//                        partFansCount = rwbResultDto.getRespMsg().getPartFansCount();
//                        newFansCount = rwbResultDto.getRespMsg().getNewFansCount();
//                    }
//                }
//            }
//        } catch (IOException e) {
//            log.info("获取任务宝数据异常", e);
//        }
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info("=========抓取群数据总量统计成功 开始抓取状态为正在运行的列表  然后遍历=========");
//        //获取状态为正在运行的列表  然后遍历
//        httpPost = new HttpPost("https://www.94mxd.com/mxd/taskposter/query?pageSize=100&pageNum=1");
//        setRwbCommHeader(httpPost);
//        httpPost.addHeader("content-type", "application/json");
//        httpPost.setHeader("referer", "https://www.94mxd.com/index/ext/taskpost/record");
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("status", 1);
//        try {
//            httpPost.setEntity(new StringEntity(jsonObject.toString()));
//            response = httpClient.execute(httpPost, context);
//            if (response != null) {
//                HttpEntity resEntity = response.getEntity();
//                if (resEntity != null) {
//                    String result = EntityUtils.toString(resEntity, "UTF-8");
//                    log.info("[HttpClient]: 抓取群数据总量统计，result = " + result);
//                    if(StringUtils.isNotBlank(result)) {
//                        RwbResultDto rwbResultDto = JsonUtils.jsonToObject(result, RwbResultDto.class);
//                        System.out.println(rwbResultDto);
//                        List<ActiveEntity> list = rwbResultDto.getRespMsg().getList();
//                        list.forEach(activeEntity -> {
//                            log.info("=========即将获取id为{}，title为{}的详细信息",activeEntity.getId(),activeEntity.getTitle());
//                            getDataByCurrTask(activeEntity.getId(),activeEntity.getTitle(), loginUser);
//                        });
//                        RwbPatchResult rwbPatchResult = new RwbPatchResult();
//                        rwbPatchResult.setName("总结");
//                        rwbPatchResult.setSubscription("");
//                        rwbPatchResult.setYesterdayParticipationNum(partFansCount);
//                        rwbPatchResult.setYesterdayNewNum(newFansCount);
//                        rwbPatchResult.setDate(DateUtils.formatDate(new Date()));
//                        rwbPatchResult.setShareCount(0);
//                        rwbPatchResults.add(rwbPatchResult);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            log.info("获取任务宝数据异常", e);
//        }
//        return rwbResult;
//    }
//
//    private void getDataByCurrTask(Integer id, String name, LoginUser loginUser) {
////        if (!"100张宣纸".equalsIgnoreCase(name)){
////            return;
////        }
//        RwbPatchResult rwbPatchResult = new RwbPatchResult();
//
//        //获取新增粉丝 和 参与人数
//        httpGet = new HttpGet("https://www.94mxd.com/mxd/taskposter/stats/daydetail?taskposterId="+id+"&channelId=0");
//        setRwbCommHeader(httpGet);
//        httpGet.setHeader("referer", "https://www.94mxd.com/index/ext/taskpost/stas/"+id);
//        try {
//            response = httpClient.execute(httpGet, context);
//            if (response != null) {
//                HttpEntity resEntity = response.getEntity();
//                if (resEntity != null) {
//                    String result = EntityUtils.toString(resEntity, "UTF-8");
//                    if(StringUtils.isNotBlank(result)) {
//                        RwbResultDto rwbResultDto = JsonUtils.jsonToObject(result, RwbResultDto.class);
//                        List<DayDetail> daydetail = rwbResultDto.getRespMsg().getDaydetail();
//                        //按时间倒序 并取出前两个
//                        List<DayDetail> collect = daydetail.stream().sorted(Comparator.comparing(DayDetail::getStatData).reversed()).limit(2).collect(Collectors.toList());
//                        DayDetail today = collect.get(0);
//                        rwbPatchResult.setName(name);
//                        String currDate = DateUtils.formatDate(new Date());
//                        rwbPatchResult.setDate(currDate);
//                        if (daydetail.size()>1){
//                            DayDetail yesterday = collect.get(1);
//                            rwbPatchResult.setYesterdayNewNum(yesterday.getNewNum());
//                            rwbPatchResult.setYesterdayParticipationNum(yesterday.getTotalParticipationNum());
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//            log.info("获取任务宝数据异常", e);
//        }
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        //公众号id及名称
//        httpGet = new HttpGet("https://www.94mxd.com/mxd/taskposter/channel_subs_list?taskposterId="+id);
//        setRwbCommHeader(httpGet);
//        httpGet.setHeader("referer", "https://www.94mxd.com/index/ext/taskpost/stas/"+id);
//        try {
//            response = httpClient.execute(httpGet, context);
//            if (response != null) {
//                HttpEntity resEntity = response.getEntity();
//                if (resEntity != null) {
//                    String result = EntityUtils.toString(resEntity, "UTF-8");
//                    if(StringUtils.isNotBlank(result)) {
//                        RwbResultDto rwbResultDto = JsonUtils.jsonToObject(result, RwbResultDto.class);
//                        rwbPatchResult.setSubscription(rwbResultDto.getRespMsg().getSubsList().get(0).getNickName());
//                    }
//                }
//            }
//        } catch (IOException e) {
//            log.info("获取任务宝数据异常", e);
//        }
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        //获取分享率 分享带来的粉丝数
//        httpGet = new HttpGet("https://www.94mxd.com/mxd/taskposter/stats/total?taskposterId="+id+"&channelId=0");
//        setRwbCommHeader(httpGet);
//        httpGet.setHeader("referer", "https://www.94mxd.com/index/ext/taskpost/stas/"+id);
//        try {
//            response = httpClient.execute(httpGet, context);
//            if (response != null) {
//                HttpEntity resEntity = response.getEntity();
//                if (resEntity != null) {
//                    String result = EntityUtils.toString(resEntity, "UTF-8");
//                    if(StringUtils.isNotBlank(result)) {
//                        RwbResultDto rwbResultDto = JsonUtils.jsonToObject(result, RwbResultDto.class);
//                        RespMsgDto respMsg = rwbResultDto.getRespMsg();
//                        PartDist partDist = respMsg.getPartDist();
//                        List<LevelDto> level = respMsg.getLevel();
//                        Total total = respMsg.getTotal();
//                        double doubleValue = 0;
//                        try {
//                            float val = (float) (partDist.getTotalPart()-partDist.getPartNoProm()) / partDist.getTotalPart();
//                            doubleValue = new BigDecimal(val).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
//                        } catch (Exception e) {
//                            log.error("=====任务宝中{}数据异常 没有进行记录",name);
//                            //此情况为今日新开的活动 昨日没有数据 不需要进行统计
//                            return;
//                        }
//                        double shareRate = doubleValue;
//                        rwbPatchResult.setShareRate(shareRate); //3.分享率=1-参与但未推广率
//                        List<LevelDto> collect = level.stream().filter(levelDto -> levelDto.getLevel() == 1).collect(Collectors.toList());
//                        Integer partNum = total.getPartNum();
//                        int shareCount = partNum - collect.get(0).getCount();
//                        rwbPatchResult.setShareCount(shareCount); //4.分享带来的粉丝数=总参与人数-1级参与人数
//                        double averageNewCount = 0;
//                        try {
//                            double kRate = new BigDecimal((float)shareCount/partNum).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
//                            rwbPatchResult.setKRate(kRate);
//                            averageNewCount = new BigDecimal((float)shareCount/(partNum*shareRate)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
//                        } catch (Exception e) {
//                            log.error("=====任务宝中{}数据异常 没有进行记录",name);
//                            //此情况为今日新开的活动 昨日没有数据 不需要进行统计
//                            return;
//                        }
//                        rwbPatchResult.setAverageNewCount(averageNewCount);
//                        rwbPatchResults.add(rwbPatchResult);
//                        rwbResult.setRwbPatchResultList(rwbPatchResults);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            log.info("获取任务宝数据异常", e);
//        }
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//
//    }
//
//
//    public HttpClientContext rewubaoLogin(String userName, String password) {
//        //验证登录状态是否有效
//        httpGet = new HttpGet("https://www.94mxd.com/mxd/taskposter/stats/all/yesterday");
//        setRwbCommHeader(httpGet);
//        httpGet.setHeader("referer", "https://www.94mxd.com/index/dashboard");
//        try {
//            response = httpClient.execute(httpGet, context);
//            if (response != null) {
//                HttpEntity resEntity = response.getEntity();
//                if (resEntity != null) {
//                    String result = EntityUtils.toString(resEntity, "UTF-8");
//                    log.info("[HttpClient]: 抓取群数据总量统计，result = " + result);
//                    if(StringUtils.isNotBlank(result)) {
//                        RwbResultDto rwbResultDto = JsonUtils.jsonToObject(result, RwbResultDto.class);
//                        if (rwbResultDto.getRespCode() == 0){
//                            log.info("***********登录状态有效 无需重新登录************");
//                            return context;
//                        }
//
//                    }
//                }
//            }
//
//            //登录
//            httpPost = new HttpPost("https://www.94mxd.com/mxd/user/signin");
//            setRwbCommHeader(httpPost);
//            httpPost.setHeader("referer", "https://www.94mxd.com/signin");
////            httpPost.setHeader("content-length", "84");
//            httpPost.setHeader("content-type", "application/json");
//            httpPost.setHeader("cookie", "SESSION=6a8208f6-269f-461f-8ed3-58067040a268; SERVERID=7b74f0f837bd29e9d1e95acb52b90183|1600312316|1600312307");
//            httpPost.setHeader("origin", "https://www.94mxd.com");
//            httpPost.setEntity(new StringEntity(JSONObject.toJSONString(RenwubaoEntity.builder().email(userName).password(password).build())));
//            response = httpClient.execute(httpPost, context);
//
//            if (response != null) {
//                HttpEntity resEntity = response.getEntity();
//                if (resEntity != null) {
//                    String loginResult = EntityUtils.toString(resEntity, "UTF-8");
//                    log.info("[HttpClient]:login result=" + loginResult);
//                    RwbResultDto rwbResultDto = JsonUtils.jsonToObject(loginResult, RwbResultDto.class);
//                    if ("success".equalsIgnoreCase(rwbResultDto.getDescription()) && 0== rwbResultDto.getRespCode()){
//                        return context;
//                    }
//                }
//            }
//        } catch (IOException e) {
//            log.error("任务宝登录异常，异常原因为",e);
//            e.printStackTrace();
//        }
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public static void setRwbCommHeader(HttpUriRequest httpPost){
//        httpPost.setHeader("accept", "*/*");
//        httpPost.setHeader("accept-encoding", "gzip, deflate");
//        httpPost.setHeader("accept-language", "zh-CN,zh;q=0.9");
//        httpPost.setHeader("sec-fetch-dest", "empty");
//        httpPost.setHeader("sec-fetch-mode", "cors");
//        httpPost.setHeader("sec-fetch-site", "same-origin");
//        httpPost.setHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36");
//    }
//
//    public static void main(String[] args) {
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("status", 1);
//        String s = jsonObject.toString();
//        System.out.println(jsonObject.toString());
//    }
//}
//
//
//
