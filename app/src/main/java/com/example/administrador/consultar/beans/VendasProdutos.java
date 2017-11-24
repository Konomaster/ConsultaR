package com.example.administrador.consultar.beans;

import java.util.List;

/**
 * Created by wever on 19/10/2017.
 */

public class VendasProdutos {

    private Venda venda;
    private List<Produto> produtos;

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
