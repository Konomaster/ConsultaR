package com.example.administrador.consultar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrador.consultar.beans.Cliente;
import com.example.administrador.consultar.beans.Produto;
import com.example.administrador.consultar.beans.Produto_Venda;
import com.example.administrador.consultar.beans.Venda;
import com.example.administrador.consultar.servicos.servixos_all;
import com.example.administrador.consultar.sinc.JSONDados;
import com.example.administrador.consultar.sinc.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static com.example.administrador.consultar.sinc.JSONDados.URL_SERVICO3;
import static com.example.administrador.consultar.sinc.JSONDados.URL_SERVICO4;
import static com.example.administrador.consultar.sinc.JSONDados.URL_SERVICO5;

public class SplashActivity extends AppCompatActivity {

    private ImageView splash;
    AnimationDrawable splashAnimation;
    private ProgressBar mProgress;
    private servixos_all bd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mProgress = (ProgressBar) findViewById(R.id.progressB);
        mProgress.setProgress(0);
        splash = (ImageView) findViewById(R.id.ivAnimacao);// imageWiew no layout
        splash.setBackgroundResource(R.drawable.cafeanimation);//drawable construido para animaçao
        bd = new servixos_all(getBaseContext());

        requisitaPost(
                JSONDados.geraJsonVenda(
                        pegaCPF()),
                //Arrumar este o host
                URL_SERVICO3
        );
        //inicia processamento paralelo a thread
        new InsertAsync().execute("");
    }

    public String pegaCPF(){
        List<Cliente> contatos = bd.findAllCliente();
        Log.d("Tamanho Array", contatos.size() + "");
        String texto = "";

        for (int i = 0; i < contatos.size(); i++) {
            Cliente c = contatos.get(i);
            Log.d("[IFMG]", "finalizando baixar defeitos" + c.getCliCodigo());
            texto = c.getCliCPF();
        }
        Log.d("[IFMG]", "finalizando baixar defeitos" + texto);

        return texto;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //inicia animação na splash, na thread da interface gráfica mesmo
        splashAnimation = (AnimationDrawable) splash.getBackground();
        if (hasFocus) {
            splashAnimation.start();
        } else {
            splashAnimation.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int count = 0;

        // Conta ate 100 para parar a execução(timeout)
        count++;
        if (count >= 110) {
            count = 0;
//            mProgress.setProgress(count);
            Toast.makeText(getApplicationContext(),"TimeOut!!!",Toast.LENGTH_SHORT).show();
        }
//        int tempoSplash = 5000;//5 segundos
//
//        //cria delay para entrar na proxima activity
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Seta intent para abrir nova Activity
//                Intent intent = new Intent(TelaSplashActivity.this, PrincipalActivity.class);
//                startActivity(intent);
//                //Fecha Activity atual
//                finish();
//            }
//        }, tempoSplash);

    }

    /* -------------------------------------------------------
    SUBCLASSE RESPONSÁVEL POR CRIAR A SEGUNDA THREAD, OBJETIVANDO PROCESSAMENTO
    PARALELO AO DA THREAD DA INTERFACE GRÁFICA
     ----------------------------------------------------------*/
    class InsertAsync extends AsyncTask<String, String, String> {
        //método executado antes do método da segunda thread doInBackground
        @Override
        protected void onPreExecute() {

        }

        //método que será executado em outra thread
        @Override
        protected String doInBackground(String... args) {
//            int j = 0;
//            for (int i = 0; i < 1000; i++) {
//                j++;
//            }
            return "";
        }

        //método executado depois da thread do doInBackground
        @Override
        protected void onPostExecute(String retorno) {
            //manda mensagem na tela para dizer que já executou a segunda thread
            //    Toast.makeText(getApplicationContext(), "Executou, j=" + retorno, Toast.LENGTH_LONG).show();
        }
    }

    public void requisitaPost(final String parametroJSON, final String URL_) {


        //thread obrigatória para realização da requisição pode ser usado com outras formas de thread
        new Thread(new Runnable() {
            public void run() {
                JSONParser jsonParser = new JSONParser();
                JSONObject json = null;
                try {
                    //prepara parâmetros para serem enviados via método POST
                    HashMap<String, String> params = new HashMap<>();
                    params.put("sincronizar", parametroJSON);

                    Log.d("[IFMG]", parametroJSON);
                    Log.d("[IFMG]", "JSON Envio Iniciando...");

                    //faz a requisição POST e retorna o que o webservice REST envoiu dentro de json
                    json = jsonParser.makeHttpRequest(URL_, "POST", params);

                    Log.d("[IFMG]", " JSON Envio Terminado...");

                    //Mostra no log e retorna o que o json retornou, caso não retornou nulo
                    if (json != null) {
                        Log.d("[IFMG]", json.toString());
                        //return json;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("[IFMG]", "finalizando baixar defeitos");

                //----------------------------------------------
                //PÓS DOWNLOAD
                //----------------------------------------------

                //teste para ferificar se o json chegou corretamente e foi interpretado
                if (json != null) {
                    //------------------------------------------------------------
                    //AQUI SE PEGA O JSON RETORNADO E TRATA O QUE DEVE SER TRATADO
                    //------------------------------------------------------------
                    final String resp = interpretaJSON_Aritimetica(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //startacativity
                            Log.d("IFMG", "run: " +resp);

                            //   if(resp!="") {
                            List<Cliente> contatos = bd.findAllCliente();
                            Log.d("Tamanho Array", contatos.size() + "");
                            String texto = "";

                            for (int i = 0; i < contatos.size(); i++) {
                                Cliente c = contatos.get(i);
                                Log.d("[IFMG]", "finalizando baixar defeitos" + c.getCliCodigo());
                            }
                            mProgress.setProgress(33);
                            requisitaPost1(
                                    JSONDados.geraJsonProduto_Venda(
                                            pegaCPF()),
                                    //Arrumar este o host
                                    URL_SERVICO4
                            );


//                            Intent i=new Intent(getApplicationContext(), SplashActivity.class);
//                            startActivity(i);
                            //      }else{
                            //        }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Falha na conexão!!!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        }).start();
    }

    public void requisitaPost1(final String parametroJSON, final String URL_) {


        //thread obrigatória para realização da requisição pode ser usado com outras formas de thread
        new Thread(new Runnable() {
            public void run() {
                JSONParser jsonParser = new JSONParser();
                JSONObject json = null;
                try {
                    //prepara parâmetros para serem enviados via método POST
                    HashMap<String, String> params = new HashMap<>();
                    params.put("sincronizar", parametroJSON);

                    Log.d("[IFMG]", parametroJSON);
                    Log.d("[IFMG]", "JSON Envio Iniciando...");

                    //faz a requisição POST e retorna o que o webservice REST envoiu dentro de json
                    json = jsonParser.makeHttpRequest(URL_, "POST", params);

                    Log.d("[IFMG]", " JSON Envio Terminado...");

                    //Mostra no log e retorna o que o json retornou, caso não retornou nulo
                    if (json != null) {
                        Log.d("[IFMG]", json.toString());
                        //return json;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("[IFMG]", "finalizando baixar defeitos");

                //----------------------------------------------
                //PÓS DOWNLOAD
                //----------------------------------------------

                //teste para ferificar se o json chegou corretamente e foi interpretado
                if (json != null) {
                    //------------------------------------------------------------
                    //AQUI SE PEGA O JSON RETORNADO E TRATA O QUE DEVE SER TRATADO
                    //------------------------------------------------------------
                    final String resp = interpretaJSON_Aritimetica1(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //startacativity
                            Log.d("IFMG", "run: " +resp);

                            //   if(resp!="") {
                            List<Cliente> contatos = bd.findAllCliente();
                            Log.d("Tamanho Array", contatos.size() + "");
                            String texto = "";

                            for (int i = 0; i < contatos.size(); i++) {
                                Cliente c = contatos.get(i);
                                Log.d("[IFMG]", "finalizando baixar defeitos" + c.getCliCodigo());
                            }
                            mProgress.setProgress(66);

                            requisitaPost2(
                                    JSONDados.geraJsonProduto(
                                            pegaCPF()),
                                    //Arrumar este o host
                                    URL_SERVICO5
                            );


                            Intent i=new Intent(getApplicationContext(), PrincipalActivity.class);
                            startActivity(i);
                            //      }else{
                            //        }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Falha na conexão!!!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        }).start();
    }

    public void requisitaPost2(final String parametroJSON, final String URL_) {


        //thread obrigatória para realização da requisição pode ser usado com outras formas de thread
        new Thread(new Runnable() {
            public void run() {
                JSONParser jsonParser = new JSONParser();
                JSONObject json = null;
                try {
                    //prepara parâmetros para serem enviados via método POST
                    HashMap<String, String> params = new HashMap<>();
                    params.put("sincronizar", parametroJSON);

                    Log.d("[IFMG]", parametroJSON);
                    Log.d("[IFMG]", "JSON Envio Iniciando...");

                    //faz a requisição POST e retorna o que o webservice REST envoiu dentro de json
                    json = jsonParser.makeHttpRequest(URL_, "POST", params);

                    Log.d("[IFMG]", " JSON Envio Terminado...");

                    //Mostra no log e retorna o que o json retornou, caso não retornou nulo
                    if (json != null) {
                        Log.d("[IFMG]", json.toString());
                        //return json;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("[IFMG]", "finalizando baixar defeitos");

                //----------------------------------------------
                //PÓS DOWNLOAD
                //----------------------------------------------

                //teste para ferificar se o json chegou corretamente e foi interpretado
                if (json != null) {
                    //------------------------------------------------------------
                    //AQUI SE PEGA O JSON RETORNADO E TRATA O QUE DEVE SER TRATADO
                    //------------------------------------------------------------
                    final String resp = interpretaJSON_Aritimetica2(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //startacativity
                            Log.d("IFMG", "run: " +resp);

                            //   if(resp!="") {
                            List<Cliente> contatos = bd.findAllCliente();
                            Log.d("Tamanho Array", contatos.size() + "");
                            String texto = "";
                            mProgress.setProgress(100);

                            for (int i = 0; i < contatos.size(); i++) {
                                Cliente c = contatos.get(i);
                                Log.d("[IFMG]", "finalizando baixar defeitos" + c.getCliCodigo());
                            }


                            Intent i=new Intent(getApplicationContext(), PrincipalActivity.class);
                            startActivity(i);
                            //      }else{
                            //        }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Falha na conexão!!!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        }).start();
    }
    /**
     * Método criado para receber, interpretar o obj json e retornar uma string formatada do mesmo
     *
     * @param json
     * @return string formatada
     */
    public String interpretaJSON_Aritimetica(JSONObject json) {
        String texto = "";
        Log.d("ENTROU", json.toString());
        try {
           // Log.d("sad", json.get("ven"));
            JSONArray nani=(JSONArray) json.get("vendas");
            Log.d("sad", nani.toString());
            Log.d("sad2",nani.getJSONObject(0).getString("venCodigo"));
//            JSONObject tata=(JSONObject) json.get("vendas");
  //          Log.d("sad", "interpretaJSON_Aritimetica: "+tata.toString());
         //   Log.d("[IFMG]", "jsonObject: "+tata.getString("cliNome"));

           // Log.d("[IFMG]", "json.get: "+((JSONObject) json.get("cliente")).getString("cliCPF"));
            //Printando na string os elementos identificados nela

//                if(linhas.length()>0) {
//            List<Venda> contatos = bd.findAllVenda();
//
//            JSONObject tata1=(JSONObject) json.get("erro");
//
////            texto += tata1.getString("erro");
////            if(contatos.size()<1) {
            for(int i=0;i<nani.length();i++) {
                Venda c = new Venda();
                c.setVenCodigo(nani.getJSONObject(i).getInt("venCodigo"));
                c.setVenData(nani.getJSONObject(i).getString("venData"));
                c.setVenFormaPagamento(nani.getJSONObject(i).getString("venFormaPag"));
                c.setVenValorTotal((float) nani.getJSONObject(i).getDouble("venValorTotal"));
                c.setVenStatus(nani.getJSONObject(i).getString("venStatus"));
                c.setVen_cliCodigo(nani.getJSONObject(i).getInt("cliCodigo"));
                bd.saveVenda(c);
              //  Log.d("[IMFG]", "interpretaJSON_Aritimetica: " + contatos.size());
            }
//                    // linhas =;//pega vetor do json recebido
//                    JSONObject l = (JSONObject) linhas.get(1);
//                c.setIdProduto_Venda(tata.getInt("proCodigo"));
//                c.setPro_venCodigo(tata.getInt("proNome"));
//                c.setVen_proCodigo(tata.getInt("proNome"));
//                c.setProQtdProduto(tata.getInt("asd"));
//                c.setProValorUnitario(tata.getDouble("sss"));

//                c.setCliCodigo(tata.getInt("cliCodigo"));
//                Log.d("CliCodigo", c.getCliCodigo() + "");
//                c.setCliNome(tata.getString("cliNome"));
//                c.setCliCPF(tata.getString("cliCPF"));
//                c.setCliTelefone(tata.getString("cliTelefone"));
//                c.setCliEmail(tata.getString("cliEmail"));
//                c.setCliSaldo(tata.getDouble("cliSaldo"));


           //     bd.saveProduto_Venda(c);
//           }
//               //     c.setCliNome(linhas.getString("cliNome"));
//
//                    Log.d("[IFMG]", "sexxxx: " + c.getCliCodigo());
//
//
////                if (linhas.length() > 0) {//verifica we exite algum registro recebido do servidor
////                    for (int i = 0; i < linhas.length(); i++) {
//                    //   cliente
//                    //             JSONObject linha = (JSONObject) linhas.get(i);
//                    //         texto +=  linha.getBoolean("sincronizacao");
//                    //           texto += linha.getJSONArray("cliente");
//
////                        texto += "Soma: " + linha.getInt("soma") + ", ";
////                        texto += "Subtracao: " + linha.getInt("subtracao") + ", ";
////                        texto += "Multiplicacao: " + linha.getString("multiplicacao") + ".\n";
//                    Log.d("[IFMG]", "resultado: " + json);
////                    }
//                }

//                }
        } catch (Exception e) {//JSONException e) {
            Log.d("ERROU", texto);
            e.printStackTrace();
        }
        return texto;
    }


    public String interpretaJSON_Aritimetica1(JSONObject json) {
        String texto = "";
        Log.d("ENTROU", json.toString());
        try {

            JSONArray nani=(JSONArray) json.get("produto_venda");
            //   Log.d("[IFMG]", "jsonObject: "+tata.getString("cliNome"));

            // Log.d("[IFMG]", "json.get: "+((JSONObject) json.get("cliente")).getString("cliCPF"));
            //Printando na string os elementos identificados nela

//                if(linhas.length()>0) {
 //           List<Produto_Venda> contatos = bd.findAllProduto_Venda();

   //         JSONObject tata1=(JSONObject) json.get("erro");

//            JSONObject tata=(JSONObject) json.get("vendas");
            //          Log.d("sad", "interpretaJSON_Aritimetica: "+tata.toString());
            //   Log.d("[IFMG]", "jsonObject: "+tata.getString("cliNome"));

            // Log.d("[IFMG]", "json.get: "+((JSONObject) json.get("cliente")).getString("cliCPF"));
            //Printando na string os elementos identificados nela

//            Log.d("sad", nani.toString());
//            Log.d("sad2",nani.getJSONObject(0).getString("prvCodigo"));
//            JSONObject tata=(JSONObject) json.get("vendas");
            //          Log.d("sad", "interpretaJSON_Aritimetica: "+tata.toString());
            //   Log.d("[IFMG]", "jsonObject: "+tata.getString("cliNome"));

            // Log.d("[IFMG]", "json.get: "+((JSONObject) json.get("cliente")).getString("cliCPF"));
            //Printando na string os elementos identificados nela

//                if(linhas.length()>0) {
//            List<Venda> contatos = bd.findAllVenda();
//
//            JSONObject tata1=(JSONObject) json.get("erro");
//
////            texto += tata1.getString("erro");
////            if(contatos.size()<1) {
//            for(int i=0;i<nani.length();i++) {
//                Venda c = new Venda();
//                c.setVenCodigo(nani.getJSONObject(i).getInt("venCodigo"));
//                c.setVenData(nani.getJSONObject(i).getString("venData"));
//                c.setVenFormaPagamento(nani.getJSONObject(i).getString("venFormaPag"));
//                c.setVenValorTotal((float) nani.getJSONObject(i).getDouble("venValorTotal"));
//                c.setVenStatus(nani.getJSONObject(i).getString("venStatus"));
//                c.setVen_cliCodigo(nani.getJSONObject(i).getInt("cliCodigo"));
//                bd.saveVenda(c);
//                //  Log.d("[IMFG]", "interpretaJSON_Aritimetica: " + contatos.size());
//            }
//                if(linhas.length()>0) {
//            List<Venda> contatos = bd.findAllVenda();
//
//            JSONObject tata1=(JSONObject) json.get("erro");
//
////            texto += tata1.getString("erro");
////            if(contatos.size()<1) {
            for(int i=0;i<nani.length();i++) {
               Produto_Venda c = new Produto_Venda();
//              COLOCAR OS NOMES CERTOS
                c.setIdProduto_Venda(nani.getJSONArray(0).getJSONObject(i).getInt("prvCodigo"));
                c.setPro_venCodigo(nani.getJSONArray(0).getJSONObject(i).getInt("proCodigo"));
                c.setVen_proCodigo(nani.getJSONArray(0).getJSONObject(i).getInt("venCodigo"));
                //c.setProValorUnitario(nani.getJSONArray(0).getJSONObject(i).getDouble("venValorTotal"));
                c.setProQtdProduto(nani.getJSONArray(0).getJSONObject(i).getInt("prv_qtdProduto"));
                bd.saveProduto_Venda(c);
            }

//            texto += tata1.getString("erro");
//            if(contatos.size()<1) {
//            Produto_Venda c = new Produto_Venda();
//            c.setIdProduto_Venda(tata.getInt("prvCodigo"));
//            c.setProQtdProduto(tata.getInt("prv_qtdProduto"));
//            c.setPro_venCodigo(tata.getInt("proCodigo"));
//            c.setVen_proCodigo(tata.getInt("venCodigo"));
//            bd.saveProduto_Venda(c);
//            Log.d("[IMFG]", "interpretaJSON_Aritimetica1: "+contatos.size());

//                    // linhas =;//pega vetor do json recebido
//                    JSONObject l = (JSONObject) linhas.get(1);
//                c.setIdProduto_Venda(tata.getInt("proCodigo"));
//                c.setPro_venCodigo(tata.getInt("proNome"));
//                c.setVen_proCodigo(tata.getInt("proNome"));
//                c.setProQtdProduto(tata.getInt("asd"));
//                c.setProValorUnitario(tata.getDouble("sss"));

//                c.setCliCodigo(tata.getInt("cliCodigo"));
//                Log.d("CliCodigo", c.getCliCodigo() + "");
//                c.setCliNome(tata.getString("cliNome"));
//                c.setCliCPF(tata.getString("cliCPF"));
//                c.setCliTelefone(tata.getString("cliTelefone"));
//                c.setCliEmail(tata.getString("cliEmail"));
//                c.setCliSaldo(tata.getDouble("cliSaldo"));


            //     bd.saveProduto_Venda(c);
//           }
//               //     c.setCliNome(linhas.getString("cliNome"));
//
//                    Log.d("[IFMG]", "sexxxx: " + c.getCliCodigo());
//
//
////                if (linhas.length() > 0) {//verifica we exite algum registro recebido do servidor
////                    for (int i = 0; i < linhas.length(); i++) {
//                    //   cliente
//                    //             JSONObject linha = (JSONObject) linhas.get(i);
//                    //         texto +=  linha.getBoolean("sincronizacao");
//                    //           texto += linha.getJSONArray("cliente");
//
////                        texto += "Soma: " + linha.getInt("soma") + ", ";
////                        texto += "Subtracao: " + linha.getInt("subtracao") + ", ";
////                        texto += "Multiplicacao: " + linha.getString("multiplicacao") + ".\n";
//                    Log.d("[IFMG]", "resultado: " + json);
////                    }
//                }

//                }
        } catch (Exception e) {//JSONException e) {
            Log.d("ERROU", texto);
            e.printStackTrace();
        }
        return texto;
    }

    public String interpretaJSON_Aritimetica2(JSONObject json) {
        String texto = "";
        Log.d("ENTROU", json.toString());
        try {
            JSONArray nani=(JSONArray) json.get("produto");
            //   Log.d("[IFMG]", "jsonObject: "+tata.getString("cliNome"));

            // Log.d("[IFMG]", "json.get: "+((JSONObject) json.get("cliente")).getString("cliCPF"));
            //Printando na string os elementos identificados nela

//                if(linhas.length()>0) {
  //          List<Produto> contatos = bd.findAllProduto();

      //      JSONObject tata1=(JSONObject) json.get("erro");

//            texto += tata1.getString("erro");
////            if(contatos.size()<1) {
//            Produto c = new Produto();
//            c.setProCodigo(tata.getInt(""));
//            c.setProNome(tata.getString(""));
//            bd.saveProduto(c);
  //          Log.d("[IMFG]", "interpretaJSON_Aritimetica2: "+contatos.size());

//                    // linhas =;//pega vetor do json recebido
//                    JSONObject l = (JSONObject) linhas.get(1);
//                c.setIdProduto_Venda(tata.getInt("proCodigo"));
//                c.setPro_venCodigo(tata.getInt("proNome"));
//                c.setVen_proCodigo(tata.getInt("proNome"));
//                c.setProQtdProduto(tata.getInt("asd"));
//                c.setProValorUnitario(tata.getDouble("sss"));
            for(int i=0;i<nani.length();i++) {
                Produto c = new Produto();
                //COLOCAR NOMES CERTOS
                c.setProCodigo(nani.getJSONArray(0).getJSONObject(i).getInt("proCodigo"));
                c.setProNome(nani.getJSONArray(0).getJSONObject(i).getString("proNome"));
                c.setProValorVenda(nani.getJSONArray(0).getJSONObject(i).getDouble("proValorVenda"));
                bd.saveProduto(c);
            }
//                c.setCliCodigo(tata.getInt("cliCodigo"));
//                Log.d("CliCodigo", c.getCliCodigo() + "");
//                c.setCliNome(tata.getString("cliNome"));
//                c.setCliCPF(tata.getString("cliCPF"));
//                c.setCliTelefone(tata.getString("cliTelefone"));
//                c.setCliEmail(tata.getString("cliEmail"));
//                c.setCliSaldo(tata.getDouble("cliSaldo"));


            //     bd.saveProduto_Venda(c);
//           }
//               //     c.setCliNome(linhas.getString("cliNome"));
//
//                    Log.d("[IFMG]", "sexxxx: " + c.getCliCodigo());
//
//
////                if (linhas.length() > 0) {//verifica we exite algum registro recebido do servidor
////                    for (int i = 0; i < linhas.length(); i++) {
//                    //   cliente
//                    //             JSONObject linha = (JSONObject) linhas.get(i);
//                    //         texto +=  linha.getBoolean("sincronizacao");
//                    //           texto += linha.getJSONArray("cliente");
//
////                        texto += "Soma: " + linha.getInt("soma") + ", ";
////                        texto += "Subtracao: " + linha.getInt("subtracao") + ", ";
////                        texto += "Multiplicacao: " + linha.getString("multiplicacao") + ".\n";
//                    Log.d("[IFMG]", "resultado: " + json);
////                    }
//                }

//                }
        } catch (Exception e) {//JSONException e) {
            Log.d("ERROU", texto);
            e.printStackTrace();
        }
        return texto;
    }

    public static ProgressDialog gerarDialogIndeterminado(String mensagem, Context activityContexto) {
        ProgressDialog pDialog = new ProgressDialog(activityContexto);
        pDialog.setMessage(mensagem);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        //pDialog.show();
        return pDialog;
    }
}
