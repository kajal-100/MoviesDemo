package com.assignment.moviesdemo.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.assignment.moviesdemo.BuildConfig;
import com.assignment.moviesdemo.models.ErrorResponse;
import com.assignment.moviesdemo.models.Movie;
import com.assignment.moviesdemo.models.MoviesResponse;
import com.assignment.moviesdemo.network.ApiClient;
import com.assignment.moviesdemo.network.ApiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsRepository {
    private static volatile MovieDetailsRepository instance;
    private final ApiServices apiServices;

    private MovieDetailsRepository(){
        apiServices = ApiClient.getInstance().getApiServices();
    }

    public static MovieDetailsRepository getInstance(){
        if(instance == null){
            synchronized (MovieDetailsRepository.class){
                if(instance == null)
                    instance = new MovieDetailsRepository();
            }
        }
        return instance;
    }

    public LiveData<Movie> getMovieDetails(Integer movieId){
        MutableLiveData<Movie> movieMutableLiveData = new MutableLiveData<>();
        apiServices.getMovieDetails(movieId,BuildConfig.API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie;
                if(response.isSuccessful()){
                    movie = response.body();
                    if(movie==null){
                        movie = new Movie();
                        movie.setErrorResponse(new ErrorResponse());
                    }

                }
                else{
                    movie = new Movie();
                    movie.setErrorResponse(new ErrorResponse());
                }
                movieMutableLiveData.setValue(movie);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Movie movie = new Movie();
                movie.setErrorResponse(new ErrorResponse());
                movieMutableLiveData.setValue(movie);
            }
        });
        return movieMutableLiveData;
    }

}
