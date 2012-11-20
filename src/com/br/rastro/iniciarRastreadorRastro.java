package com.br.rastro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class iniciarRastreadorRastro extends BroadcastReceiver {

	@Override
	public void onReceive(Context ctx, Intent i) {
		// TODO Auto-generated method stub
		
		 // Exibe a Activity Principal após o boot
        // do Android
        Intent intent = new Intent(ctx, VerGPS.class);  //era RASTRO_GPSActivity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);   
		
	}

}
