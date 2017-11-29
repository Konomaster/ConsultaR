package com.example.administrador.consultar.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrador.consultar.R;
import com.example.administrador.consultar.adapters.AdapterList;
import com.example.administrador.consultar.beans.Cliente;
import com.example.administrador.consultar.servicos.servixos_all;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPerfil extends Fragment {
    servixos_all bd;
    private ListView listView;
    List<String> lista = new ArrayList<String>();

    private AdapterList salv;   //adaptador, declarado global para aparecer no método
    // de atualização do listView

    private int cod;                        //valor inteiro declarad global para ser atribuido
    // quando o usuário clicar em algum item do listview e
    // será usado no Alerta para deletar elementos da lista

    private String s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //extraio o objeto view para trabalhar com demais componentes no fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        bd = new servixos_all(getContext());
        listView = (ListView) view.findViewById(R.id.listViewPerfil);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//           // public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            }
//        });
        criaLista();
        return view;
    }

    private void criaLista() {
        List<Cliente> cursor = bd.findAllCliente();
        for(int i = 0; i<cursor.size();i++) {
            lista.add("    Nome: \n        "+cursor.get(i).getCliNome());
            lista.add("    Email: \n       "+cursor.get(i).getCliEmail());
            lista.add("    CPF: \n       "+cursor.get(i).getCliCPF());
            lista.add("    Telefone: \n       "+cursor.get(i).getCliTelefone());
            System.out.println("Teste0101 "+lista.get(0));
        }
        //criando o adapter customizado
        salv = new AdapterList(getContext());
        salv.setLista(lista);
        //setando o adapter customizado ao list
        //listView=new ListView(getContext());
        listView.setAdapter(salv);
    }
}