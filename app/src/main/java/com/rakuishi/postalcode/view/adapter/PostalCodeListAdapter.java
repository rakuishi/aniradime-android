package com.rakuishi.postalcode.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.model.PostalCode;

import java.util.ArrayList;
import java.util.List;

public class PostalCodeListAdapter extends RecyclerView.Adapter<PostalCodeListAdapter.ViewHolder> {

    public interface Callback {
        void onItemClick(PostalCode postalCode);
    }

    public enum Type {
        PREFECTURE, CITY, STREET
    }

    private List<PostalCode> postalCodes;
    private LayoutInflater inflater;
    private Type type;
    private Callback callback;

    public PostalCodeListAdapter(Context context, Type type, Callback callback) {
        postalCodes = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        this.type = type;
        this.callback = callback;
    }

    @Override
    public PostalCodeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.list_postal_code_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostalCode postalCode = postalCodes.get(position);

        switch (type) {
            case PREFECTURE:
                holder.primaryTextView.setText(postalCode.prefecture);
                holder.secondaryTextView.setVisibility(View.GONE);
                break;
            case CITY:
                holder.primaryTextView.setText(postalCode.city);
                holder.secondaryTextView.setText(postalCode.cityYomi);
                holder.secondaryTextView.setVisibility(View.VISIBLE);
                break;
            case STREET:
                holder.primaryTextView.setText(postalCode.street);
                holder.secondaryTextView.setText(postalCode.streetYomi);
                holder.secondaryTextView.setVisibility(View.VISIBLE);
                break;
        }

        holder.itemView.setOnClickListener(v -> callback.onItemClick(postalCode));
    }

    @Override
    public int getItemCount() {
        return postalCodes.size();
    }

    public void addAll(List<PostalCode> postalCodes) {
        this.postalCodes = postalCodes;
        notifyDataSetChanged();
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
