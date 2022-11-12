package com.assignment.moviesdemo.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.assignment.moviesdemo.BuildConfig;
import com.assignment.moviesdemo.models.ErrorResponse;
import com.assignment.moviesdemo.models.MoviesResponse;
import com.assignment.moviesdemo.network.ApiClient;
import com.assignment.moviesdemo.network.ApiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private static volatile MoviesRepository instance;
    private final ApiServices apiServices;

    private MoviesRepository(){
        apiServices = ApiClient.getInstance().getApiServices();
    }

    public static MoviesRepository getInstance(){
        if(instance == null){
            synchronized (MoviesRepository.class){
                if(instance == null)
                    instance = new MoviesRepository();
            }
        }
        return instance;
    }

    public LiveData<MoviesResponse> fetchMovies(String sortBy,int page){
        MutableLiveData<MoviesResponse> moviesMutableLiveData = new MutableLiveData<>();
        apiServices.fetchMovies(BuildConfig.API_KEY,sortBy,page).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                MoviesResponse moviesResponse;
                if(response.isSuccessful()){
                     moviesResponse = response.body();
                    if(moviesResponse==null){
                        moviesResponse = new MoviesResponse();
                        moviesResponse.setErrorResponse(new ErrorResponse());
                    }
                    moviesMutableLiveData.setValue(moviesResponse);
                }
                else{
                    moviesResponse = new MoviesResponse();
                    moviesResponse.setErrorResponse(new ErrorResponse());
                    moviesMutableLiveData.setValue(moviesResponse);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                MoviesResponse moviesResponse = new MoviesResponse();
                moviesResponse.setErrorResponse(new ErrorResponse());
                moviesMutableLiveData.setValue(moviesResponse);
            }
        });
        return moviesMutableLiveData;
    }

}
