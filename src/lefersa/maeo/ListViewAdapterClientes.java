package lefersa.maeo;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
@SuppressLint("ViewHolder")
public class ListViewAdapterClientes extends BaseAdapter {
 
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	HashMap<String, String> resultp = new HashMap<String, String>();
 
	public ListViewAdapterClientes(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
	}

 
	@Override
	public int getCount() {
		return data.size();
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView codigo;
		TextView rut;
		TextView razon;
		TextView pago;
		TextView direccion;
		TextView ciudad;
		TextView telefono;
		TextView celular;
		
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View itemView = inflater.inflate(R.layout.listviewcliente_item, parent, false);
		// Get the position
		
		resultp = data.get(position);
 
		// Locate the TextViews in listview_item.xml
		codigo = (TextView) itemView.findViewById(R.id.codigoCliente);
		rut = (TextView) itemView.findViewById(R.id.rutCliente);
		razon = (TextView) itemView.findViewById(R.id.razonCliente);
		pago = (TextView) itemView.findViewById(R.id.pagoCliente);
		direccion = (TextView) itemView.findViewById(R.id.direccionCliente);
		ciudad = (TextView) itemView.findViewById(R.id.ciudadCliente);
		telefono = (TextView) itemView.findViewById(R.id.telefonoCliente);
		celular = (TextView) itemView.findViewById(R.id.celularCliente);
 		 
		// Capture position and set results to the TextViews
		codigo.setText(resultp.get(MostrarClientesActivity.CODIGO));
		rut.setText(resultp.get(MostrarClientesActivity.RUT));
		razon.setText(resultp.get(MostrarClientesActivity.RAZON));
		pago.setText(resultp.get(MostrarClientesActivity.PAGO));
		direccion.setText(resultp.get(MostrarClientesActivity.DIRECCION));
		ciudad.setText(resultp.get(MostrarClientesActivity.CIUDAD));
		telefono.setText(resultp.get(MostrarClientesActivity.TELEFONO));
		celular.setText(resultp.get(MostrarClientesActivity.CELULAR));
		

		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, SingleItemViewCliente.class);
				// Pass all data image
				intent.putExtra("codigo", resultp.get(MostrarClientesActivity.CODIGO));
				intent.putExtra("rut", resultp.get(MostrarClientesActivity.RUT));
				intent.putExtra("razon", resultp.get(MostrarClientesActivity.RAZON));
				intent.putExtra("pago", resultp.get(MostrarClientesActivity.PAGO));
				intent.putExtra("direccion", resultp.get(MostrarClientesActivity.DIRECCION));
				intent.putExtra("ciudad", resultp.get(MostrarClientesActivity.CIUDAD));
				intent.putExtra("telefono", resultp.get(MostrarClientesActivity.TELEFONO));
				intent.putExtra("celular", resultp.get(MostrarClientesActivity.CELULAR));
				// Start SingleItemView Class
				context.startActivity(intent);
				System.exit(0);
 
			}
		});
		return itemView;
	}
}