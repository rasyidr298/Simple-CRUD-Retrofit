package com.errdev.crudwithretrofit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.errdev.crudwithretrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_NimDetail) TextView detNim;
    @BindView(R.id.tvNamaDetail) TextView detNama;
    @BindView(R.id.tvKelasDetail) TextView detKelas;
    @BindView(R.id.tv_SesiDetail) TextView detSesi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String nim = intent.getExtras().getString("nim");
        String nama = intent.getExtras().getString("nama");
        String jur = intent.getExtras().getString("kelas");
        String alam = intent.getExtras().getString("sesi");

        detNim.setText(nim);
        detNama.setText(nama);
        detKelas.setText(jur);
        detSesi.setText(alam);

        //menampilkan tombol kembali
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    //action tombol kembali
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
