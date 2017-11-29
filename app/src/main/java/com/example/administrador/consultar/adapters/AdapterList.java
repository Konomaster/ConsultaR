package com.example.administrador.consultar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrador.consultar.R;
import java.util.ArrayList;
import java.util.List;

public class AdapterList extends BaseAdapter {
    private List<String> lista = new ArrayList<String>();
    private Context context;

    public void setLista(List<String> lista){
        this.lista = lista;
        System.out.println("Teste1 "+lista.get(0));
        System.out.println("Teste2 "+this.lista.get(0));
    }

    public AdapterList(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        System.out.println("TesteC "+lista.size());
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        System.out.println("Teste3 "+lista.get(position));
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        System.out.println("Teste5 "+position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("Teste4 "+position);
        String itemLista = lista.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_listview, parent, false);
        TextView t = (TextView) view.findViewById(R.id.tvtv);
        t.setText(itemLista);
        ImageView i = (ImageView) view.findViewById(R.id.imageViewAdapter);

        if(position==0){
            i.setBackgroundResource(R.drawable.nome);
        } else if(position==1){
            i.setBackgroundResource(R.drawable.email);
        } else if(position==2){
            i.setBackgroundResource(R.drawable.cpf);
        } else if(position==3){
            i.setBackgroundResource(R.drawable.telefone);
        }
        return view;
    }
}