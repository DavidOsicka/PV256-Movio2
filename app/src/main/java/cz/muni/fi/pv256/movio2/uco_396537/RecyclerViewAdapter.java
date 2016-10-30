package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import cz.muni.fi.pv256.movio2.uco_396537.Models.Movie;

/**
 * Created by david on 10.10.16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = RecyclerViewAdapter.class.getName();
    public static final int EMPTY_VIEW = 0, CATEGORY_VIEW = 1, MOVIE_VIEW = 2;

    private ArrayList<Object> mItems = new ArrayList<Object>();
    private WeakReference<Context> mContextWeakReference = null;
    private boolean noData = false;



    public RecyclerViewAdapter(String noDataLabel) {
        mItems.add(noDataLabel);
        noData = true;
    }

    public RecyclerViewAdapter(ArrayList<Object> data, Context context) {
        mItems = data;
        mContextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(noData) {
            return EMPTY_VIEW;
        } else if (mItems.get(position) instanceof String) {
            return CATEGORY_VIEW;
        } else if (mItems.get(position) instanceof Movie) {
            return MOVIE_VIEW;
        }
        return -1;
    }


// Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Log.d(TAG, "onCreateViewHolder method");
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View view;
        switch(viewType) {
            case  CATEGORY_VIEW :
                view = inflater.inflate(R.layout.category_view, parent, false);
                return new ViewHolder_category(view);
            case MOVIE_VIEW:
                view = inflater.inflate(R.layout.movie_view, parent, false);
                return new ViewHolder_movie(view, mContextWeakReference.get());
            case EMPTY_VIEW :
                view = inflater.inflate(R.layout.empty_view, parent, false);
                return new ViewHolder_empty(view);
        }
        return null;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // get element from dataset at this position and replace the contents of the view with that element
        Log.d(TAG, "onBindViewHolder method");
        if(holder == null || position < 0 || position > mItems.size()) {
            return;
        }
        switch(holder.getItemViewType()) {
            case CATEGORY_VIEW :
                ViewHolder_category categoryHolder = (ViewHolder_category) holder;
                categoryHolder.mCategoryLabel.setText((String) mItems.get(position));
                break;
            case MOVIE_VIEW :
                ViewHolder_movie movieHolder = (ViewHolder_movie) holder;
                Movie movie = (Movie) mItems.get(position);
                movieHolder.mMovieTitle.setText(movie.getTitle());
                movieHolder.mMovieRating.setText(Float.toString(movie.getPopularity()));
                if(!movie.getBackdrop().isEmpty()) {
                    movieHolder.mMovieImage.setImageDrawable(mContextWeakReference.get().getDrawable(Integer.parseInt(movie.getBackdrop())));
                }
                break;
            case EMPTY_VIEW :
                ViewHolder_empty emptyView = (ViewHolder_empty) holder;
                emptyView.mEmptyListLabel.setText(mItems.get(position).toString() /*noDataLabel*/);
                break;
        }
    }






    public static class ViewHolder_movie extends RecyclerView.ViewHolder {

        public TextView mMovieTitle;
        public ImageView mMovieImage;
        public TextView mMovieRating;
        private RelativeLayout mLayout;

        public ViewHolder_movie(View view, final Context context) {
            super(view);
            mMovieTitle = (TextView) view.findViewById(R.id.movie_title);
            mMovieImage = (ImageView) view.findViewById(R.id.movie_image);
            mMovieRating = (TextView) view.findViewById(R.id.movie_rating);
            mLayout = (RelativeLayout) view.findViewById(R.id.layout);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(context != null) {
                        ((MainActivity) context).onMovieItemSelected(getAdapterPosition());
                    }
                }
            };
            View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick (View view) {
                    if(context != null) {
                        ((MainActivity) context).onMovieLongClick(getAdapterPosition());
                        return true;
                    }
                    return false;
                }
            };
            mLayout.setOnClickListener(clickListener);
            mLayout.setOnLongClickListener(longClickListener);
        }
    }

    public static class ViewHolder_category extends RecyclerView.ViewHolder {

        public TextView mCategoryLabel;

        public ViewHolder_category(View view) {
            super(view);
            mCategoryLabel = (TextView) view.findViewById(R.id.category_label);
        }
    }

    public static class ViewHolder_empty extends RecyclerView.ViewHolder {

        public TextView mEmptyListLabel;

        public ViewHolder_empty(View view) {
            super(view);
            mEmptyListLabel = (TextView) view.findViewById(R.id.empty_list_label);
        }
    }

}
