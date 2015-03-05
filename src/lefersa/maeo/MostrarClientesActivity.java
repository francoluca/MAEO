package lefersa.maeo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class MostrarClientesActivity extends Activity {
	// Declare Variables
			JSONObject jsonobject;
			JSONArray jsonarray;
			ListView listview;
			ListViewAdapterClientes adapter;
			ProgressDialog mProgressDialog;
			ArrayList<HashMap<String, String>> arraylist;
			static String CODIGO = "codigo";
			static String RUT = "rut";
			static String RAZON = "razon";
			static String PAGO = "pago";
			static String DIRECCION = "direccion";
			static String CIUDAD = "ciudad";
			static String TELEFONO = "telefono";
			static String CELULAR = "celular";
			private String razon1;
			private String razon2;
					
		 
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				
				// Get the view from listview_main.xml
				setContentView(R.layout.listviewcliente_main);
				// Execute DownloadJSON AsyncTask
				new DownloadJSON().execute();
			}
		 
			// DownloadJSON AsyncTask
			private class DownloadJSON extends AsyncTask<Void, Void, Void> {
		 
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					// Create a progressdialog
					mProgressDialog = new ProgressDialog(MostrarClientesActivity.this);
					// Set progressdialog title
					mProgressDialog.setTitle("Cargando Clientes");
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
							.getJSONfromURL("http://maescobar.com/mostrarClientes.php");
		 
					try {
		 
						for (int i = 0; i < jsonarray.length(); i++) {
							HashMap<String, String> map = new HashMap<String, String>();
							jsonobject = jsonarray.getJSONObject(i);
							
							// Retrive JSON Objects
							map.put("codigo", jsonobject.getString("codigo"));
							map.put("rut", jsonobject.getString("rut"));
							map.put("razon", jsonobject.getString("razon"));
							map.put("pago", jsonobject.getString("pago"));
							map.put("direccion", jsonobject.getString("direccion"));
							map.put("ciudad", jsonobject.getString("ciudad"));
							map.put("telefono", jsonobject.getString("telefono"));
							map.put("celular", jsonobject.getString("celular"));
							// Set the JSON Objects into the array
							arraylist.add(map);
							
							Collections.sort(arraylist,new Comparator<HashMap<String,String>>(){
							   
								public int compare(HashMap<String,String> mapping1,HashMap<String,String> mapping2){
		
							    		razon1 = mapping1.get("razon");
										razon2 = mapping2.get("razon");
									
							        return razon1.compareTo(razon2);
							}});
							
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
					listview = (ListView) findViewById(R.id.listviewcliente);
					
					// Pass the results into ListViewAdapter.java			
					adapter = new ListViewAdapterClientes(MostrarClientesActivity.this, arraylist);
					
					// Set the adapter to the ListView
					listview.setAdapter(adapter);
					// Close the progressdialog
					mProgressDialog.dismiss();
				}
			}
}
