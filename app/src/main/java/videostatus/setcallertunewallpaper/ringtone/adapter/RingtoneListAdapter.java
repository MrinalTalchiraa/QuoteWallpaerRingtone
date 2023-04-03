package videostatus.setcallertunewallpaper.ringtone.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.quotestatus.saved.ui.QuotesSavedListActivity;
import videostatus.setcallertunewallpaper.quotestatus.ui.QuotesMainActivity;
import videostatus.setcallertunewallpaper.ringtone.ui.RingtoneCallerCategoryListActivity;
import videostatus.setcallertunewallpaper.ringtone.ui.RingtonePlayActivity;

public class RingtoneListAdapter extends RecyclerView.Adapter<RingtoneListAdapter.MyViewHolder> {

    Activity mcontext;
    String mCatename;

    Uri ImageLink;
    //public static ArrayList<String> mWallpaperList;


    public RingtoneListAdapter(Activity context, String catename) {

        this.mcontext = context;
        this.mCatename = catename;

        //mSize = RingtoneCallerCategoryListActivity.list.size();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ringtone_list, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        if (RingtoneCallerCategoryListActivity.list.size() == 0) {

            Toast.makeText(mcontext, "data not found", Toast.LENGTH_SHORT).show();

        } else {


            holder.songname.setText(RingtoneCallerCategoryListActivity.list.get(position).getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int remainder = position % 2;

                    if(remainder == 0) {

                        Intent intent = new Intent(mcontext, RingtonePlayActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("arraylist", (Serializable) RingtoneCallerCategoryListActivity.list);
                        intent.putExtras(bundle);
                        //intent.putExtra("link", mlist.get(position).getLink());
                        intent.putExtra("position", position);
                        intent.putExtra("offline", true);
                        SplashLaunchActivity.InterstitialAdsCall(mcontext, intent);
                        mcontext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }else {

                        Intent intent = new Intent(mcontext, RingtonePlayActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("arraylist", (Serializable) RingtoneCallerCategoryListActivity.list);
                        intent.putExtras(bundle);
                        //intent.putExtra("link", mlist.get(position).getLink());
                        intent.putExtra("position", position);
                        intent.putExtra("offline", true);
                        mcontext.startActivity(intent);
                        mcontext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }

                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return RingtoneCallerCategoryListActivity.list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView songname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            songname = (TextView) itemView.findViewById(R.id.name);

        }
    }

}
