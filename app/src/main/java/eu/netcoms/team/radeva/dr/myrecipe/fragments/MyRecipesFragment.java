package eu.netcoms.team.radeva.dr.myrecipe.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import eu.netcoms.team.radeva.dr.myrecipe.R;
import eu.netcoms.team.radeva.dr.myrecipe.adapters.RecipeAdapter;
import eu.netcoms.team.radeva.dr.myrecipe.data.DbOperations;
import eu.netcoms.team.radeva.dr.myrecipe.data.Recipe;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyRecipesFragment extends ListFragment implements OnItemClickListener {

    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<Recipe> recipeArray = new ArrayList<>();
        DbOperations dbOperations = new DbOperations(getActivity().getApplicationContext());
        Cursor cursor = dbOperations.getRecipesContent();
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                int a = cursor.getColumnIndex("recipe_id");
                recipe.setRecipeId(cursor.getInt(0));
                recipe.setName(cursor.getString(1));
//                recipe.setImage_link(cursor.getString(cursor.getColumnIndex("image_link")));
                recipeArray.add(recipe);
            } while (cursor.moveToNext());
        }
        RecipeAdapter recipeAdapter = new RecipeAdapter(getActivity(), R.layout.listview_item_row, recipeArray);

        //View header = getActivity().getLayoutInflater().inflate(R.layout.listview_header_row, null);
        View rootView = inflater.inflate(R.layout.my_recipes_fragment, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.lvMyRecipes);
        //listView.addHeaderView(header);
        listView.setAdapter(recipeAdapter);
        return listView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
}
