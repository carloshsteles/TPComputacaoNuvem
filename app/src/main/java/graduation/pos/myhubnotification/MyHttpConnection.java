package graduation.pos.myhubnotification;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by carlo on 10/07/2017.
 */

public class MyHttpConnection extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params)  {

        HttpURLConnection urlConnection;
        JSONObject json = new JSONObject();
        JSONObject info = new JSONObject();
        String keyFirebaseServer = "key=AAAAn5ZvqF0:APA91bEuDgVaqp78r1HiBQ5Y2SNULpO2VIvHSD2CI9jiI6k7eHi3JGcayH62UzJe_sGiIJBHFxskVIzipFXRvweVOyOcNSX3UOLlb7Ladn75VmKqDAu-8G64nFXQxn7tP_0NhaBF10cd";
        try {
            info.put("title", "Compra no Sistema!");   // Notification title
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            info.put("body", params[0]+" acabou de adquirir um "+ params[1]+", tamanho "+params[2]); // Notification body
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            info.put("sound", "mySound"); // Notification sound
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("notification", info);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            json.put("condition", "\'Papaleguas\' in topics || \'Coiote\' in topics");
        } catch (JSONException e) {
            e.printStackTrace();
        }


       /* if (params[0].equalsIgnoreCase("Papaleguas")) {
            try {
                json.put("to", "/topics/Coiote");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            try {
                json.put("to", "/topics/Papeleguas");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/

        Log.e("jsonn==> ",json.toString());
        String data = json.toString();
        String result = null;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL("https://fcm.googleapis.com/fcm/send").openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", keyFirebaseServer);
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(data);
            writer.close();
            outputStream.close();

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
       
    }

}



    