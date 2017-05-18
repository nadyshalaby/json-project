package com.example.taekunger.jsonproject;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class JSONHttpRequest {

    final private static String TAG = JSONHttpRequest.class.getSimpleName();
    final public static String POST = "POST";
    final public static String GET = "GET";

    public static String makeRequest(String url, String method, String params) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            if (params != null && params.length() > 0) {
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(params);


                writer.flush();
                writer.close();
                os.close();

            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder response = new StringBuilder();
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            return response.toString();

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        return null;
    }
}