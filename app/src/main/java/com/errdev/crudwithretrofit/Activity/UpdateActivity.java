package com.errdev.crudwithretrofit.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.errdev.crudwithretrofit.Api.MahasiswaAPI;
import com.errdev.crudwithretrofit.Model.Value;
import com.errdev.crudwithretrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.errdev.crudwithretrofit.Api.Server.URL;

public class UpdateActivity extends AppCompatActivity {

    private RadioButton radioButton;
    private ProgressDialog progressDialog;

    @BindView(R.id.rbSesiUp) RadioGroup radioGroupSesiUp;
    @BindView(R.id.rbPagiUp) RadioButton radioButtonPagiUp;
    @BindView(R.id.rbSiangUp) RadioButton radioButtonSiangUp;
    @BindView(R.id.etNimUp) EditText editTextNimUp;
    @BindView(R.id.etNamaUp) EditText editTextNamaUp;
    @BindView(R.id.etKelasUp) EditText editTextKelasUp;
    @OnClick(R.id.btUpdate) void ubah(){
        //membuat progres dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        //mengambil data dari edit text
        String nim = editTextNimUp.getText().toString();
        String nama = editTextNamaUp.getText().toString();
        String kelas = editTextKelasUp.getText().toString();

        int selectedId = radioGroupSesiUp.getCheckedRadioButtonId();
        // mencari id radio button
        radioButton = findViewById(selectedId);
        String sesi = radioButton.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MahasiswaAPI api = retrofit.create(MahasiswaAPI.class);
        Call<Value> call = api.ubah(nim, nama, kelas, sesi);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progressDialog.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(UpdateActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ubah Data");

        Intent intent = getIntent();
        String nim = intent.getStringExtra("nim");
        String nama = intent.getStringExtra("nama");
        String kelas = intent.getStringExtra("kelas");
        String sesi = intent.getStringExtra("sesi");

        editTextNimUp.setText(nim);
        editTextNamaUp.setText(nama);
        editTextKelasUp.setText(kelas);

        if (sesi.equals("Pagi (09.00-11.00 WIB)")) {
            radioButtonPagiUp.setChecked(true);
        } else {
            radioButtonSiangUp.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    //action tombol kembali
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_delete:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Peringatan");
                alertDialogBuilder
                        .setMessage("Apakah Anda yakin ingin mengapus data ini?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String nim = editTextNimUp.getText().toString();
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                MahasiswaAPI api = retrofit.create(MahasiswaAPI.class);
                                Call<Value> call = api.hapus(nim);
                                call.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")) {
                                            Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        t.printStackTrace();
                                        Toast.makeText(UpdateActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
