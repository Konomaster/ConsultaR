package com.example.administrador.consultar.beans;

/**
 * Created by wever on 29/03/2017.
 */

public class Cliente {

    private int cliCodigo;
    private String cliNome;
    private String cliEmail;
    private String cliCPF;
    private double cliSaldo;
    private String cliTelefone;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getCliCodigo() {
        return cliCodigo;
    }

    public void setCliCodigo(int cliCodigo) {
        this.cliCodigo = cliCodigo;
    }

    public String getCliNome() {
        return cliNome;
    }

    public void setCliNome(String cliNome) {
        this.cliNome = cliNome;
    }

    public String getCliEmail() {
        return cliEmail;
    }

    public void setCliEmail(String cliEmail) {
        this.cliEmail = cliEmail;
    }

    public String getCliCPF() {
        return cliCPF;
    }

    public void setCliCPF(String cliCPF) {
        this.cliCPF = cliCPF;
    }

    public double getCliSaldo() {
        return cliSaldo;
    }

    public void setCliSaldo(double cliSaldo) {
        this.cliSaldo = cliSaldo;
    }

    public String getCliTelefone() {
        return cliTelefone;
    }

    public void setCliTelefone(String cliTelefone) {
        this.cliTelefone = cliTelefone;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cliCodigo=" + cliCodigo +
                ", cliNome='" + cliNome + '\'' +
                ", cliEmail='" + cliEmail + '\'' +
                ", cliCPF='" + cliCPF + '\'' +
                ", cliSaldo='" + cliSaldo + '\'' +
                ", cliTelefone='" + cliTelefone + '\'' +
                '}';
    }
}
