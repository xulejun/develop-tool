//package com.legend.crawler.test;
//
//import com.google.common.collect.Maps;
//import com.legend.crawler.CrawlerApplication;
//import com.legend.crawler.wechat.bean.WechatArticleBean;
//import com.legend.crawler.wechat.content.WechatList;
//import com.legend.crawler.wechat.content.WechatLogin;
//import com.legend.crawler.wechat.ninevalence.AccountNineValenceNotice;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//import java.util.Map;
//
//
///**
// * 微信公众号九价文章采集通知
// *
// * @author legend xu
// * @date 2021/11/25
// */
//@Slf4j
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = CrawlerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class AccountNineValenceNoticeTest {
//    @Value("${wechat.cookie}")
//    private String cookie;
//
//    @Autowired
//    private AccountNineValenceNotice nineValenceNotice;
//    /**
//     * 采集文章的时间范围
//     */
//    private static Map<String, String> timeRange = Maps.newHashMap();
//
//    static {
//        timeRange.put("07:55:00", "08:01:00");
//        timeRange.put("10:55:00", "11:01:00");
//        timeRange.put("11:59:00", "12:20:00");
//        timeRange.put("12:25:00", "12:31:00");
//        timeRange.put("16:40:00", "17:01:00");
//        timeRange.put("18:55:00", "19:01:00");
//        timeRange.put("19:55:00", "20:01:00");
//        timeRange.put("21:55:00", "22:01:00");
//    }
//
//    @Test
//    public void nineValenceTest() throws Exception {
////        while (true) {
////            DateTime dateTime = new DateTime();
////            String dateStr = dateTime.toString();
////            // 取出年月日 yyyy-MM-dd
////            String date = dateStr.substring(0, dateStr.indexOf(" ") + 1);
////            for (String start : timeRange.keySet()) {
////                String end = timeRange.get(start);
////                String startTimeStr = date.concat(start);
////                String endTimeStr = date.concat(end);
////                Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTimeStr);
////                Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTimeStr);
//////                log.info("开始时间={}，结束时间={}", startTimeStr, endTimeStr);
////                // 处于策略范围内，则采集
////                if (dateTime.isIn(startTime, endTime)) {
//                    try {
//                        nineValenceNotice.notice();
//                    } catch (Exception e) {
//                        log.warn("采集文章失败：", e);
//                        return;
//                    }
////                    break;
////                }
////            }
////            TimeUnit.SECONDS.sleep(RandomUtil.randomLong(1, 5));
////        }
//    }
//
//    /**
//     * 测试cookie 有效性
//     */
//    @Test
//    public void testCookie() throws Exception {
//        String queryAccount = "公园接种";
//        String token = WechatLogin.getToken(cookie);
//        String fakeId = WechatLogin.getFakeId(cookie, queryAccount, token);
//        List<WechatArticleBean> list = WechatList.getWeixinArticleList(token, fakeId, 1);
//        list.forEach(System.out::println);
//    }
//}
