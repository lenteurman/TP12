package fr.formation.tp12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import fr.formation.tp12.database.datasource.DataSource;
import fr.formation.tp12.database.modele.User;

public class Activity2 extends Principale {

    Button bouton2;
    String texte;
    TextView textview;
    DataSource<User> dataSource;
    List<User> userList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        bouton2 = (Button) findViewById(R.id.button2) ;
        textview = (TextView) findViewById(R.id.textView);

        bouton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edittext = (EditText) findViewById(R.id.editText);
                texte = edittext.getText().toString();

                User user = new User();
                user.setNom(texte);

                /*userList = dataSource.readAll();
                if (!userList.contains(user)) {
                    try {
                        insertRecord(user);
                        edittext.setText("");
                        Intent intent = new Intent(Activity2.this,Principale.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {//on update
                    try {
                        updateRecord(user);
                        edittext.setText("");
                        Intent intent = new Intent(Activity2.this,Principale.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/

                // Transformation en JSON :
                String flux = new Gson().toJson(user);
                Log.d("Utilisateur en JSON", flux);

                // On dépose notre utilisateur jsonné dans l'intent
                Intent resultIntent = new Intent();
                resultIntent.putExtra("utilisateur", flux);
                setResult(2, resultIntent);

                // Bye l'activité
                finish();


            }
        });
    }
}
