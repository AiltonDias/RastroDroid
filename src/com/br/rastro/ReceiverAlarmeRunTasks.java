package com.br.rastro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReceiverAlarmeRunTasks extends BroadcastReceiver {

	@Override
	public void onReceive(Context c, Intent i) {
		// TODO Auto-generated method stub
		Toast.makeText(c, "Rastro by Ailton executando o receiver1", Toast.LENGTH_LONG);
	}

}
