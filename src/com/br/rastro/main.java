package com.br.rastro;



import com.br.rastro.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class main extends ListActivity  {
	  private static final String TAG = "Services";
	  Button buttonIniciar, buttonParar;

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main1);

	    buttonIniciar = (Button) findViewById(R.id.buttonStart);
	    buttonParar = (Button) findViewById(R.id.buttonStop);

	    buttonIniciar.setOnClickListener((OnClickListener) this);
	    buttonParar.setOnClickListener((OnClickListener) this);
	  }

	  public void onClick(View src) {
	    switch (src.getId()) {
	    case R.id.buttonStart:
	      Log.d(TAG, "no Click: inicia o servi�o");
	      startActivity(new Intent(this, servi�oagendado.class));//inicia a actvity do servico
	      break;
	    case R.id.buttonStop:
	      Log.d(TAG, "no click: para o servi�o");
	      stopService(new Intent(this, main.class)); //para o servico
	      break;
	    }
	  }
}
