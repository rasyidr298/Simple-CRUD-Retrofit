package com.errdev.crudwithretrofit.Api;

import android.renderscript.Sampler;

import com.errdev.crudwithretrofit.Model.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MahasiswaAPI {

    //insert data
    @FormUrlEncoded
    @POST("insert.php")
    Call<Value> daftar(@Field("nim")String nim,
                       @Field("nama")String nama,
                       @Field("kelas")String kelas,
                       @Field("sesi")String sesi);

    //melihat data
    @GET("view.php")
    Call<Value> view();

    //meng update data
    @FormUrlEncoded
    @POST("update.php")
    Call<Value> ubah (@Field("nim")String nim,
                      @Field("nama")String nama,
                      @Field("kelas")String kelas,
                      @Field("sesi")String sesi);

    //delete data
    @FormUrlEncoded
    @POST("delete.php")
    Call<Value> hapus (@Field("nim") String nim);

    //search data
    @FormUrlEncoded
    @POST("search.php")
    Call<Value> search (@Field("search") String search);
}
