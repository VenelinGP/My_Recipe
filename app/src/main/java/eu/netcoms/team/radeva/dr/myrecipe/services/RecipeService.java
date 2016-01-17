package eu.netcoms.team.radeva.dr.myrecipe.services;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.widget.Toast;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.EverliveAppSettings;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;

import java.util.ArrayList;
import java.util.List;

import eu.netcoms.team.radeva.dr.myrecipe.data.DbOperations;
import eu.netcoms.team.radeva.dr.myrecipe.data.Recipe;
import eu.netcoms.team.radeva.dr.myrecipe.models.PreparationRecipeTable;
import eu.netcoms.team.radeva.dr.myrecipe.models.ProductsTable;
import eu.netcoms.team.radeva.dr.myrecipe.models.RecipesTable;

public class RecipeService extends Service {
    EverliveApp recipeApp;
    private ArrayList<RecipesTable> recipeArray;
    private ArrayList<ProductsTable> producsArray;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeTelerikSDK();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        recipeApp.getAccessToken();

        getDataFromRecipes();
        for (RecipesTable currentRecipe  : recipeArray) {
            uploadToTableRecipes(currentRecipe);

            getDataFromProducts(currentRecipe);
            uploadToTableProducts();
        }

//        uploadToTableProducts();
//        uploadToTablePreparationRecipe();

        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    protected void initializeTelerikSDK() {
        String appID = "nont4v0bq19kdzpx";
        EverliveAppSettings appSettings = new EverliveAppSettings();
        appSettings.setAppId(appID);
        appSettings.setUseHttps(false);
        recipeApp = new EverliveApp(appSettings);
    }

    private void uploadToTableRecipes(final RecipesTable recipe) {
        recipeApp.workWith().data(RecipesTable.class).create(recipe).executeAsync(new RequestResultCallbackAction<RecipesTable>() {
            @Override
            public void invoke(RequestResult<RecipesTable> requestResult) {
                if (requestResult.getSuccess()) {
                    System.out.println("===== Sucsess - Recipie Table =====");
                    setIsUploaded(recipe);
                } else {
                    System.out.println("===== Error ====="+requestResult.getError().toString());
                }
            }
        });
    }

    private void getDataFromRecipes(){
        recipeArray = new ArrayList<>();
        final DbOperations dbOperations = new DbOperations(getApplicationContext());
        Cursor cursor = dbOperations.getRecipesContent();
        if (cursor.moveToFirst()) {
            do {
                RecipesTable recipe = new RecipesTable();
                recipe.setRecipe_id(cursor.getInt(cursor.getColumnIndex("recipe_id")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                recipe.setImage_link(cursor.getString(cursor.getColumnIndex("image_link")));
                recipe.setIs_uploaded(cursor.getInt(cursor.getColumnIndex("is_uploaded")));
                if (recipe.getIs_uploaded() == 0) {
                    recipeArray.add(recipe);
                }
            } while (cursor.moveToNext());
        }
        dbOperations.close();
    }

    private void getDataFromProducts(RecipesTable currentRecipe) {
        producsArray = new ArrayList<>();
        final DbOperations dbOperations = new DbOperations(getApplicationContext());
        Cursor cursor = dbOperations.getProductsContent(currentRecipe.getRecipe_id());
        if (cursor.moveToFirst()) {
            do {
                ProductsTable product = new ProductsTable();
                product.setRecipe_id(cursor.getInt(cursor.getColumnIndex("recipe_id")));
                product.setProduct(cursor.getString(cursor.getColumnIndex("product")));
                if (product.getRecipe_id() == currentRecipe.getRecipe_id()) {
                    producsArray.add(product);
                }
            } while (cursor.moveToNext());
        }
        dbOperations.close();
    }

    private void setIsUploaded(RecipesTable currentRecipe){
        final DbOperations dbOperations = new DbOperations(getApplicationContext());
        Cursor cursor = dbOperations.getRecipesContent();
        if (cursor.moveToFirst()) {
            do {
                RecipesTable recipe = new RecipesTable();
                recipe.setRecipe_id(cursor.getInt(cursor.getColumnIndex("recipe_id")));
                if (recipe.getRecipe_id() == currentRecipe.getRecipe_id()) {
                    dbOperations.UpdateToRecipes(recipe.getRecipe_id());
                }
            } while (cursor.moveToNext());
        }
        dbOperations.close();
    }

    private void uploadToTableProducts() {
//        ProductsTable pt = new ProductsTable();
//        pt.setRecipe_id(0);
//        pt.setProduct("kori za banica");
        recipeApp.workWith().data(ProductsTable.class).create(producsArray).executeAsync(new RequestResultCallbackAction<ProductsTable>() {
            @Override
            public void invoke(RequestResult<ProductsTable> requestResult) {
                if (requestResult.getSuccess()) {
                    System.out.println("===== Sucsess - Products Table =====");
                } else {
                    System.out.println("===== Error ====="+requestResult.getError().toString());
                }
            }

        });
    }

    private void uploadToTablePreparationRecipe() {
        PreparationRecipeTable prt = new PreparationRecipeTable();
        prt.setRecipe_id(0);
        prt.setPreparation("Slagate korite v tava.");
        recipeApp.workWith().data(PreparationRecipeTable.class).create(prt).executeAsync(new RequestResultCallbackAction<PreparationRecipeTable>() {
            @Override
            public void invoke(RequestResult<PreparationRecipeTable> requestResult) {
                if (requestResult.getSuccess()) {
                    System.out.println("===== Sucsess - Preparation Recipe Table=====");
                } else {
                    System.out.println("===== Error ====="+requestResult.getError().toString());
                }
            }
        });
    }
}
