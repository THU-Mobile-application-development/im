package com.ceocho.kakaotalk.Utill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;


public class MaptoJsonUtill {


    public static Map<String, Object> getMap(String jsonString) {
//        System.out.println("fuckkkkkkkkkkkkk?");
//        System.out.println(jsonString);
//
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            System.out.println("it is working?");
//            System.out.println(jsonString);
//            // convert JSON string to Map
//            Map<String, Object> map = mapper.readValue(jsonString, Map.class);
//
//            // it works
//            //Map<String, String> map = mapper.readValue(jsonString, new TypeReference<Map<String, String>>() {});
//
//            System.out.println(map);
//            return map;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            @SuppressWarnings("unchecked")
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = (String) keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static String getJson(Map map) {
        ObjectMapper mapper = new ObjectMapper();

        if (map.isEmpty() || map == null)
            return null;

        try {

            // convert map to JSON string
            String json = mapper.writeValueAsString(map);

            System.out.println(json);   // compact-print

            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

            System.out.println(json);   // pretty-print
            return json;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static List<String> jsonlisttolist(JSONArray arr, String keyname) {


        List<String> list = new ArrayList<String>();
        for (int i = 0; i < arr.length(); i++) {
            try {
                list.add(arr.getJSONObject(i).getString(keyname));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println(list);
        return list;


    }


}