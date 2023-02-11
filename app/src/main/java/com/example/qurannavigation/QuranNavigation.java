package com.example.qurannavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuranNavigation extends AppCompatActivity {
    String number;
    String type;
    String language;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;


    ArrayList<Verse> verses;

    TextView txt;
    JSONObject data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_navigation);
        Intent intent=getIntent();
        number=intent.getStringExtra("Number");
        type=intent.getStringExtra("Type");
        language=intent.getStringExtra("Language");
        readJSONFromAsset();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new myRecyclerViewAdapter(verses) ;
        recyclerView.setAdapter(adapter);
    }

    private void readJSONFromAsset() {
        verses=new ArrayList<Verse>();
        try {
            InputStream inputStream = getAssets().open("QuranMetaData.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONArray jsonArray = new JSONArray(builder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String juz = object.getString("juz").toString();
                String surah_number=object.getString("surah_number").toString();
                String text=object.getString("text").toString();
                String translation="";
                if(type.equals("Surah") && surah_number.equals(number) )
                {
                    text=object.getString("text");
                    if(language.equals("Urdu"))
                        translation=object.getString("UrduTranslation");
                    else if(language.equals("English"))
                        translation=object.getString("EnglishTranslation");
                    else if(language.equals("Sindhi"))
                        translation=object.getString("SindhiTranslation");
                    else if(language.equals("Hindi"))
                        translation=object.getString("HindiTranslation");
                    else if(language.equals("Pushto"))
                        translation=object.getString("PushtoTransation");
                    Verse verse=new Verse(text,translation);
                    verses.add(verse);
                }
                if(type.equals("Para") && juz.equals(number) ) {
                    text = object.getString("text");
                    if (language.equals("Urdu"))
                        translation = object.getString("UrduTranslation");
                    else if (language.equals("English"))
                        translation = object.getString("EnglishTranslation");
                    else if (language.equals("Sindhi"))
                        translation = object.getString("SindhiTranslation");
                    else if (language.equals("Hindi"))
                        translation = object.getString("HindiTranslation");
                    else if (language.equals("Pushto"))
                        translation = object.getString("PushtoTransation");
                    Verse verse=new Verse(text,translation);
                    verses.add(verse);
                }
            }
        } catch (IOException e) {
            Log.e("File Error", "Error reading file: " + e.getMessage());
        } catch (JSONException e) {
            Log.e("JSON Error", "Error parsing JSON object: " + e.getMessage());
        }
    }

}