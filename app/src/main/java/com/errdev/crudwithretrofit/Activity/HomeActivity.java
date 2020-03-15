package com.errdev.crudwithretrofit.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.errdev.crudwithretrofit.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_addData)
    void ButtonAdd(){
        startActivity(new Intent(HomeActivity.this,AddActivity.class));
    }

    @OnClick(R.id.bt_showData)
    void ButtonShow(){
        startActivity(new Intent(HomeActivity.this, ShowActivity.class));
    }
}
