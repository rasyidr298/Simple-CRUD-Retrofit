package com.errdev.crudwithretrofit.Adapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.errdev.crudwithretrofit.Activity.AddActivity;
import com.errdev.crudwithretrofit.Activity.DetailActivity;
import com.errdev.crudwithretrofit.Activity.HomeActivity;
import com.errdev.crudwithretrofit.Activity.ShowActivity;
import com.errdev.crudwithretrofit.Activity.UpdateActivity;
import com.errdev.crudwithretrofit.Api.MahasiswaAPI;
import com.errdev.crudwithretrofit.Model.Mahasiswa;
import com.errdev.crudwithretrofit.Model.Value;
import com.errdev.crudwithretrofit.R;

import java.time.temporal.ValueRange;
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

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Mahasiswa> mahasiswas;

    public RecycleAdapter(Context mContext, ArrayList<Mahasiswa> mahasiswas) {
        this.mContext = mContext;
        this.mahasiswas = mahasiswas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inisialisasi viewholder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);

        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //menampilkan data pada view
        Mahasiswa mahasiswa = mahasiswas.get(position);
        holder.textViewNim.setText(mahasiswa.getNim());
        holder.textViewNama.setText(mahasiswa.getNama());
        holder.textViewkelas.setText(mahasiswa.getKelas());
        holder.textViewSesi.setText(mahasiswa.getSesi());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                //passing data to detaio act
                intent.putExtra("nim",mahasiswas.get(position).getNim());
                intent.putExtra("nama",mahasiswas.get(position).getNama());
                intent.putExtra("kelas",mahasiswas.get(position).getKelas());
                intent.putExtra("sesi",mahasiswas.get(position).getSesi());
                mContext.startActivity(intent);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.view_dialog);
                dialog.setTitle("pilih aksi");
                dialog.show();

                Button edtButton = dialog.findViewById(R.id.bt_EditData);

                //apabila tombol edit di click
                edtButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        onEditMhs(position);
                    }
                });
                return true;
            }
        });
    }

    private void onEditMhs(int position) {
        Intent intent = new Intent(mContext, UpdateActivity.class);
        //passing data to detaio act
        intent.putExtra("nim",mahasiswas.get(position).getNim());
        intent.putExtra("nama",mahasiswas.get(position).getNama());
        intent.putExtra("kelas",mahasiswas.get(position).getKelas());
        intent.putExtra("sesi",mahasiswas.get(position).getSesi());
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mahasiswas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        //inisialisasi view
        @BindView(R.id.tvNim) TextView textViewNim;
        @BindView(R.id.tvNama) TextView textViewNama;
        @BindView(R.id.tvKelas) TextView textViewkelas;
        @BindView(R.id.tvSesi) TextView textViewSesi;
        @BindView(R.id.cv_main) CardView cardView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}