package com.pantrypro.logic;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pantrypro.R;
import com.pantrypro.objects.Recipe;
import com.pantrypro.presentation.MainActivity;
import com.pantrypro.presentation.ViewRecipe;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * RecyclerView adapter for displaying recipes.
 */
public class Recipe_RecyclerViewAdapter extends RecyclerView.Adapter<Recipe_RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Recipe> recipes;

    /**
     * Constructor for Recipe_RecyclerViewAdapter.
     * @param context The context
     * @param recipes List of recipes
     */
    public Recipe_RecyclerViewAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    public void setNewList(List<Recipe> newList) {
        recipes = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Recipe_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipes_rv_row, parent, false);

        return new Recipe_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Recipe_RecyclerViewAdapter.MyViewHolder holder, int position) {

        int recipeID = recipes.get(position).getID();
        String imgURL = recipes.get(position).getImage();
        Picasso.get().load(imgURL).into(holder.imageView);
        String titleName = recipes.get(position).getRecipeTitle();
        holder.title.setText(titleName);
        holder.duration.setText("Prep and cook time: ~" + recipes.get(position).getDuration() + " min");
        holder.foodType.setText("Food type: " + recipes.get(position).getFoodType());
        holder.parent.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("recipeID", recipeID);
            bundle.putString("recipePreview", imgURL);
            bundle.putString("recipeTitle", titleName);
            bundle.putString("recipeDuration", "Prep and cook time: ~" + recipes.get(position).getDuration() + " min");
            bundle.putString("recipeIngredients", recipes.get(position).getIngredients());
            bundle.putString("recipeSteps", recipes.get(position).getSteps());
            ViewRecipe singleViewRecipe = new ViewRecipe();
            singleViewRecipe.setArguments(bundle);
            ((MainActivity)context).replaceFragment(singleViewRecipe);
            ((MainActivity)context).getToolbar().setTitle(titleName);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, duration, foodType;
        LinearLayout parent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textView);
            duration = itemView.findViewById(R.id.textView2);
            foodType = itemView.findViewById(R.id.textView3);
            parent = itemView.findViewById(R.id.parentRecipe);
        }
    }
}
