package cn.iocoder.springboot.lab15.springdataelasticsearch.repository;

import cn.iocoder.springboot.lab15.springdataelasticsearch.Application;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * 一句话功能描述
 * 功能详细描述
 * <p>
 *
 * @author dc
 * @version V1.0
 * @since 2020-08-06 11:23
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RestHighLevelClientTest {

    @Autowired
    RestHighLevelClient highLevelClient;
}
