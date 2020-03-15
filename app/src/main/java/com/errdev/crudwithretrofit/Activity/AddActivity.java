package com.errdev.crudwithretrofit.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.errdev.crudwithretrofit.Api.MahasiswaAPI;
import com.errdev.crudwithretrofit.Api.Server;
import com.errdev.crudwithretrofit.Model.Value;
import com.errdev.crudwithretrofit.R;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.errdev.crudwithretrofit.Api.Server.URL;

public class AddActivity extends AppCompatActivity {

    private RadioButton radioButton;
    private ProgressDialog progressDialog;

    @BindView(R.id.rbSesi) RadioGroup radioGroup;
    @BindView(R.id.etNim) EditText etNim;
    @BindView(R.id.etNama) EditText etNama;
    @BindView(R.id.etKelas) EditText etKelas;

    @OnClick (R.id.btInsert) void btInsert(){
        //memuat progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //mengambil data dari edit text
        String nim = etNim.getText().toString();
        String nama = etNama.getText().toString();
        String kelas = etKelas.getText().toString();

        int selectedId = radioGroup.getCheckedRadioButtonId();
        //mencari id radio button
        radioButton = findViewById(selectedId);
        String sesi = radioButton.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MahasiswaAPI api = retrofit.create(MahasiswaAPI.class);
        Call<Value> call = api.daftar(nim, nama, kelas, sesi);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progressDialog.dismiss();
                if (value.equals("1")){
                    Toast.makeText(AddActivity.this,message,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddActivity.this, HomeActivity.class));
                }else {
                    Toast.makeText(AddActivity.this,message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(AddActivity.this,"jaringan errorr !",Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        //menampilkan tombol kembali
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
