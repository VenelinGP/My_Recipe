package eu.netcoms.team.radeva.dr.myrecipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CurrentRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_recipe);

        Intent intent = getIntent();
        String message = intent.getStringExtra("Message");
        TextView tv = (TextView)findViewById(R.id.tv_test);
        tv.setText(message);
    }
}
