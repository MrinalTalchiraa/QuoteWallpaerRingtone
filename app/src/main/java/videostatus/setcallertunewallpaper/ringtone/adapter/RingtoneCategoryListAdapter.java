package videostatus.setcallertunewallpaper.ringtone.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.quotestatus.saved.ui.QuotesSavedListActivity;
import videostatus.setcallertunewallpaper.quotestatus.ui.QuotesMainActivity;
import videostatus.setcallertunewallpaper.ringtone.model.RingtoneCategoryData;
import videostatus.setcallertunewallpaper.ringtone.ui.RingtoneCallerCategoryListActivity;

public class RingtoneCategoryListAdapter extends RecyclerView.Adapter<RingtoneCategoryListAdapter.MyViewHolder> {

    Activity mcontext;
    List<RingtoneCategoryData> mlist = new ArrayList<>();

    public RingtoneCategoryListAdapter(Activity context, List<RingtoneCategoryData> list) {

        this.mcontext = context;
        this.mlist = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallpaper_category_list, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        String mName = mlist.get(position).getCatename();

        holder.catename.setText(mName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int remainder = position % 2;

                if(remainder == 0) {

                    Intent intent = new Intent(mcontext, RingtoneCallerCategoryListActivity.class);
                    intent.putExtra("catename", mlist.get(position).getCatename());
                    intent.putExtra("position", position);
                    //intent.putExtra("size", mlist.get(position).getSize());
                    SplashLaunchActivity.InterstitialAdsCall(mcontext, intent);
                    mcontext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }else {

                    Intent intent = new Intent(mcontext, RingtoneCallerCategoryListActivity.class);
                    intent.putExtra("catename", mlist.get(position).getCatename());
                    intent.putExtra("position", position);
                    //intent.putExtra("size", mlist.get(position).getSize());
                    mcontext.startActivity(intent);
                    mcontext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }



            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView catename;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            catename = (TextView) itemView.findViewById(R.id.catename);

        }
    }

}
