package eu.netcoms.team.radeva.dr.myrecipe;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import eu.netcoms.team.radeva.dr.myrecipe.data.DbOperations;
import eu.netcoms.team.radeva.dr.myrecipe.data.Recipe;
import eu.netcoms.team.radeva.dr.myrecipe.models.ProductsTable;

public class CurrentRecipeActivity extends AppCompatActivity {

    private ArrayList<Recipe> recipeArray;
    private ViewGroup currentRecipeIngredients;
    private ArrayList<ProductsTable> productArray;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_recipe);

        Intent intent = getIntent();
        String message = intent.getStringExtra("Message");

        ImageView imageOfCurrentRecipe = (ImageView) findViewById(R.id.iv_current_recipe_image);
        TextView nameOfCurrentRecipe = (TextView) findViewById(R.id.tv_current_recipe_name);
        TextView descriptionOfCurrentRecipe = (TextView) findViewById(R.id.tv_current_recipe_description);
        currentRecipeIngredients = (ViewGroup) findViewById(R.id.ll_current_recipe_ingrediants);

        recipeArray = new ArrayList<>();
        productArray = new ArrayList<>();

        final DbOperations dbOperations = new DbOperations(getApplicationContext());
        Cursor cursor = dbOperations.getRecipesContent();
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.setRecipeId(cursor.getInt(cursor.getColumnIndex("recipe_id")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setImage_link(cursor.getString(cursor.getColumnIndex("image_link")));
                recipeArray.add(recipe);
            } while (cursor.moveToNext());
        }

        String recipeName = message.toLowerCase();
        for (Recipe recipe : recipeArray) {
            String currentRecipeName = recipe.getName().toLowerCase();
            if (recipeName.equals(currentRecipeName)) {
                nameOfCurrentRecipe.setText(message);
                descriptionOfCurrentRecipe.setText(recipe.getDescription());

                int resId = getResources().getIdentifier(recipe.getImage_link(), "drawable", getPackageName());
                imageOfCurrentRecipe.setImageResource(resId);

                cursor = dbOperations.getProductsContent(recipe.getRecipeId());
                if (cursor.moveToFirst()) {
                    do {
                        ProductsTable product = new ProductsTable();
                        product.setRecipe_id(cursor.getInt(cursor.getColumnIndex("recipe_id")));
                        product.setProduct(cursor.getString(cursor.getColumnIndex("product")));
                        if (product.getRecipe_id() == recipe.getRecipeId()) {
                            productArray.add(product);
                        }
                    } while (cursor.moveToNext());
                }

                for(ProductsTable product : productArray) {
                    TextView currentProduct = new TextView(this);
                    currentProduct.setText((count + 1) + ") " + product.getProduct());
                    currentRecipeIngredients.addView(currentProduct, count++);
                }

            }
        }
    }
}
