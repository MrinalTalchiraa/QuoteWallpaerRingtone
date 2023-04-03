package videostatus.setcallertunewallpaper.wallpaper.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.io.Serializable;
import java.util.List;

import videostatus.setcallertunewallpaper.R;

import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.wallpaper.model.WallpaperData;
import videostatus.setcallertunewallpaper.wallpaper.ui.WallpaperViewActivity;

public class WallpaperListAdapter extends RecyclerView.Adapter<WallpaperListAdapter.MyViewHolder> {

    Activity mcontext;
    String mCatename;

    Uri ImageLink;

    List<WallpaperData> list;

    Intent intentMain;

    public WallpaperListAdapter(Activity context, String catename, List<WallpaperData> list1) {

        this.mcontext = context;
        this.mCatename = catename;
        this.list = list1;


    }

    @NonNull
    @Override
    public WallpaperListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.img_list, parent, false);
        return new WallpaperListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperListAdapter.MyViewHolder holder, final int position) {


            Glide.with(mcontext)
                    .asBitmap()
                    .load(list.get(position).getUri())
                    .placeholder(R.drawable.imgload)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(holder.imageViewWallpaper);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ImageLink = Uri.parse(list.get(position).getUri());


                    int remainder = position % 2;

                    if(remainder == 0) {

                        intentMain = new Intent(mcontext, WallpaperViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("arraylist", (Serializable) list);
                        intentMain.putExtras(bundle);
                        intentMain.putExtra("imagelink", ImageLink);
                        intentMain.putExtra("position", position);

                        CallAct1();



                    }else {

                        Intent intent = new Intent(mcontext, WallpaperViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("arraylist", (Serializable) list);
                        intent.putExtras(bundle);
                        intent.putExtra("imagelink", ImageLink);
                        intent.putExtra("position", position);
                        mcontext.startActivity(intent);
                        mcontext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }

                }
            });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewWallpaper;
        // TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewWallpaper = (ImageView) itemView.findViewById(R.id.image);
            // name = (TextView) itemView.findViewById(R.id.name);

        }
    }

    private void CallAct1() {
        SplashLaunchActivity.InterstitialAdsCall(mcontext, intentMain);
        mcontext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
