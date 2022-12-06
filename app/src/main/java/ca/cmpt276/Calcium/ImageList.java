package ca.cmpt276.Calcium;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;

public class ImageList extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> name;

    public ImageList(@NonNull Context context, ArrayList<String> s) {
        super(context, R.layout.config_item, R.id.name, s);
        this.context = context;
        this.name = s;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(row == null){
            row = layoutInflater.inflate(R.layout.config_item, null, true);
        }
        TextView textViewCountry = (TextView) row.findViewById(R.id.name);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.icon);


        textViewCountry.setText(name.get(position));

        File f = new File("data/data/ca.cmpt276.Calcium/files/Pictures/" + name.get(position) + ".jpg");
        if (!f.exists()){
            imageFlag.setImageResource(R.drawable.ic_default_box);
        }
        else{
            imageFlag.setImageURI(Uri.fromFile(f));
        }
        return  row;
    }
}
