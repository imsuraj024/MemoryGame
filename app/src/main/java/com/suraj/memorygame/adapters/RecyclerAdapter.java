package com.suraj.memorygame.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suraj.memorygame.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final ArrayList<Integer> cards;
    private ItemClickListener mClickListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View v, int position);
    }

    public RecyclerAdapter(ArrayList<Integer> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.front.setImageResource(cards.get(position));

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView front;
        EasyFlipView flipView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            front = itemView.findViewById(R.id.cardfront);
            flipView = itemView.findViewById(R.id.flip);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            flipView.flipTheView();
            if (mClickListener != null){
                mClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }



}
