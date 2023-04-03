package videostatus.setcallertunewallpaper.ringtone.saved.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.quotestatus.saved.ui.QuotesSavedListActivity;
import videostatus.setcallertunewallpaper.quotestatus.ui.QuotesMainActivity;
import videostatus.setcallertunewallpaper.ringtone.saved.model.RingtoneData;
import videostatus.setcallertunewallpaper.ringtone.saved.ui.RingtonePlayActivity;
import videostatus.setcallertunewallpaper.ringtone.saved.ui.RingtoneSavedListActivity;

public class RingtoneSavedAdapter extends RecyclerView.Adapter<RingtoneSavedAdapter.MyViewHolder> {
    int activity_type;
    int checkBoxVisibility = 8;
    private Activity context;
    public  static ArrayList<RingtoneData> fileModelArrayList;
    boolean isAllSelected;

    int selectedImageCount;

    public RingtoneSavedAdapter(Activity context, ArrayList<RingtoneData> filePathStrings) {
        this.context = context;
        this.fileModelArrayList = filePathStrings;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        List<MyViewHolder> holders = new ArrayList();
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ringtone_list_saved, parent, false));
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) this.context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        RingtoneData fileModel = new RingtoneData();
        fileModel = (RingtoneData) this.fileModelArrayList.get(position);
        String path = fileModel.getUri();
        String name = fileModel.getName();

        if (RingtoneSavedListActivity.FilePathStrings.size() == 0) {

            Toast.makeText(context, "data not found", Toast.LENGTH_SHORT).show();

        } else {


            holder.songname.setText(RingtoneSavedListActivity.FilePathStrings.get(position).getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int remainder = position % 2;

                    if(remainder == 0) {

                        Intent intent = new Intent(context, RingtonePlayActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("arraylist", (Serializable) RingtoneSavedListActivity.FilePathStrings);
                        intent.putExtras(bundle);
                        //intent.putExtra("link", mlist.get(position).getLink());
                        intent.putExtra("position", position);
                        intent.putExtra("offline", true);
                        SplashLaunchActivity.InterstitialAdsCall(context, intent);
                        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }else {

                        Intent intent = new Intent(context, RingtonePlayActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("arraylist", (Serializable) RingtoneSavedListActivity.FilePathStrings);
                        intent.putExtras(bundle);
                        //intent.putExtra("link", mlist.get(position).getLink());
                        intent.putExtra("position", position);
                        intent.putExtra("offline", true);
                        context.startActivity(intent);
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

        TextView songname;

        public MyViewHolder(View itemView) {
            super(itemView);

            songname = (TextView) itemView.findViewById(R.id.name);

        }
    }
}


