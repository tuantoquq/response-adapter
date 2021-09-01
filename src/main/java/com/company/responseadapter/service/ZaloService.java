package com.company.responseadapter.service;

import com.company.responseadapter.builder.Response;
import com.company.responseadapter.constant.ApplicationConstant;
import com.company.responseadapter.model.request.MessageData;
import com.company.responseadapter.model.response.FacebookResponse;
import com.company.responseadapter.model.response.ZaloResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@Service
public class ZaloService implements BaseService{
    private static final Logger logger = LoggerFactory.getLogger(ZaloService.class);
    Gson gson = new Gson();
//    @Async("processExecutor")
    @Override
    public Response sendText(MessageData mes) {
        Response svResponse;
        String url = String.format("https://openapi.zalo.me/v2.0/oa/message?access_token=%s", ApplicationConstant.ACCESS_TOKEN_ZALO);
        try {
            URLConnection con = new URL(url).openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            String json = String.format("{\"recipient\":{\"user_id\":\"%s\"},\"message\":{\"text\":\"%s\"}}",mes.getReceiveId(),mes.getText());
            String output = GeneralService.getResponseFromSocial(http, json);

            ZaloResponse res = gson.fromJson(GeneralService.getResponseFromZalo(output), ZaloResponse.class);

            svResponse = new Response.Builder(200)
                    .buildMessage("send to Zalo successfully")
                    .buildData(res)
                    .build();
            logger.info(String.format("### -> response from zalo -> : %s",output));

        } catch (IOException e) {
            logger.error("open connection failed: {}",e.getMessage());
            svResponse = new Response.Builder(404)
                    .buildMessage("URL not found")
                    .build();
        }
        return svResponse;
    }

    @Override
    public Response sendAttachment(MessageData mes) {
        return null;
    }
}
