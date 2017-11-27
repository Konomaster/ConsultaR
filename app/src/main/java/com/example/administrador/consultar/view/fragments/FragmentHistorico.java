package com.example.administrador.consultar.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.administrador.consultar.R;
import com.example.administrador.consultar.adapters.AdapterExpandableListView;
import com.example.administrador.consultar.beans.Produto;
import com.example.administrador.consultar.beans.Venda;
import com.example.administrador.consultar.beans.VendasProdutos;
import com.example.administrador.consultar.servicos.servixos_all;

import java.util.ArrayList;
import java.util.List;

public class FragmentHistorico extends Fragment {

    private servixos_all bd = null;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        bd=new servixos_all(getContext());

        ExpandableListView expandableListView=(ExpandableListView) view.findViewById(R.id.expandable);
        expandableListView.setAdapter(new AdapterExpandableListView(getContext(),bd.findAllCliente().get(0),getLista()));

        return view;

    }



    public void setBD(servixos_all bd){
        this.bd=bd;
    }

    //ADD AND GET DATA
    private List<Venda> getLista(){

        List<Venda> vendas=bd.findAllVenda();
//        List<VendasProdutos> vendasProdutos=new ArrayList<VendasProdutos>();
//
//        if(bd!=null){
//
//            List<Venda> vendas=bd.findAllVenda();
//
//            for(Venda v: vendas){
//                VendasProdutos vp=new VendasProdutos();
//                vp.setVenda(v);
//                List<Produto> produtos=bd.findAllProdutosOfVenda(v.getVenCodigo());
////                Log.d("Produto", produtos.get(0).getProNome());
//                vp.setProdutos(produtos);
//                vendasProdutos.add(vp);
//
//            }
//
//
//
//
//
//
//        }

        return vendas;
    }
//    private ArrayList<Object> getData()
//    {
//
////        Team t1=new Team("Man Utd");
////        t1.players.add("Wayne Rooney");
////        t1.players.add("Van Persie");
////        t1.players.add("Ander Herera");
////        t1.players.add("Juan Mata");
////        Team t2=new Team("Arsenal");
////        t2.players.add("Aaron Ramsey");
////        t2.players.add("Mesut Ozil");
////        t2.players.add("Jack Wilshere");
////        t2.players.add("Alexis Sanchez");
////        Team t3=new Team("Chelsea");
////        t3.players.add("John Terry");
////        t3.players.add("Eden Hazard");
////        t3.players.add("Diego Costa");
////        t3.players.add("Oscar");
////        ArrayList<Team> allTeams=new ArrayList<Team>();
////        allTeams.add(t1);
////        allTeams.add(t2);
////        allTeams.add(t3);
//        return 0;
//    }
}