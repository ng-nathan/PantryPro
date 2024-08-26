package com.pantrypro.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pantrypro.R;
import com.squareup.picasso.Picasso;

public class ViewRecipe extends Fragment {

    private View view;
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.view_recipe_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.deleteRecipe){
            showWarningDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_view_recipe, container, false);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

        setHasOptionsMenu(true);

        String imgURL = getArguments().getString(RecipeKeys.PREVIEW);
        ImageView previewImg = view.findViewById(R.id.recipePreview);
        Picasso.get().load(imgURL).into(previewImg);

        final TextView title = view.findViewById(R.id.recipeTitle);
        title.setText(getArguments().getString(RecipeKeys.TITLE));

        final TextView duration = view.findViewById(R.id.recipeDuration);
        duration.setText(getArguments().getString(RecipeKeys.DURATION));

        final TextView ingredients = view.findViewById(R.id.recipeIngredients);
        ingredients.setText(getArguments().getString(RecipeKeys.INGREDIENTS));

        final TextView steps = view.findViewById(R.id.recipeSteps);
        steps.setText(getArguments().getString(RecipeKeys.STEPS));

        return view;
    }

    private void showWarningDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
        alert.setView(R.layout.warning_dialog);
        alert.setPositiveButton("YES, DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteRecipe();
                Toast.makeText(requireContext(), "Recipe Deleted", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("NO, KEEP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();

        alertDialog.getWindow().setLayout(800, 400);

        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        btnPositive.setLayoutParams(layoutParams);
        btnPositive.setTextColor(ContextCompat.getColor(requireContext(), R.color.darkRed));
        btnPositive.setTextSize(15);
        btnNegative.setLayoutParams(layoutParams);
        btnNegative.setTextSize(15);
    }

    private void deleteRecipe(){
        assert getArguments() != null;
        int recipeID = getArguments().getInt("recipeID");
        closeFragment();
        ((MainActivity) requireContext()).getHandleRecipesDB().removeRecipeByID(recipeID);
    }

    private void closeFragment() {
        ((MainActivity) requireContext()).replaceFragment(new HomeFragment());
    }
    private static class RecipeKeys {
        public static final String PREVIEW = "recipePreview";
        public static final String TITLE = "recipeTitle";
        public static final String DURATION = "recipeDuration";
        public static final String INGREDIENTS = "recipeIngredients";
        public static final String STEPS = "recipeSteps";
    }
}