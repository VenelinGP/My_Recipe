package eu.netcoms.team.radeva.dr.myrecipe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import eu.netcoms.team.radeva.dr.myrecipe.R;
import eu.netcoms.team.radeva.dr.myrecipe.data.DbOperations;

public class AddNewRecipieFragment extends Fragment {

    private ViewGroup mContainerView;
    private int count;
    private EditText editName;
    private EditText editImageLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_recipie_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mContainerView = (ViewGroup)rootView.findViewById(R.id.ll_ingrediants);
        ImageView newIngredient = (ImageView)rootView.findViewById(R.id.iv_new_ingr);
        ImageView removeIngredient = (ImageView)rootView.findViewById(R.id.iv_remove_ingr);
        // Do not touch the counter - will cause a bug!
        count = 0;

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

        Button btnSave = (Button) rootView.findViewById(R.id.btn_save);
        editName = (EditText)rootView.findViewById(R.id.editTxtName);
        //TODO: Da opravim vryzkite img_link <-> description
        editImageLink = (EditText)rootView.findViewById(R.id.editTxtDescription);
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.btn_save) {
                    DbOperations dbOperations = new DbOperations(getActivity().getApplicationContext());
                    dbOperations.AddToRecipes(0, editName.getText().toString(), editImageLink.getText().toString(), 0);
                    Toast.makeText(getActivity().getApplicationContext(), "Recipe is Added", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getActivity().getApplicationContext(), "Recipe New", Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}
