package graduation.pos.myhubnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MyListGunsActivity extends AppCompatActivity {
    private ListView listViewArmas;
    private Banco bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_guns);
        bd = new Banco(this);

        List<String> arrayArmas = bd.getListaArmasBD();

        listViewArmas = (ListView) findViewById(R.id.listViewArmas);
        ArrayAdapter<String> itemsAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayArmas);
        listViewArmas.setAdapter(itemsAdapter);


    }
}
