package ru.home;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {
    public static ObjectMapper mapper = new ObjectMapper();

    public static final String REMOTE_SERVICE_URI =
            "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("My Test Service")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(6000)
                        .setSocketTimeout(10000)
                        .setRedirectsEnabled(true)
                        .build())
                .build();

        HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
        CloseableHttpResponse response = httpClient.execute(request);

        List<JsonPost> posts = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
        });
        for (JsonPost post : posts) {
            if (post.getUpvotes() > 0)
                System.out.println(post);
        }
    }
}

