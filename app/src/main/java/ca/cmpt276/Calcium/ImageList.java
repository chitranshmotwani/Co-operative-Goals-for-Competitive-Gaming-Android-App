package ca.cmpt276.Calcium;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ImageList extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> name;
    ArrayList<Bitmap> icon;

    public ImageList(@NonNull Context context, ArrayList<String> s, ArrayList<Bitmap> b) {
        super(context, R.layout.config_item, R.id.name, s);
        this.context = context;
        this.name = s;
        this.icon = b;
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

        if (icon.get(position) != null){
            Toast.makeText(context, "BBHBKHBKJ", Toast.LENGTH_SHORT).show();
        }
        textViewCountry.setText(name.get(position));
//        imageFlag.setImageBitmap(icon.get(position));
        return  row;
    }
}
