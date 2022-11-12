package com.assignment.moviesdemo.adapters.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.moviesdemo.databinding.ItemMoviesListBinding;
import com.assignment.moviesdemo.listener.MovieClickListener;
import com.assignment.moviesdemo.models.Result;

public class MoviesListItemViewHolder extends RecyclerView.ViewHolder {

    private final ItemMoviesListBinding binding;
    private final MovieClickListener movieClickListener;

    public MoviesListItemViewHolder(@NonNull ItemMoviesListBinding binding,MovieClickListener movieClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.movieClickListener = movieClickListener;
    }

    public void bind(Result movie){
        binding.setMovieResult(movie);
        binding.cvMovie.setOnClickListener(view -> movieClickListener.onMovieClicked(movie.getId()));

    }
}
