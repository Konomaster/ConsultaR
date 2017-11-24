package com.example.administrador.consultar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrador.consultar.R;
import com.example.administrador.consultar.beans.Venda;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterListView extends BaseAdapter {
    private List<Venda> lista = new ArrayList<Venda>();
    private Context context;

    private String cliente;

    public void setLista(List<Venda> lista, String cliente){
        this.cliente=cliente;
        this.lista=lista;


    }



    public AdapterListView(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String itemListaData = lista.get(position).getVenData();
        String itemListaValor= String.valueOf(lista.get(position).getVenValorTotal());

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_listview_item, parent, false);

            TextView nome = (TextView) view.findViewById(R.id.tvNomeCliente);
            TextView data = (TextView) view.findViewById(R.id.tvData);
            TextView valor = (TextView) view.findViewById(R.id.tvValor);
            TextView status = (TextView) view.findViewById(R.id.tvStatus);
            if(lista.size()>0) {
                nome.setText("Cliente: " + cliente);
                data.setText("Data: " + itemListaData);
                valor.setText("Valor Total: R$" + itemListaValor);
                status.setText("Status: " + lista.get(position).getVenStatus());
            }else{
                nome.setText("Parabéns, nenhuma pendência!");
                data.setText("");
                valor.setText("");
                status.setText("");

            }


        return view;
    }
}
