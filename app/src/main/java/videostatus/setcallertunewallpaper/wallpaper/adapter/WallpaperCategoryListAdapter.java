package videostatus.setcallertunewallpaper.wallpaper.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.wallpaper.ui.WallpaperCategoryListActivity;

import java.util.ArrayList;
import java.util.List;

public class WallpaperCategoryListAdapter extends RecyclerView.Adapter {


    private List<Object> mDataset = new ArrayList<>();

    Activity mcontext;

    Intent intentMain;

    public WallpaperCategoryListAdapter(Activity context, List<Object> dataset) {

        this.mcontext = context;
        this.mDataset.addAll(dataset);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallpaper_category_list, parent, false);
        return new WallpaperCategoryListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        String mName = String.valueOf(mDataset.get(position));

        myViewHolder.catename.setText(mName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name1 = String.valueOf(mDataset.get(position));


                int remainder = position % 2;

                if(remainder == 0) {


                    intentMain = new Intent(mcontext, WallpaperCategoryListActivity.class);
                    intentMain.putExtra("catename", name1);
                    intentMain.putExtra("position", position);
                    intentMain.putExtra("name", name1);
                    //intent.putExtra("size", mlist.get(position).getSize());

                    CallAct1();

                }else {

                    Intent intent = new Intent(mcontext, WallpaperCategoryListActivity.class);
                    intent.putExtra("catename", name1);
                    intent.putExtra("position", position);
                    intent.putExtra("name", name1);
                    //intent.putExtra("size", mlist.get(position).getSize());
                    mcontext.startActivity(intent);
                    mcontext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView catename;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            catename = (TextView) itemView.findViewById(R.id.catename);

        }
    }


    private void CallAct1() {
        SplashLaunchActivity.InterstitialAdsCall(mcontext, intentMain);
        mcontext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
