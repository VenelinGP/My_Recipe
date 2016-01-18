package eu.netcoms.team.radeva.dr.myrecipe;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import eu.netcoms.team.radeva.dr.myrecipe.data.DbOperations;
import eu.netcoms.team.radeva.dr.myrecipe.models.RecipesTable;
import eu.netcoms.team.radeva.dr.myrecipe.models.ProductsTable;

public class CurrentRecipeActivity extends AppCompatActivity {

    private ArrayList<RecipesTable> recipeArray;
    private ViewGroup currentRecipeIngredients;
    private ArrayList<ProductsTable> productArray;
    private RecipesTable currentRecipe;
    private int count = 0;

    private static final int SELECT_PICTURE = 1;
    private ImageView imageOfCurrentRecipe;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            DbOperations dbOperations = new DbOperations(getApplicationContext());
            dbOperations.UpdateToRecipes(currentRecipe.getRecipe_id(), uri.toString());
            //content://media/external/images/media/15268
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                imageOfCurrentRecipe.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_recipe);

        Intent intent = getIntent();
        String message = intent.getStringExtra("Message");

        imageOfCurrentRecipe = (ImageView) findViewById(R.id.iv_current_recipe_image);
        TextView nameOfCurrentRecipe = (TextView) findViewById(R.id.tv_current_recipe_name);
        TextView descriptionOfCurrentRecipe = (TextView) findViewById(R.id.tv_current_recipe_description);
        currentRecipeIngredients = (ViewGroup) findViewById(R.id.ll_current_recipe_ingrediants);

        recipeArray = new ArrayList<>();
        productArray = new ArrayList<>();

        imageOfCurrentRecipe.setLongClickable(true);
        imageOfCurrentRecipe.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                return false;
            }
        });

        final DbOperations dbOperations = new DbOperations(getApplicationContext());
        Cursor cursor = dbOperations.getRecipesContent();
        if (cursor.moveToFirst()) {
            do {
                RecipesTable recipe = new RecipesTable();
                recipe.setRecipe_id(cursor.getInt(cursor.getColumnIndex("recipe_id")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setImage_link(cursor.getString(cursor.getColumnIndex("image_link")));
                recipeArray.add(recipe);
            } while (cursor.moveToNext());
        }

        String recipeName = message.toLowerCase();
        for (RecipesTable recipe : recipeArray) {
            String currentRecipeName = recipe.getName().toLowerCase();
            if (recipeName.equals(currentRecipeName)) {
                currentRecipe = recipe;
                nameOfCurrentRecipe.setText(message);
                descriptionOfCurrentRecipe.setText(recipe.getDescription());

                if(!recipe.getImage_link().startsWith("content")) {
                    int resId = getResources().getIdentifier(recipe.getImage_link(), "drawable", getPackageName());
                    imageOfCurrentRecipe.setImageResource(resId);
                }
                else {
                    Uri uri = Uri.parse(recipe.getImage_link());
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imageOfCurrentRecipe.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                cursor = dbOperations.getProductsContent(recipe.getRecipe_id());
                if (cursor.moveToFirst()) {
                    do {
                        ProductsTable product = new ProductsTable();
                        product.setRecipe_id(cursor.getInt(cursor.getColumnIndex("recipe_id")));
                        product.setProduct(cursor.getString(cursor.getColumnIndex("product")));
                        if (product.getRecipe_id() == recipe.getRecipe_id()) {
                            productArray.add(product);
                        }
                    } while (cursor.moveToNext());
                }

                for (ProductsTable product : productArray) {
                    TextView currentProduct = new TextView(this);
                    currentProduct.setText((count + 1) + ") " + product.getProduct());
                    currentRecipeIngredients.addView(currentProduct, count++);
                }

            }
        }
    }
}
