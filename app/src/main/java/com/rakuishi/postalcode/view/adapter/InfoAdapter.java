package com.rakuishi.postalcode.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rakuishi.postalcode.BuildConfig;
import com.rakuishi.postalcode.R;

public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface Callback {
        void onClickUrl(String url);
    }

    private final @NonNull Context context;
    private final @NonNull Callback callback;
    private final @NonNull LayoutInflater inflater;

    public InfoAdapter(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_two_line, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        switch (position) {
            case 0:
                holder1.primaryTextView.setText(BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")");
                holder1.secondaryTextView.setText(R.string.version);
                holder1.secondaryTextView.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder1.primaryTextView.setText(R.string.database_date);
                holder1.secondaryTextView.setText(R.string.last_update_of_postal_code_data);
                holder1.secondaryTextView.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder1.primaryTextView.setText(R.string.developer);
                holder1.secondaryTextView.setText(R.string.developed_by);
                holder1.secondaryTextView.setVisibility(View.VISIBLE);
                holder1.itemView.setOnClickListener(v -> {
                    callback.onClickUrl(context.getString(R.string.developer_homepage));
                });
                break;
            case 3:
                holder1.primaryTextView.setText(R.string.license);
                holder1.secondaryTextView.setVisibility(View.GONE);
                holder1.itemView.setOnClickListener(v -> {
                    callback.onClickUrl(context.getString(R.string.licenses_path));
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView primaryTextView;
        TextView secondaryTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            primaryTextView = (TextView) itemView.findViewById(R.id.primary_text_view);
            secondaryTextView = (TextView) itemView.findViewById(R.id.secondary_text_view);
        }
    }
}
