package com.example.administrador.consultar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrador.consultar.R;
import com.example.administrador.consultar.beans.Cliente;
import com.example.administrador.consultar.beans.Produto;
import com.example.administrador.consultar.beans.Venda;
import com.example.administrador.consultar.beans.VendasProdutos;
import com.example.administrador.consultar.servicos.servixos_all;

import java.util.List;

public class AdapterExpandableListView extends BaseExpandableListAdapter {
    private Context c;
    private List<VendasProdutos> team;
    private LayoutInflater inflater;
    private Cliente cliente;
    public AdapterExpandableListView(Context c, List<VendasProdutos> team, Cliente cliente)
    {
        this.cliente=cliente;
        this.c=c;
        this.team=team;
        inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //GET A SINGLE PLAYER
    @Override
    public Object getChild(int groupPos, int childPos) {
        // TODO Auto-generated method stub
        return team.get(groupPos).getProdutos().get(childPos);
    }
    //GET PLAYER ID
    @Override
    public long getChildId(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return 0;
    }
    //GET PLAYER ROW
    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        //ONLY INFLATER XML ROW LAYOUT IF ITS NOT PRESENT,OTHERWISE REUSE IT
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.fragment_produtos, null);
        }

        //GET CHILD/PLAYER NAME

        Produto produto =(Produto)getChild(groupPos,childPos);

        //SET CHILD NAME
        TextView nomeProduto=(TextView) convertView.findViewById(R.id.nomeProduto);
        TextView qtdProduto=(TextView) convertView.findViewById(R.id.qtdProduto);
        TextView preco=(TextView) convertView.findViewById(R.id.preco);


        nomeProduto.setText(produto.getProNome());

        preco.setText(produto.getProValorVenda()+"");
        //GET TEAM NAME

//        //ASSIGN IMAGES TO PLAYERS ACCORDING TO THEIR NAMES AN TEAMS
//        if(teamName=="Man Utd")
//        {
//            if(child=="Wayne Rooney")
//            {
//                img.setImageResource(R.drawable.rooney)  ;
//            }else if(child=="Ander Herera")
//            {
//                img.setImageResource(R.drawable.herera)  ;
//            }else if(child=="Van Persie")
//            {
//                img.setImageResource(R.drawable.vanpersie)  ;
//            }else if(child=="Juan Mata")
//            {
//                img.setImageResource(R.drawable.mata)  ;
//            }
//        }else if(teamName=="Chelsea")
//        {
//            if(child=="John Terry")
//            {
//                img.setImageResource(R.drawable.terry)  ;
//            }else if(child=="Eden Hazard")
//            {
//                img.setImageResource(R.drawable.hazard)  ;
//            }else if(child=="Oscar")
//            {
//                img.setImageResource(R.drawable.oscar)  ;
//            }else if(child=="Diego Costa")
//            {
//                img.setImageResource(R.drawable.costa)  ;
//            }
//        }else if(teamName=="Arsenal")
//        {
//            if(child=="Jack Wilshere")
//            {
//                img.setImageResource(R.drawable.wilshere)  ;
//            }else if(child=="Alexis Sanchez")
//            {
//                img.setImageResource(R.drawable.sanchez)  ;
//            }else if(child=="Aaron Ramsey")
//            {
//                img.setImageResource(R.drawable.ramsey)  ;
//            }else if(child=="Mesut Ozil")
//            {
//                img.setImageResource(R.drawable.ozil)  ;
//            }
//        }
        return convertView;
    }
    //GET NUMBER OF PLAYERS
    @Override
    public int getChildrenCount(int groupPosw) {
        // TODO Auto-generated method stub
        return team.get(groupPosw).getProdutos().size();
    }
    //GET TEAM
    @Override
    public Object getGroup(int groupPos) {
        // TODO Auto-generated method stub
        return team.get(groupPos);
    }
    //GET NUMBER OF TEAMS
    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return team.size();
    }
    //GET TEAM ID
    @Override
    public long getGroupId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    //GET TEAM ROW
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //ONLY INFLATE XML TEAM ROW MODEL IF ITS NOT PRESENT,OTHERWISE REUSE IT
        if(convertView == null)
        {
            convertView=inflater.inflate(R.layout.adapter_listview_item, null);
        }
        //GET GROUP/TEAM ITEM

            Venda t=(Venda) ((VendasProdutos)getGroup(groupPosition)).getVenda();
//        //SET GROUP NAME
        TextView tvNomeCliente=(TextView) convertView.findViewById(R.id.tvNomeCliente);
        TextView tvData=(TextView) convertView.findViewById(R.id.tvData);
        TextView tvValor=(TextView) convertView.findViewById(R.id.tvValor);
        TextView tvStatus=(TextView) convertView.findViewById(R.id.tvStatus);

        tvNomeCliente.setText(cliente.getCliNome());
        tvData.setText(t.getVenData());
        tvValor.setText(t.getVenValorTotal()+"");
        tvStatus.setText(t.getVenStatus());

//        //ASSIGN TEAM IMAGES ACCORDING TO TEAM NAME
//        if(name=="Man Utd")
//        {
//            img.setImageResource(R.drawable.manutd);
//        }else if(name=="Chelsea")
//        {
//            img.setImageResource(R.drawable.chelsea);
//        }else if(name=="Arsenal")
//        {
//            img.setImageResource(R.drawable.arsenal);
//        }
//        //SET TEAM ROW BACKGROUND COLOR
//        convertView.setBackgroundColor(Color.LTGRAY);
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }
}