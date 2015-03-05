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
public class ListViewAdapter extends BaseAdapter {
 
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	HashMap<String, String> resultp = new HashMap<String, String>();
 
	public ListViewAdapter(Context context,
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
		TextView cliente;
		TextView productos;
		TextView fecha;
		
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View itemView = inflater.inflate(R.layout.listview_item, parent, false);
		// Get the position
		
		resultp = data.get(position);
 
		// Locate the TextViews in listview_item.xml
		codigo = (TextView) itemView.findViewById(R.id.codigo);
		cliente = (TextView) itemView.findViewById(R.id.cliente);
		productos = (TextView) itemView.findViewById(R.id.productos);
		fecha = (TextView) itemView.findViewById(R.id.fecha);
 		 
		// Capture position and set results to the TextViews
		codigo.setText(resultp.get(MostrarPedidosActivity.CODIGO));
		cliente.setText(resultp.get(MostrarPedidosActivity.CLIENTE));
		productos.setText(resultp.get(MostrarPedidosActivity.PRODUCTOS));
		fecha.setText(resultp.get(MostrarPedidosActivity.FECHA));

		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, SingleItemView.class);
				// Pass all data image
				intent.putExtra("id", resultp.get(MostrarPedidosActivity.ID));
				intent.putExtra("codigo", resultp.get(MostrarPedidosActivity.CODIGO));
				intent.putExtra("cliente", resultp.get(MostrarPedidosActivity.CLIENTE));
				intent.putExtra("productos", resultp.get(MostrarPedidosActivity.PRODUCTOS));
				intent.putExtra("fecha", resultp.get(MostrarPedidosActivity.FECHA));
				// Start SingleItemView Class
				context.startActivity(intent);
				System.exit(0);
			}
		});
		return itemView;
	}
}