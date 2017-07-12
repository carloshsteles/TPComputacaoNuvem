package graduation.pos.myhubnotification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateus Dias on 13/07/2015.
 */
public class Banco extends SQLiteOpenHelper {
    public static String NOME_BD = "BD";
    public static String COL_USUARIO = "usuario";
    public static String COL_EQUIPE = "equipe";
    public static String COL_ARMA = "arma";
    public static String COL_TAMANHO = "tamanho";

    public static int VERSAO = 2;

    public Banco(Context context) {
        super(context, NOME_BD, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS registro (" +
                "id_usuario INTEGER PRIMARY KEY autoincrement," +
                COL_USUARIO +" varchar(45) NOT NULL, " +
                COL_EQUIPE + " varchar(45) NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS armas (" +
                "id_arma INTEGER PRIMARY KEY autoincrement," +
                COL_ARMA +" varchar(45) NOT NULL, " +
                COL_TAMANHO + " varchar(45) NOT NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public boolean excluirUsuarioBD(String usuario){
        SQLiteDatabase sqlDB = getWritableDatabase();
        int result = sqlDB.delete("registro", "usuario=?", new String[]{usuario});
        //sqlDB.execSQL("delete usuarios_tbl where usuario = " + usuario);
        sqlDB.close();
        if (result > 0) {
            return true;
        }
        return false;


    }

    public boolean atualizarUsuarioBD(String usuario, String senha, String nome){
        SQLiteDatabase sqlDB = getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("senha", senha);
        valores.put("nome_completo", nome);

        int result = sqlDB.update("usuarios_tbl", valores, "usuario=?", new String[]{usuario});
        sqlDB.close();
        if (result>0) {
            return true;
        }
        return false;

    }

    public List<String> getListaUsuariosBD() {
        SQLiteDatabase sqlDB = getReadableDatabase();
        List<String> usuarios = new ArrayList<String>();
        Cursor cursor = sqlDB.query("usuarios_tbl",
                new String[]{"usuario", "senha", "nome_completo"},
                null, null, null, null, null);

        //Cursor cursorExemplo2 = sqlDB.rawQuery("select * from usuarios_tbl",null);

        while (cursor.moveToNext()) {
            usuarios.add(cursor.getString(0) + " - " + cursor.getString(1) + " - " + cursor.getString(2));
        }
        //Lembre-se de sempre fechar o cursor
        cursor.close();
        sqlDB.close();

        return usuarios;
    }

/**********************************************************************/
/* Função que cadastra usuário do aplicativo e escreve em um tópico */
/**********************************************************************/
    public boolean inserirUsuarioBD(String usuario, String equipe) {
        SQLiteDatabase sqlDB = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COL_USUARIO, usuario);
        valores.put(COL_EQUIPE, equipe);
        long result = sqlDB.insert("registro", null, valores);
        sqlDB.close();
        if (result!=-1) {
            FirebaseMessaging.getInstance().subscribeToTopic(equipe);
            return true;
        }
        return false;

    }


/********************************************************/
/* Função que retorna o usuário cadastrado do aplicativo*/
/********************************************************/
    public String[] getUsuarioBD() {
        SQLiteDatabase sqlDB = getReadableDatabase();
        String[] usuario = new String[]{"empty", "empty"};

        Cursor cursor = sqlDB.query("registro",
            new String[]{COL_USUARIO, COL_EQUIPE},
            null, null, null, null, null);

        while (cursor.moveToNext()) {
            usuario[0] = cursor.getString(0);
            usuario[1] = cursor.getString(1);
        }
        cursor.close();
        sqlDB.close();

        return usuario;
    }


/**********************************************************************/
/* Função que insere arma no banco                                    */
/**********************************************************************/
    public boolean inserirArmaBD(String arma, String tamanho) {
        SQLiteDatabase sqlDB = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COL_ARMA, arma);
        valores.put(COL_TAMANHO, tamanho);
        long result = sqlDB.insert("armas", null, valores);
        sqlDB.close();
        if (result!=-1) {
            return true;
        }
        return false;

    }

/**********************************************************************/
/* Função que retorna lista de armas do banco                         */
/**********************************************************************/

    public List<String> getListaArmasBD() {
        SQLiteDatabase sqlDB = getReadableDatabase();
        List<String> armas = new ArrayList<String>();
        Cursor cursor = sqlDB.query("armas",
                new String[]{"arma", "tamanho"},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            armas.add(cursor.getString(0) + " - " + cursor.getString(1));
        }
        //Lembre-se de sempre fechar o cursor
        cursor.close();
        sqlDB.close();

        return armas;
    }





}
