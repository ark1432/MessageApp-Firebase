package com.example.firebasemessageappex1.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.firebasemessageappex1.R;
import com.example.firebasemessageappex1.databinding.ActivityMainBinding;
import com.example.firebasemessageappex1.viewmodel.MyViewModel;

public class MainActivity extends AppCompatActivity {

    MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.setVModel(viewModel);
    }
}