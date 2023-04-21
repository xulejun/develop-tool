package com.legend.crawler.test;

import com.legend.crawler.CrawlerApplication;
import com.legend.crawler.wechat.WechatNineValenceNoticeJob;
import com.legend.crawler.wechat.ninevalence.AppletMaternityCareNotice;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * @author legend xu
 * @date 2021/11/29
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CrawlerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppletMaternityCareNoticeTest {
    @Autowired
    private AppletMaternityCareNotice appletMaternityCareNotice;

    @Autowired
    WechatNineValenceNoticeJob job;

    @Test
    public void testRequest() {
        // 发送请求
        appletMaternityCareNotice.notice();
    }

    @Test
    public void testAppletJob() throws Exception {
        // 发送请求
        job.maternityCareAppletNotice();
    }
}
