package com.errdev.crudwithretrofit.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.errdev.crudwithretrofit.Adapter.RecycleAdapter;
import com.errdev.crudwithretrofit.Api.MahasiswaAPI;
import com.errdev.crudwithretrofit.Api.Server;
import com.errdev.crudwithretrofit.Model.Mahasiswa;
import com.errdev.crudwithretrofit.Model.Value;
import com.errdev.crudwithretrofit.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.errdev.crudwithretrofit.Api.Server.URL;

public class ShowActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ArrayList<Mahasiswa> mahasiswas = new ArrayList<>();
    private RecycleAdapter recycleAdapter;

    @BindView(R.id.myRecyclerview) RecyclerView myRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daftar Mahasiswa");

        recycleAdapter = new RecycleAdapter(this, mahasiswas);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        myRecyclerView.setLayoutManager(mLayoutManager);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myRecyclerView.setAdapter(recycleAdapter);
        loadDataMahasiswa();

        //menampilkan tombol kembali
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataMahasiswa();
    }

    private void loadDataMahasiswa() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MahasiswaAPI api = retrofit.create(MahasiswaAPI.class);
        Call<Value> call = api.view();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                progressBar.setVisibility(View.GONE);
                if (value.equals("1")) {
                    mahasiswas = response.body().getMahasiswaList();
                    recycleAdapter = new RecycleAdapter(ShowActivity.this, mahasiswas);
                    myRecyclerView.setAdapter(recycleAdapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

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
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Cari Nama Mahasiswa");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        myRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MahasiswaAPI api = retrofit.create(MahasiswaAPI.class);
        Call<Value> call = api.search(newText);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                progressBar.setVisibility(View.GONE);
                myRecyclerView.setVisibility(View.VISIBLE);
                if (value.equals("1")) {
                    mahasiswas = response.body().getMahasiswaList();
                    recycleAdapter = new RecycleAdapter(ShowActivity.this, mahasiswas);
                    myRecyclerView.setAdapter(recycleAdapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
        return true;
    }
}