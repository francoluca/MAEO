package lefersa.maeo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity{
	
	Button buttonNuevoPedido;
	Button buttonVerPedidos;
	Button buttonNuevoCliente;
	Button buttonVerClientes;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        buttonNuevoPedido = (Button) findViewById(R.id.buttonAgregarPedido);
	        buttonVerPedidos = (Button) findViewById(R.id.buttonVerPedidos);
	        buttonNuevoCliente = (Button) findViewById(R.id.buttonAgregarCliente);
	        buttonVerClientes = (Button) findViewById(R.id.buttonVerClientes);
	        
	        buttonNuevoPedido.setOnClickListener(new View.OnClickListener() {
	        	 
	            @Override
	            public void onClick(View v) {    
	            	Intent intentAgregarPedido = new Intent(getBaseContext(),AgregarPedidoActivity.class);
	            	startActivity(intentAgregarPedido);
	            }
	        });
	        
	        buttonVerPedidos.setOnClickListener(new View.OnClickListener() {
	        	 
	            @Override
	            public void onClick(View v) {    
	            	Intent intentVerPedidos = new Intent(getBaseContext(),MostrarPedidosActivity.class);
	            	startActivity(intentVerPedidos);
	            }
	        });
	        
	        buttonNuevoCliente.setOnClickListener(new View.OnClickListener() {
	        	 
	            @Override
	            public void onClick(View v) {    
	            	Intent intentNuevoCliente = new Intent(getBaseContext(),AgregarClienteActivity.class);
	            	startActivity(intentNuevoCliente);
	            }
	        });
	        
	        buttonVerClientes.setOnClickListener(new View.OnClickListener() {
	        	 
	            @Override
	            public void onClick(View v) {    
	            	Intent intentVerClientes = new Intent(getBaseContext(),MostrarClientesActivity.class);
	            	startActivity(intentVerClientes);
	            }
	        });
	        
	 }

}
