package com.example.myapplication.ui.fragment.pets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.domain.Pet;
import com.squareup.picasso.Picasso;

import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetsAdapter extends ListAdapter<Pet, PetsAdapter.PetViewHolder> {

    private static final int NUM_CACHED_VIEWS = 5;

    private Stack<View> cachedViews = new Stack<>();
    private OnItemClickListener listener;

    PetsAdapter(Context context, OnItemClickListener listener) {
        super(PetsDiffUtil.create());
        this.listener = listener;
        AsyncLayoutInflater inflater = new AsyncLayoutInflater(context);
        for (int i = 0; i < NUM_CACHED_VIEWS; i++) {
            inflater.inflate(R.layout.pet_item_view, null, (view, resid, parent) -> cachedViews.push(view));
        }
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (cachedViews.isEmpty()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item_view, parent, false);
        } else {
            view = cachedViews.pop();
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        return new PetViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        holder.bind(getItem(position), position);
    }

    static class PetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.pet_item_image)
        ImageView petItemImage;
        @BindView(R.id.pet_item_id)
        TextView petItemId;
        @BindView(R.id.textView)
        TextView textView;

        private Pet pet;
        private int position;
        private OnItemClickListener listener;

        PetViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Pet pet, int position) {
            this.pet = pet;
            this.position = position;
            Picasso.get().load(pet.getUrlImage()).placeholder(R.mipmap.ic_launcher).into(petItemImage);
            petItemId.setText(String.valueOf(pet.getId()));
            textView.setText(pet.getTitle());
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(pet, position);
        }
    }

    private static class PetsDiffUtil extends DiffUtil.ItemCallback<Pet> {

        @Override
        public boolean areItemsTheSame(@NonNull Pet oldItem, @NonNull Pet newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Pet oldItem, @NonNull Pet newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getUrlImage().equals(newItem.getUrlImage());
        }

        static PetsDiffUtil create() {
            return new PetsDiffUtil();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Pet pet, int position);
    }

}
