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
import com.example.administrador.consultar.beans.MesAno;
import com.example.administrador.consultar.beans.Produto;
import com.example.administrador.consultar.beans.Venda;
import com.example.administrador.consultar.beans.VendasProdutos;
import com.example.administrador.consultar.servicos.servixos_all;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AdapterExpandableListView extends BaseExpandableListAdapter {
    private Context c;
    private List<Venda> team;
    private LayoutInflater inflater;
    private Cliente cliente;
    private ArrayList<ArrayList<Venda>> mChildVenda;
    private ArrayList<MesAno> mGroups;
    private String[] meses = new String[]{"Jan ", "Fev ", "Mar ", "Abr ", "Mai ", "Jun ", "Jul ", "Ago ", "Set ", "Out ", "Nov", "Dez "};

    public AdapterExpandableListView(Context c, Cliente cliente, List<Venda> lv) {

        //array de grupo (mes e ano)
        mGroups = new ArrayList<MesAno>();

        //array de child (as vendas dentro do grupo no mesmo index)
        mChildVenda = new ArrayList<ArrayList<Venda>>();

        //vendas de um grupo x
        ArrayList<Venda> mVendas = new ArrayList<Venda>();

        for (Venda v : lv) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(formatter.parse(v.getVenData()));

                int month = gc.get(GregorianCalendar.MONTH);
                int year = gc.get(GregorianCalendar.YEAR);

                MesAno ma = new MesAno();
                ma.setMes(month);
                ma.setAno(year);
                ma.setNomeMes(meses[month]);

                if (mGroups.isEmpty()) {

                    mGroups.add(ma);
                    mVendas.add(v);
                } else {
                    boolean contem=false;
                    int gIndex=-21;

                 //   for(MesAno mesAno:mGroups){
                    for (int i=0;i<mGroups.size();i++){
                     if(mGroups.get(i).getMes()==ma.getMes() && mGroups.get(i).getAno()==ma.getAno()){
                        //if(mesAno.getMes()==ma.getMes() && mesAno.getAno()==ma.getAno()){
                            contem=true;
                            gIndex=i;
                        }
                    }

                        if(contem){

                            mChildVenda.get(gIndex).add(v);

                        }else{

                            mVendas.add(v);
                            mChildVenda.add(mVendas);
                            mGroups.add(ma);
                            mVendas = new ArrayList<Venda>();
                        }





                }
               // mVendas.add(v);

            } catch (Exception excepcion) {
                excepcion.printStackTrace();
            }
            if (!mVendas.isEmpty()) {
                mChildVenda.add(mVendas);
                mVendas=new ArrayList<Venda>();
            }


        }


        this.cliente = cliente;
        this.c = c;

        this.team = team;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //GET A SINGLE PLAYER
    @Override
    public Object getChild(int groupPos, int childPos) {
        // TODO Auto-generated method stub
        return null;
        //     return team.get(groupPos).getProdutos().get(childPos);
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_listview_item, null);
        }

        //GET CHILD/PLAYER NAME
        TextView nome = (TextView) convertView.findViewById(R.id.tvNomeCliente);
        TextView data = (TextView) convertView.findViewById(R.id.tvData);
        TextView valor = (TextView) convertView.findViewById(R.id.tvValor);
        TextView status = (TextView) convertView.findViewById(R.id.tvStatus);

        nome.setText("Cliente: " + cliente.getCliNome());
        data.setText("Data: " + mChildVenda.get(groupPos).get(childPos).getVenData());
        valor.setText("Valor Total: R$" + mChildVenda.get(groupPos).get(childPos).getVenValorTotal());
        status.setText("Status: " + mChildVenda.get(groupPos).get(childPos).getVenStatus());


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
        return mChildVenda.get(groupPosw).size();
    }

    //GET TEAM
    @Override
    public Object getGroup(int groupPos) {
        // TODO Auto-generated method stub
        return null;
        //return team.get(groupPos);
    }

    //GET NUMBER OF TEAMS
    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        System.out.println("GroupCount: "+mGroups.size());
        return mGroups.size();
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_mes, null);
        }

        TextView tvMes = (TextView) convertView.findViewById(R.id.tvMes);
        TextView tvAno = (TextView) convertView.findViewById(R.id.tvAno);

        tvMes.setText(mGroups.get(groupPosition).getNomeMes());
        tvAno.setText(mGroups.get(groupPosition).getAno()+"");

        //GET GROUP/TEAM ITEM
//        Venda t=((VendasProdutos) getGroup(groupPosition)).getVenda();
//
//        TextView tvMes=(TextView) convertView.findViewById(R.id.tvMes);
//        TextView tvAno=(TextView) convertView.findViewById(R.id.tvAno);
//
//        GregorianCalendar gc=new GregorianCalendar();


        //        //SET GROUP NAME
//        TextView tvNomeCliente=(TextView) convertView.findViewById(R.id.tvNomeCliente);
//        TextView tvData=(TextView) convertView.findViewById(R.id.tvData);
//        TextView tvValor=(TextView) convertView.findViewById(R.id.tvValor);
//        TextView tvStatus=(TextView) convertView.findViewById(R.id.tvStatus);


//        tvNomeCliente.setText(cliente.getCliNome());
//        tvData.setText(t.getVenData());
//        tvValor.setText(t.getVenValorTotal()+"");
//        tvStatus.setText(t.getVenStatus());

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