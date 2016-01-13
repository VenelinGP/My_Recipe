package eu.netcoms.team.radeva.dr.myrecipe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.netcoms.team.radeva.dr.myrecipe.R;

public class AddNewRecipieFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_new_recipie_fragment, container, false);

        return rootView;
    }
}
