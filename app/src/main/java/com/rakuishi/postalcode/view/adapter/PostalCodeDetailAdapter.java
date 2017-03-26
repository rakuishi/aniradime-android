package com.rakuishi.postalcode.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.model.PostalCode;

public class PostalCodeDetailAdapter extends RecyclerView.Adapter<PostalCodeDetailAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private PostalCode postalCode;

    public PostalCodeDetailAdapter(Context context, PostalCode postalCode) {
        this.postalCode = postalCode;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostalCodeDetailAdapter.ViewHolder(inflater.inflate(R.layout.list_two_line_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.primaryTextView.setText(postalCode.getHyphenedCode());
                holder.secondaryTextView.setText(R.string.postal_code);
                break;
            case 1:
                holder.primaryTextView.setText(postalCode.getFullName());
                holder.secondaryTextView.setText(R.string.address);
                break;
            case 2:
            default:
                holder.primaryTextView.setText(postalCode.getFullYomi());
                holder.secondaryTextView.setText(R.string.pronunciation);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
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
