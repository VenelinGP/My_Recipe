package eu.netcoms.team.radeva.dr.myrecipe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoreDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "my_recipes.db";
    public SQLiteDatabase sqLiteDatabase;

    public StoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_RECIPES_TABLE =
                "CREATE TABLE IF NOT EXISTS " + RecipeContract.RecipesEntry.TABLE_NAME + " (" +
                        RecipeContract.RecipesEntry._ID + " INTEGER PRIMARY KEY, " +
                        RecipeContract.RecipesEntry.COLUMN_RECIPE_ID + " INTEGER, " +
                        RecipeContract.RecipesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                        RecipeContract.RecipesEntry.COLUMN_IMAGE_LINK + " TEXT NOT NULL, " +
                        RecipeContract.RecipesEntry.COLUMN_IS_UPLOADED + " INTEGER DEFAULT 0" +
                        ");";
        final String SQL_CREATE_PRODUCTS_TABLE =
                "CREATE TABLE IF NOT EXISTS " + RecipeContract.ProductsEntry.TABLE_NAME + " (" +
                        RecipeContract.ProductsEntry._ID + " INTEGER PRIMARY KEY, " +
                        RecipeContract.ProductsEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
                        RecipeContract.ProductsEntry.PRODUCT + " TEXT NOT NULL" +
                        ");";

        final String SQL_CREATE_PREPARATION_RECIPE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + RecipeContract.PreparationEntry.TABLE_NAME + " (" +
                        RecipeContract.PreparationEntry._ID + " INTEGER PRIMARY KEY, " +
                        RecipeContract.PreparationEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
                        RecipeContract.PreparationEntry.PREPARATION + " TEXT NOT NULL" +
                        ");";

        sqLiteDatabase.execSQL(SQL_CREATE_RECIPES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PREPARATION_RECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void openForWriting() {
        sqLiteDatabase = getWritableDatabase();
    }

    public void openForReading() {
        sqLiteDatabase = getReadableDatabase();
    }

    @Override
    public synchronized void close() {
        super.close();
    }
}
