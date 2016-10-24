package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by david on 10.10.16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private String[] mDataset;
    private WeakReference<Context> mContextWeakReference = null;


    public RecyclerViewAdapter(String[] myDataset, Context context) {
        mDataset = myDataset;
        mContextWeakReference = new WeakReference<Context>(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Context context = null;
        if(mContextWeakReference != null) {
            context = mContextWeakReference.get();
        }

        if(context != null) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
            ViewHolder vh = new ViewHolder(view, context);
            return vh;
        }
        return null;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(holder != null) {
            holder.mTextView.setText(mDataset[position]);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public RelativeLayout layout;

        public ViewHolder(View view, final Context context) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.title);
            layout = (RelativeLayout) view.findViewById(R.id.layout);

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(context != null) {
                        ((MainActivity) context).onMovieItemSelected(getAdapterPosition());
                    }
                }
            };

            layout.setOnClickListener(listener);
        }
    }

}
