package com.example.administrador.consultar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrador.consultar.servicos.servixos_all;
import com.example.administrador.consultar.view.fragments.AActivity;
import com.example.administrador.consultar.view.fragments.FragmentHistorico;
import com.example.administrador.consultar.view.fragments.FragmentPendencia;
import com.example.administrador.consultar.view.fragments.FragmentPerfil;
import com.example.administrador.consultar.view.fragments.SobreFragement;
import com.example.administrador.consultar.view.fragments.WelcomeFragment;
import com.example.administrador.consultar.view.fragments.ajuda_fragment;

//import com.example.fernando.menudeslisante.view.fragments.Opcao3Fragment;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ProgressDialog pDialog;
    servixos_all bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        bd = new servixos_all(this);
//        Intent i=new Intent(getApplicationContext(), LoginActivity.class);
//        startActivity(i);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView nomeCliente=(TextView) navigationView.getHeaderView(0).findViewById(R.id.tvConsultaR);
        nomeCliente.setText(bd.findAllCliente().get(0).getCliNome());
//        requisitaPost(
//                JSONDados.geraJsonTeste("Weverton"),
//                JSONDados.URL_SERVICO2
//        );
//        //fixa o layout vertical
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //seta o fragment inicial

        invalidateOptionsMenu();
        replaceFragment(new WelcomeFragment());
    }
//

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        replaceFragment(new WelcomeFragment());
        ActionBar sup = getSupportActionBar();
        sup.setDisplayUseLogoEnabled(true);
        sup.setTitle("ConsultaR");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //evento do menu  no  canto superiror direito
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_sobre):
                SobreFragement op1= new SobreFragement();
                replaceFragment(op1);
                ActionBar sup = getSupportActionBar();
                sup.setDisplayUseLogoEnabled(true);
                sup.setTitle("Sobre");
              //  Toast.makeText(getApplicationContext(), "Teste Sobre!", Toast.LENGTH_LONG).show();
                break;
            case (R.id.action_ajuda):
                ajuda_fragment op2= new ajuda_fragment();
                replaceFragment(op2);
                ActionBar supo = getSupportActionBar();
                supo.setDisplayUseLogoEnabled(true);
                supo.setTitle("Ajuda");
                //   Toast.makeText(getApplicationContext(), "Teste  Ajuda!", Toast.LENGTH_LONG).show();
                break;

        }


        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_opcao1:
                ActionBar sup = getSupportActionBar();
                sup.setDisplayUseLogoEnabled(true);
                sup.setTitle("Pendências");
                FragmentPendencia op= new FragmentPendencia();
                replaceFragment(op);
                //Toast.makeText(getApplicationContext(), "Teste1!", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_opcao3:
                ActionBar supq = getSupportActionBar();
                supq.setDisplayUseLogoEnabled(true);
                supq.setTitle("Histórico de Compras");
                FragmentHistorico op1= new FragmentHistorico();
                op1.setBD(bd);
                replaceFragment(op1);
                break;

            case R.id.nav_opcao4:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mudar dados");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Desejo mudar meus dados para: ");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "batata@gmail.com");
                emailIntent.setType("message/rfc822");
                startActivity(emailIntent);
                break;

            case R.id.nav_como_funciona:
                FragmentPerfil op3 = new FragmentPerfil();
                replaceFragment(op3);
                ActionBar supo = getSupportActionBar();
                supo.setDisplayUseLogoEnabled(true);
                supo.setTitle("Perfil");
                break;

            case R.id.nav_sair:


                bd.execSQL("DELETE FROM Produto WHERE 1");
                bd.execSQL("DELETE FROM Cliente WHERE 1");
                bd.execSQL("DELETE FROM Venda WHERE 1");
//                Log.d("[IFMG]", "onNavigationItemSelected: "+bd.findAllVenda().size());
                bd.execSQL("DELETE FROM Produto_Venda WHERE 1");
                Toast.makeText(getApplicationContext(), "Saindo!", Toast.LENGTH_LONG).show();
                Intent i=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragments, fragment, "TAG").addToBackStack(null).commit();
    }

//    public void requisitaPost(final String parametroJSON, final String URL_) {
//        //thread obrigatória para realização da requisição pode ser usado com outras formas de thread
//        new Thread(new Runnable() {
//            public void run() {
//                JSONParser jsonParser = new JSONParser();
//                JSONObject json = null;
//                try {
//                    //prepara parâmetros para serem enviados via método POST
//                    HashMap<String, String> params = new HashMap<>();
//                    params.put("sincronizar", parametroJSON);
//
//                    Log.d("[IFMG]", parametroJSON);
//                    Log.d("[IFMG]", "JSON Envio Iniciando...");
//
//                    //faz a requisição POST e retorna o que o webservice REST envoiu dentro de json
//                    json = jsonParser.makeHttpRequest(URL_, "POST", params);
//
//                    Log.d("[IFMG]", " JSON Envio Terminado...");
//
//                    //Mostra no log e retorna o que o json retornou, caso não retornou nulo
//                    if (json != null) {
//                        Log.d("[IFMG]", json.toString());
//                        //return json;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                //{"sincronizacao":[{"cliNome":"Weverton"}]}
//                Log.d("[IFMG]", "finalizando baixar defeitos");
//                Log.d("[IFMG]", "finalizando baixar defeitos: "+ interpretaJSON_AritimeticaSinc(json));
//                //----------------------------------------------
//                //PÓS DOWNLOAD
//                //----------------------------------------------
//
//                //teste para ferificar se o json chegou corretamente e foi interpretado
//                if (json != null) {
//                    //------------------------------------------------------------
//                    //AQUI SE PEGA O JSON RETORNADO E TRATA O QUE DEVE SER TRATADO
//                    //------------------------------------------------------------
//                    final String resp = interpretaJSON_AritimeticaSinc(json);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), "Sincronizado!!!", Toast.LENGTH_LONG).show();
//
//                            // etResp.setText(resp);
//                        }
//                    });
//
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), "Falha na conexão!!!", Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//                }
//
//            }
//        }).start();
//    }
//
//    /**
//     * Método criado para receber, interpretar o obj json e retornar uma string formatada do mesmo
//     *
//     * @param json
//     * @return string formatada
//     */
//    public String interpretaJSON_AritimeticaSinc(JSONObject json) {
//        String texto = "";
//        try {
//            JSONArray linhas = null;
//            //Printando na string os elementos identificados nela
//            try {
//                linhas = (JSONArray) json.get("sincronizar");//pega vetor do json recebido
//                if (linhas.length() > 0) {//verifica we exite algum registro recebido do servidor
//                    for (int i = 0; i < linhas.length(); i++) {
//                        JSONObject linha = (JSONObject) linhas.get(i);
//                        texto += linha.getJSONArray("sincronizacao");
//                        texto += linha.getJSONArray("cliente") + ", ";
//                        texto += linha.getJSONArray("venda") + ".\n";
//                        Log.d("[IFMG]", "resultado: " + texto);
//                    }
//                }
//            } catch (Exception c) {
//                c.printStackTrace();
//                Log.d("[IFMG]", "Erro: " + c.getMessage());
//            }
//        } catch (Exception e) {//JSONException e) {
//            e.printStackTrace();
//        }
//        return texto;
//    }
//
//
//    public static ProgressDialog gerarDialogIndeterminado(String mensagem, Context activityContexto) {
//        ProgressDialog pDialog = new ProgressDialog(activityContexto);
//        pDialog.setMessage(mensagem);
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(true);
//        //pDialog.show();
//        return pDialog;
//    }
}

