package com.example.administrador.consultar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.consultar.beans.Cliente;
import com.example.administrador.consultar.servicos.servixos_all;
import com.example.administrador.consultar.sinc.JSONDados;
import com.example.administrador.consultar.sinc.JSONParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static com.example.administrador.consultar.sinc.JSONDados.URL_SERVICO2;

//import static com.example.fernando.menudeslisante.sinc.JSONDados.URL_SERVICO1;

public class LoginActivity extends AppCompatActivity {

    private EditText etN1;
    private servixos_all bd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etN1 = (EditText) findViewById(R.id.editText4);

        bd = new servixos_all(getBaseContext());

        if(bd.findAllCliente().size()>0){
            Intent i=new Intent(getApplicationContext(), PrincipalActivity.class);
            startActivity(i);
        }
    }

    ProgressDialog pDialog;

    public void onCLick(View v) {
        //chama o metodo de requisição passando o json como parametro e a URL do webservice
        requisitaPost(
                JSONDados.geraJsonUsuario(
                        etN1.getText().toString()),
                //Arrumar este o host
                URL_SERVICO2
        );
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

                            if(resp.equals("true")) {
                                List<Cliente> contatos = bd.findAllCliente();
                                Log.d("Tamanho Array", contatos.size() + "");
                                String texto = "";

                                for (int i = 0; i < contatos.size(); i++) {
                                    Cliente c = contatos.get(i);
                                    Log.d("[IFMG]", "finalizando baixar defeitos" + c.getCliCodigo());
                                }
                                Intent i=new Intent(getApplicationContext(), SplashActivity.class);
                                startActivity(i);
                            }else{
                                    Toast.makeText(getBaseContext(), "Erro ao logar", Toast.LENGTH_SHORT).show();
                                }
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
        Log.d("ENTROU", ""+json.toString().length());
        if(json.toString().length()==19){
            texto="false";
        }
        else{
            texto="true";
        }
        try {
            JSONObject tata=(JSONObject) json.get("cliente");
            Log.d("[IFMG]", "jsonObject: "+tata.getString("cliNome"));
            Log.d("[IFMG]", "interpretaJSON_Aritimetica: "+tata.toString());

            Log.d("[IFMG]", "json.get: "+((JSONObject) json.get("cliente")).getString("cliCPF"));
            //Printando na string os elementos identificados nela

//                if(linhas.length()>0) {
            List<Cliente> contatos = bd.findAllCliente();

//            JSONObject tata1=(JSONObject) json.get("erro");

  //          texto += tata1.getString("erro");
        if(contatos.size()<1) {
            Cliente c = new Cliente();
//                    // linhas =;//pega vetor do json recebido
//                    JSONObject l = (JSONObject) linhas.get(1);
            c.setCliCodigo(tata.getInt("cliCodigo"));
            Log.d("CliCodigo", c.getCliCodigo() + "");
            c.setCliNome(tata.getString("cliNome"));
            c.setCliCPF(tata.getString("cliCPF"));
            c.setCliTelefone(tata.getString("cliTelefone"));
            c.setCliEmail(tata.getString("cliEmail"));
            c.setCliSaldo(tata.getDouble("cliSaldo"));
            bd.saveCliente(c);
        }
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
