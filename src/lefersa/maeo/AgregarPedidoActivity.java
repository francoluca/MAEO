package lefersa.maeo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AgregarPedidoActivity extends Activity implements OnItemSelectedListener {
	 
    private Spinner spinnerClientes;
    private EditText editTextPedido;
    private EditText editTextFecha;
    private Button buttonAgregar;
    // array list for spinner adapter
    ArrayList<HashMap<String, String>> arrayList;
    ProgressDialog pDialog;
    JSONObject jsonobject;
	JSONArray jsonarray;
	private String itemSeleccionado;
	private String codigo;
	
    // Url to get all clientes
    private String URL_CLIENTES = "http://maescobar.com/get_clientes.php";
    private String URL_NEW_PEDIDO = "http://maescobar.com/nuevo_pedido.php";
    
 
    @SuppressLint("DefaultLocale")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pedido);
 
        spinnerClientes = (Spinner) findViewById(R.id.spinner_clientes);
        editTextPedido = (EditText) findViewById(R.id.editTextPedido);
        editTextFecha = (EditText) findViewById(R.id.editTextFecha);
        buttonAgregar = (Button) findViewById(R.id.buttonRegistrar);
        
        // spinner item select listener
        spinnerClientes.setOnItemSelectedListener(this);
       
       
     // Add new pedido click event
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
               if (editTextPedido.getText().toString().trim().length() > 0 && editTextFecha.getText().toString().trim().length() > 0) {
                    
            	   	String cod= codigo; 
                    String cliente = itemSeleccionado;
                    String pedido = editTextPedido.getText().toString().toUpperCase();
                    String fecha = editTextFecha.getText().toString();
                    
 
                    // Call Async task to create new category
                    new AddNewCliente().execute(cod,cliente,pedido,fecha);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "No se ha ingresado un pedido o fecha", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        
     // Execute DownloadJSON AsyncTask
        new DownloadJSON().execute();
    }
    
    
 // DownloadJSON AsyncTask
 		private class DownloadJSON extends AsyncTask<Void, Void, Void> {
 	 
 			@Override
 			protected void onPreExecute() {
 				super.onPreExecute();
 				// Create a progressdialog
 				pDialog = new ProgressDialog(AgregarPedidoActivity.this);
 				// Set progressdialog title
 				pDialog.setTitle("Cargando Clientes");
 				// Set progressdialog message
 				pDialog.setMessage("Espere...");
 				pDialog.setIndeterminate(false);
 				// Show progressdialog
 				pDialog.show();
 			}
 	 
 			@Override
 			protected Void doInBackground(Void... params) {
 				// Create an array
 				arrayList = new ArrayList<HashMap<String, String>>();
 				// Retrieve JSON Objects from the given URL address
 				jsonarray = JSONfunctions
 						.getJSONfromURL(URL_CLIENTES);
 	 
 				try {
 	 
 					for (int i = 0; i < jsonarray.length(); i++) {
 						HashMap<String, String> map = new HashMap<String, String>();
 						jsonobject = jsonarray.getJSONObject(i);
 						// Retrive JSON Objects
 						map.put("codigo", jsonobject.getString("codigo"));
 						map.put("razon", jsonobject.getString("razon"));
 						// Set the JSON Objects into the array
 						arrayList.add(map);
 					}
 				} catch (JSONException e) {
 					Log.e("Error", e.getMessage());
 					e.printStackTrace();
 				}
 				return null;
 			}
 	 
 			@Override
 			protected void onPostExecute(Void args) {
 				populateSpinner();
 				pDialog.dismiss();
 			}
 		}

     
    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
         
     
        for (int i = 0; i < arrayList.size(); i++) {
            lables.add(arrayList.get(i).get("razon"));
        }
        
        Collections.sort(lables);
     
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
     
        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     
        // attaching data adapter to spinner
        spinnerClientes.setAdapter(spinnerAdapter);
    }
    
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
            long id) {
        Toast.makeText(
                getApplicationContext(),
                        parent.getItemAtPosition(position).toString() + " Seleccionado" ,
                Toast.LENGTH_LONG).show();
        
        itemSeleccionado = parent.getItemAtPosition(position).toString();
        for (int i = 0; i < jsonarray.length(); i++) {
				try {
					jsonobject = jsonarray.getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if(jsonobject.getString("razon")==itemSeleccionado){
						codigo = jsonobject.getString("codigo");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
       
 
    }
 
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {      
    }
    
    private class AddNewCliente extends AsyncTask<String, Void, Void> {
    	 
        boolean isNewPedidoCreated = false;
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AgregarPedidoActivity.this);
            pDialog.setMessage("Agregando Pedido..");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(String... arg) {
        	
        	String codigo = arg[0];
            String cliente = arg[1];
            String pedido = arg[2];
            String fecha = arg[3];
 
            // Preparing post params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("codigo", codigo));
            params.add(new BasicNameValuePair("cliente", cliente));
            params.add(new BasicNameValuePair("pedido", pedido));
            params.add(new BasicNameValuePair("fecha", fecha));
 
            ServiceHandler serviceClient = new ServiceHandler();
 
            String json = serviceClient.makeServiceCall(URL_NEW_PEDIDO,
                    ServiceHandler.POST, params);
 
            Log.d("Create Response: ", "> " + json);
 
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    boolean error = jsonObj.getBoolean("error");
                    // checking for error node in json
                    if (!error) {   
                        // new category created successfully
                        isNewPedidoCreated = true;
                    } else {
                        Log.e("Error al crear el pedido: ", "> " + jsonObj.getString("message"));
                    }
 
                } catch (JSONException e) {
                    e.printStackTrace();
                }
 
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (isNewPedidoCreated) {
            	Toast.makeText(
                        getApplicationContext(),"Se ha agregado correctamente el pedido" ,
                        Toast.LENGTH_LONG).show();
            	Intent intent = new Intent(getBaseContext(),MainActivity.class);
            	startActivity(intent);
            	finish();
            }
        }
 
    }
    
}