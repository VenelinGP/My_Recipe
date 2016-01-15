package eu.netcoms.team.radeva.dr.myrecipe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import eu.netcoms.team.radeva.dr.myrecipe.R;

public class AddNewRecipieFragment extends Fragment {

    private ViewGroup mContainerView;
    private int count;

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

        return rootView;
    }
}
