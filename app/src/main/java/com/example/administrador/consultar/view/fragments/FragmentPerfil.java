package com.example.administrador.consultar.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrador.consultar.R;
import com.example.administrador.consultar.adapters.Adapter;
import com.example.administrador.consultar.beans.Cliente;
import com.example.administrador.consultar.servicos.servixos_all;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPerfil extends Fragment {
    servixos_all bd;
    TextView tvNome;
    TextView tvEmail;
    TextView tvCpf;
    TextView tvTelefone;
    private ListView listView;
    List<String> lista = new ArrayList<String>();
    private int codigo = -1;
    private String nome;

    private Adapter salv;   //adaptador, declarado global para aparecer no método
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
        tvNome = (TextView) view.findViewById(R.id.tvNome);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        tvCpf = (TextView) view.findViewById(R.id.tvCPF);
        tvTelefone = (TextView) view.findViewById(R.id.tvTelefone);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//           // public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            }
       // });

        atualizaList();
        return view;
    }

    public void atualizaList(){

        }


    public void onClickOpcao(View v){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mudar dados");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Desejo mudar meus dados para: ");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "wevertonrodriguesarantes@gmail.oom");
        emailIntent.setType("message/rfc822");
        startActivity(emailIntent);

    }






    /**
     * A simple {@link Fragment} subclass.
     */



        public void setFiltroEstabelecimento(int estCodigo, String estNome) {
            this.codigo = estCodigo;

        }
                  //valor string declarad global para ser atribuido
        // quando o usuário clicar em algum item do listview e
        // será usado no Alerta para deletar elementos da lista

        @Override


        public void onResume() {
            super.onResume();

            criaLista();
        }

        private void criaLista() {

            List<Cliente> cursor = bd.findAllCliente();
            for(int i = 0; i<cursor.size();i++) {
                lista.add(cursor.get(i).getCliNome());
                lista.add(cursor.get(i).getCliEmail());
                lista.add(cursor.get(i).getCliCPF());
                lista.add(cursor.get(i).getCliTelefone());
                System.out.println("Teste0101 "+lista.get(0));
            }
            //criando o adapter customizado
            salv = new Adapter(getContext());
            salv.setLista(lista);
            //setando o adapter customizado ao list
            listView=new ListView(getContext());
            listView.setAdapter(salv);

        }



}
