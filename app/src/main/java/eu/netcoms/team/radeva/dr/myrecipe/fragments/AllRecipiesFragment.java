package eu.netcoms.team.radeva.dr.myrecipe.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import eu.netcoms.team.radeva.dr.myrecipe.R;
import eu.netcoms.team.radeva.dr.myrecipe.data.DbOperations;

public class AllRecipiesFragment extends Fragment {
    private EditText editAllRecipes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_recipies_fragment, container, false);
        Button btnShow = (Button) rootView.findViewById(R.id.btn_show);
        editAllRecipes = (EditText)rootView.findViewById(R.id.editTextShow);
        btnShow.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DbOperations dbOperations = new DbOperations(getActivity().getApplicationContext());
                Cursor cursor = dbOperations.getRecipesContent();
                StringBuilder stringBuilder = new StringBuilder();
                if (cursor.moveToFirst()){
                    do {
                        stringBuilder.append(cursor.getString(cursor.getColumnIndex("_id")));
                        stringBuilder.append(" | ");
                        stringBuilder.append(cursor.getString(cursor.getColumnIndex("title")));
                        stringBuilder.append(" | ");
                        stringBuilder.append(cursor.getString(cursor.getColumnIndex("image_link")));
                        stringBuilder.append(System.getProperty("line.separator"));
                    } while (cursor.moveToNext());
                }
                cursor.close();
                dbOperations.close();
                editAllRecipes.setText(stringBuilder.toString());
            }
        });
        return rootView;
    }
}
