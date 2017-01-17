package com.jluo80.amazinggifter.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class URLReader {

    public static String sendGet(String address) throws Exception{

        URL url = new URL(address);
        URLConnection connection = url.openConnection();

        /** Set request header content */
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8");

        String inputLine;
        String response;
        long totalBytes = 0  ;

        StringBuilder builder = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((inputLine = in.readLine()) != null) {
            builder.append(inputLine);
            totalBytes += inputLine.getBytes("UTF-8").length ;
            //System.out.println("Total bytes read ::  " + totalBytes);
        }

        response = builder.toString();

        return response ;
    }

}