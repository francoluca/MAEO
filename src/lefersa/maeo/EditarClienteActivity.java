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
public class EditarClienteActivity extends Activity{

    private EditText editTextCodigo;
    private EditText editTextRut;
    private EditText editTextRazon;
    private EditText editTextPago;
    private EditText editTextDireccion;
    private EditText editTextCiudad;
    private EditText editTextTelefono;
    private EditText editTextCelular;
    private Button buttonAceptar;
    // array list for spinner adapter
    ArrayList<HashMap<String, String>> arrayList;
    ProgressDialog pDialog;
    JSONObject jsonobject;
	JSONArray jsonarray;
	String codigo;
	String rut;
	String razon;
	String pago;
	String direccion;
	String ciudad;
	String telefono;
	String celular;
	InputStream is=null;
	String line=null;
	String result=null;
	int code;
	
	private String URL_UPDATE_CLIENTE = "http://maescobar.com/editar_cliente.php";
    
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);
 
        editTextCodigo = (EditText) findViewById(R.id.editTextCodigo);
        editTextRut = (EditText) findViewById(R.id.editTextRut);
        editTextRazon = (EditText) findViewById(R.id.editTextRazon);
        editTextPago = (EditText) findViewById(R.id.editTextPago);
        editTextDireccion = (EditText) findViewById(R.id.editTextDireccion);
        editTextCiudad = (EditText) findViewById(R.id.editTextCiudad);
        editTextTelefono = (EditText) findViewById(R.id.editTextTelefono);
        editTextCelular = (EditText) findViewById(R.id.editTextCelular);
        buttonAceptar = (Button) findViewById(R.id.buttonAceptar);
        
        Intent i = getIntent();
        codigo = i.getStringExtra("codigo");
		rut = i.getStringExtra("rut");
		razon = i.getStringExtra("razon");
		pago = i.getStringExtra("pago");
		direccion = i.getStringExtra("direccion");
		ciudad = i.getStringExtra("ciudad");
		telefono = i.getStringExtra("telefono");
		celular = i.getStringExtra("celular");
		
        editTextCodigo.setText(codigo);
        editTextRut.setText(rut);
        editTextRazon.setText(razon);
        editTextPago.setText(pago);
        editTextDireccion.setText(direccion);
        editTextCiudad.setText(ciudad);
        editTextTelefono.setText(telefono);
        editTextCelular.setText(celular);
               
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
			
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    				
    			codigo = editTextCodigo.getText().toString();
    			rut = editTextRut.getText().toString();
    			razon = editTextRazon.getText().toString().toUpperCase();
    			pago = editTextPago.getText().toString().toUpperCase();
    			direccion = editTextDireccion.getText().toString().toUpperCase();
    			ciudad = editTextCiudad.getText().toString().toUpperCase();
    			telefono = editTextTelefono.getText().toString();
    			celular = editTextCelular.getText().toString();
    			
    			new Update().execute(codigo,rut,razon,pago,direccion,ciudad,telefono,celular);
    		}
    	});        
        }
     
    private class Update extends AsyncTask<String, Void, Void>{
    	
    	boolean isEdit = false;
    	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditarClienteActivity.this);
            pDialog.setMessage("Editando Cliente...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(String... arg) {
 
        	String codigo = arg[0];
        	String rut = arg[1];
            String razon = arg[2];
            String pago = arg[3];
            String direccion = arg[4];
            String ciudad = arg[5];
            String telefono = arg[6];
            String celular = arg[7];
 
            // Preparing post params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("codigo", codigo));
            params.add(new BasicNameValuePair("rut", rut));
            params.add(new BasicNameValuePair("razon", razon));
            params.add(new BasicNameValuePair("pago", pago));
            params.add(new BasicNameValuePair("direccion", direccion));
            params.add(new BasicNameValuePair("ciudad", ciudad));
            params.add(new BasicNameValuePair("telefono", telefono));
            params.add(new BasicNameValuePair("celular", celular));
 
            ServiceHandler serviceClient = new ServiceHandler();
 
            String json = serviceClient.makeServiceCall(URL_UPDATE_CLIENTE,
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
                        Log.e("Error al editar el cliente: ", "> " + jsonObj.getString("message"));
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
                        getApplicationContext(),"Se ha editado correctamente el cliente" ,
                        Toast.LENGTH_LONG).show();
            	Intent intent = new Intent(getBaseContext(),MainActivity.class);
            	startActivity(intent);
            	finish();
            }
        }
 
    }
    	
}
              
