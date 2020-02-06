package cz.mikymarvin.ugovakarta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class support extends AppCompatActivity {

    Button zpet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        zpet = findViewById(R.id.button_zpet);

        zpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(support.this, uset_blog.class);
                startActivity(i);
            }
        });



    }


}
