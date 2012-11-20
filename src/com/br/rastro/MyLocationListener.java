package com.br.rastro;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MyLocationListener extends Activity implements LocationListener {
	
	// variavel onde vai ser salvo os logs
	private String TAG = "GPS_LISTENER";

	public static String MY_LOCATION_EXTRA_RESULTS = MyLocationListener.class.getName().toUpperCase();

	// To get the location service
	private LocationManager lm;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Log.d(TAG, "MyLocationListener::onCreate");
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		setContentView(R.layout.main);
		super.onCreate(savedInstanceState);
	}
	
	
	
	
	
	@Override
	protected void onResume() {
		//Log.d(TAG, "MyLocationListener::onResume");
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		super.onResume();
	}	
	
	@Override
	protected void onPause() {
		//Log.d(TAG, "MyLocationListener::onPause");
		//Toast.makeText(this, "app 2 pausada", Toast.LENGTH_LONG).show();
		lm.removeUpdates(this);
		super.onPause();
	}
	


	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		//Log.d(TAG, "MyLocationListener::onLocationChanged");
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		double altitude = location.getAltitude();
		float accurancy = location.getAccuracy();
		long time = location.getTime();

		// FIXME use String.format
		String s = "Localização: longitude[" + longitude + "] \nlatitude[" + latitude + "] \naltitude[" + altitude + "] \naccurancy[" + accurancy + "] \ntime[" + time + "]";

		Intent intent = this.getIntent();
		intent.putExtra(MY_LOCATION_EXTRA_RESULTS, new double[] { longitude, latitude, altitude, accurancy, time });
		this.setResult(RESULT_OK, intent);
		Log.d(TAG, s);
		super.finish();		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Log.d(TAG, "MyLocationListener::onProviderDisabled");
		Log.d(TAG, "Provider disabled");		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Log.d(TAG, "MyLocationListener::onProviderEnabled");
		Log.d(TAG, "Provider enabled");		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		Log.d(TAG, "MyLocationListener::Status");
		switch (status) {
		case LocationProvider.OUT_OF_SERVICE:
			Log.v(TAG, "Status Changed: Fora de Serviço");
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			Log.v(TAG, "Status Changed: temporariamente indisponivel");
			break;
		case LocationProvider.AVAILABLE:
			Log.v(TAG, "Status Changed: disponivel");
			break;
		default:
			Log.v(TAG, "Status Changed: desconhecido... foi lançada uma exceção");		
	}

}
}
