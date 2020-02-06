package cz.mikymarvin.ugovakarta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class uset_blog extends AppCompatActivity {

    Button odhlasitse, kontakt, oaplikaci;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uset_blog);

        kontakt = findViewById(R.id.kontakt_a_podpora_button);
        odhlasitse = findViewById(R.id.odhlasit_se_button);
        oaplikaci = findViewById(R.id.o_aplikaci_button);


        prefs = getApplicationContext().getSharedPreferences("ugo", 0);
        String ugoCard = prefs.getString("ugoCard", null);
        String name = prefs.getString("name", null);


        TextView ugoCard_view_uset = findViewById(R.id.user_number_card);
        TextView ugoName_view_uset = findViewById(R.id.user_name_card);

        ugoCard_view_uset.setText(ugoCard);
        ugoName_view_uset.setText(name);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.item_down3);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.item_down1:
                        startActivity(new Intent(getApplicationContext()
                                , scrap_blog.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.item_down2:
                        startActivity(new Intent(getApplicationContext()
                                , Home.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.item_down3:
                        startActivity(new Intent(getApplicationContext()
                                , uset_blog.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

        odhlasitse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(uset_blog.this, Login.class);
                startActivity(intToMain);
            }
        });

        kontakt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(uset_blog.this, support.class);
                startActivity(intToMain);
            }
        });

        oaplikaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(uset_blog.this, WelcomeSlide.class);
                startActivity(i);
            }
        });

    }
}
