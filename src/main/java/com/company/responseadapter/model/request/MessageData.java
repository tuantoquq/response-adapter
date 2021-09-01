package com.company.responseadapter.model.request;

import com.company.responseadapter.model.request.BaseData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageData extends BaseData {
    private String text;
    private Object attachments;
}
