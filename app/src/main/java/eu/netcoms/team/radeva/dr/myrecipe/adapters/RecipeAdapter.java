package eu.netcoms.team.radeva.dr.myrecipe.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import eu.netcoms.team.radeva.dr.myrecipe.R;
import eu.netcoms.team.radeva.dr.myrecipe.data.Recipe;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private  int layoutId;
    List<Recipe> data;

    public RecipeAdapter(Context context, int layoutResourceId, List<Recipe> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutId = layoutResourceId;
        this.data = data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View row = inflater.inflate(layoutId, null);
        TextView tvRecipeId = (TextView) row.findViewById(R.id.txtRecipeId);
        TextView tvName = (TextView) row.findViewById(R.id.txtTitle);
        TextView tvDescription = (TextView) row.findViewById(R.id.txtDescription);
        ImageView ivImage = (ImageView) row.findViewById(R.id.imgIcon);
//        File imgFile = new File(data.get(position).getImage_link());
//        if (imgFile.exists()){
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            ImageView ivIcon = (ImageView) row.findViewById(R.id.imgIcon);
//            ivIcon.setImageBitmap(myBitmap);
//        }
        tvRecipeId.setText(data.get(position).getRecipeId().toString());
        tvName.setText(data.get(position).getName());
        tvDescription.setText(data.get(position).getDescription());
        //int resourceId = Activity.getResources().getIdentifier("testimage", "drawable", "your.package.name");
        ivImage.setImageResource(getContext().getResources().getIdentifier(data.get(position).getImage_link(), "drawable", "eu.netcoms.team.radeva.dr.myrecipe"));
        return row;
    }
}
