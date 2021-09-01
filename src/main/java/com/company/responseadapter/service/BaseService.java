package com.company.responseadapter.service;

import com.company.responseadapter.builder.Response;
import com.company.responseadapter.model.request.MessageData;



public interface BaseService {
    public Response sendText(MessageData mes);
    public Response sendAttachment(MessageData mes);
}
