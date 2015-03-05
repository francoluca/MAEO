package lefersa.maeo;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;




import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class SingleItemViewCliente extends Activity {
	// Declare Variables
	String scodigo;
	String srut;
	String srazon;
	String spago;
	String sdireccion;
	String sciudad;
	String stelefono;
	String scelular;
	TextView tcodigo;
	TextView trut;
	TextView trazon;
	TextView tpago;
	TextView tdireccion;
	TextView tciudad;
	TextView ttelefono;
	TextView tcelular;
	Button editar;
	Button eliminar;
	ProgressDialog pDialog;
	InputStream is=null;
	String result=null;
	String line=null;
	int code;
	
	private String URL_DELETE_CLIENTE = "http://maescobar.com/eliminar_cliente.php";

 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singleitemviewcliente);
 
		Intent i = getIntent();
		scodigo = i.getStringExtra("codigo");
		srut = i.getStringExtra("rut");
		srazon = i.getStringExtra("razon");
		spago = i.getStringExtra("pago");
		sdireccion = i.getStringExtra("direccion");
		sciudad = i.getStringExtra("ciudad");
		stelefono = i.getStringExtra("telefono");
		scelular = i.getStringExtra("celular");

		tcodigo = (TextView) findViewById(R.id.textCodigoCliente);
		trut = (TextView) findViewById(R.id.textRutCliente);
		trazon = (TextView) findViewById(R.id.textRazonCliente);
		tpago = (TextView) findViewById(R.id.textPagoCliente);
		tdireccion = (TextView) findViewById(R.id.textDireccionCliente);
		tciudad = (TextView) findViewById(R.id.textCiudadCliente);
		ttelefono = (TextView) findViewById(R.id.textTelefonoCliente);
		tcelular = (TextView) findViewById(R.id.textCelularCliente);
		
		
		tcodigo.setText(scodigo);
		trut.setText(srut);
		trazon.setText(srazon);
		tpago.setText(spago);
		tdireccion.setText(sdireccion);
		tciudad.setText(sciudad);
		ttelefono.setText(stelefono);
		tcelular.setText(scelular);
		
		editar = (Button) findViewById(R.id.buttonEditarCliente);
		eliminar = (Button) findViewById(R.id.buttonEliminarCliente);
		
		editar.setOnClickListener(new View.OnClickListener() {
       	 
            @Override
            public void onClick(View v) {    
            	Intent intentEditar = new Intent(getBaseContext(),EditarClienteActivity.class);
            	intentEditar.putExtra("codigo", scodigo);
            	intentEditar.putExtra("rut", srut);
            	intentEditar.putExtra("razon", srazon);
            	intentEditar.putExtra("pago", spago);
            	intentEditar.putExtra("direccion", sdireccion);
            	intentEditar.putExtra("ciudad", sciudad);
            	intentEditar.putExtra("telefono", stelefono);
            	intentEditar.putExtra("celular", scelular);
            	startActivity(intentEditar);
            }
        });
		
		eliminar.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View v) {    
            	
            	new DeleteCliente().execute(scodigo);
            }
        });
		
	}
	
	private class DeleteCliente extends AsyncTask<String, Void, Void> {
   	 
        boolean isDelete = false;
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SingleItemViewCliente.this);
            pDialog.setMessage("Eliminando Cliente...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(String... arg) {
 
            String codigo = arg[0];
           
            // Preparing post params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("codigo", codigo));
            ServiceHandler serviceClient = new ServiceHandler();
 
            String json = serviceClient.makeServiceCall(URL_DELETE_CLIENTE,
                    ServiceHandler.POST, params);
 
            Log.d("Create Response: ", "> " + json);
 
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    boolean error = jsonObj.getBoolean("error");
                    // checking for error node in json
                    if (!error) {   
                        // new category created successfully
                        isDelete = true;
                    } else {
                        Log.e("Error al eliminar el cliente: ", "> " + jsonObj.getString("message"));
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
            if (isDelete) {
            	Toast.makeText(
                        getApplicationContext(),"Se ha eliminado correctamente el cliente" ,
                        Toast.LENGTH_LONG).show();
            	Intent intent = new Intent(getBaseContext(),MainActivity.class);
            	startActivity(intent);
            	finish();
            }
        }
 
    }
    
}