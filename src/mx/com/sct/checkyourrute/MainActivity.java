package mx.com.sct.checkyourrute;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import mx.com.sct.checkyourrute.comsult.from.to.activity.ConsultFromToActivity;
import mx.com.sct.checkyourrute.consult.between.points.activity.ConsultBetweenPointsActivity;
import mx.com.sct.checkyourrute.data.Location;
import mx.com.sct.checkyourrute.data.Section;
import mx.com.sct.checkyourrute.data.Trace;
import mx.com.sct.checkyourrute.json.service.TraceRouteServiceTask;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MainActivity extends ActionBarActivity implements OnMarkerClickListener{
	
	private GoogleMap mMap;
	public static final LatLng SAGRADA_FAMILIA = new LatLng(41.40347, 2.17432);
	static final LatLng MEXICO = new LatLng(19.378689, -99.136692);
	private List<LatLng> markersList; 
	public static Trace trace;
	private ProgressBar spinner;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (ProgressBar)findViewById(R.id.progressBarSync);
        spinner.setVisibility(View.GONE);
        setUpMap();
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A22B2")));        
//		setMarker(SAGRADA_FAMILIA,"Sagrada Familia","Distrito: Barcelona"); // Agregamos el marcador verde
//		 drawPolilyne(Posiciones.POLILINEA);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);    
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.trace_route) {
        	traceRoute();
            return true;
            
        }else if (id == R.id.change_view) {
        	changeView();
            return true;
            
        }
        	
        	
        return super.onOptionsItemSelected(item);
    }
    
    public void consultFromTo(View view){
    	Intent i = new Intent(this, ConsultFromToActivity.class);
//    	i.putExtra("Value1", "This value one for ActivityTwo ");
//    	i.putExtra("Value2", "This value two ActivityTwo"); 
    	startActivity(i); 
    }
    
    public void consultBetweenPoints(View view){
    	Intent i = new Intent(this, ConsultBetweenPointsActivity.class);
//    	i.putExtra("Value1", "This value one for ActivityTwo ");
//    	i.putExtra("Value2", "This value two ActivityTwo"); 
    	startActivity(i); 
    }
    
	private void setUpMap() {
		// Configuramos el objeto GoogleMaps con valores iniciales.
		   if (mMap == null) {
		      //Instanciamos el objeto mMap a partir del MapFragment definido bajo el Id "map"
		      mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		      // Chequeamos si se ha obtenido correctamente una referencia al objeto GoogleMap
		      if (mMap != null) {
		        // El objeto GoogleMap ha sido referenciado correctamente 
		        //ahora podemos manipular sus propiedades
		        
		        //Seteamos el tipo de mapa 
		        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		        	
		        //Activamos la capa o layer MyLocation
		        mMap.setMyLocationEnabled(true);
		        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {		      
		        	@Override
		            public void onMapLongClick(LatLng point) {

		                MarkerOptions marker = new MarkerOptions().position(
		                        new LatLng(point.latitude, point.longitude))
		                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

		                mMap.addMarker(marker);
		                saveMarker(point);
		            System.out.println(point.latitude+"---"+ point.longitude);  
		            }
		        });
		        
		        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MEXICO, 15));

		        // Zoom in, animating the camera.
		        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null); 
		        mMap.setOnMarkerClickListener(this);
		        
		      }
		   }
		}
	
	
	private void traceRoute(){
//		LatLng sourcePoint=null;
//		LatLng destinationPoint=null;
		
        
        //spinner.setVisibility(View.VISIBLE);

		if(markersList!=null && markersList.size()  > 1 ){
		
//			traceRouteService = new TraceRouteService(String.valueOf(markersList.get(0).latitude),
//					  String.valueOf(markersList.get(1).latitude),
//					  String.valueOf(markersList.get(0).longitude),
//					  String.valueOf(markersList.get(1).longitude));	
//			traceRouteService.fetchJSON();
			
			
		final ProgressDialog ringProgressDialog = ProgressDialog.show(MainActivity.this, "Espere un momento", "Trazando ruta ...", true);
			
			new Thread(new Runnable(){
        		public void run(){
            			TraceRouteServiceTask traceRouteServiceTask = new TraceRouteServiceTask(); 
            			String [] params= new String[(markersList.size() * 2)];
            			int lat =0;
            			int lng =1;
            			for(int i=0; i<markersList.size(); i++){
            				params[lat]= String.valueOf(markersList.get(i).latitude);
            				params[lng]= String.valueOf(markersList.get(i).longitude);
            				lat+=2;
            				lng+=2;
            			}
            			try {
							traceRouteServiceTask.execute(params).get();
						
							
							
							runOnUiThread(new Runnable(){
         					 @Override
                             public void run(){
                                 try {
                                	 if(trace!=null)
                                    	 drawPolilyne();
                                	 else{
                                		 spinner.setVisibility(View.GONE); 
                                		 Toast.makeText(getBaseContext(), "Failed!", Toast.LENGTH_LONG).show();
                                		 ringProgressDialog.dismiss();
                                	 }
                                 } catch (Exception e) {
                                     Log.e("runOnUIThread", e.toString());
                                 } 
                            }
                          });
            			} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ExecutionException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
            			ringProgressDialog.dismiss();
            			
        		}
        		
    		 }).start();
    		
			
		
		 }else{
			 Builder alert = new AlertDialog.Builder(this);
		        alert.setTitle("Alerta!");
		        alert.setMessage("Selecciones un origen y un destino");
		        alert.setPositiveButton("Aceptar", null);
		        alert.show();
		 }
	}
	
	private void saveMarker(LatLng point){
		if(markersList==null)
			markersList=new ArrayList<LatLng>();
		markersList.add(point);
	}
	
	
	
	private void drawPolilyne(){
		PolylineOptions options = new PolylineOptions().color(Color.BLUE);
		LatLng ll=null;
		boolean isFirt=true;
		LatLng fromLat=null;
		for(Section section:trace.getSectionList()){
			for(Location location:section.getLocationList()){
				if(isFirt)
					fromLat=new LatLng(location.getLat(), location.getLng()); ;
				ll =new LatLng(location.getLat(), location.getLng()); 
	        	options.add(ll);
	        	isFirt=true;
	        }
        	addMarker(section.getHint(), ll);

		}
		
	    mMap.addPolyline(options);	
//	    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fromLat, 25));	
	    }

	public void changeView(){
		mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	}
	
	public List<LatLng> getMarkersList() {
		return markersList;
	}


	public void setMarkersList(List<LatLng> markersList) {
		this.markersList = markersList;
	}


	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		return false;
	}

    
    
    private void addMarker(String message, LatLng latLng){
    	Bitmap.Config conf = Bitmap.Config.ARGB_8888;
    	Bitmap bmp = Bitmap.createBitmap(150, 150, conf);
    	Canvas canvas1 = new Canvas(bmp);

    	// paint defines the text color,
    	// stroke width, size
    	Paint color = new Paint();
    	color.setTextSize(10);
    	color.setColor(Color.WHITE);

    	//modify canvas
    	canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
    	    R.drawable.ic_message), 0,0, color);
//    	canvas1.drawText(message, 0, 160, color);

    	//add marker to Map
    	mMap.addMarker(new MarkerOptions().position(latLng)
    	    .icon(BitmapDescriptorFactory.fromBitmap(bmp))
    	    // Specifies the anchor to be at a particular point in the marker image.
    	    .anchor(0.5f, 1)
    	    .title(message));
    	
    	
    }
    
    
}
