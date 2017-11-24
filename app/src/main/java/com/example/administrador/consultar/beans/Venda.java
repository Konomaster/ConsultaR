package com.example.administrador.consultar.beans;

import java.util.Date;

/**
 * Created by wever on 29/03/2017.
 */

public class Venda {

    private int venCodigo;
    private String venFormaPagamento;
    private String venStatus;
    private float venValorTotal;
    private String venData;
    private int ven_cliCodigo;
    private boolean check;

    public String getVenData() {
        return venData;
    }

    public void setVenData(String venData) {
        this.venData = venData;
    }

    public int getVenCodigo() {
        return venCodigo;
    }

    public void setVenCodigo(int venCodigo) {
        this.venCodigo = venCodigo;
    }

    public String getVenFormaPagamento() {
        return venFormaPagamento;
    }

    public void setVenFormaPagamento(String venFormaPagamento) {
        this.venFormaPagamento = venFormaPagamento;
    }

    public String getVenStatus() {
        return venStatus;
    }

    public void setVenStatus(String venStatus) {
        this.venStatus = venStatus;
    }

    public float getVenValorTotal() {
        return venValorTotal;
    }

    public void setVenValorTotal(float venValorTotal) {
        this.venValorTotal = venValorTotal;
    }


    public int getVen_cliCodigo() {
        return ven_cliCodigo;
    }

    public void setVen_cliCodigo(int ven_cliCodigo) {
        this.ven_cliCodigo = ven_cliCodigo;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "venCodigo=" + venCodigo +
                ", venFormaPagamento='" + venFormaPagamento + '\'' +
                ", venStatus='" + venStatus + '\'' +
                ", venValorTotal=" + venValorTotal +
                ", venData=" + venData +
                ", ven_cliCodigo=" + ven_cliCodigo +
                ", check=" + check +
                '}';
    }
}
