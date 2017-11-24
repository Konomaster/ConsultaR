package com.example.administrador.consultar.view.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrador.consultar.PrincipalActivity;
import com.example.administrador.consultar.R;
import com.example.administrador.consultar.adapters.AdapterListView;
import com.example.administrador.consultar.beans.Cliente;
import com.example.administrador.consultar.beans.Venda;
import com.example.administrador.consultar.servicos.servixos_all;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Opcao1Fragment extends Fragment {
    private ListView listView;
    List<String> lista = new ArrayList<String>();
    private int codigo = -1;
    private String nome;
    TextView list;
    servixos_all crud = null;

    public Opcao1Fragment() {
        // Required empty public constructor
    }

    private AdapterListView salv;   //adaptador, declarado global para aparecer no método
    // de atualização do listView

    private int cod;                        //valor inteiro declarad global para ser atribuido
    // quando o usuário clicar em algum item do listview e
    // será usado no Alerta para deletar elementos da lista

    private String s;                       //valor string declarad global para ser atribuido
    // quando o usuário clicar em algum item do listview e
    // será usado no Alerta para deletar elementos da lista

    PrincipalActivity p;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        //extraio o objeto view para trabalhar com demais componentes no fragment
        View view = inflater.inflate(R.layout.fragment_opcao1, container, false);

        


      //  Toast.makeText(getContext(), "2", Toast.LENGTH_LONG).show();
    //    list = (TextView) view.findViewById(R.id.tv1);
        p=new PrincipalActivity();
        // identifica ListView
        listView = (ListView) view.findViewById(R.id.listViewHistorico);
        crud = new servixos_all(getContext());
        salv = new AdapterListView(getContext());
        //O erro está aqui porque nn pode pegar o findAll tem que pegar o findbySql
        List<Cliente> cursor = crud.findAllCliente();
        final List<Venda> cursor1 = crud.findAllVenda();
        Log.d("IFMG", "onCreateView: " + cursor1.get(0).getVenData());

        salv.setLista(cursor1, cursor.get(0).getCliNome());
        listView.setAdapter(salv);
        //evento de click na listagem de pratos
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int codvenda = cursor1.get(position).getVen_cliCodigo();
                Intent telavenda = new Intent(getActivity(), FragmentPerfil.class);
           //     FragmentProdutos op1= new FragmentProdutos();
            //    p.replaceFragment(op1);
                //MUDAR TELA PARA PASSAR VIA FRAGEMENT
//                telavenda.putExtra("cod", codvenda);
//                getActivity().startActivity(telavenda);


                alertDialogSelList(position);
            }
        });
        atualizaCampoTexto();
        return view;
    }

    public void onResume() {
        super.onResume();
    }

    public void atualizaCampoTexto() {


        //List<Contato> contatos = bd.findAll();
        List<Venda> contatos = crud.findAllVenda();

        //  String texto = "";
        float x = 0;
        for (Venda v : contatos) {
            if (v.getVenStatus().equals("Pendente")) {
                x = x + v.getVenValorTotal();
            }
        }
        if (x != 0) {
            list.setText("Divida Total: R$" + x + "");
        } else {
            list.setText("Sem dívidas, Parabéns!");
        }

    }

//        list.setText(x);


    private AlertDialog alerta;

    private void alertDialogSelList(final int position) {

        //final BdVeiculoUnidade bdVeiculoUnidade = new BdVeiculoUnidade(getContext());

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //define o titulo
        builder.setTitle("Comprar");
        //define a mensagem
        builder.setMessage("Deseja bla bla bla?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //bdVeiculoUnidade.deleteVeiculoUnidade(veiculoUnidadeList.get(position).getVeiPlaca());
                Toast.makeText(listView.getContext(), "Indice: : " + position, Toast.LENGTH_SHORT).show();
                // Recriar a lista atualizando-a
                //criaLista();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

}
