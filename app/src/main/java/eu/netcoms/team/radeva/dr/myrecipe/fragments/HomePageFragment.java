package eu.netcoms.team.radeva.dr.myrecipe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import eu.netcoms.team.radeva.dr.myrecipe.R;

public class HomePageFragment extends Fragment {
    private ArrayList<String> names;
    private ArrayList<String> found;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        // Hides the keyboard on start

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        names = new ArrayList<>();
        found = new ArrayList<>();

        names.add("Salata");
        names.add("Kurvavata Meri");
        names.add("Ruska salata");
        names.add("Salata");
        names.add("Salata");
        names.add("Salata");
        names.add("Salata");
        names.add("Salata");
        names.add("Salata");
        names.add("Salata");

        final EditText editText = (EditText)rootView.findViewById(R.id.et_search);
        final TextView textView = (TextView)rootView.findViewById(R.id.tv_found_items_count);

        ListView stuff = (ListView)rootView.findViewById(R.id.lv_found_items);
        ListAdapter adapter = new ArrayAdapter<>(this.getActivity(), R.layout.home_found_items_list_view, found);
        stuff.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String input = editText.getText().toString();
                //TODO:   Check if more than 2 strings are passed and if they both start
                //TODO:   the wanted word. Example: Salata and Shopska Salata should be
                //TODO:   found both.

                if(input.length() <= 0) {
                    textView.setText("Type a word to search.");
                    found.clear();
                    return;
                }

                for(String name : names) {
                    if(name.toLowerCase().startsWith(input.toLowerCase())) {
                        if(!found.contains(name)) {
                            found.add(name);
                        }
                    }
                }

                textView.setText("Found " + found.size() + " item(s)!");
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        return rootView;
    }
}
