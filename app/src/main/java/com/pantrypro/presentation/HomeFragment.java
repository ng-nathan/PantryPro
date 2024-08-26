package com.pantrypro.presentation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.pantrypro.R;
import com.pantrypro.logic.Recipe_RecyclerViewAdapter;
import com.pantrypro.objects.Recipe;
import com.pantrypro.persistence.stubs.RecipePersistenceStub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Fragment class for the home screen.
 */
public class HomeFragment extends Fragment {
    private RecipePersistenceStub db = new RecipePersistenceStub();

    private Recipe_RecyclerViewAdapter adapter;
    private List<Recipe> recipes;
    private SearchView searchView;
    private Button findMeal, findSnack, findDessert, findBeverage;
    private boolean isFindMealToggled, isFindSnackToggled, isFindDessertToggled, isFindBeverageToggled = false;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("PantryPro");

        // Search queries
        searchView = view.findViewById(R.id.searchBar);
        findMeal = view.findViewById(R.id.findMeal);
        findSnack = view.findViewById(R.id.findSnack);
        findDessert = view.findViewById(R.id.findDessert);
        findBeverage = view.findViewById(R.id.findBeverage);

        // search bar
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!isFindMealToggled || !isFindSnackToggled || !isFindDessertToggled || !isFindBeverageToggled){
                    resetAllButton();
                }
                searchList(newText);
                return true;
            }
        });

        // prebuilt queries
        findMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFindMealToggled = !isFindMealToggled;
                isFindSnackToggled= false;
                isFindDessertToggled= false;
                isFindBeverageToggled = false;
                buttonOff(findSnack);
                buttonOff(findDessert);
                buttonOff(findBeverage);
                if(isFindMealToggled){
                    buttonOn(findMeal);
                } else {
                    resetRecipeList();
                    buttonOff(findMeal);
                }
            }
        });

        findSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFindMealToggled = false;
                isFindSnackToggled = !isFindSnackToggled;
                isFindDessertToggled= false;
                isFindBeverageToggled = false;
                buttonOff(findMeal);
                buttonOff(findDessert);
                buttonOff(findBeverage);
                if(isFindSnackToggled){
                    buttonOn(findSnack);
                } else {
                    resetRecipeList();
                    buttonOff(findSnack);
                }
            }
        });

        findDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFindMealToggled = false;
                isFindSnackToggled = false;
                isFindDessertToggled= !isFindDessertToggled;
                isFindBeverageToggled = false;
                buttonOff(findMeal);
                buttonOff(findSnack);
                buttonOff(findBeverage);
                if(isFindDessertToggled){
                    buttonOn(findDessert);
                } else {
                    resetRecipeList();
                    buttonOff(findDessert);
                }
            }
        });

        findBeverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFindMealToggled = false;
                isFindSnackToggled = false;
                isFindDessertToggled= false;
                isFindBeverageToggled = !isFindBeverageToggled;
                buttonOff(findMeal);
                buttonOff(findSnack);
                buttonOff(findDessert);
                if(isFindBeverageToggled){
                    buttonOn(findBeverage);
                } else {
                    resetRecipeList();
                    buttonOff(findBeverage);
                }
            }
        });

//        View.OnClickListener buttonClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isFindMealToggled = false;
//                isFindSnackToggled = false;
//                isFindDessertToggled = false;
//                isFindBeverageToggled = false;
//                buttonOff(findMeal);
//                buttonOff(findSnack);
//                buttonOff(findDessert);
//                buttonOff(findBeverage);
//
//                if (v.getId() == R.id.findMeal) {
//                    isFindMealToggled = true;
//                } else if (v.getId() == R.id.findSnack) {
//                    isFindSnackToggled = true;
//                } else if (v.getId() == R.id.findDessert) {
//                    isFindDessertToggled = true;
//                } else if (v.getId() == R.id.findBeverage) {
//                    isFindBeverageToggled = true;
//                }
//
//                if (v.getTag() != null && (boolean) v.getTag()) {
//                    resetRecipeList();
//                    v.setTag(false);
//                } else {
//                    v.setTag(true);
//                    buttonOn((Button) v);
//                }
//            }
//        };
//
//        findMeal.setOnClickListener(buttonClickListener);
//        findSnack.setOnClickListener(buttonClickListener);
//        findDessert.setOnClickListener(buttonClickListener);
//        findBeverage.setOnClickListener(buttonClickListener);

        // Find the RecyclerView for recipes in the fragment's layout
        initRecycleView(view);
        // Reset recycle view
        adapter.notifyDataSetChanged();
        return view;
    }

    private void initRecycleView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recipesRecyclerView);
        recipes = ((MainActivity) requireContext()).getHandleRecipesDB().getAllRecipes();
        Collections.reverse(recipes);

        adapter = new Recipe_RecyclerViewAdapter(getContext(), recipes);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    //search list
    private void searchList(String text){
        List<Recipe> newList = new ArrayList<>();
        for(Recipe recipe : recipes){
            if(recipe.getRecipeTitle().toLowerCase().contains(text.toLowerCase())){
                newList.add(recipe);
            }
        }
        if(!newList.isEmpty()){
            adapter.setNewList(newList);
        }
    }

    private void prebuiltQueryList(String text){
        List<Recipe> newList = new ArrayList<>();
        for(Recipe recipe : recipes){
            if(recipe.getFoodType().toLowerCase().contains(text.toLowerCase())){
                newList.add(recipe);
            }
        }
        if(!newList.isEmpty()){
            adapter.setNewList(newList);
        } else {
            Toast.makeText(requireContext(), "Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetAllButton(){
        isFindMealToggled = false;
        isFindSnackToggled= false;
        isFindDessertToggled= false;
        isFindBeverageToggled = false;
        buttonOff(findMeal);
        buttonOff(findSnack);
        buttonOff(findDessert);
        buttonOff(findBeverage);
    }

    private void buttonOn(Button btn){
        btn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greyishGreen));
        prebuiltQueryList(btn.getText().toString());
    }

    private void buttonOff(Button btn){
        btn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greenPastel));
    }

    private void resetRecipeList(){
        List<Recipe> newList = ((MainActivity) requireContext()).getHandleRecipesDB().getAllRecipes();
        Collections.reverse(newList);
        adapter.setNewList(newList);
    }
}