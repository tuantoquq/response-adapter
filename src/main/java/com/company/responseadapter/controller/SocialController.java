package com.company.responseadapter.controller;

import com.company.responseadapter.builder.Response;
import com.company.responseadapter.model.request.MessageData;
import com.company.responseadapter.service.FacebookService;
import com.company.responseadapter.service.ZaloService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sendMessage")
public class SocialController {

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private ZaloService zaloService;


    Gson gson = new Gson();
    @PostMapping(
            path = "/facebook",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Response> sendMessageToFacebook(@RequestBody String data) {
        Response response = null;
        if (data.contains("\"text\"")) {
            MessageData mes = gson.fromJson(data, MessageData.class);
            response = facebookService.sendText(mes);
        }
        return new ResponseEntity<>(response,HttpStatus.valueOf(response.getCode()));
    }

    @PostMapping(
            path = "/zalo",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Response> sendMessageToZalo(@RequestBody String data) {
        Response response = null;
        if (data.contains("\"text\"")) {
            MessageData mes = gson.fromJson(data, MessageData.class);
            response = zaloService.sendText(mes);
        }
        return new ResponseEntity<>(response,HttpStatus.valueOf(response.getCode()));
    }



}
