package com.example.administrador.consultar.beans;

/**
 * Created by wever on 29/03/2017.
 */

public class Produto {

    private int proCodigo;
    private String proNome;
    private double proValorVenda;
    private boolean check;

    public double getProValorVenda() {
        return proValorVenda;
    }

    public void setProValorVenda(double proValorVenda) {
        this.proValorVenda = proValorVenda;
    }

    public int getProCodigo() {
        return proCodigo;
    }

    public void setProCodigo(int proCodigo) {
        this.proCodigo = proCodigo;
    }

    public String getProNome() {
        return proNome;
    }

    public void setProNome(String proNome) {
        this.proNome = proNome;
    }


    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "proCodigo=" + proCodigo +
                ", proNome='" + proNome + '\'' +
                ", check=" + check +
                '}';
    }
}
