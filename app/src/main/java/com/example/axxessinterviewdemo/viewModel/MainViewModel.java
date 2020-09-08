package com.example.axxessinterviewdemo.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.axxessinterviewdemo.model.Imagesmodel;
import com.example.axxessinterviewdemo.repos.ImagesRepo;

public class MainViewModel extends AndroidViewModel {
    private ImagesRepo imagesRepo;

    public MainViewModel(@NonNull Application application) {
        super(application);

        imagesRepo = new ImagesRepo();

    }


    public LiveData<Imagesmodel> getSearchImages(String query, String header){
        return imagesRepo.getMutableLiveData(query, header);
    }



}