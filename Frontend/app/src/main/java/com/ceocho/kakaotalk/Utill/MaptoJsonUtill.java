package com.ceocho.kakaotalk.Utill;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

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


public class MaptoJsonUtill<staic, statc> {


    public static Map<String, Object> getMap(String jsonString) {
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
        System.out.println(arr);

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

    public static List<String> getreplycontent(String str,int times) {
        List<String> result = new ArrayList<>();
        for(int i=0;i<times;i++) {
            String content = "";
            content = substringBetween(str, "\"content\":\"", "\",");
            result.add(content);
            str = str.substring(str.indexOf(content)+content.length());
            System.out.println(content);
        }
        return result;

    }

    public static List<String> getreplytime(String str,int times) {
        List<String> result = new ArrayList<>();
        for(int i=0;i<times;i++) {
            String content = "";
            content = substringBetween(str, "\"replytime\":\"", "\",");
            result.add(content);
            str = str.substring(str.indexOf(content)+content.length());
            System.out.println(content);
        }
        return result;

    }

    public static List<String> getreplyusername(String str,int times) {
        List<String> result = new ArrayList<>();
        for(int i=0;i<times;i++) {
            String content = "";
            content = substringBetween(str, "\"username\":\"", "\"");
            result.add(content);
            str = str.substring(str.indexOf(content)+content.length());
            System.out.println(content);
        }
        return result;

    }



    public static String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }






}