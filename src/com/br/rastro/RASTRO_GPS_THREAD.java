package com.br.rastro;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class RASTRO_GPS_THREAD extends Activity implements Runnable {

	LocationManager manager;
	Location LocalAtual;
 
	//TextView locationView;


	public static String MY_LOCATION_EXTRA_RESULTS = MyLocationListener.class.getName().toUpperCase();
	
	static final String TAG = "RASTRO_GPS";

	private int LOCATION_REQUEST = 01;

	// formatação de data e hora
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	
    Handler handler = new Handler();
    private static final int TEMPO =10000;
    private boolean fimCaminho = false;

 
	@Override
   public void onCreate(Bundle savedInstanceState) {
	     if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			//Se não estiver habilitado, habilitar o GPS
			turnGPSOnOff(); 
	     }
		handler.postDelayed(this, TEMPO);   //  starta a contagem de tempo
		
		super.onCreate(savedInstanceState);
       // locationView = new TextView(this);
    //   setContentView(locationView);
		setContentView(R.layout.main);

 
        manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    
	}
	
	public void run() {

		
		//Obtêm a uma localização na cache
		LocalAtual = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		atualizaTela();
			
		
        if(!fimCaminho) // se a variavel for verdadeira...      	
        	handler.postDelayed(this, TEMPO);  // apos a primeira contagem, executa a proxima atualizacao de tela
	
	
	}	
	
	@Override
	protected void onResume() {
		super.onResume();

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		manager.removeUpdates(listener);
	}
	
	private void turnGPSOnOff(){
		  String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		  if(!provider.contains("gps")){
		    final Intent poke = new Intent();
		    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
		    poke.setData(Uri.parse("3"));    //custom:0 - wifi 
		    sendBroadcast(poke);             //custom:1 - brilho 
		                                     //custom:2 - sincronização automática 
		                                     //custom:3 - gps 
		                                     //custom:4 - bluetooth 
		    //Toast.makeText(this, "Your GPS is Enabled",Toast.LENGTH_SHORT).show();
		  }
	}
	
    private void Vibrar()
    {
        Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milliseconds = 300; 
        rr.vibrate(milliseconds); 
    }
	private void atualizaTela() {
		if(LocalAtual == null){
			//locationView.setText("Determinando sua localização...");
		} else {
			//locationView.setText(String.format("Sua localização:\n%.7f,%.7f", LocalAtual.getLatitude(),
			//		LocalAtual.getLongitude()));
			
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
				Toast.makeText(this, "Lat..: "+LocalAtual.getLatitude()+"Long..: "+LocalAtual.getLongitude()+"\nT[" + sdf.format(new Date(4)) + "]", Toast.LENGTH_LONG).show();

			}
		}
		
 
	//Manipula as chamadas dos eventos de localização
	private LocationListener listener = new LocationListener() {
 
		//@Override
		public void onLocationChanged(Location location) {
			LocalAtual = location;
			atualizaTela();
	
		}
 
		//@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
 
		}
 
		//@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
 
		}
 
		//@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
 
		}
	};
 

}