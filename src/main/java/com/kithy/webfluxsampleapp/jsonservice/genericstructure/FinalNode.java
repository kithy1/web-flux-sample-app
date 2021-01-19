package com.kithy.webfluxsampleapp.jsonservice.genericstructure;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FinalNode extends Node{

    private Map<String, List<Object>> content;

    public FinalNode(){
        this.content = new LinkedHashMap<>();
    }

    public FinalNode addAttribute(String key, List<Object> value){
        this.content.put(key, value);
        return this;
    }
}
