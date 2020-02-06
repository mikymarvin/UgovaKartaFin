package cz.mikymarvin.ugovakarta;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class scrap_blog extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ParseAdapter adapter;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap_blog);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.item_down1);
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

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ParseAdapter(parseItems, this);
        recyclerView.setAdapter(adapter);

        Content content = new Content();
        content.execute();
    }


    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(scrap_blog.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(scrap_blog.this, android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
        }



        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String url = "https://www.ugo.cz/blog";

                Document doc = Jsoup.connect(url).get();

                Elements data = doc.select("article.post-item.col-sm-6.col-md-4");
                int size = data.size();
                Log.d("doc", "doc: "+doc);
                Log.d("data", "data: "+data);
                Log.d("size", ""+size);
                for (int i = 0; i < size; i++) {
                    String imgUrl = data.select("a.post-item-img")
                            .select("img")
                            .eq(i)
                            .attr("src");

                    String title = data.select("h2.col-md-12.no-padd.desktop-left-center")
                            .select("a")
                            .eq(i)
                            .text();

                    String detailUrl = data.select("a")
                            .select("a")
                            .eq(i)
                            .attr("href");

                    parseItems.add(new ParseItem(imgUrl, title, detailUrl));
                    Log.d("items", "img: " + imgUrl + " . title: " + title);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}






