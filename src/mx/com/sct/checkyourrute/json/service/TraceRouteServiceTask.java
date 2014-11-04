package mx.com.sct.checkyourrute.json.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mx.com.sct.checkyourrute.MainActivity;
import mx.com.sct.checkyourrute.data.Location;
import mx.com.sct.checkyourrute.data.Section;
import mx.com.sct.checkyourrute.data.Trace;
import mx.com.sct.checkyourrute.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class TraceRouteServiceTask extends AsyncTask<String, Void, String> {

	private StringBuilder sb = new StringBuilder();  
	

	@Override
	protected String doInBackground(String... params) {
		 try {
	        	String resourceParams = params[0] + ","
	        				  + params[1] + "";
//	        				  + "{" + latSource + "},"
//	        				  + "{" + ingDestination + "}";
	        
	        	
	        	
	        	
	        	JSONObject locationJson;
	        	
	        	
	        	JSONArray jsonArr = new JSONArray();
	        	for(int i=2; i<params.length;i+=2){
	        		locationJson = new JSONObject();
	        		locationJson.put("lat", params[i]);
		        	locationJson.put("lng", params[i+1]);
		        	jsonArr.put(locationJson);
	        	}
	        	
	        
	        	
	            URL url = new URL("https://training.devhive.mx/rutamxapi/route/" + resourceParams);
	            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	            urlConnection.setDoOutput(true);   
	            urlConnection.setRequestMethod("POST");  
	            urlConnection.setUseCaches(false);  
	            urlConnection.setConnectTimeout(90000);  
	            urlConnection.setReadTimeout(90000);  
	            urlConnection.setRequestProperty("Content-Type","application/json");   

	            urlConnection.setRequestProperty("Host", "training.devhive.mx");
	            urlConnection.connect();
	            OutputStreamWriter out = new   OutputStreamWriter(urlConnection.getOutputStream());
	            out.write(jsonArr.toString());
	            out.close();  

	            int HttpResult =urlConnection.getResponseCode();  
	            if(HttpResult ==HttpURLConnection.HTTP_OK){  
	                BufferedReader br = new BufferedReader(new InputStreamReader(  
	                    urlConnection.getInputStream(),"utf-8"));  
	                String line = null;  
	                while ((line = br.readLine()) != null) {  
	                    sb.append(line + "\n");  
	                }  
	                br.close();  
	                Log.i("JSOn", sb.toString());
	                readAndParseJSON(sb.toString());
	            }else{  
	              
	            	Log.e("ERROR",urlConnection.getResponseMessage());  
	            	return "failed";
//	         InputStream stream = urlConnection.getInputStream();
//
//	      String data = convertStreamToString(stream);
//
//	      readAndParseJSON(data);
//	         stream.close();

	            }
	          } catch (Exception e) {
	            e.printStackTrace();
	         }
	         
		return "success";
	
}
    @Override
    protected void onPostExecute(String result) {
    	//Log.i(TAG, "onPostExecute");
    }

    @Override
    protected void onPreExecute() {
//    	MainActivity.customDialog.dismiss();
//        //Log.i(TAG, "onPreExecute");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        //Log.i(TAG, "onProgressUpdate");
    }

    protected void onInterrupted(InterruptedException e) {
        //Log.i(TAG, "onProgressUpdate");
        
    }

    public void readAndParseJSON(String in) {
	      try {
	         JSONObject reader = new JSONObject(in);

	         JSONArray resultArray  = reader.getJSONArray("results");
	         JSONObject resultObject = resultArray.getJSONObject(0);
	         
	         Trace Mytrace = new Trace();
	         Mytrace.setTotalGas(resultObject.getDouble("gasTotal"));
	         Mytrace.setTotal(resultObject.getDouble("total"));
	         Mytrace.setTitle(resultObject.getString("titulo"));
	         JSONArray grafoArray  = resultObject.getJSONArray("grafo");
	         JSONArray sectionArray;
	         JSONArray locationArray;
	         Section section;
	         List<Section> sectionList = new ArrayList<Section>();
	         List<Location> locationList;
	         Location location;
	         JSONArray locationObject;
	         for(int i=0; i < grafoArray.length(); i++){
	        	 sectionArray =grafoArray.getJSONArray(i);
	        	 section = new Section();
	        	 section.setSection(sectionArray.getString(Constants.GRAFO_INDEX_SECTION));
	        	 section.setSource(sectionArray.getString(Constants.GRAFO_INDEX_SOURCE));
	        	 section.setHint(sectionArray.getString(Constants.GRAFO_INDEX_HINT));
	        	 section.setCountry(sectionArray.getString(Constants.GRAFO_INDEX_COUNTRY));
	        	 section.setCountryCode(sectionArray.getString(Constants.GRAFO_INDEX_COUNTRY));
	        	 section.setDestination(sectionArray.getString(Constants.GRAFO_INDEX_DESTINATION));
	        	 locationArray = sectionArray.getJSONArray(Constants.GRAFO_INDEX_LOCATION);
	        	 locationList = new ArrayList<Location>();
	        	 for(int x=0; x<locationArray.length(); x++){
		        	 locationObject = locationArray.getJSONArray(x);
		        	 location = new Location();
		        	 location.setLat(locationObject.getDouble(1));
		        	 location.setLng(locationObject.getDouble(0));
		        	 locationList.add(location);
		         }
	        	 section.setLocationList(locationList);
	        	 sectionList.add(section);
	         }
	         
	         Mytrace.setSectionList(sectionList);
	         MainActivity.trace=Mytrace;
	         
	         
//	         country = sys.getString("country");
//
//	         JSONObject main  = reader.getJSONObject("main");
//	         temperature = main.getString("temp");
//
//	         pressure = main.getString("pressure");
//	         humidity = main.getString("humidity");
//
//	         parsingComplete = false;



	        } catch (JSONException e) {
	           e.printStackTrace();
	        } catch (Exception e) {
		       e.printStackTrace();
		    }

	   }
}
