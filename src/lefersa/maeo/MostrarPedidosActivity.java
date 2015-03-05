package lefersa.maeo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

@SuppressLint("SimpleDateFormat")
public class MostrarPedidosActivity extends Activity {
	// Declare Variables
			JSONObject jsonobject;
			JSONArray jsonarray;
			ListView listview;
			ListViewAdapter adapter;
			ProgressDialog mProgressDialog;
			ArrayList<HashMap<String, String>> arraylist;
			static String ID = "id";
			static String CODIGO = "codigo";
			static String CLIENTE = "cliente";
			static String PRODUCTOS = "productos";
			static String FECHA = "fecha";
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yy");
	    	Date fecha1 = null;
	    	Date fecha2 = null;
			
		 
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				
				// Get the view from listview_main.xml
				setContentView(R.layout.listview_main);
				// Execute DownloadJSON AsyncTask
				new DownloadJSON().execute();
			}
		 
			// DownloadJSON AsyncTask
			private class DownloadJSON extends AsyncTask<Void, Void, Void> {
		 
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					// Create a progressdialog
					mProgressDialog = new ProgressDialog(MostrarPedidosActivity.this);
					// Set progressdialog title
					mProgressDialog.setTitle("Cargando Pedidos");
					// Set progressdialog message
					mProgressDialog.setMessage("Espere...");
					mProgressDialog.setIndeterminate(false);
					// Show progressdialog
					mProgressDialog.show();
				}
		 
				@Override
				protected Void doInBackground(Void... params) {
					// Create an array
					arraylist = new ArrayList<HashMap<String, String>>();
					// Retrieve JSON Objects from the given URL address
					jsonarray = JSONfunctions
							.getJSONfromURL("http://maescobar.com/get_pedidos.php");
		 
					try {
		 
						for (int i = 0; i < jsonarray.length(); i++) {
							HashMap<String, String> map = new HashMap<String, String>();
							jsonobject = jsonarray.getJSONObject(i);
							
							// Retrive JSON Objects
							map.put("codigo", jsonobject.getString("codigo"));
							map.put("id", jsonobject.getString("id"));
							map.put("cliente", jsonobject.getString("cliente"));
							map.put("productos", jsonobject.getString("productos"));
							map.put("fecha", jsonobject.getString("fecha"));
							// Set the JSON Objects into the array
							arraylist.add(map);
							
							Collections.sort(arraylist,new Comparator<HashMap<String,String>>(){
							   
								public int compare(HashMap<String,String> mapping1,HashMap<String,String> mapping2){
		
							    	try {
										fecha1 = formatoDelTexto.parse(mapping1.get("fecha"));
										fecha2 = formatoDelTexto.parse(mapping2.get("fecha"));
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							        return fecha1.compareTo(fecha2);
							}});
							
							Collections.reverse(arraylist);
						}
					} catch (JSONException e) {
						Log.e("Error", e.getMessage());
						e.printStackTrace();
					}
					return null;
				}
		 
				@Override
				protected void onPostExecute(Void args) {
					// Locate the listview in listview_main.xml
					listview = (ListView) findViewById(R.id.listview);
					
					// Pass the results into ListViewAdapter.java			
					adapter = new ListViewAdapter(MostrarPedidosActivity.this, arraylist);
					
					// Set the adapter to the ListView
					listview.setAdapter(adapter);
					// Close the progressdialog
					mProgressDialog.dismiss();
				}
			}
			
			public void fin(){
				finish();
			}
}
