package graduation.pos.myhubnotification;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MyRegistrationFormActivity extends AppCompatActivity {
    final Context context = this;
    String[] opcoes = new String[]{"Papaleguas", "Coiote"};
    EditText edtUsuario;
    Spinner spinnerTime;
    Button btnCadastrar;
    private Banco bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        spinnerTime = (Spinner) findViewById(R.id.spinnerTime);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        bd = new Banco(this);

        //Criando um spinner simples
        //simple_spinner_item = layout do item selecionado
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opcoes);
        //simple_spinner_dropdown_item = layout do spinner com item fechado
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(spinnerAdapter);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtUsuario.getText().length() == 0)
                    Toast.makeText(getApplicationContext(), "O usuário não pode ser nulo.", Toast.LENGTH_SHORT).show();

                else {

                    //Chama função para inserir usuário (usuario, equipe) no banco e se increve em um topico, deve retornar um booleano.
                    if (bd.inserirUsuarioBD(edtUsuario.getText().toString(),spinnerTime.getSelectedItem().toString())) {
                        Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(context, MyMainActivity.class);
                        startActivity(it);

                    }else
                        Toast.makeText(getApplicationContext(), "Erro. Cadastro não realizado.", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    public boolean CadastraUsuario(){

        //chamar API para inserir usuário no banco.
        //FirebaseMessaging.getInstance().subscribeToTopic(spinnerTime.getSelectedItem().toString());
        return true;

    }





}
