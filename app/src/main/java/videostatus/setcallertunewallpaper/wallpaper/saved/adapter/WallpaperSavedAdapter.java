package videostatus.setcallertunewallpaper.wallpaper.saved.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.wallpaper.saved.model.FileModel;
import videostatus.setcallertunewallpaper.wallpaper.saved.ui.WallpaperViewSavedActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WallpaperSavedAdapter extends RecyclerView.Adapter<WallpaperSavedAdapter.MyViewHolder> {

    int activity_type;
    int checkBoxVisibility = 8;
    private Activity context;
    public  static ArrayList<FileModel> fileModelArrayList;
    boolean isAllSelected;

    int selectedImageCount;

    Intent intentMain;

    public WallpaperSavedAdapter(Activity context, ArrayList<FileModel> filePathStrings) {
        this.context = context;
        this.fileModelArrayList = filePathStrings;
    }

    public WallpaperSavedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        List<WallpaperSavedAdapter.MyViewHolder> holders = new ArrayList();
        return new WallpaperSavedAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_for_image, parent, false));
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(final WallpaperSavedAdapter.MyViewHolder holder, final int position) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) this.context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        FileModel fileModel = new FileModel();
        fileModel = (FileModel) this.fileModelArrayList.get(position);
        String imageFilePath = fileModel.getImageFilePath();
        final String imageFileName = fileModel.getImageFileName();
        Log.e("file path in adapter " + position + "  ", imageFilePath);
        Boolean isImageFileChecked = fileModel.getImageChecked();
        Log.e("file image is visiblity or not", isImageFileChecked + "");
        if (imageFilePath != null) {
            //Glide.with(this.context).load(new File(imageFilePath)).override((width - 10) / 3, 180).fitCenter().centerCrop().into(holder.img);

            Glide.with(context).load(new File(imageFilePath)).into(holder.img);

            holder.img.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    int remainder = position % 2;

                    if(remainder == 0) {

                        intentMain = new Intent(context, WallpaperViewSavedActivity.class);
                        intentMain.putExtra("type", activity_type);
                        intentMain.putExtra("Position", position);
                        intentMain.putExtra("Vplay", ((FileModel) fileModelArrayList.get(position)).getImageFilePath());

                        CallAct1();


                    }else {

                        Intent i = new Intent(context, WallpaperViewSavedActivity.class);
                        i.putExtra("type", activity_type);
                        i.putExtra("Position", position);
                        i.putExtra("Vplay", ((FileModel) fileModelArrayList.get(position)).getImageFilePath());
                        context.startActivity(i);
                        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                    }


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return fileModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.img = (ImageView) itemView.findViewById(R.id.img);

        }
    }

    private void CallAct1() {
        SplashLaunchActivity.InterstitialAdsCall(context, intentMain);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}


