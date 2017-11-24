package com.example.administrador.consultar.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrador.consultar.R;
import com.example.administrador.consultar.beans.Cliente;
import com.example.administrador.consultar.servicos.servixos_all;

import java.util.List;

public class AActivity extends Fragment {

    servixos_all bd;
    TextView tvNome;
    TextView tvEmail;
    TextView tvCpf;
    TextView tvTelefone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.activity_a, container, false);
        bd = new servixos_all(getContext());
        tvNome = (TextView) view.findViewById(R.id.tvNome);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        tvCpf = (TextView) view.findViewById(R.id.tvCPF);
        tvTelefone = (TextView) view.findViewById(R.id.tvTelefone);
        atualizaList();
      //  Toast.makeText(getContext(), "Pegadinha!", Toast.LENGTH_LONG).show();
        return view;
    }

    public void atualizaList(){

        List<Cliente> cursor = bd.findAllCliente();
        for(int i = 0; i<cursor.size();i++){
            tvNome.setText(cursor.get(i).getCliNome());
            tvEmail.setText(cursor.get(i).getCliEmail());
            tvCpf.setText(cursor.get(i).getCliCPF());
            tvTelefone.setText(cursor.get(i).getCliTelefone());
        }
    }

    public void onClickOpcao(View v){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mudar dados");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Desejo mudar meus dados para: ");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "wevertonrodriguesarantes@gmail.oom");
        emailIntent.setType("message/rfc822");
        startActivity(emailIntent);

    }

    public void onResume() {
        super.onResume();
    }


}

