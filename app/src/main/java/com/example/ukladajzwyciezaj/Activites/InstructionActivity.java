package com.example.ukladajzwyciezaj.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.ukladajzwyciezaj.R;
import com.example.ukladajzwyciezaj.UI.ListInstructionAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class InstructionActivity extends AppCompatActivity {

    ListInstructionAdapter adapterCards;
    ListInstructionAdapter adapterFunctionaly;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        adapterCards = getListInstructionAdapter(this.getResources().openRawResource(R.raw.instruction_cards));
        adapterFunctionaly = getListInstructionAdapter(this.getResources().openRawResource(R.raw.functionality_instruction));
    }

    public void OnClickButtonCards(View v){
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapterCards);
    }

    public void OnClickButtonFunctionaly(View v){
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapterFunctionaly);
    }

    public ListInstructionAdapter getListInstructionAdapter(InputStream inputStream){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<Integer> picture = new ArrayList<>();

        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] actualValue = line.split(";");
            stringBuilder.append(line);
            if (actualValue[0].equals("----name_grafic----")) {
                continue;
            }
            name.add(actualValue[1]);
            description.add(actualValue[2]);
            picture.add(getResources().getIdentifier(actualValue[0], "drawable", getPackageName()));
        }

        int[] pictureCards = new int[picture.size()];
        for (int j=0; j<description.size(); j++){
            pictureCards[j] = picture.get(j);
        }

        ListInstructionAdapter adapter = new ListInstructionAdapter(this, pictureCards, description.toArray(new String[description.size()]), name.toArray(new String[name.size()]));
        return adapter;
    }

}

