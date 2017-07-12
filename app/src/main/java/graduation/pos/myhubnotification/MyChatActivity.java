package graduation.pos.myhubnotification;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyChatActivity extends AppCompatActivity {
    final Context context = this;
    TextView edtChatPrincipal;
    TextView edtChatResposta;
    Button btnChatEnviaMsg;
    private Banco bd;
    String[] armas = new String[]{"Carabina", "Espada do He-Man", "Escudo do Capitão América", "Míssil Teleguiado", "Armadilha de Urso"};
    String[] tamanhos = new String[]{"Pequeno", "Médio", "Grande", "Gigante"};
    int estagio_compra = 0;
    int arma_escolhida = -1;
    int tamanho_escolhido = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat);

        edtChatPrincipal = (TextView) findViewById(R.id.edtChatPrincipal);
        edtChatResposta = (TextView) findViewById(R.id.edtChatResposta);
        btnChatEnviaMsg = (Button) findViewById(R.id.btnChatEnviaMsg);
        bd = new Banco(this);
        edtChatPrincipal.append("Atendente: Olá, "+getIntent().getExtras().getString("Usuario")+"!");

        escolheItem();

        btnChatEnviaMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (estagio_compra == 10) {

                    Intent it = new Intent(context, MyMainActivity.class);
                    startActivity(it);
                }

                if (estagio_compra == 1){

                    if (edtChatResposta.getText().toString().equals("1"))
                        tamanho_escolhido = 0;
                    else if (edtChatResposta.getText().toString().equals("2"))
                        tamanho_escolhido = 1;
                    else if (edtChatResposta.getText().toString().equals("3"))
                        tamanho_escolhido = 2;
                    else if (edtChatResposta.getText().toString().equals("4"))
                        tamanho_escolhido = 3;
                    else
                        tamanho_escolhido = -1;

                    if (tamanho_escolhido == -1) {
                        addMessageChat("\nOpção inválida.");
                        escolheTamanho();

                    }else {
                        addMessageChat("\nVocê escolheu o tamanho: " + tamanhos[tamanho_escolhido]);
                        addMessageChat("\n\n Atendente: Você comprou "+armas[arma_escolhida]+", tamanho "+tamanhos[tamanho_escolhido]);
                        addMessageChat("\n\n Atendente: Obrigado por comprar conosco!");

                        //Chama função para salvar item no banco de dados;
                        bd.inserirArmaBD(armas[arma_escolhida],tamanhos[tamanho_escolhido]);

                        btnChatEnviaMsg.setText("Voltar");
                        estagio_compra = 10;

                        sendPostwithPermission();

                    }

                }


                if (estagio_compra == 0){

                    if (edtChatResposta.getText().toString().equals("1"))
                        arma_escolhida = 0;
                    else if (edtChatResposta.getText().toString().equals("2"))
                        arma_escolhida = 1;
                    else if (edtChatResposta.getText().toString().equals("3"))
                        arma_escolhida = 2;
                    else if (edtChatResposta.getText().toString().equals("4"))
                        arma_escolhida = 3;
                    else if (edtChatResposta.getText().toString().equals("5"))
                        arma_escolhida = 4;
                    else
                        arma_escolhida = -1;

                    if (arma_escolhida == -1) {
                        addMessageChat("\nOpção inválida.");
                        escolheItem();

                    }else {
                        addMessageChat("\nVocê escolheu o item: " + armas[arma_escolhida]);
                        estagio_compra = 1;
                        escolheTamanho();
                    }

                }

            }
        });




    }

    public void escolheItem (){
        edtChatPrincipal.append("\n\nAtendente: O que gostaria de comprar?");
        edtChatPrincipal.append("\n1 - Carabina");
        edtChatPrincipal.append("\n2 - Espada do He-man");
        edtChatPrincipal.append("\n3 - Escudo do Capitão América");
        edtChatPrincipal.append("\n4 - Míssil Teleguiado");
        edtChatPrincipal.append("\n5 - Armadilha de Urso");
    }

    public void escolheTamanho (){
        edtChatPrincipal.append("\n\nAtendente: Qual tamanho de "+armas[arma_escolhida]+" gostaria de comprar?");
        edtChatPrincipal.append("\n1 - Pequeno");
        edtChatPrincipal.append("\n2 - Médio");
        edtChatPrincipal.append("\n3 - Grande");
        edtChatPrincipal.append("\n4 - Gigante");

    }

    public void addMessageChat (String message){
        edtChatPrincipal.append("\n"+message);

    }


    public void sendPostwithPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.INTERNET},
                    123);
        } else {

            try {
                String Result = new MyHttpConnection().execute(getIntent().getExtras().getString("Equipe"),armas[arma_escolhida],tamanhos[tamanho_escolhido]).get();
                Log.e("Server Return:",Result);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    sendPostwithPermission();
                } else {
                    Log.d("TAG", "Internet Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }

}
