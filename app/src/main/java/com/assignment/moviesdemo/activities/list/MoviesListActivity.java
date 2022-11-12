package com.assignment.moviesdemo.activities.list;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.assignment.moviesdemo.R;
import com.assignment.moviesdemo.activities.base.BaseActivity;
import com.assignment.moviesdemo.adapters.MoviesListAdapter;
import com.assignment.moviesdemo.databinding.ActivityMoviesListBinding;
import com.assignment.moviesdemo.listener.MovieClickListener;
import com.assignment.moviesdemo.models.MoviesResponse;
import com.assignment.moviesdemo.utils.CommonUtils;
import com.assignment.moviesdemo.utils.GridSpacingItemDecoration;
import com.assignment.moviesdemo.utils.UIUtils;
import java.util.Arrays;
import java.util.List;


public class MoviesListActivity extends BaseActivity<ActivityMoviesListBinding> implements MovieClickListener {

    private MoviesListViewModel moviesListViewModel;
    private  MoviesListAdapter moviesListAdapter;
    private GridLayoutManager gridLayoutManager;
    private boolean isScrolling;
    private String sortBy = "popularity.desc";
    private final boolean[] filterSetArr = new boolean[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        setToolbarTitle();
        setGridItemDecoration();
        getPopularMovies(sortBy,moviesListViewModel.getPage());
        setScrollListenerOnRV();
        setDropDownData();
        setDropDownClickListener();
    }

    private void setToolbarTitle(){
        setToolbarTitle(getString(R.string.app_name));
    }

    private void setDropDownClickListener(){
        binding.acFilter.setOnItemClickListener((parent, view, position, id) -> {
            if(!filterSetArr[position]){  //checking if filter is not selected already
                markFilterSelected(position);
                String[] arr = getResources().getStringArray(R.array.sort_by_items);
                sortBy = moviesListViewModel.getSortByMap().get(arr[position]);
                moviesListViewModel.resetPage();
                getPopularMovies(sortBy,moviesListViewModel.getPage());
            }

        });
    }

    private void markFilterSelected(int position){
        for(int i=0;i<filterSetArr.length;i++){
            filterSetArr[i] = i == position;
        }
    }

    private void setDropDownData(){
        List<String> sortByList = Arrays.asList(getResources().getStringArray(R.array.sort_by_items));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.sort_by_items));
        binding.acFilter.setText(sortByList.get(0));
        binding.acFilter.setAdapter(adapter);
        moviesListViewModel.setSortByMap(sortByList); //set hashmap of sorting key and value

    }

    private void setScrollListenerOnRV(){
        binding.rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                     isScrolling  =true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    int currentVisibleItems = gridLayoutManager.getChildCount();
                    int totalItems = gridLayoutManager.getItemCount();
                    int scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();
                    if(isScrolling && (currentVisibleItems+scrollOutItems)==totalItems){
                        isScrolling = false;
                        moviesListViewModel.updatePage();
                        getPopularMovies(sortBy,moviesListViewModel.getPage());
                    }
                }

            }
        });
    }

    private void getPopularMovies(String sortBy, int page){
        if(CommonUtils.isNetworkAvailable(this)){
            if(moviesListViewModel.pagesLeft()){
                moviesListViewModel.fetchMovies(sortBy,page).observe(this,moviesResponse -> {
                    switch (moviesResponse.getStatus()){
                        case Loading:
                            binding.progressBar.getRoot().setVisibility(View.VISIBLE);
                            break;
                        case Success:
                            handleMoviesSuccess(moviesResponse);
                            binding.progressBar.getRoot().setVisibility(View.GONE);
                            break;
                        case Fail:
                            binding.progressBar.getRoot().setVisibility(View.GONE);
                            handleMoviesFailure();
                            break;

                    }
                });
            }
        }
       else{
           showNetworkError();
           binding.tvNoNetwork.setVisibility(View.VISIBLE);
        }

    }

    private void handleMoviesSuccess(MoviesResponse moviesResponse){
        if(moviesResponse.getResults()!=null && moviesResponse.getResults().size()>0){
            if(moviesListViewModel.getPage()==1)
                 setAdapter(moviesResponse);
            else{
                moviesListAdapter.notifyItemInserted(moviesResponse.getResults().size()-1);
            }
        }

        else
            binding.rvMovies.setVisibility(View.GONE);
    }

    private void handleMoviesFailure(){
        UIUtils.showToast(this,getString(R.string.some_error_occurred));
        binding.tvNoNetwork.setVisibility(View.VISIBLE);
        binding.tvNoNetwork.setText(getString(R.string.oops_something_went_wrong));
    }

    private void initViewModel(){
        moviesListViewModel = new ViewModelProvider(this).get(MoviesListViewModel.class);
    }

    private void setAdapter(MoviesResponse moviesResponse){
        gridLayoutManager  =new GridLayoutManager(this, 2);
        moviesListAdapter = new MoviesListAdapter(moviesResponse.getResults(),this);
        binding.rvMovies.setLayoutManager(gridLayoutManager);
        binding.rvMovies.setAdapter(moviesListAdapter);
        binding.rvMovies.setItemViewCacheSize(20);
        binding.rvMovies.setDrawingCacheEnabled(true);
    }

    private void setGridItemDecoration(){
        int spanCount = 2;
        int spacing = 20;
        boolean includeEdge = false;
        binding.rvMovies.addItemDecoration(new GridSpacingItemDecoration(spanCount,spacing,includeEdge));

    }


    @Override
    protected ActivityMoviesListBinding getViewBinding() {
        return ActivityMoviesListBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onMovieClicked(Integer movieId) {
        launchMovieDetailsScreen(movieId);
    }
}