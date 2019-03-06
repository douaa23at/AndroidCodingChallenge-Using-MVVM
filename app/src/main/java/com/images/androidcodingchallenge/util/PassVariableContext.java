package com.images.androidcodingchallenge.util;

import com.images.androidcodingchallenge.model.Hit;

import java.util.HashMap;


public class PassVariableContext {
    private static final String KEY_HIT = "Hit";
    private static PassVariableContext instance = new PassVariableContext();
    private HashMap<String, Object> map = new HashMap<>();
    public Hit getHit(){
        return (Hit)map.get(KEY_HIT);
    }
    public void setHit(Hit hit){
        map.put(KEY_HIT,hit);
    }
    public static PassVariableContext getInstance(){
        return instance;
    }
}
