package mx.com.sct.checkyourrute.json.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mx.com.sct.checkyourrute.data.GPlaces;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;

public class GplacesService {
	private GPlaces gPlace;

	  @SuppressLint("NewApi")
	   public void readAndParseJSON(String in) {
	      try {
	         JSONObject reader = new JSONObject(in);

	         JSONArray gplacesArray  = reader.getJSONArray("gplaces");
	         gPlace = new GPlaces();
	         
//	         country = sys.getString("country");
//
//	         JSONObject main  = reader.getJSONObject("main");
//	         temperature = main.getString("temp");
//
//	         pressure = main.getString("pressure");
//	         humidity = main.getString("humidity");
//
//	         parsingComplete = false;



	        } catch (Exception e) {
	           // TODO Auto-generated catch block
	           e.printStackTrace();
	        }

	   }
	   public void fetchJSON(){
	      Thread thread = new Thread(new Runnable(){
	         @Override
	         public void run() {
	         try {
	            URL url = new URL("https://training.devhive.mx/rutamxapi/search/%7Bcadena%20de%20busqueda%7D?4sq&gplaces");
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setReadTimeout(10000 /* milliseconds */);
	            conn.setConnectTimeout(15000 /* milliseconds */);
	            conn.setRequestMethod("GET");
	            conn.setDoInput(true);
	            // Starts the query
	            conn.connect();
	         InputStream stream = conn.getInputStream();

	      String data = convertStreamToString(stream);

	      readAndParseJSON(data);
	         stream.close();

	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	         }
	      });

	       thread.start(); 		
	   }
	   static String convertStreamToString(java.io.InputStream is) {
	      java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	      return s.hasNext() ? s.next() : "";
	   }
}
