package videostatus.setcallertunewallpaper.wallpaper.saved.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.wallpaper.saved.model.FileModel;
import videostatus.setcallertunewallpaper.wallpaper.saved.ui.WallpaperSavedListActivity;


import java.io.File;
import java.util.ArrayList;

public class WallpaperSavedImageAdapter extends PagerAdapter {
    Context context;
    LayoutInflater mLayoutInflater;
    private ArrayList<FileModel> ImagesList = WallpaperSavedListActivity.FilePathStrings;

    public WallpaperSavedImageAdapter(Context context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return ImagesList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.getimageview);
        FileModel fileModel = new FileModel();
        fileModel = (FileModel) this.ImagesList.get(position);
        String imageFilePath = fileModel.getImageFilePath();
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        Glide.with(this.context).load(new File(imageFilePath)).fitCenter().into(imageView);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
