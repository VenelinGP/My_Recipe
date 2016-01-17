package eu.netcoms.team.radeva.dr.myrecipe.data;

import android.provider.BaseColumns;

public class RecipeContract {
    public static final class RecipesEntry implements BaseColumns {
        public static final String TABLE_NAME = "recipes";

        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IMAGE_LINK = "image_link";
        public static final String COLUMN_IS_UPLOADED = "is_uploaded";
    }

    public static final class ProductsEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";

        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String PRODUCT= "product";
    }

    public static final class PreparationEntry implements BaseColumns {
        public static final String TABLE_NAME = "preparation_recipe";

        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String PREPARATION= "preparation";
    }
}
