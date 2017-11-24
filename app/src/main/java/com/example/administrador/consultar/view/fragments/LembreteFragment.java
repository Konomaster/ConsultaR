package com.example.administrador.consultar.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrador.consultar.R;
import com.example.administrador.consultar.utils.FileUtils;
import com.example.administrador.consultar.utils.IOUtils;
import com.example.administrador.consultar.utils.NotificationUtil;
import com.example.administrador.consultar.utils.SDCardUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LembreteFragment extends AppCompatActivity {

    private EditText etNomeArquivoW;
    private EditText etTextoW;
    private EditText etNomeArquivoR;
    private EditText etTextoR;
    private WebView webView;
    private Button btInserir;
    private Button btLer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //extraio o objeto view para trabalhar com demais componentes no fragment
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lembrete);
        etNomeArquivoW = (EditText) findViewById(R.id.etNomeArquivoW);
        webView = (WebView) findViewById(R.id.webView);
        etTextoW = (EditText) findViewById(R.id.etTextoArquivoW);
        etNomeArquivoR = (EditText) findViewById(R.id.etNomeArquivoR);
//       etTextoR= (EditText)findViewById(R.id.etTextoArquivoR);
        btInserir = (Button) findViewById(R.id.btnInserir);
        btLer = (Button) findViewById(R.id.btnLerArquivo);

//        btInserir.setOnClickListener((View.OnClickListener) getContext());
//        btLer.setOnClickListener((View.OnClickListener) getContext());
    }

//    public void onClicGravar(View v) {
//        //Escrita interna na pasta da aplicação
//        gravaArquivoMemoriaExterna(
//                context,
//                etTextoW.getText().toString(),
//                etNomeArquivoW.getText().toString()
//        );
//
////        //Escrita interna na pasta da aplicação
////
//        gravaArquivoMemoriaInterna(
//                getActivity().getApplicationContext(),
//                etTextoW.getText().toString(),
//                etNomeArquivoW.getText().toString()
//        );
//
//        int id = 2;
//        //seta a activity a ser chamada quando for clicado no evento a barra de status do android
//        Intent intent = new Intent(getContext(), LembreteFragment.class);
//        //seta a mensagem que será exibida na activity
//        //prepara a lista de mensagens
//        List<String> list = new ArrayList<>();
//        list.add(etTextoW.getText().toString());
//        //seta o titulo do alerta
//        String contentTitle = "Arquivo de titulo: n" + etNomeArquivoW.getText().toString();
//        String contentText = String.format("Você possui %s novas mensagens", list.size());
//        NotificationUtil.createBig(getContext(), intent, contentTitle, contentText, list, id);
//    }
//
//    public void onClicLer(View v) {
//        //Leitura externa da pasta da aplicação
//        etTextoR.setText(
//                lerArquivoMemoriaExterna(
//                        context,
//                        etNomeArquivoR.getText().toString()
//                )
//        );
//
//        //Leitura interna na pasta da aplicação
//
//
//        String html =
//                "<html>" +
//                        "<head>" +
//                        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=latin1\">\n" +
//                        "<meta charset=\"latin1\">\n" +
//                        "<title>Teste Web</title>" +
//                        "</head>" +
//
//                        "<body>" +
//                        lerArquivoMemoriaInterna(getContext(), etNomeArquivoR.getText().toString()) +
//                        "</body>" +
//                        "</html>";
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setSupportZoom(true);
//        //seta a os dados da página
//        webView.loadData(html, "text/html", null);
//    }

    //-------------------------------------------------------------------------------------------
    //Inicio dos métodos de leitura e gravação

    //Escrita interna na pasta da aplicação
    public void gravaArquivoMemoriaInterna(Context context, String texto, String nomeArquivo) {
        //cria arquivo em /data/data/exemplo.exemplogravaarquivo/files/
        File f = FileUtils.getFile(context, nomeArquivo);

        IOUtils.writeString(f, texto);
        Log.d("IFMG", "Arquivo gravado:" + f.getAbsolutePath());
    }

    //Escrita externa da pasta da aplicação
    public void gravaArquivoMemoriaExterna(Context context, String texto, String nomeArquivo) {
        //cria arquivo privado nos downloads do sd card
        File f = SDCardUtils.getPublicFile(nomeArquivo, Environment.DIRECTORY_PICTURES);
        IOUtils.writeString(f, texto);
        Log.d("IFMG", "Arquivo gravado na pasta Downloads: " + f.getAbsolutePath());
    }


    //Leitura interna na pasta da aplicação
    public String lerArquivoMemoriaInterna(Context context, String nomeArquivo) {
        Log.d("IFMG", "Abreindo arquivo gravado: " + nomeArquivo);
        String resp = null;
        try {
            resp = FileUtils.readFile(context, nomeArquivo, "UTF-8");
        } catch (IOException x) {
            Log.e("IFMG", "Erro no sistema: " + x.getMessage());
        }
        return resp;
    }

    //Leitura externa da pasta da aplicação
    public String lerArquivoMemoriaExterna(Context context, String nomeArquivo) {
        Log.d("IFMG", "Abrindo arquivo gravado: " + nomeArquivo);
        String resp = null;
        try {
            Scanner f = new Scanner(SDCardUtils.getSdCardFile(context, Environment.DIRECTORY_PICTURES, nomeArquivo));
            resp = f.nextLine();
        } catch (IOException x) {
            Log.e("IFMG", "Erro no sistema: " + x.getMessage());
        }
        return resp;
    }

    public void onClickBtnInserir(View view) {
        //Escrita interna na pasta da aplicação
//        gravaArquivoMemoriaExterna(
//                getApplicationContext(),
//                etTextoW.getText().toString(),
//                etNomeArquivoW.getText().toString()
//        );

        //Escrita interna na pasta da aplicação

        gravaArquivoMemoriaInterna(
                getApplicationContext(),
                etTextoW.getText().toString(),
                etNomeArquivoW.getText().toString()
        );

        int id = 2;
        //seta a activity a ser chamada quando for clicado no evento a barra de status do android
        Intent intent = new Intent(this, LembreteFragment.class);
        //seta a mensagem que será exibida na activity
        //prepara a lista de mensagens
        List<String> list = new ArrayList<>();
        list.add(etTextoW.getText().toString());
        //seta o titulo do alerta
        String contentTitle = "Arquivo de titulo: n" + etNomeArquivoW.getText().toString();
        String contentText = String.format("Você possui %s novas mensagens", list.size());
        NotificationUtil.createBig(this, intent, contentTitle, contentText, list, id);
    }

    public void onClickBtnLer(View view) {
        //Leitura externa da pasta da aplicação
//        etTextoR.setText(
//                lerArquivoMemoriaExterna(
//                        getApplicationContext(),
//                        etNomeArquivoR.getText().toString()
//                )
//        );

        //Leitura interna na pasta da aplicação

        webView.loadUrl("www.google.com.br");
        String html =
                "<html>" +
                        "<head>" +
                        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=latin1\">\n" +
                        "<meta charset=\"latin1\">\n" +
                        "<title>Teste Web</title>" +
                        "</head>" +

                        "<body>" +
                        lerArquivoMemoriaInterna(this, etNomeArquivoR.getText().toString()) +
                        "</body>" +
                        "</html>";
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        //seta a os dados da página
        webView.loadData(html, "text/html", null);
    }






    //Fim dos Métodos de Leitura e Gravação
    //-------------------------------------------------------------------------------------------
}
