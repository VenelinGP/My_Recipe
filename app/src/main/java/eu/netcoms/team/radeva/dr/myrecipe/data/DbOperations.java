package eu.netcoms.team.radeva.dr.myrecipe.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbOperations extends StoreDbHelper {
    public DbOperations(Context context) {
        super(context);
    }

    public void AddToRecipes(Integer recipe_id, String title, String image_link, Integer is_uploaded) {
        openForWriting();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.RecipesEntry.COLUMN_RECIPE_ID, recipe_id);
        contentValues.put(RecipeContract.RecipesEntry.COLUMN_TITLE, title);
        contentValues.put(RecipeContract.RecipesEntry.COLUMN_IMAGE_LINK, image_link);
        contentValues.put(RecipeContract.RecipesEntry.COLUMN_IS_UPLOADED, is_uploaded);
        sqLiteDatabase.insert("recipes", null, contentValues);
    }

    public Cursor getRecipesContent() {
        openForReading();
        String[] projection = {RecipeContract.RecipesEntry._ID, RecipeContract.RecipesEntry.COLUMN_TITLE, RecipeContract.RecipesEntry.COLUMN_IMAGE_LINK};
        String sortOrder = RecipeContract.RecipesEntry.COLUMN_TITLE + " ASC";
        return this.sqLiteDatabase.query("recipes", projection, null, null, null, null, sortOrder);
    }

    public int getRecipesCount() {
        openForReading();
        String countQuery = "SELECT  * FROM recipes";
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public void AddToProducts(Integer recipe_id, String product) {
        openForWriting();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.ProductsEntry.COLUMN_RECIPE_ID, recipe_id);
        contentValues.put(RecipeContract.ProductsEntry.PRODUCT, product);
        this.sqLiteDatabase.insert("product", null, contentValues);
    }

    public Cursor getProductsContent(String recipe_id) {
        openForReading();
        String[] projection = {RecipeContract.ProductsEntry.COLUMN_RECIPE_ID, RecipeContract.ProductsEntry.PRODUCT};
        String selection = RecipeContract.ProductsEntry.COLUMN_RECIPE_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(recipe_id)};
        return this.sqLiteDatabase.query("products", projection, selection, selectionArgs, null, null, null);
    }

    public void AddToPreparation(Integer recipe_id, String preparation) {
        openForWriting();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.PreparationEntry.COLUMN_RECIPE_ID, recipe_id);
        contentValues.put(RecipeContract.PreparationEntry.PREPARATION, preparation);
        this.sqLiteDatabase.insert("preparation_recipe", null, contentValues);
    }

    public Cursor getPreparationContent(String recipe_id) {
        openForReading();
        String[] projection = {RecipeContract.PreparationEntry.COLUMN_RECIPE_ID, RecipeContract.PreparationEntry.PREPARATION};
        String selection = RecipeContract.ProductsEntry.COLUMN_RECIPE_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(recipe_id)};
        return this.sqLiteDatabase.query("preparation_recipe", projection, selection, selectionArgs, null, null, null);
    }

}
