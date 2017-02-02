package com.example.jorav.examenjordi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jorav on 02/02/2017.
 */

public class Utils {


    //Clase que gestiona la conexion con la URL que se le pasara
    public static String conectaURL(String urlPagina){
        URL url;
        String respuesta = "";

        try {
            url = new URL(urlPagina);
            HttpsURLConnection conexion = (HttpsURLConnection)url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setDoInput(true);
            conexion.connect();
            InputStream is = new BufferedInputStream(conexion.getInputStream());
            respuesta = convString(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    //Clase que gestionara la transformacion del JSON a String
    public static String convString(InputStream is){
        BufferedReader reader=null;
        StringBuilder sb = new StringBuilder();

        try {

            reader = new BufferedReader(new InputStreamReader(is));

            String texto;

            while ((texto = reader.readLine()) != null){
                sb.append(texto).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return  sb.toString();
    }

    //Clase que mostrara una notificación personalizada segun el mensaje que le pases. Le tenemos que pasar el contexto para poder mostrarla
    public static void mostrarNotificacion(Context context, String mensaje){
        Intent notificationIntent = new Intent(context, context.getClass());
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentIntent(intent);
        mBuilder.setSmallIcon(R.drawable.ic_directions_run_black_24dp);
        mBuilder.setContentTitle("Notificación");
        mBuilder.setContentText("Has pulsado la opción "+mensaje);
        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

}
