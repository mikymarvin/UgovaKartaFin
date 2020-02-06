package cz.mikymarvin.ugovakarta;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class Home extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    ImageView slides;
    SharedPreferences prefs;

    Button popupBTN;
    Dialog mDialog; //tady jsem skončil

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prefs = getApplicationContext().getSharedPreferences("ugo", 0);


        popupBTN = findViewById(R.id.popupBTN);
        mDialog = new Dialog(this);

        String ugoCard = prefs.getString("ugoCard", null);
        String name = prefs.getString("name", null);
        TextView ugoCard_view = findViewById(R.id.home_ugoCard);
        TextView ugoCard_view1 = findViewById(R.id.home_ugoCard_number);
        TextView name_view = findViewById(R.id.home_name);


        ugoCard_view.setText(ugoCard);
        ugoCard_view1.setText(ugoCard);
        name_view.setText(name);
        slides = findViewById(R.id.info_slide);

        popupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialog.setContentView(R.layout.popup);

                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.item_down2);
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


        slides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, WelcomeSlide.class);
                startActivity(i);
            }
        });






    }

}