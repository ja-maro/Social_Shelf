package com.quest.etna.service;

import org.springframework.stereotype.Service;

@Service
public class JsonService {

    public String successBody(Boolean success) {
        return "{\"success\":" + success + "}";
    }

}
