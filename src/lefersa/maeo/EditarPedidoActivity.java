package lefersa.maeo;


import java.io.InputStream;
import java.util.ArrayList;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class EditarPedidoActivity extends Activity{

    private EditText editTextPedido;
    private Button buttonAceptar;
    // array list for spinner adapter
    ArrayList<HashMap<String, String>> arrayList;
    ProgressDialog pDialog;
    JSONObject jsonobject;
	JSONArray jsonarray;
	String id;
	String cliente;
	String pedido;
	String productos;
	String fecha;
	InputStream is=null;
	String line=null;
	String result=null;
	int code;
	
	private String URL_UPDATE_PEDIDO = "http://maescobar.com/editar_pedido.php";
    
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pedido);
 
        editTextPedido = (EditText) findViewById(R.id.editTextPedido);
        buttonAceptar = (Button) findViewById(R.id.buttonAceptar);
        
        Intent i = getIntent();
        id = i.getStringExtra("id");
		cliente = i.getStringExtra("cliente");
		pedido = i.getStringExtra("pedido");
		fecha = i.getStringExtra("fecha");
		
        editTextPedido.setText(pedido);
               
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
			
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    				
    			productos = editTextPedido.getText().toString().toUpperCase();
    			
    			new Update().execute(id,cliente,productos,fecha);
    		}
    	});        
        }
     
    private class Update extends AsyncTask<String, Void, Void>{
    	
    	boolean isEdit = false;
    	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditarPedidoActivity.this);
            pDialog.setMessage("Editando Pedido...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(String... arg) {
 
        	String id = arg[0];
        	String cliente = arg[1];
            String productos = arg[2];
            String fecha = arg[3];
 
            // Preparing post params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("cliente", cliente));
            params.add(new BasicNameValuePair("productos", productos));
            params.add(new BasicNameValuePair("fecha", fecha));
 
            ServiceHandler serviceClient = new ServiceHandler();
 
            String json = serviceClient.makeServiceCall(URL_UPDATE_PEDIDO,
                    ServiceHandler.POST, params);
 
            Log.d("Create Response: ", "> " + json);
 
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    boolean error = jsonObj.getBoolean("error");
                    // checking for error node in json
                    if (!error) {   
                        // new category created successfully
                        isEdit = true;
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
            if (isEdit) {
            	Toast.makeText(
                        getApplicationContext(),"Se ha editado correctamente el pedido" ,
                        Toast.LENGTH_LONG).show();
            	Intent intent = new Intent(getBaseContext(),MainActivity.class);
            	startActivity(intent);
            	finish();
            }
        }
 
    }
    	
}
              
