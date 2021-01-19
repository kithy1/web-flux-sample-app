package com.kithy.webfluxsampleapp.controller;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

@RestController
@RequestMapping("/main")
public class MainController {

    @PostMapping
    public void read(@RequestBody String stringObject) {
        JSONObject jsonObject = new JSONObject(stringObject);
        extractJson(jsonObject);
    }

    public void extractJson(JSONObject jsonObject) {
        JSONArray keys = jsonObject.names ();
        //System.out.println(keys);

        for (int i = 0; i < keys.length (); i++) {

            String key = keys.getString (i); // Here's your key
            String value;
            try {
                value = jsonObject.getString(key);
            } catch (JSONException e){
                value = jsonObject.get(key).toString();
                    if(value.contains("{") && value.contains("}")) {
                        value = value.replace("[","");
                        value = value.replace("]", "");
                        String[] split = value.split("},\\{");
                        for (int j = 0; j < split.length; j++) {
                            if(j%2==0) {
                                split[j] = split[j] + "}";
                            } else {
                                split[j] = "{" + split[j];
                            }
                        }
                        System.out.println(key + " " + Arrays.toString(split));
                    }



            }
           // System.out.println(key + " : " + value);
        }
    }


}
