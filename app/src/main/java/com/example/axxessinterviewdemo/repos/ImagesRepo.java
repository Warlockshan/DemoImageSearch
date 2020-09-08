package com.example.axxessinterviewdemo.repos;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.axxessinterviewdemo.api.ApiDataService;
import com.example.axxessinterviewdemo.api.RetrofitClient;
import com.example.axxessinterviewdemo.model.Imagesmodel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagesRepo {
    private static final String TAG = "ImagesSearchRepo";
    private Imagesmodel imagesmodel = new Imagesmodel();
    private MutableLiveData<Imagesmodel> mutableLiveData = new MutableLiveData<>();

    public ImagesRepo() {
    }

    public MutableLiveData<Imagesmodel> getMutableLiveData(String query, String header) {

        final ApiDataService userDataService = RetrofitClient.getService();
        Call<Imagesmodel> call = userDataService.getImagesBySearch(query,header);
        call.enqueue(new Callback<Imagesmodel>() {
            @Override
            public void onResponse(Call<Imagesmodel> call, Response<Imagesmodel> response) {
                Imagesmodel imagesDBResponse = response.body();
                Log.e("response",response.body().toString());
                if (imagesDBResponse != null && imagesDBResponse.getData() != null) {
                    imagesmodel.setStatus(imagesDBResponse.getStatus());
                    imagesmodel.setSuccess(imagesDBResponse.getSuccess());
                    imagesmodel.setData(imagesDBResponse.getData());
                    mutableLiveData.setValue(imagesmodel);
                    Log.e("response",response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Imagesmodel> call, Throwable t) {
            }
        });
        return mutableLiveData;
    }
}
