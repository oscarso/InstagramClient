package co.oscarsoft.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.oscarsoft.instagramclient.R;
import co.oscarsoft.instagramclient.InstagramPhoto;

/**
 * Created by oscarso on 12/5/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);

        tvCaption.setText(photo.caption);
        ivPhoto.setImageResource(0);//clear image
        Picasso.with(getContext()).load(photo.imageUrl).fit().centerInside().into(ivPhoto);

        return convertView;
    }
}
