package com.assignment.moviesdemo.activities.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.assignment.moviesdemo.models.MoviesResponse;
import com.assignment.moviesdemo.models.Result;
import com.assignment.moviesdemo.models.Status;
import com.assignment.moviesdemo.repositories.MoviesRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MoviesListViewModel extends ViewModel {

    private final MoviesRepository moviesRepository;
    private int page = 1;
    private final List<Result> moviesList = new ArrayList<>();
    private final HashMap<String,String> sortByMap = new HashMap<>();

    public int getPage() {
        return page;
    }

    public void updatePage() {
        page++;
    }

    public boolean pagesLeft(){
        int limit = 400;
        return page<= limit;
    }

    public void resetPage(){
        page=1;
    }

    public void clearData(){
        moviesList.clear();
    }

    public MoviesListViewModel(){
        moviesRepository  =MoviesRepository.getInstance();
    }

    public LiveData<MoviesResponse> fetchMovies(String sortBy, int page){
        LiveData<MoviesResponse> moviesLiveData = moviesRepository.fetchMovies(sortBy,page);
        MutableLiveData<MoviesResponse> mutableLiveData = new MutableLiveData<>();
        MoviesResponse moviesResponse = new MoviesResponse();
        moviesResponse.setStatus(Status.Loading);
        mutableLiveData.setValue(moviesResponse);
        moviesLiveData.observeForever(new Observer<MoviesResponse>() {
            @Override
            public void onChanged(MoviesResponse moviesResponse) {
                if(moviesResponse.getErrorResponse() == null){
                    if(page==1){
                        clearData();
                    }
                    updateMoviesList(moviesResponse.getResults());
                    moviesResponse.setResults(moviesList);
                    moviesResponse.setStatus(Status.Success);
                }
                else{
                    moviesResponse.setStatus(Status.Fail);
                }
                mutableLiveData.setValue(moviesResponse);
                moviesLiveData.removeObserver(this);
            }
        });
       return mutableLiveData;
    }

    public void updateMoviesList(List<Result> moreMovies){
        moviesList.addAll(moreMovies);
    }

    public void setSortByMap(List<String> sortByList){
        sortByMap.put(sortByList.get(0),"popularity.desc");
        sortByMap.put(sortByList.get(1),"vote_average.desc");
        sortByMap.put(sortByList.get(2),"popularity.asc");
        sortByMap.put(sortByList.get(3),"vote_average.asc");
    }

    public HashMap<String, String> getSortByMap(){
        return this.sortByMap;
    }
}
