package com.company.responseadapter.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseData {
    protected String sendTo;
    protected String receiveId;
    protected String senderId;
}
