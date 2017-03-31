package com.rakuishi.postalcode.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.model.PostalCode;

public class PostalCodeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface Callback {
        void onLongClickText(String text);
        void onClickShare(String q);
        void onClickOpenInGoogleMaps(String q);
    }

    private Context context;
    private LayoutInflater inflater;
    private Callback callback;
    private @Nullable PostalCode postalCode;

    public PostalCodeDetailAdapter(Context context, Callback callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    public void add(PostalCode postalCode) {
        this.postalCode = postalCode;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new PostalCodeDetailAdapter.CodeViewHolder(inflater.inflate(R.layout.item_two_line, parent, false));
            case 1:
                return new PostalCodeDetailAdapter.NoteViewHolder(inflater.inflate(R.layout.item_note, parent, false));
            case 2:
            default:
                return new PostalCodeDetailAdapter.OpenViewHolder(inflater.inflate(R.layout.item_one_line_with_icon, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                CodeViewHolder holder0 = (CodeViewHolder) holder;
                switch (position) {
                    case 0:
                        holder0.primaryTextView.setText(postalCode.getHyphenedCode());
                        holder0.secondaryTextView.setText(R.string.postal_code);
                        holder0.itemView.setOnLongClickListener(v -> {
                            callback.onLongClickText(postalCode.code);
                            return true;
                        });
                        break;
                    case 1:
                        holder0.primaryTextView.setText(postalCode.getFullName());
                        holder0.secondaryTextView.setText(R.string.address);
                        holder0.itemView.setOnLongClickListener(v -> {
                            callback.onLongClickText(postalCode.getFullName());
                            return true;
                        });
                        break;
                    case 2:
                        holder0.primaryTextView.setText(postalCode.getFullYomi());
                        holder0.secondaryTextView.setText(R.string.pronunciation);
                        holder0.itemView.setOnLongClickListener(v -> {
                            callback.onLongClickText(postalCode.getFullYomi());
                            return true;
                        });
                        break;
                }
                break;
            case 1:
                // SupplementaryViewHolder holder1 = (SupplementaryViewHolder) holder;
                // do nothing
                break;
            case 2:
            default:
                OpenViewHolder holder2 = (OpenViewHolder) holder;
                switch (position) {
                    case 4:
                        holder2.textView.setText(R.string.share);
                        holder2.imageView.setImageDrawable(context.getDrawable(R.drawable.ic_share));
                        holder2.itemView.setOnClickListener(v -> callback.onClickShare(postalCode.getFullName()));
                        break;
                    case 5:
                        holder2.textView.setText(R.string.open_in_google_maps);
                        holder2.imageView.setImageDrawable(context.getDrawable(R.drawable.ic_google_maps));
                        holder2.itemView.setOnClickListener(v -> callback.onClickOpenInGoogleMaps(postalCode.getFullName()));
                        break;
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return postalCode == null ? 0 : 6;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
            case 1:
            case 2:
                return 0;
            case 3:
                return 1;
            case 4:
            default:
                return 2;
        }
    }

    class CodeViewHolder extends RecyclerView.ViewHolder {

        TextView primaryTextView;
        TextView secondaryTextView;

        public CodeViewHolder(View itemView) {
            super(itemView);
            primaryTextView = (TextView) itemView.findViewById(R.id.primary_text_view);
            secondaryTextView = (TextView) itemView.findViewById(R.id.secondary_text_view);
        }
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        public NoteViewHolder(View itemView) {
            super(itemView);
        }
    }

    class OpenViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public OpenViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }
}
