package cn.iocoder.springboot.lab15.springdataelasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.http.HttpHeaders;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 一句话功能描述
 * 功能详细描述
 * <p>
 *
 * @author dc
 * @version V1.0
 * @since 2020-08-06 11:21
 **/
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        HttpHeaders defaultHeaders = new HttpHeaders();
        defaultHeaders.add("some-header", "on every request");

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("192.168.3.27:9200")
                //.usingSsl()
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))
                //.withDefaultHeaders(defaultHeaders)
                //.withBasicAuth(username, password)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
