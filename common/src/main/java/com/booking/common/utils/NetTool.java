package com.booking.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author JeffreyShi
 * @Mail : shijunfan@gmail.com
 */

public class NetTool {

    public static byte[] GET(String url) throws Exception {
        URL requestUrl = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) requestUrl
                .openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(45 * 1000);
        if (urlConnection.getResponseCode() == 200) {
            byte[] bytes = NetTool.read(urlConnection.getInputStream());
            urlConnection.disconnect();
            return bytes;
        }
        return null;
    }

    public static byte[] POST(String url, String params) {
        //写
        OutputStream out = null;
        //		InputStream in = null;   //读
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(45 * 1000);
            //一定要设置 Content-Type 要不然服务端接收不到参数
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.setDoOutput(true);
            out = urlConnection.getOutputStream();
            out.write(params.getBytes());
            out.flush();
            if (urlConnection.getResponseCode() == 200) {
                byte[] bytes = NetTool.read(urlConnection.getInputStream());
                urlConnection.disconnect();
                return bytes;
            }
        } catch (Exception e) {

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] read(InputStream inputStream) throws IOException {
        ByteArrayOutputStream arrayBuffer = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = inputStream.read(b)) != -1) {
            arrayBuffer.write(b, 0, len);
        }
        inputStream.close();
        arrayBuffer.close();
        return arrayBuffer.toByteArray();
    }
}
