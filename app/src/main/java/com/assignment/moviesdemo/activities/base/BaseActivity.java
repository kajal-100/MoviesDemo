package com.assignment.moviesdemo.activities.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.assignment.moviesdemo.R;
import com.assignment.moviesdemo.activities.details.MovieDetailsActivity;
import com.assignment.moviesdemo.utils.Constants;
import com.assignment.moviesdemo.utils.UIUtils;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB binding;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();
        setContentView(binding.getRoot());
        setToolbar();
    }

    protected abstract VB getViewBinding();

    public void launchMovieDetailsScreen(int movieId){
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(Constants.MOVIE_ID,movieId);
        startActivity(intent);
    }

    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
    }

    public void setToolbarTitle(String title){
        ((TextView)(toolbar.findViewById(R.id.tvTitle))).setText(title);
    }

    public void showNetworkError(){
        UIUtils.showToast(this,getString(R.string.please_connect_to_internet));
    }
}