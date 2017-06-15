package fr.formation.tp12;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fr.formation.tp12.database.datasource.DataSource;
import fr.formation.tp12.database.modele.User;

import static android.R.layout.simple_list_item_1;

public class Principale extends AppCompatActivity {

    DataSource<User> dataSource;
    FloatingActionButton bouton1;
    ListView liste;
    List<String> noms = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    // il faut commencer a la version 1 sinon on appelle une db null
    private int versionDB = 1; // Permet de detruire la base de données SQLite si on incrémente la version

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);

        bouton1 = (FloatingActionButton) findViewById(R.id.fab);
        liste = (ListView) findViewById(R.id.list);

        adapter = new ArrayAdapter<String>(Principale.this,
                simple_list_item_1, noms);
        liste.setAdapter(adapter);

        bouton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principale.this,Activity2.class);
                startActivityForResult(intent, 2);
            }
        });

        //refresh();

        // Create or retrieve the database
        /*try {
            dataSource = new DataSource<>(this, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // open the database
        //openDB();

        // Insert a new record
        // -------------------
        /*User user = new User();
        user.setNom("Tintin");
        try {
            insertRecord(user);
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // update that line
        // ----------------
        /*try {
            user.setNom("Bidochon");
            updateRecord(user);
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // Query that line
        // ---------------
        //queryTheDatabase();

        // And then delete it:
        // -------------------
        //deleteRecord(user);
        //refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //openDB();
        refresh();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //closeDB();
    }


    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (dataSource == null) {
                dataSource = new DataSource<>(this, User.class, versionDB);
                dataSource.open();
            }
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        try {
            dataSource.close();
        } catch (Exception e) {
            // Traiter le cas !
            e.printStackTrace();
        }
    }


    protected long insertRecord(User user) throws Exception {

        // Insert the line in the database
        long rowId = dataSource.insert(user);

        // Test to see if the insertion was ok
        if (rowId == -1) {
            Toast.makeText(this, "Error when creating an User",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "User created and stored in database",
                    Toast.LENGTH_LONG).show();
        }
        return rowId;
    }

    /**
     * * Update a record
     *
     * @return the updated row id
     */
    protected long updateRecord(User user) throws Exception {

        int rowId = dataSource.update(user);

        // test to see if the insertion was ok
        if (rowId == -1) {
            Toast.makeText(this, "Error when updating an User",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "User updated in database", Toast.LENGTH_LONG)
                    .show();
        }
        return rowId;
    }

    /*private void deleteRecord(User user) {
        long rowId = dataSource.delete(user);
        if (rowId == -1) {
            Toast.makeText(this, "Error when deleting an User",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "User deleted in database", Toast.LENGTH_LONG)
                    .show();
        }
    }*/

    /**
     * Query a the database
     */
    /*private void queryTheDatabase() {
        List<User> users = dataSource.readAll();
        displayResults(users);
    }*/

    /*private void displayResults(List<User> users) {

        int count = 0;
        for (User user : users) {
            Toast.makeText(
                    this,
                    "Utilisateur :" + user.getNom() + "("
                            + user.getId() + ")", Toast.LENGTH_LONG).show();
            count++;
        }
        Toast.makeText(this,
                "The number of elements retrieved is " + count,
                Toast.LENGTH_LONG).show();

    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 2) {

            String flux = data.getStringExtra("utilisateur"); // Tester si pas null ;-)
            User utilisateur = new Gson().fromJson(flux, User.class);

            try {
                dataSource.insert(utilisateur);
            } catch (Exception e) {
                // Que faire :-(
                e.printStackTrace();
            }

            // Indiquer un changement au RecycleView
            refresh();
        }

    }
    public void refresh() {
        List<User> users = dataSource.readAll();
        noms.clear();
        int count = 0;
        for(User user : users) {
            noms.add(count, user.getNom());
            count++;
        }

        adapter.notifyDataSetChanged();
    }
}

