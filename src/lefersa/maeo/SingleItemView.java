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
 
public class SingleItemView extends Activity {
	// Declare Variables
	String sid;
	String scodigo;
	String scliente;
	String sproductos;
	String sfecha;
	TextView tCodigo;
	TextView tCliente;
	TextView tProductos;
	TextView tFecha;
	Button editar;
	Button eliminar;
	ProgressDialog pDialog;
	InputStream is=null;
	String result=null;
	String line=null;
	int code;
	
	private String URL_DELETE_PEDIDO = "http://maescobar.com/eliminar_pedido.php";

 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singleitemview);
 
		Intent i = getIntent();
		sid = i.getStringExtra("id");
		scodigo = i.getStringExtra("codigo");
		scliente = i.getStringExtra("cliente");
		sproductos = i.getStringExtra("productos");
		sfecha = i.getStringExtra("fecha");

		tCodigo = (TextView) findViewById(R.id.textCodigo);
		tCliente = (TextView) findViewById(R.id.textCliente);
		tProductos = (TextView) findViewById(R.id.textProductos);
		tFecha = (TextView) findViewById(R.id.textfecha);
		
		tCodigo.setText(scodigo);
		tCliente.setText(scliente);
		tProductos.setText(sproductos);
		tFecha.setText(sfecha);
		
		editar = (Button) findViewById(R.id.buttonEditar);
		eliminar = (Button) findViewById(R.id.buttonEliminar);
		
		editar.setOnClickListener(new View.OnClickListener() {
       	 
            @Override
            public void onClick(View v) {    
            	Intent intentEditar = new Intent(getBaseContext(),EditarPedidoActivity.class);
            	intentEditar.putExtra("id", sid);
            	intentEditar.putExtra("codigo", scodigo);
            	intentEditar.putExtra("cliente", scliente);
            	intentEditar.putExtra("pedido", sproductos);
            	intentEditar.putExtra("fecha", sfecha);
            	startActivity(intentEditar);
            }
        });
		
		eliminar.setOnClickListener(new View.OnClickListener() {
	       	 
            @Override
            public void onClick(View v) {    
            	
            	new DeletePedido().execute(sid);
            }
        });
		
	}
	
	private class DeletePedido extends AsyncTask<String, Void, Void> {
   	 
        boolean isDelete = false;
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SingleItemView.this);
            pDialog.setMessage("Eliminando Pedido..");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(String... arg) {
 
            String id = arg[0];
           
            // Preparing post params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            ServiceHandler serviceClient = new ServiceHandler();
 
            String json = serviceClient.makeServiceCall(URL_DELETE_PEDIDO,
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
                        Log.e("Error al eliminar el pedido: ", "> " + jsonObj.getString("message"));
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
                        getApplicationContext(),"Se ha eliminado correctamente el pedido" ,
                        Toast.LENGTH_LONG).show();
            	Intent intent = new Intent(getBaseContext(),MainActivity.class);
            	startActivity(intent);
            	finish();
            }
        }
 
    }
    
}