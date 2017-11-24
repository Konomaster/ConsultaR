package com.example.administrador.consultar.beans;

/**
 * Created by Weverton on 29/03/2017.
 */

public class Produto_Venda {

    private  int idProduto_Venda;
    private int ven_proCodigo;
    private int pro_venCodigo;
    private int proQtdProduto;
//    private double proValorUnitario;
    private boolean check;

//    public double getProValorUnitario() {
//        return proValorUnitario;
//    }
//
//    public void setProValorUnitario(double proValorUnitario) {
//        this.proValorUnitario = proValorUnitario;
//    }

    public int getIdProduto_Venda() {
        return idProduto_Venda;
    }

    public void setIdProduto_Venda(int idProduto_Venda) {
        this.idProduto_Venda = idProduto_Venda;
    }

    public int getProQtdProduto() {
        return proQtdProduto;
    }

    public void setProQtdProduto(int proQtdProduto) {
        this.proQtdProduto = proQtdProduto;
    }

    public int getVen_proCodigo() {
        return ven_proCodigo;
    }

    public void setVen_proCodigo(int ven_proCodigo) {
        this.ven_proCodigo = ven_proCodigo;
    }

    public int getPro_venCodigo() {
        return pro_venCodigo;
    }

    public void setPro_venCodigo(int pro_venCodigo) {
        this.pro_venCodigo = pro_venCodigo;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "Produto_Venda{" +
                "idProduto_Venda=" + idProduto_Venda +
                ", ven_proCodigo=" + ven_proCodigo +
                ", pro_venCodigo=" + pro_venCodigo +
                ", proQtdProduto=" + proQtdProduto +
//                ", proValorUnitario=" + proValorUnitario +
                ", check=" + check +
                '}';
    }
}
