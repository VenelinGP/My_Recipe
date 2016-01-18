package eu.netcoms.team.radeva.dr.myrecipe.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import eu.netcoms.team.radeva.dr.myrecipe.R;
import eu.netcoms.team.radeva.dr.myrecipe.data.DbOperations;
import eu.netcoms.team.radeva.dr.myrecipe.models.RecipesTable;
import eu.netcoms.team.radeva.dr.myrecipe.services.RecipeService;
import eu.netcoms.team.radeva.dr.myrecipe.validations.Validator;

public class AddNewRecipeFragment extends Fragment {

    private ViewGroup mContainerView;
    private int count;
    private EditText editName;
    private EditText editDescription;
    private String imgLink = "@drawable/glorious_sou_recipes_icon400_400x400";
    // Used for validation fail toasts
    private boolean validName = false;
    private boolean validDescription = false;
    private String nameFailMessage = "Name cannot be empty";
    private String descriptionailMessage = "Description cannot be empty";


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_new_recipie_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mContainerView = (ViewGroup)rootView.findViewById(R.id.ll_ingrediants);
        final Button newIngredient = (Button)rootView.findViewById(R.id.iv_new_ingr);
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

        newIngredient.setLongClickable(true);
        newIngredient.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                for (int i = 0; i < 5; i++) {
                    EditText newView = new EditText(getActivity());
                    newView.setText((count + 1) + ") ");
                    mContainerView.addView(newView, count);
                    count++;
                }
                return false;
            }
        });

        removeIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    mContainerView.removeViewAt(count - 1);
                    count--;
                }
            }
        });

        removeIngredient.setLongClickable(true);
        removeIngredient.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mContainerView.removeAllViews();
                count = 0;
                return false;
            }
        });


        ImageButton btnSave = (ImageButton) rootView.findViewById(R.id.btn_save);
        editName = (EditText)rootView.findViewById(R.id.et_Name);

        editDescription = (EditText)rootView.findViewById(R.id.et_Description);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!validName) {
                    Toast.makeText(getActivity().getApplicationContext(), nameFailMessage, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!validDescription) {
                    Toast.makeText(getActivity().getApplicationContext(), descriptionailMessage, Toast.LENGTH_SHORT).show();
                    return;
                } else if (mContainerView.getChildCount() < 2) {
                    Toast.makeText(getActivity().getApplicationContext(), "A recipe must have at least two Ingredients.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DbOperations dbOperations = new DbOperations(getActivity().getApplicationContext());

                ArrayList<RecipesTable> recipeArray = new ArrayList<>();
                Cursor cursor = dbOperations.getRecipesContent();
                int countItems = cursor.getCount();
                if (cursor.moveToFirst()) {
                    do {
                        RecipesTable recipe = new RecipesTable(countItems + 1,
                                cursor.getString(cursor.getColumnIndex("name")),
                                cursor.getString(cursor.getColumnIndex("description")),
                                cursor.getString(cursor.getColumnIndex("image_link")));
                        recipeArray.add(recipe);
                    } while (cursor.moveToNext());
                }

                String name = nameOfRecipe.getText().toString().toLowerCase();

                for (RecipesTable recipe : recipeArray) {
                    String currentRecipeName = recipe.getName().toLowerCase();
                    if (name.equals(currentRecipeName)) {
                        nameFailMessage = "Recipe already exists.";
                        validName = false;
                        Toast.makeText(getActivity().getApplicationContext(), nameFailMessage, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // Adding recipe in table recipes.
                dbOperations.AddToRecipes(countItems + 1, editName.getText().toString(), editDescription.getText().toString(), imgLink, 0);

                // Adding ingredients in table products.
                int ingredientsCount = mContainerView.getChildCount();
                int findIndex;
                for (int i = 0; i < ingredientsCount; i++) {
                    EditText ingredient = (EditText) mContainerView.getChildAt(i);
                    String txtIngredient = ingredient.getText().toString();
                    findIndex = txtIngredient.indexOf(")");
                    String newIngredient = txtIngredient.substring(findIndex + 2, txtIngredient.length());
                    dbOperations.AddToProducts(countItems + 1, newIngredient);
                }
                Intent startSrv = new Intent(getActivity(), RecipeService.class);
                getActivity().startService(startSrv);
                //TODO: Make text of fields reset.
                sendNotification(name);
            }
        });
        return rootView;
    }

    private void sendNotification(String name) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
        mBuilder.setContentTitle("My recipe");
        mBuilder.setContentText("Recipe: " + name +" is added to database.");
        mBuilder.setSmallIcon(R.drawable.icon);

        Notification notificationObj = mBuilder.build();
        NotificationManager notificatinManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificatinManager.notify(6904, notificationObj);
    }
}
