package com.mastercooker.async.download;

import android.util.Log;

import com.mastercooker.model.Cook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DownLoad {
    private String Shadow(String httpUrl) {
        StringBuilder resultBuffer = new StringBuilder();
        BufferedReader reader = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("apikey", "37d70aedc21c47350f8021ac3a03dc71");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                resultBuffer.append(strRead);
                resultBuffer.append("\r\n");
            }
            inputStream.close();
            result = resultBuffer.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private ArrayList<Cook> MyJSONOfArray(String s) {
        ArrayList<Cook> cooks = new ArrayList<>();
        if(null==s)
            return null;
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                ArrayList<Cook> cookArrayList = MyJSONObj(object);
                if (null != cookArrayList)
                    cooks.addAll(cookArrayList);
            }
            return cooks;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private ArrayList<Cook> MyJSONArray(String s) {
        ArrayList<Cook> cooks = new ArrayList<>();
        if(null==s)
            return null;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("tngou");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                ArrayList<Cook> cookArrayList = MyJSONObj(object);
                if (null != cookArrayList)
                    cooks.addAll(cookArrayList);
            }
            return cooks;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private ArrayList<Cook> MyJSONObj(String s) {
        if(null==s)return null;
        try {
            JSONObject jsonObject = new JSONObject(s);
            return MyJSONObj(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Cook> MyJSONObj(JSONObject jsonObject) {
        if(null==jsonObject)
            return null;
        ArrayList<Cook> cooks = new ArrayList<>();
        try {
            Cook cook = new Cook();
            cook.setKeywords(jsonObject.getString("keywords"));
            cook.setName(jsonObject.getString("name"));
            if (jsonObject.has("message"))
                cook.setMessage(jsonObject.getString("message"));
            else
                cook.setMessage(showDownload(jsonObject.getInt("id")).get(0).getMessage());
            cook.setDescription(jsonObject.getString("description"));
            cook.setImages(jsonObject.getString("images"));
            cook.setImg(jsonObject.getString("img"));
            cook.setFood(jsonObject.getString("food"));
            cook.setCount(jsonObject.getInt("count"));
            cook.setId(jsonObject.getInt("id"));
            cook.setF_count(jsonObject.getInt("fcount"));
            cook.setR_count(jsonObject.getInt("rcount"));

            cooks.add(cook);
            return cooks;
        } catch (JSONException e) {
            Log.i("Main", "MyJSONObj------- JSONException"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Cook> listDownload(int i) {
        String httpUrl = "http://apis.baidu.com/tngou/cook/list";
        String httpArg = "id=";
        httpUrl = httpUrl + "?" + httpArg + i;
        return MyJSONArray(Shadow(httpUrl));
    }

    public ArrayList<Cook> showDownload(int i) {
        String httpUrl = "http://apis.baidu.com/tngou/cook/show";
        String httpArg = "id=";
        httpUrl = httpUrl + "?" + httpArg + i;
        return MyJSONObj(Shadow(httpUrl));
    }


    public ArrayList<Cook> nameDownload(String s) {
        //通过名称查询
        StringBuilder httpUrl = new StringBuilder("http://apis.baidu.com/tngou/cook/name?");
        String httpArg = "name=";
        httpUrl.append(httpArg);
        try {
              httpUrl.append(URLEncoder.encode(s, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        return MyJSONOfArray(Shadow(httpUrl.toString()));
    }

    public String classifyDownload() {
        //返回所有的分类
        String httpUrl = "http://apis.baidu.com/tngou/cook/classify";
        String httpArg = "id=0";
        httpUrl = httpUrl + "?" + httpArg;
        return Shadow(httpUrl);
    }
}
