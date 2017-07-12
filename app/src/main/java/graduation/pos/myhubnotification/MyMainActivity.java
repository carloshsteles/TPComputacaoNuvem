package graduation.pos.myhubnotification;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyMainActivity extends AppCompatActivity {

    final Context context = this;
    TextView txtBemVindo;
    Button btnChatCompraArmas;
    Button btnListadeArmas;

    String[] usuario = new String[2];
    private Banco bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_page);
        bd = new Banco(this);

        txtBemVindo = (TextView) findViewById(R.id.txtBemVindo);

        btnChatCompraArmas = (Button) findViewById(R.id.btnChatCompraArmas);
        btnListadeArmas = (Button) findViewById(R.id.btnListadeArmas);

        usuario = bd.getUsuarioBD();

        if (usuario[0] == "empty") {
            Intent it = new Intent(context, MyRegistrationFormActivity.class);
            startActivity(it);
        }

        txtBemVindo.setText("Bem Vindo "+usuario[0]+" da Equipe "+usuario[1]);

        btnChatCompraArmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent it = new Intent(context, MyChatActivity.class);
            it.putExtra("Usuario", usuario[0]);
            it.putExtra("Equipe", usuario[1]);
            startActivity(it);

            }
        });

        btnListadeArmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(context, MyListGunsActivity.class);
                startActivity(it);

            }
        });







    }
}
