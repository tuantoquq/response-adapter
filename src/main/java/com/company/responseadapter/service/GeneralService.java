package com.company.responseadapter.service;

import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class GeneralService {
    private static final JsonParser jsonParser = new JsonParser();
    public static String getResponseFromSocial(HttpURLConnection http, String json) throws IOException {
        byte[] out  = json.getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        http.connect();

        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String output = br.readLine();
        return output;
    }
    public static String getResponseFromZalo(String json){
        return jsonParser.parse(json)
                .getAsJsonObject()
                .get("data").toString();
    }
}
