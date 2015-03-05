package lefersa.maeo;

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
public class AgregarClienteActivity extends Activity {
	 
    private EditText editTextCodigo;
    private EditText editTextRut;
    private EditText editTextRazon;
    private EditText editTextPago;
    private EditText editTextDireccion;
    private EditText editTextCiudad;
    private EditText editTextTelefono;
    private EditText editTextCelular;
    private Button buttonAgregar;
    // array list for spinner adapter
    ArrayList<HashMap<String, String>> arrayList;
    ProgressDialog pDialog;
    JSONObject jsonobject;
	JSONArray jsonarray;
	
    private String URL_NEW_CLIENTE = "http://maescobar.com/nuevo_cliente.php";
    
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);
 
        editTextCodigo = (EditText) findViewById(R.id.editTextCodigoCliente);
        editTextRut = (EditText) findViewById(R.id.editTextRutCliente);
        editTextRazon = (EditText) findViewById(R.id.editTextRazonCliente);
        editTextPago = (EditText) findViewById(R.id.editTextPagoCliente);
        editTextDireccion = (EditText) findViewById(R.id.editTextDireccionCliente);
        editTextCiudad = (EditText) findViewById(R.id.editTextCiudadCliente);
        editTextTelefono = (EditText) findViewById(R.id.editTextTelefonoCliente);
        editTextCelular = (EditText) findViewById(R.id.editTextCelularCliente);
        buttonAgregar = (Button) findViewById(R.id.buttonAgregarCliente);
        
       
       
     // Add new pedido click event
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
 
			@Override
            public void onClick(View v) {
               if (editTextCodigo.getText().toString().trim().length() > 0 
            		   && editTextRut.getText().toString().trim().length() > 0
            		   && editTextRazon.getText().toString().trim().length() > 0) {
                     
                    String codigo = editTextCodigo.getText().toString();
                    String rut = editTextRut.getText().toString();
                    String razon = editTextRazon.getText().toString().toUpperCase();
                    String pago = editTextPago.getText().toString().toUpperCase();
                    String direccion = editTextDireccion.getText().toString().toUpperCase();
                    String ciudad = editTextCiudad.getText().toString().toUpperCase();
                    String telefono = editTextTelefono.getText().toString();
                    String celular = editTextCelular.getText().toString();
                    
 
                    // Call Async task to create new category
                    new AddNewCliente().execute(codigo,rut,razon,pago,direccion,ciudad,telefono,celular);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Falta ingresar Código, RUT o Razón Social! ", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        
    }
    
    
  
    
    private class AddNewCliente extends AsyncTask<String, Void, Void> {
    	 
        boolean isNewClienteCreated = false;
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AgregarClienteActivity.this);
            pDialog.setMessage("Agregando Cliente...");
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
 
            String json = serviceClient.makeServiceCall(URL_NEW_CLIENTE,
                    ServiceHandler.POST, params);
 
            Log.d("Create Response: ", "> " + json);
 
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    boolean error = jsonObj.getBoolean("error");
                    // checking for error node in json
                    if (!error) {   
                        // new category created successfully
                        isNewClienteCreated = true;
                    } else {
                        Log.e("Error al crear cliente: ", "> " + jsonObj.getString("message"));
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
            if (isNewClienteCreated) {
            	Toast.makeText(
                        getApplicationContext(),"Se ha agregado correctamente el cliente" ,
                        Toast.LENGTH_LONG).show();
            	Intent intent = new Intent(getBaseContext(),MainActivity.class);
            	startActivity(intent);
            	finish();
            }
        }
 
    }
    
}
