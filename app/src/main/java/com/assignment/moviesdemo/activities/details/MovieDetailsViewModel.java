package com.assignment.moviesdemo.activities.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.assignment.moviesdemo.models.Movie;
import com.assignment.moviesdemo.models.Status;
import com.assignment.moviesdemo.repositories.MovieDetailsRepository;

public class MovieDetailsViewModel extends ViewModel {

    private final MovieDetailsRepository movieRepository;

    public MovieDetailsViewModel(){
        movieRepository = MovieDetailsRepository.getInstance();
    }

    public LiveData<Movie> getMovieDetails(Integer movieId){
        Movie movieResponse = new Movie();
        MutableLiveData<Movie> mutableLiveData = new MutableLiveData<>();
        if(movieId==null){  //no need to call API, return if movieId is null
            movieResponse.setStatus(Status.Fail);
            mutableLiveData.setValue(movieResponse);
            return mutableLiveData;
        }
        LiveData<Movie> moviesLiveData = movieRepository.getMovieDetails(movieId);
        movieResponse.setStatus(Status.Loading);
        mutableLiveData.setValue(movieResponse);
        moviesLiveData.observeForever(new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieResponse) {
                if(movieResponse.getErrorResponse() == null){
                    movieResponse.setStatus(Status.Success);
                }
                else{
                    movieResponse.setStatus(Status.Fail);
                }
                mutableLiveData.setValue(movieResponse);
                moviesLiveData.removeObserver(this);
            }
        });
        return mutableLiveData;
    }
}
