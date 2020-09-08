package com.example.axxessinterviewdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.axxessinterviewdemo.adapter.ImagesSearchAdapter;
import com.example.axxessinterviewdemo.databinding.ActivityMainJavaBinding;
import com.example.axxessinterviewdemo.model.Datum;
import com.example.axxessinterviewdemo.model.Imagesmodel;
import com.example.axxessinterviewdemo.utils.ItemDecorationColumns;
import com.example.axxessinterviewdemo.viewModel.MainViewModel;

import java.util.ArrayList;

public class MainActivityJava extends AppCompatActivity implements View.OnClickListener{
    ActivityMainJavaBinding binding;
    ImagesSearchAdapter imagesSearchAdapter;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main_java);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        getSupportActionBar().hide();
        binding.relError.setVisibility(View.VISIBLE);
        binding.progressLoading.setVisibility(View.VISIBLE);
        binding.tvNoConnectivity.setVisibility(View.GONE);
        binding.ivNoData.setVisibility(View.GONE);
        //RecyclerSetup
        binding.recImages.setLayoutManager(new GridLayoutManager(this,2));
        binding.recImages.setHasFixedSize(true);
        binding.recImages.addItemDecoration(new ItemDecorationColumns(
                getResources().getDimensionPixelSize(R.dimen.photos_list_spacing),
                getResources().getInteger(R.integer.photo_list_preview_columns)));
        imagesSearchAdapter = new ImagesSearchAdapter(this);
        binding.recImages.setAdapter(imagesSearchAdapter);
        getSearchImages("vanilla");
        binding.searchQuery.setOnClickListener(this);

        //editetct ime option click
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (binding.etSearch.getText().length()>0){
                        binding.relError.setVisibility(View.VISIBLE);
                        binding.progressLoading.setVisibility(View.VISIBLE);
                        binding.tvNoConnectivity.setVisibility(View.GONE);
                        binding.ivNoData.setVisibility(View.GONE);
                        getSearchImages(binding.etSearch.getText().toString());
                        hideSoftKeyboard(MainActivityJava.this);
                    }else {
                        hideSoftKeyboard(MainActivityJava.this);
                        binding.etSearch.setError("Please provide word!");
                    }
                    return true;
                }
                return false;
            }
        });
    }



    private void getSearchImages(String query){

        mainViewModel.getSearchImages(query,"Client-ID 137cda6b5008a7c").observe(this, new Observer<Imagesmodel>() {
            @Override
            public void onChanged(@Nullable Imagesmodel imagesmodel) {
                assert imagesmodel != null;
                if (imagesmodel.getStatus()==200 && imagesmodel.getData().size()>0){
                    binding.relError.setVisibility(View.GONE);
                    binding.progressLoading.setVisibility(View.GONE);
                    binding.tvNoConnectivity.setVisibility(View.GONE);
                    binding.ivNoData.setVisibility(View.GONE);
                    imagesSearchAdapter.setSearchImages((ArrayList<Datum>) imagesmodel.getData());

                }else {
                    binding.relError.setVisibility(View.VISIBLE);
                    binding.progressLoading.setVisibility(View.GONE);
                    binding.ivNoData.setVisibility(View.VISIBLE);
                    binding.tvNoConnectivity.setVisibility(View.VISIBLE);

                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.searchQuery){
            if (binding.etSearch.getText().length()>0){
                binding.relError.setVisibility(View.VISIBLE);
                binding.progressLoading.setVisibility(View.VISIBLE);
                binding.tvNoConnectivity.setVisibility(View.GONE);
                binding.ivNoData.setVisibility(View.GONE);
                getSearchImages(binding.etSearch.getText().toString());
                hideSoftKeyboard(this);
            }else {
                hideSoftKeyboard(this);
                binding.etSearch.setError("Please provide word!");
            }

        }
    }

    public static void hideSoftKeyboard(AppCompatActivity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}