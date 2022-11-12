package com.assignment.moviesdemo.activities.details;

import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.assignment.moviesdemo.R;
import com.assignment.moviesdemo.activities.base.BaseActivity;
import com.assignment.moviesdemo.databinding.ActivityMovieDetailsBinding;
import com.assignment.moviesdemo.models.Movie;
import com.assignment.moviesdemo.utils.CommonUtils;
import com.assignment.moviesdemo.utils.Constants;
import com.assignment.moviesdemo.utils.UIUtils;

public class MovieDetailsActivity extends BaseActivity<ActivityMovieDetailsBinding> {

    private Integer movieId;
    private MovieDetailsViewModel movieDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        initViewModel();
        getMovieDetails();
    }

    private void initViewModel(){
        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
    }

    private void getMovieDetails(){
        if(CommonUtils.isNetworkAvailable(this)){
            movieDetailsViewModel.getMovieDetails(movieId).observe(this,movie -> {
                switch (movie.getStatus()){
                    case Loading:
                        UIUtils.showLoading(this);
                        break;
                    case Success:
                        UIUtils.hideLoading();
                        handleMovieSuccess(movie);
                        break;
                    case Fail:
                        UIUtils.hideLoading();
                        handleMovieFailure();
                        break;
                }
            });
        }
        else{
            showNetworkError();
            binding.tvNoNetwork.setVisibility(View.VISIBLE);
        }

    }

    private void handleMovieSuccess(Movie movieResponse){
        setMovieNameOnToolbar(movieResponse.getOriginalTitle());
        binding.setMovie(movieResponse);
    }

    private void setMovieNameOnToolbar(String movieName){
        setToolbarTitle(movieName);
    }

    private void handleMovieFailure(){
        UIUtils.showToast(this,getString(R.string.some_error_occurred));
        binding.tvNoNetwork.setVisibility(View.VISIBLE);
        binding.tvNoNetwork.setText(getString(R.string.oops_something_went_wrong));
    }

    private void getIntentData(){
        Intent intent = getIntent();
        if(intent!=null){
            movieId = intent.getIntExtra(Constants.MOVIE_ID,0);
        }
    }

    @Override
    protected ActivityMovieDetailsBinding getViewBinding() {
        return ActivityMovieDetailsBinding.inflate(getLayoutInflater());
    }
}