package com.br.rastro;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
// essa classe � um servi�o onde � feita uma requesi��o http, pro meu servidor
public class servi�oagendado extends Service {

String LocalAtual; 
String LocalAtualLat; 
String LocalAtualLng;
@Override
public void onCreate() {
 // TODO Auto-generated method stub
 Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();
}
//<service android:name=".MyAlarmService" />  // essa tag tem que ser criada l� no manifest
@Override
public IBinder onBind(Intent intent) {
 // TODO Auto-generated method stub
 Toast.makeText(this, "Servi�o vinculado", Toast.LENGTH_LONG).show();
 return null;
}

@Override
public void onDestroy() {
 // TODO Auto-generated method stub
 super.onDestroy();
 Toast.makeText(this, "Servi�o finalizado", Toast.LENGTH_LONG).show();
}

@Override
public void onStart(Intent intent, int startId) {
 // TODO Auto-generated method stub
	double longitude;
	double latitude;
 super.onStart(intent, startId);
 Toast.makeText(this, "Servi�o Startado", Toast.LENGTH_LONG).show();
 
      Bundle extras = intent.getBundleExtra(LocalAtual);
      if (extras != null) {  
 	        double values[] = extras.getDoubleArray(LocalAtual);  
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
			Toast.makeText(this, "Localiza��o: LON[" + values[0] + "] \nLAT[" + values[1] + "] \nALT[" + values[2] + "] \nACC[" + values[3] + "] \nT[" + sdf.format(new Date((long) values[4])) + "]", Toast.LENGTH_SHORT).show();
          longitude = values[0];
          latitude = values[1];
      } 
             
  
 
     // teste sobre o resultado da requisi��o
 
	String urlPost="http://192.168.0.103:8081/systema/gravardadosA.jsp";
	String urlGet="http://192.168.0.103:8081/systema/gravardadosA.jsp?in_imei="+LocalAtualLat+"&in_cliente="+LocalAtualLng;

	ArrayList<NameValuePair> iparametrosPost = new ArrayList<NameValuePair>();
	iparametrosPost.add(new BasicNameValuePair(LocalAtualLat, "longitude"));
	iparametrosPost.add(new BasicNameValuePair(LocalAtualLng, "latitude"));
	String respostaRetornada = null;
	try {
			respostaRetornada = ConexaoHttpClient.executaHttpGet(urlGet);
			String resposta = respostaRetornada.toString();

			resposta = resposta.replaceAll("\\s+", "");
			if (resposta.equals("1"))
				 //mensagem para confirmar retorno do servidor
			Toast.makeText(servi�oagendado.this, "Salvo com Sucesso", Toast.LENGTH_LONG).show();
			else
				//caso contr�rio...
			Toast.makeText(servi�oagendado.this, "Erro", Toast.LENGTH_LONG).show();
		}
		catch(Exception erro)
		{
			//Log.i("erro", "erro = "+erro);
			Toast.makeText(servi�oagendado.this, "Erro."+erro, Toast.LENGTH_LONG).show();
			//mensagemExibir("Erro", "Erro ao gravar: "+erro);
		}

}


@Override
public boolean onUnbind(Intent intent) {
 // TODO Auto-generated method stub
 Toast.makeText(this, "Servi�o desvinculado", Toast.LENGTH_LONG).show();
 return super.onUnbind(intent);
}


	


}