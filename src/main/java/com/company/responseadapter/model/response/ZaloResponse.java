package com.company.responseadapter.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ZaloResponse {
    @JsonProperty("messageId")
    private String message_id;

    @JsonProperty("userId")
    private String user_id;
}
