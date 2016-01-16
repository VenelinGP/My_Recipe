package eu.netcoms.team.radeva.dr.myrecipe.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import eu.netcoms.team.radeva.dr.myrecipe.R;
import eu.netcoms.team.radeva.dr.myrecipe.data.DbOperations;
import eu.netcoms.team.radeva.dr.myrecipe.data.Recipe;
import eu.netcoms.team.radeva.dr.myrecipe.data.RecipeContract;
import eu.netcoms.team.radeva.dr.myrecipe.validations.Validator;

public class AddNewRecipeFragment extends Fragment {

    private ViewGroup mContainerView;
    private int count;
    private EditText editName;
    private EditText editImageLink;
    // Used for validation fail toasts
    private boolean validName = false;
    private boolean validDescription = false;
    private boolean validIngredients= false;
    private String nameFailMessage = "Name cannot be empty";
    private String descriptionailMessage = "Description cannot be empty";

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_recipie_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mContainerView = (ViewGroup)rootView.findViewById(R.id.ll_ingrediants);
        Button newIngredient = (Button)rootView.findViewById(R.id.iv_new_ingr);
        Button removeIngredient = (Button)rootView.findViewById(R.id.iv_remove_ingr);

        // Do not touch the counter - will cause a bug!
        count = 0;
        EditText defaultFirstEditText = new EditText(getActivity());
        defaultFirstEditText.setText((count + 1) + ") ");
        mContainerView.addView(defaultFirstEditText, count);
        count++;
        EditText defaultSecondEditText = new EditText(getActivity());
        defaultSecondEditText.setText((count + 1) + ") ");
        mContainerView.addView(defaultSecondEditText, count);
        count++;

        // Validations
        // Check for valid name
        final EditText nameOfRecipe = (EditText)rootView.findViewById(R.id.et_Name);
        nameOfRecipe.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String currentInput = nameOfRecipe.getText().toString();
                if(Validator.nameHasBadWords(currentInput)) {
                    nameFailMessage = "Name contains forbidden words.";
                    nameOfRecipe.setTextColor(getResources().getColor(R.color.red));
                    validName = false;
                    return;
                }
                else if(Validator.nameHasInvalidLength(currentInput)) {
                    nameFailMessage = "Name must be at least 3 characters long.";
                    validName = false;
                    return;
                }
                nameOfRecipe.setTextColor(getResources().getColor(R.color.greenForText));
                validName = true;
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        //Check for valid description.
        final EditText descriptionOfRecipe = (EditText)rootView.findViewById(R.id.et_Description);
        descriptionOfRecipe.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String currentInput = descriptionOfRecipe.getText().toString();
                if (Validator.descriptionHasInvalidLength(currentInput)) {
                    descriptionailMessage = "Description must be at least 3 characters long.";
                    validDescription = false;
                    return;
                }
                validDescription = true;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        newIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newView = new EditText(getActivity());
                newView.setText((count + 1) + ") ");
                mContainerView.addView(newView, count);
                count++;
            }
        });

        removeIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count > 0) {
                    mContainerView.removeViewAt(count - 1);
                    count--;
                }
            }
        });

        ImageButton btnSave = (ImageButton) rootView.findViewById(R.id.btn_save);
        editName = (EditText)rootView.findViewById(R.id.et_Name);

        editImageLink = (EditText)rootView.findViewById(R.id.et_Description);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!validName) {
                    Toast.makeText(getActivity().getApplicationContext(), nameFailMessage, Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!validDescription) {
                    Toast.makeText(getActivity().getApplicationContext(), descriptionailMessage, Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(mContainerView.getChildCount() < 2) {
                    Toast.makeText(getActivity().getApplicationContext(), "A recipe must have at least two Ingredients.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DbOperations dbOperations = new DbOperations(getActivity().getApplicationContext());

                ArrayList<Recipe> recipeArray = new ArrayList<>();
                Cursor cursor = dbOperations.getRecipesContent();
                int countItems = cursor.getCount();
                if (cursor.moveToFirst()) {
                    do {
                        Recipe recipe = new Recipe(countItems+1,
                                cursor.getString(cursor.getColumnIndex("title")),
                                cursor.getString(cursor.getColumnIndex("image_link")));
                        recipeArray.add(recipe);
                    } while (cursor.moveToNext());
                }

                String name = nameOfRecipe.getText().toString().toLowerCase();

                for(Recipe recipe : recipeArray) {
                    String currentRecipeName = recipe.getName().toLowerCase();
                    if(name.equals(currentRecipeName)) {
                        nameFailMessage = "Recipe already exists.";
                        validName = false;
                        Toast.makeText(getActivity().getApplicationContext(), nameFailMessage, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                dbOperations.AddToRecipes(countItems + 1, editName.getText().toString(), editImageLink.getText().toString(), 0);
                int ingredientsCount = mContainerView.getChildCount();
                for (int i = 0; i < ingredientsCount; i++) {
                    EditText ingredient =(EditText) mContainerView.getChildAt(i);
                    String txtIntegrient = ingredient.getText().toString();
                    dbOperations.AddToProducts(countItems+1, txtIntegrient);
                }
                //TODO: Make text of fields reset.
                Toast.makeText(getActivity().getApplicationContext(), "Recipe added!", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}
