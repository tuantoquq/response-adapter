package com.company.responseadapter.service;

import com.company.responseadapter.builder.Response;
import com.company.responseadapter.constant.ApplicationConstant;
import com.company.responseadapter.model.request.MessageData;
import com.company.responseadapter.model.response.FacebookResponse;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


@Service
public class FacebookService implements BaseService{
    private static final Logger logger = LoggerFactory.getLogger(FacebookService.class);
    Gson gson = new Gson();

//    @Async("processExecutor")
    @Override
    public Response sendText(MessageData mes){
        Response svResponse;
        String senderId = mes.getSenderId();
        String url = String.format("https://graph.facebook.com/v11.0/%s/messages?access_token=%s",senderId, ApplicationConstant.ACCESS_TOKEN_FACEBOOK);
        try {
            URLConnection con = new URL(url).openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            String json = String.format("{\"messaging_type\":\"RESPONSE\",\"recipient\":{\"id\":\"%s\"},\"message\":{\"text\":\"%s\"}}",mes.getReceiveId(),mes.getText());

            String output = GeneralService.getResponseFromSocial(http, json);
            FacebookResponse res = gson.fromJson(output,FacebookResponse.class);
            svResponse = new Response.Builder(200)
                    .buildMessage("successfully")
                    .buildData(res)
                    .build();
            logger.info(String.format("### -> response from facebook -> : %s",output));

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

        Response svResponse;
        String senderId = mes.getSenderId();
        String url = String.format("https://graph.facebook.com/v11.0/%s/messages?access_token=%s",senderId, ApplicationConstant.ACCESS_TOKEN_FACEBOOK);
        try {
            URLConnection con = new URL(url).openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            String json = String.format("{\"messaging_type\":\"RESPONSE\",\"recipient\":{\"id\":\"%s\"},\"message\":{\"attachment\":[%s]}",mes.getReceiveId(),gson.toJson(mes.getAttachments()));

            String output = GeneralService.getResponseFromSocial(http, json);
            FacebookResponse res = gson.fromJson(output,FacebookResponse.class);
            svResponse = new Response.Builder(200)
                    .buildMessage("successfully")
                    .buildData(res)
                    .build();
            logger.info(String.format("### -> response from facebook -> : %s",output));

        } catch (IOException e) {
            logger.error("open connection failed: {}",e.getMessage());
            svResponse = new Response.Builder(404)
                    .buildMessage("URL not found")
                    .build();

        }
        return svResponse;
    }

}
