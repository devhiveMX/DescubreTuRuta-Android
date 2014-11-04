package mx.com.sct.checkyourrute.consult.between.points.activity;

import mx.com.sct.checkyourrute.R;
import mx.com.sct.checkyourrute.utils.Posiciones;
import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class ConsultBetweenPointsActivity extends Activity {
	private GoogleMap mMap;
	public static final LatLng SAGRADA_FAMILIA = new LatLng(41.40347, 2.17432);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consult_between_points);
		setUpMap();
		setMarker(SAGRADA_FAMILIA,"Sagrada Familia","Distrito: Barcelona"); // Agregamos el marcador verde
		drawPolilyne(Posiciones.POLILINEA);
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
		      }
		   }
		}
	
	private void setMarker(LatLng position, String titulo, String info) {
		  // Agregamos marcadores para indicar sitios de interéses.
		  Marker myMaker = mMap.addMarker(new MarkerOptions()
		       .position(position)
		       .title(titulo)  //Agrega un titulo al marcador
		       .snippet(info)   //Agrega información detalle relacionada con el marcador 
		       .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))); //Color del marcador
		}
	
	private void drawPolilyne(PolylineOptions options){
	    Polyline polyline = mMap.addPolyline(options);	
	}
	
	
}
