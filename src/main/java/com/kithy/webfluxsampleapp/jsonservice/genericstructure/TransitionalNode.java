package com.kithy.webfluxsampleapp.jsonservice.genericstructure;

import java.util.LinkedHashMap;
import java.util.Map;

public class TransitionalNode extends Node{

    private Map<String, Node> content;

    public TransitionalNode(){
        this.content = new LinkedHashMap<>();
    }

    public TransitionalNode addAttribute(String key, Node value){
        this.content.put(key, value);
        return this;
    }
}
