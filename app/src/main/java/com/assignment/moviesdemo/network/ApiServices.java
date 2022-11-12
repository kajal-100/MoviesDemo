package com.assignment.moviesdemo.network;

import com.assignment.moviesdemo.models.Movie;
import com.assignment.moviesdemo.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {


    @GET(ApiConstants.FETCH_POPULAR_MOVIES)
    Call<MoviesResponse> fetchMovies(@Query("api_key")String apiKey,@Query("sort_by")String sortBy,@Query("page")int page);


    @GET(ApiConstants.GET_MOVIE_DETAILS)
    Call<Movie> getMovieDetails(@Path("movie_id")Integer id,@Query("api_key")String apiKey);
}
