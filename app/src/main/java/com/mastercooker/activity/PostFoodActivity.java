package com.mastercooker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mastercooker.R;

public class PostFoodActivity extends AppCompatActivity {

    private Spinner spinner ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_food);
        initView();
    }

    private void initView() {
        spinner = (Spinner) findViewById(R.id.activity_post_food_spinner);
        String[] mItems = getResources().getStringArray(R.array.languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mItems);
        spinner.setAdapter(adapter);

    }

    public void onClickBack(View view) {
        this.finish();
    }
}
