package com.tinymonster.heartbeat3.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by TinyMonster on 28/06/2018.
 */

public class HttpUtil {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static void sendOkHttpPost(String url, RequestBody requestbody, okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        String MyUrl=url;
        RequestBody MyRequestBody=requestbody;
        Request request=new Request.Builder().url(url).post(requestbody).build();
        client.newCall(request).enqueue(callback);
    }
    public static void sendOkHttpGet(String url,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder=new Request.Builder();
        Request request=builder.get().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static String httpRequestPost(String url,HashMap<String, String> map) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpParams params = httpclient.getParams();
        if(params==null){

            params = new BasicHttpParams();
        }

        HttpConnectionParams.setConnectionTimeout(params, 40*1000);
        HttpConnectionParams.setSoTimeout(params, 40*1000);

        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        for(Map.Entry<String, String> entry : map.entrySet()){

            params1.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params1, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        String result = null;
        try {
            //返回httpResponse对象
            HttpResponse response = httpclient.execute(httpPost);
            //获取httpEntity对象，该对象包含了服务器的响应内容
            HttpEntity entity = response.getEntity();
            //
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){

                result= EntityUtils.toString(entity);

            }else {
                System.out.println(url);
                System.out.println("response StatusCode: "+response.getStatusLine().getStatusCode());
            }

            // response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

  /*  public static String  httpUpload(String url,HashMap<String, File> fileHashMap,HashMap<String, String> map) {
        String resString = "";
         //定义HttpClient对象
        HttpClient client = new DefaultHttpClient();
         //获得HttpPost对象
        HttpPost post = new HttpPost(url);
        post.addHeader("charset", HTTP.UTF_8);
        MultipartEntity mpEntity = new MultipartEntity();
        File[] tempFile = new File[fileHashMap.size()];
        try {
            int i = 0;
            for(Map.Entry<String, File> entry : fileHashMap.entrySet()){
                File inFile = entry.getValue();
//                File compressedFile = BitmapUtils.saveResizeBitmap2File(CustomApplication.getInstance(), Uri.fromFile(inFile), BitmapUtils.MAX_IMG_SIZE);
                if (compressedFile != null) {
                    inFile = compressedFile;
                    tempFile[i] = compressedFile;
                    i++;
                }
                //设置文件上传类型
                ContentBody cbFile = new FileBody(inFile, "image/jpeg");
                mpEntity.addPart(entry.getKey(), cbFile);
            }
            for(Map.Entry<String, String> entry : map.entrySet()){
                mpEntity.addPart(entry.getKey(), new StringBody(entry.getValue()));
            }
            post.setEntity(mpEntity);

            //获得响应消息
            HttpResponse resp = client.execute(post);
            if(resp != null){
                resString = EntityUtils.toString(resp.getEntity());
            }
            if(resp.getStatusLine().getStatusCode()==200){
                System.out.println("文件上传成功");
            } else {
                resString = null;
                System.out.println("身份证上传"+resp.getStatusLine().getStatusCode()+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tempFile != null ) {
                for (int i =0; i< tempFile.length;i++) {
                    if (tempFile[i] != null && tempFile[i].isFile()) {
                        tempFile[i].delete();
                    }
                }
            }
        }
        return resString;
    }*/


    public static String httpRequestGet(String url) {


        HttpClient httpclient = new DefaultHttpClient();
        HttpParams params = httpclient.getParams();
        if(params==null){

            params = new BasicHttpParams();
        }

        HttpConnectionParams.setConnectionTimeout(params, 40*1000);
        HttpConnectionParams.setSoTimeout(params, 40*1000);


        HttpGet httpGet = new HttpGet(url);

        String result = null;
        try {
            //返回httpResponse对象
            HttpResponse response = httpclient.execute(httpGet);
            System.out.println(response.getStatusLine());
            //获取httpEntity对象，该对象包含了服务器的响应内容
            HttpEntity entity = response.getEntity();
            //
            if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){

                result=EntityUtils.toString(entity);

            }else {
                System.out.println("response StatusCode: "+response.getStatusLine().getStatusCode());
            }

            System.out.println(response.getStatusLine());//1.2
            if(result != null){
                System.out.println(result);
            }
            // response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
