package com.example.qurannavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_navigate;
    EditText edt_number;
    RadioGroup type_group;
    RadioGroup language_group;

    ImageView git_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        type_group = findViewById(R.id.type_group);
        language_group = findViewById(R.id.language_group);
        edt_number = findViewById(R.id.edt_number);
        btn_navigate = findViewById(R.id.btn_navigate);
        git_link=findViewById(R.id.git_link);
        btn_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedTypeId = type_group.getCheckedRadioButtonId();
                RadioButton selectedType = findViewById(selectedTypeId);
                String selectedTypeText = selectedType.getText().toString();
                int selectedLangId = language_group.getCheckedRadioButtonId();
                RadioButton selectedLang = findViewById(selectedLangId);
                String selectedLangText = selectedLang.getText().toString();
                String number = edt_number.getText().toString();
                if(number.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Number Field Must No be Null", Toast.LENGTH_SHORT).show();
                    return;
                }
                int nmb = Integer.parseInt(number);
                if(selectedTypeText.equals("Para") && (nmb<1 || nmb > 30))
                {
                    Toast.makeText(MainActivity.this, "Para Number Must be in Range (1-30)", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedTypeText.equals("Surah") && (nmb<1 || nmb > 114))
                {
                    Toast.makeText(MainActivity.this, "Surah Number Must be in Range (1-114)", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this,QuranNavigation.class);
                intent.putExtra("Type",selectedTypeText);
                intent.putExtra("Language",selectedLangText);
                intent.putExtra("Number",number);
                startActivity(intent);
            }
        });

        git_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("https://github.com/AqibAmin/QuranNavigation/commits/main");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });
    }
}