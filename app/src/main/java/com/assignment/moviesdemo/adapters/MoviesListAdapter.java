package com.assignment.moviesdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.moviesdemo.adapters.viewholders.MoviesListItemViewHolder;
import com.assignment.moviesdemo.databinding.ItemMoviesListBinding;
import com.assignment.moviesdemo.listener.MovieClickListener;
import com.assignment.moviesdemo.models.Result;

import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListItemViewHolder> {

    private final List<Result> moviesResults;
    private final MovieClickListener movieClickListener;

    public MoviesListAdapter(List<Result> movieResults, MovieClickListener movieClickListener){
        this.moviesResults = movieResults;
        this.movieClickListener = movieClickListener;
    }

    @NonNull
    @Override
    public MoviesListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMoviesListBinding binding =ItemMoviesListBinding.inflate(layoutInflater,parent,false);
        return new MoviesListItemViewHolder(binding,movieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesListItemViewHolder holder, int position) {
        holder.bind(moviesResults.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesResults.size();
    }
}
