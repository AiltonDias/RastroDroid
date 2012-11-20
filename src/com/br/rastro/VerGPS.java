package com.br.rastro;

import java.sql.Date;
import java.text.SimpleDateFormat;

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
import android.os.Vibrator;
import android.provider.Settings;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class VerGPS extends Activity {
	 
	LocationManager manager;
	Location LocalAtual;
 
	TextView locationView;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	

	// requisição de dados de localização
	public static String MY_LOCATION_EXTRA_RESULTS = MyLocationListener.class.getName().toUpperCase();

 
	@Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationView = new TextView(this);
       setContentView(locationView);
		setContentView(R.layout.main);

 
        manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			//Se não estiver habilitado, habilitar o GPS, nas versões 2.3.3 nem sempre é possivel ativar.
			//turnGPSOnOff();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Location Manager");
			builder.setMessage("O GPS está desativado.\n"
					+ "Ativar ?");
			builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
 
				//@Override
				public void onClick(DialogInterface arg0, int arg1) {
					//Permite o usuário mudar a configuração do GPS
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(intent);
				}
			});
			builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
 
				//@Override
				public void onClick(DialogInterface arg0, int arg1) {
					//Não realiza a mudança de configuração
					finish();
				}
			});
			builder.create().show();
		}
 
		//Obtêm a uma localização cacheada, se existir
		LocalAtual = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		atualizaTela();
		Vibrar();
		//Registra as atualizações dependente do recebimento e aferimento do desvio.
		int minTime = 10000;
		float minDistance = 0;
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);
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
    {    //  vibrar o telefone ou tablet   
        Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milliseconds = 30; 
        rr.vibrate(milliseconds); 
    }
	private void atualizaTela() {
		if(LocalAtual == null){
			//locationView.setText("Determinando sua localização...");
		} else {
			locationView.setText(String.format("Sua localização:\n%.7f,%.7f", LocalAtual.getLatitude(),
					LocalAtual.getLongitude()));
			
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
				Toast.makeText(this, "Lat..: "+LocalAtual.getLatitude()+"Long..: "+LocalAtual.getLongitude()+"\nT[" + sdf.format(new Date(4)) + "]", Toast.LENGTH_LONG).show();

			}
		}
		
 
	//Manipula as chamadas dos eventos de localização pois é padrão no tratamento dos metodos.  Ailton Dias
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
 
	@Override
	protected void onPause() {
		super.onPause();
		manager.removeUpdates(listener);
	}
}
