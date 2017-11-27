package com.example.administrador.consultar.servicos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.administrador.consultar.beans.Cliente;
import com.example.administrador.consultar.beans.Produto;
import com.example.administrador.consultar.beans.Produto_Venda;
import com.example.administrador.consultar.beans.Venda;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wever on 29/03/2017.
 */

public class servixos_all extends SQLiteOpenHelper {
    private static final String TAG = "sql";

    // Nome do banco
    private static final String NOME_BANCO = "mydb";
    private static final String TABELA = "Cliente";
    private static final String TABELA1 = "Produto";
    private static final String TABELA2 = "Venda";
    private static final String TABELA3 = "Produto_Venda";
    private static final int VERSAO_BANCO = 1;

    public servixos_all(Context context) {
        // context, nome do banco, factory, versão
        super(context, NOME_BANCO, null, VERSAO_BANCO);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a Tabela " + TABELA + "...");
        db.execSQL("CREATE TABLE IF NOT EXISTS  " + TABELA + " (" +
                "  cliCodigo integer primary key autoincrement,\n" +
                "  cliNome text,\n" +
                "  cliEmail text,\n" +
                "  cliCPF text,\n" +
                "  cliSaldo double,\n" +
                "  cliTelefone text);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABELA1 + " (" +
                "  proCodigo integer primary key autoincrement,\n" +
                "  proNome text,\n" +
                "  proPreco double);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABELA2 + " (" +
                "  venCodigo integer primary key autoincrement,\n" +
                "  venFormaPagamento text,\n" +
                "  venStatus text,\n" +
                "  venValorTotal float, \n" +
                "  venData date,\n" +
                "  ven_cliCodigo int)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABELA3 + " (" +
                " idProduto_Venda integer primary key autoincrement, " +
                " ven_proCodigo int, " +
                " pro_venCodigo int, " +
                " proQtdProduto int )");
        Log.d(TAG, "Tabela " + TABELA + " criada com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Caso mude a versão do banco de dados, podemos executar um SQL aqui
        if (oldVersion == 1 && newVersion == 2) {
            // Execute o script para atualizar a versão...
        }
    }

    public void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        } finally {
            db.close();
        }
    }


    // Insere um novo CONTATO, ou atualiza se já existe.
    public long saveCliente(Cliente c) {
        long id = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("cliNome", c.getCliNome());
            values.put("cliTelefone", c.getCliTelefone());
            values.put("cliCPF", c.getCliCPF());
            values.put("cliEmail", c.getCliEmail());
            values.put("cliSaldo", c.getCliSaldo());

            if (id != 0) {//SE O ID É DIFERENTE DE 0 ATUALIZA,

                String _id = String.valueOf(c.getCliCodigo());
                String[] whereArgs = new String[]{_id};

                // update contato set values = ... where _id=?
                int count = db.update(TABELA, values, "cliCodigo=?", whereArgs);

                return count;
            } else { // SE O ID FOR 0, SIGNIFICA QUE NÃO TEM ID, ASSIM VAI INSERIR O DADO
                // insert into contato values (...)
                id = db.insert(TABELA, "", values);

                return id;

            }
        } finally {
            db.close();
        }
    }

    // Deleta o CONTATO
    public int deleteCliente(Cliente c) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from contato where _id=?
            int count = db.delete(TABELA, "cliCodigo=?", new String[]{String.valueOf(c.getCliCodigo())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os contatos
    public List<Cliente> findAllCliente() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from contato
            Cursor c = db.query(TABELA, null, null, null, null, null, null, null);

            return toListCliente(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public List<Cliente> findBySqlCliente(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql, null);
            List<Cliente> contatos = new ArrayList<Cliente>();

            if (c.moveToFirst()) {
                do {
                    Cliente contato = new Cliente();
                    contatos.add(contato);

                    // recupera os atributos de contato
                    contato.setCliCodigo(c.getInt(c.getColumnIndex("cliCodigo")));
                    contato.setCliNome(c.getString(c.getColumnIndex("cliNome")));
                    contato.setCliTelefone(c.getString(c.getColumnIndex("cliTelefone")));
                    contato.setCliCPF(c.getString(c.getColumnIndex("cliCPF")));
                    contato.setCliEmail(c.getString(c.getColumnIndex("cliEmail")));
                    contato.setCliSaldo(c.getDouble(c.getColumnIndex("cliSaldo")));

                } while (c.moveToNext());
            }
            return contatos;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Cliente> toListCliente(Cursor c) {
        List<Cliente> contatos = new ArrayList<Cliente>();

        if (c.moveToFirst()) {
            do {
                Cliente contato = new Cliente();
                contatos.add(contato);

                // recupera os atributos de contatos
                contato.setCliCodigo(c.getInt(c.getColumnIndex("cliCodigo")));
                contato.setCliNome(c.getString(c.getColumnIndex("cliNome")));
                contato.setCliTelefone(c.getString(c.getColumnIndex("cliTelefone")));
                contato.setCliCPF(c.getString(c.getColumnIndex("cliCPF")));
                contato.setCliEmail(c.getString(c.getColumnIndex("cliEmail")));
                contato.setCliSaldo(c.getDouble(c.getColumnIndex("cliSaldo")));

            } while (c.moveToNext());
        }
        return contatos;
    }

    // Insere um novo CONTATO, ou atualiza se já existe.
    public long saveProduto(Produto c) {
        long id = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("proNome", c.getProNome());
            values.put("proPreco", c.getProValorVenda());

            if (id != 0) {//SE O ID É DIFERENTE DE 0 ATUALIZA,

                String _id = String.valueOf(c.getProCodigo());
                String[] whereArgs = new String[]{_id};

                // update contato set values = ... where _id=?
                int count = db.update(TABELA1, values, "proCodigo=?", whereArgs);

                return count;
            } else { // SE O ID FOR 0, SIGNIFICA QUE NÃO TEM ID, ASSIM VAI INSERIR O DADO
                // insert into contato values (...)
                id = db.insert(TABELA1, "", values);
                return id;
            }
        } finally {
            db.close();
        }
    }

    // Deleta o CONTATO
    public int deleteProduto(Produto c) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from contato where _id=?
            int count = db.delete(TABELA1, "proCodigo=?", new String[]{String.valueOf(c.getProCodigo())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os contatos
    public List<Produto> findAllProduto() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from contato
            Cursor c = db.query(TABELA1, null, null, null, null, null, null, null);

            return toListProduto(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public List<Produto> findBySqlProduto(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql, null);
            List<Produto> contatos = new ArrayList<Produto>();

            if (c.moveToFirst()) {
                do {
                    Produto contato = new Produto();
                    contatos.add(contato);

                    // recupera os atributos de contato
                    contato.setProCodigo(c.getInt(c.getColumnIndex("proCodigo")));
                    contato.setProNome(c.getString(c.getColumnIndex("proNome")));
                    contato.setProValorVenda(c.getDouble(c.getColumnIndex("proPreco")));

                } while (c.moveToNext());
            }
            return contatos;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Produto> toListProduto(Cursor c) {
        List<Produto> contatos = new ArrayList<Produto>();

        if (c.moveToFirst()) {
            do {
                Produto contato = new Produto();
                contatos.add(contato);

                // recupera os atributos de contatos
                contato.setProCodigo(c.getInt(c.getColumnIndex("proCodigo")));
                contato.setProNome(c.getString(c.getColumnIndex("proNome")));
                contato.setProValorVenda(c.getDouble(c.getColumnIndex("proPreco")));

            } while (c.moveToNext());
        }
        return contatos;
    }

    public long saveVenda(Venda c) {
        long id = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("venFormaPagamento", c.getVenFormaPagamento());
            values.put("venStatus", c.getVenStatus());
            values.put("venValorTotal", c.getVenValorTotal());
            values.put("venData", String.valueOf(c.getVenData()));
            values.put("ven_cliCodigo", c.getVen_cliCodigo());


            if (id != 0) {//SE O ID É DIFERENTE DE 0 ATUALIZA,

                String _id = String.valueOf(c.getVenCodigo());
                String[] whereArgs = new String[]{_id};

                // update contato set values = ... where _id=?
                int count = db.update(TABELA2, values, "venCodigo=?", whereArgs);

                return count;
            } else { // SE O ID FOR 0, SIGNIFICA QUE NÃO TEM ID, ASSIM VAI INSERIR O DADO
                // insert into contato values (...)
                id = db.insert(TABELA2, "", values);
                return id;
            }
        } finally {
            db.close();
        }
    }

    // Deleta o CONTATO
    public int deleteVenda(Venda c) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from contato where _id=?
            int count = db.delete(TABELA2, "venCodigo=?", new String[]{String.valueOf(c.getVen_cliCodigo())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os contatos
    public List<Venda> findAllVenda() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from contato
            Cursor c = db.query(TABELA2, null, null, null, null, null, null, null);

            return toListVenda(c);
        } finally {
            db.close();
        }
    }

    public List<Venda> findVendasPendentes(){
        SQLiteDatabase db=getReadableDatabase();


        try{

            Cursor c= db.rawQuery("select * from venda where venStatus='Pendente';",null);

            return toListVenda(c);

        }finally {
            db.close();
        }

    }

    // Consulta por sql testar depois
    public List<Venda> findBySqlVenda(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql, null);
            List<Venda> contatos = new ArrayList<Venda>();

            if (c.moveToFirst()) {
                do {
                    Venda contato = new Venda();
                    contatos.add(contato);

                    // recupera os atributos de contato
                    contato.setVenCodigo(c.getInt(c.getColumnIndex("venCodigo")));
                    contato.setVenFormaPagamento(c.getString(c.getColumnIndex("venFormaPagamento")));
                    contato.setVenData(c.getString(c.getColumnIndex("venData")));
                    contato.setVenStatus(c.getString(c.getColumnIndex("venStatus")));
                    contato.setVenValorTotal(c.getFloat(c.getColumnIndex("venValorTotal")));
                    contato.setVen_cliCodigo(c.getInt(c.getColumnIndex("ven_cliCodigo")));

                } while (c.moveToNext());
            }
            return contatos;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Venda> toListVenda(Cursor c) {
        List<Venda> contatos = new ArrayList<Venda>();

        if (c.moveToFirst()) {
            do {
                Venda contato = new Venda();
                contatos.add(contato);

                // recupera os atributos de contatos
                contato.setVenCodigo(c.getInt(c.getColumnIndex("venCodigo")));
                contato.setVenFormaPagamento(c.getString(c.getColumnIndex("venFormaPagamento")));
                contato.setVenData(c.getString(c.getColumnIndex("venData")));
                System.out.println("Vendata: "+contato.getVenData());
                contato.setVenStatus(c.getString(c.getColumnIndex("venStatus")));
                contato.setVenValorTotal(c.getFloat(c.getColumnIndex("venValorTotal")));
                contato.setVen_cliCodigo(c.getInt(c.getColumnIndex("ven_cliCodigo")));

            } while (c.moveToNext());
        }
        return contatos;
    }
//Usar email para enviar dados de cadastro para o RONEY


    public long saveProduto_Venda(Produto_Venda c) {
        long id = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            Log.d("[IFMG]", "saveProduto_Venda: ");
            ContentValues values = new ContentValues();
            values.put("proQtdProduto", c.getProQtdProduto());
            values.put("pro_venCodigo", c.getPro_venCodigo());
            values.put("ven_proCodigo", c.getVen_proCodigo());
//            values.put("proValorUnitario",c.getProValorUnitario());

            if (id != 0) {//SE O ID É DIFERENTE DE 0 ATUALIZA,

                String _id = String.valueOf(c.getIdProduto_Venda());
                String[] whereArgs = new String[]{_id};

                // update contato set values = ... where _id=?
                int count = db.update(TABELA3, values, "idProduto_Venda=?", whereArgs);



                return count;
            } else { // SE O ID FOR 0, SIGNIFICA QUE NÃO TEM ID, ASSIM VAI INSERIR O DADO
                // insert into contato values (...)
                id = db.insert(TABELA3, "", values);
                Cursor cd=db.query(TABELA3, null, null, null, null, null, null, null);
                if(cd.moveToFirst()){
                    List<Produto_Venda> pv=toListProduto_Venda(cd);
                    int i=pv.size();
                    System.out.println(i);
                }
                return id;
            }
        } finally {
            db.close();
        }
    }

    // Deleta o CONTATO
    public int deleteProduto_Venda(Produto_Venda c) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from contato where _id=?
            int count = db.delete(TABELA3, "idProduto_Venda=?",new String[]{c.getIdProduto_Venda()+""} );
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }


    // Consulta a lista com todos os contatos
    public List<Produto_Venda> findAllProduto_Venda() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            // select * from contato
            Cursor c = db.query(TABELA3, null, null, null, null, null, null, null);

            return toListProduto_Venda(c);
        } finally {
            db.close();
        }
    }

    // Consulta por sql testar depois
    public List<Produto_Venda> findBySqlVendaProduto_Venda(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(sql, null);
            List<Produto_Venda> contatos = new ArrayList<Produto_Venda>();

            if (c.moveToFirst()) {
                do {
                    Produto_Venda contato = new Produto_Venda();
                    contatos.add(contato);

                    // recupera os atributos de contato
                    contato.setIdProduto_Venda(c.getInt(c.getColumnIndex("idProduto_Venda")));
                    contato.setPro_venCodigo(c.getInt(c.getColumnIndex("pro_venCodigo")));
                    contato.setVen_proCodigo(c.getInt(c.getColumnIndex("ven_proCodigo")));
                    contato.setProQtdProduto(c.getInt(c.getColumnIndex("proQtdProduto")));
//                    contato.setProValorUnitario(c.getDouble(c.getColumnIndex("proValorUnitario")));

                } while (c.moveToNext());
            }
            return contatos;
        } finally {
            db.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Produto_Venda> toListProduto_Venda(Cursor c) {
        List<Produto_Venda> contatos = new ArrayList<Produto_Venda>();

        if (c.moveToFirst()) {
            do {
                Produto_Venda contato = new Produto_Venda();
                contatos.add(contato);

                // recupera os atributos de contatos
                contato.setIdProduto_Venda(c.getInt(c.getColumnIndex("idProduto_Venda")));
                contato.setPro_venCodigo(c.getInt(c.getColumnIndex("pro_venCodigo")));
                contato.setVen_proCodigo(c.getInt(c.getColumnIndex("ven_proCodigo")));
                contato.setProQtdProduto(c.getInt(c.getColumnIndex("proQtdProduto")));
//                contato.setProValorUnitario(c.getDouble(c.getColumnIndex("proValorUnitario")));


            } while (c.moveToNext());
        }
        return contatos;
    }

    public List<Produto> findAllProdutosOfVenda(int venCodigo) {
        SQLiteDatabase bd = getReadableDatabase();
        List<Produto> produtos = new ArrayList<Produto>();

        try {
            Cursor d=bd.rawQuery("select * from Produto_Venda",null);

            // Cursor c=bd.execSQL("select * from produto where ven_proCodigo="+venCodigo+";");
            Cursor c = bd.rawQuery("select * from Produto join (select * from Produto_Venda where Produto_Venda.ven_proCodigo=?) as pv where Produto.proCodigo=pv.pro_venCodigo", new String[]{venCodigo + ""});

            if (c.moveToFirst()) {
                do {
                    Produto produto = new Produto();
                    produto.setProCodigo(c.getInt(c.getColumnIndex("proCodigo")));
                    produto.setProNome(c.getString(c.getColumnIndex("proNome")));
                    produto.setProValorVenda(c.getDouble(c.getColumnIndex("proPreco")));

                    produtos.add(produto);
                } while (c.moveToNext());

            }
        } finally {
            bd.close();
        }
        Log.d("[IFMG]","tamanho do array:"+produtos.size());
        return produtos;
    }
}
