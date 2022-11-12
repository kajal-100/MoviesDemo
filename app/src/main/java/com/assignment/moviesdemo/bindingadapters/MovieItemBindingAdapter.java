package com.assignment.moviesdemo.bindingadapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.assignment.moviesdemo.R;
import com.assignment.moviesdemo.models.Genre;
import com.assignment.moviesdemo.network.ApiConstants;
import com.assignment.moviesdemo.utils.DateUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;

public class MovieItemBindingAdapter {

    @BindingAdapter("releaseDate")
    public static void setReleaseDate(TextView textView, String releaseDate){
        String date = DateUtil.formatDate(releaseDate);
        if(!date.equals("")){
            textView.setVisibility(View.VISIBLE);
            textView.setText(DateUtil.formatDate(releaseDate));
        }
        else
            textView.setVisibility(View.GONE);
    }

    @BindingAdapter("posterImageSmall")
    public static void setPosterImageSmall(ImageView imageView, String imgUrl){
        String url = ApiConstants.BASE_IMAGE_URL_W500+imgUrl;
        if(imgUrl!=null){
            Glide.with(imageView.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
        }

    }

    @BindingAdapter("genres")
    public static void setGenres(TextView textView, List<Genre> genreList){
        StringBuilder stringBuilder = new StringBuilder();
        if(genreList!=null){
            for(int i=0;i<genreList.size();i++){
                Genre genre = genreList.get(i);
                if(genreList.size()==1){
                    stringBuilder.append(textView.getContext().getString(R.string.bullet_symbol)).append(" ").append(genre.getName());
                }
                else if(i==0){
                    stringBuilder.append(textView.getContext().getString(R.string.bullet_symbol)).append(" ").append(genre.getName()).append(", ");
                }
                else if(i==genreList.size()-1){
                    stringBuilder.append(genre.getName());
                }
                else{
                    stringBuilder.append(genre.getName()).append(", ");
                }

            }
            textView.setVisibility(View.VISIBLE);
            textView.setText(stringBuilder);
        }
        else
            textView.setVisibility(View.GONE);

    }

    @BindingAdapter("releasedOn")
    public static void setReleasedOn(TextView textView, String releaseDate){
       String date = DateUtil.formatDate(releaseDate);
       if(!date.equals("")){
           textView.setVisibility(View.VISIBLE);
           textView.setText(textView.getContext().getString(R.string.released_on,date));
       }
       else
           textView.setVisibility(View.GONE);
    }

    @BindingAdapter("tagline")
    public static void setTagline(TextView textView, String tagline){
        if(tagline!=null && !tagline.equals("")){
            textView.setVisibility(View.VISIBLE);
            textView.setText(tagline);
        }
        else
            textView.setVisibility(View.GONE);
    }

    @BindingAdapter("overviewVisibility")
    public static void setOverviewVisibility(TextView textView, String overview){
        textView.setVisibility(overview!=null && !overview.isEmpty()?View.VISIBLE:View.GONE);
    }

}
