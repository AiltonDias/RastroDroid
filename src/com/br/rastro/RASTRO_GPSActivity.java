package com.br.rastro;


import java.sql.Date;
import java.text.SimpleDateFormat;
import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.net.NetworkInfo;

public class RASTRO_GPSActivity extends Activity {

	
	LocationManager manager;
	
	static final String TAG = "RASTRO_GPS";

	// usado para requisitar no intent
	private int LOCATION_REQUEST = 01;

	// Usado para escrever TextView no layout principal
	private TextView tv;

	// used to format Date to BR format
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	
    Handler handler = new Handler();
    private static final int TEMPO =60000;
    private boolean fimCaminho = false;
    
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
		Log.d(TAG, "RASTRO_GPS::onCreate");
		Toast.makeText(this, "RASTRO iniciando...", Toast.LENGTH_SHORT).show();
		
		setContentView(R.layout.main);
		
		//turnGPSOnOff();
        //handler.postDelayed(this, TEMPO);   //  starta a contagem de tempo
		
		tv = (TextView) findViewById(R.id.text_id);
		tv.setText("RASTRO_GPS ");// + sdf.format(new Date()));

		// Cahama minha activity de localização e requisita os dados do GPS
		startActivityForResult(new Intent(this, MyLocationListener.class), LOCATION_REQUEST);
		

		super.onCreate(savedInstanceState);
    }
    
//	public void run() {
		// TODO Auto-generated method stub
      //  GeoPoint gpproximoPonto = IrParaProximoPonto();        
      //  local = new LocalOverlay(gpproximoPonto, Color.RED);
      //  mapa.getOverlays().add(local);
     //   controledomapa.animateTo(gpproximoPonto);  // atualiza tela com nova posicao
	    // if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			//Se não estiver habilitado, habilitar o GPS
		//	turnGPSOnOff(); 
	    // }
	//	startActivityForResult(new Intent(this, MyLocationListener.class), LOCATION_REQUEST);
    //    if(!fimCaminho)       	
     //   	handler.postDelayed(this, TEMPO);  // apos a primeira contagem, executa a proxima atualizacao de tela
//	}	
	  
	/*
	 * onResume is is always called after onStart, even if the app hasn't been
	 * paused
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		Log.d(TAG, "RASTRO_GPS::");
		Toast.makeText(this, "resumo da app", Toast.LENGTH_SHORT).show();

		// startActivityForResult(new Intent(this, MyLocationListener.class),
		// LOCATION_REQUEST);
		super.onResume();
		
		//  new Checker(this).pass(new Checker.Pass() {
		//	     @Override public void pass() {
		//	        
		//	     }
		//	  }).check(Resource.GPS, Resource.NETWORK, Resource.BLUETOOTH);
		
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "RASTRO_GPS::Pause");
		Toast.makeText(this, "app pausada", Toast.LENGTH_SHORT).show();
		super.onPause();
	}

	@Override
	public void finish() {
		/* Salva algumas informações antes de sair */
		Log.d(TAG, "RASTRO_GPS::finish");
		Log.d(TAG, "Saindo da Aplicação...!");
		Toast.makeText(this, "Saindo da Aplicação...", Toast.LENGTH_SHORT).show();

		super.finish();
		
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "RASTRO_GPS::onActivityResult");
		if (requestCode == LOCATION_REQUEST) {
			Log.d(TAG, "getting LOCATION_REQUEST=[" + LOCATION_REQUEST + "]");
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				if (bundle != null) {
					// get the date from bundle
					double values[] = bundle.getDoubleArray(MyLocationListener.MY_LOCATION_EXTRA_RESULTS);
					tv.setText("Location Changed: LON[" + values[0] + "] \nLAT[" + values[1] + "] \nALT[" + values[2] + "] \nACC[" + values[3] + "] \nT[" + values[4] + "]");
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
					Toast.makeText(this, "1 Location Changed: LON[" + values[0] + "] \nLAT[" + values[1] + "] \nALT[" + values[2] + "] \nACC[" + values[3] + "] \nT[" + sdf.format(new Date((long) values[4])) + "]", Toast.LENGTH_SHORT).show();
				} else {
					Log.d(TAG, "getting response=NO_EXTRAS");
				}
			}
		}
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
    }

