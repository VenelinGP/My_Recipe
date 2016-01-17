package eu.netcoms.team.radeva.dr.myrecipe.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import eu.netcoms.team.radeva.dr.myrecipe.MainActivity;
import eu.netcoms.team.radeva.dr.myrecipe.R;
import eu.netcoms.team.radeva.dr.myrecipe.adapters.RecipeAdapter;
import eu.netcoms.team.radeva.dr.myrecipe.data.DbOperations;
import eu.netcoms.team.radeva.dr.myrecipe.data.Recipe;

public class HomePageFragment extends Fragment {
    private ArrayList<Recipe> recipeArray;
    private onRecipeClickListener mListener;
    private ArrayList<Recipe> result;

    public interface onRecipeClickListener {
        void onRecipeSelected(String name);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (onRecipeClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onRecipeClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        // Hides the keyboard on start
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        DbOperations dbOperations = new DbOperations(getActivity().getApplicationContext());

        recipeArray = new ArrayList<>();
        result = new ArrayList<>();

        Cursor cursor = dbOperations.getRecipesContent();
        int countItems = cursor.getCount();
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(countItems + 1,
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("description")),
                        cursor.getString(cursor.getColumnIndex("image_link")));
                recipeArray.add(recipe);
            } while (cursor.moveToNext());
        }

        final EditText editText = (EditText) rootView.findViewById(R.id.et_search);
        final TextView textView = (TextView) rootView.findViewById(R.id.tv_found_items_count);

        ListView stuff = (ListView) rootView.findViewById(R.id.lv_found_items);
        final RecipeAdapter recipeAdapter = new RecipeAdapter(getActivity(), R.layout.listview_item_row, result);

        stuff.setAdapter(recipeAdapter);
        stuff.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView clickedRecipe = (TextView) view.findViewById(R.id.txtTitle);
                String nameOfrecipe = clickedRecipe.getText().toString();

                mListener.onRecipeSelected(nameOfrecipe);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String[] input = editText.getText().toString().split("\\s+");
                result.clear();
                recipeAdapter.clear();

                if (input[0].length() <= 0) {
                    textView.setText("Type a word to search.");
                    recipeAdapter.clear();
                    return;
                }

                for (Recipe recipe : recipeArray) {
                    String[] currentRecipeName = recipe.getName().split("\\s+");
                    for (int i = 0; i < input.length; i++) {
                        String word = input[i].toLowerCase();
                        for (int j = 0; j < currentRecipeName.length; j++) {
                            if (currentRecipeName[j].toLowerCase().startsWith(word)) {
                                if (!result.contains(recipe)) {
                                    result.add(recipe);
                                }
                            }
                        }
                    }
                }

                textView.setText("Found " + result.size() + " item(s)!");
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        return rootView;
    }
}
