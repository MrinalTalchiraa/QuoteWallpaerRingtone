package videostatus.setcallertunewallpaper.quotestatus.adapter;

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
import videostatus.setcallertunewallpaper.quotestatus.model.QuotesCategoryData;
import videostatus.setcallertunewallpaper.quotestatus.ui.QuotesSubMainActivity;

import java.util.ArrayList;
import java.util.List;

public class QuotesCategoryListAdapter extends RecyclerView.Adapter<QuotesCategoryListAdapter.MyViewHolder> {

    public static String Catename;
    public static int Position;

    Activity mcontext;
    List<QuotesCategoryData> mlist = new ArrayList<>();

    Intent intentMain;

    public QuotesCategoryListAdapter(Activity context, List<QuotesCategoryData> list) {

        this.mcontext = context;
        this.mlist = list;

    }

    @NonNull
    @Override
    public QuotesCategoryListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallpaper_category_list, parent, false);
        return new QuotesCategoryListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull QuotesCategoryListAdapter.MyViewHolder holder, final int position) {

        String mName = mlist.get(position).getCatename();

        holder.catename.setText(mName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Catename = mlist.get(position).getCatename();
                Position = position;
                //size = mlist.get(position).getSize();


                int remainder = position % 2;

                if(remainder == 0) {
                    intentMain = new Intent(mcontext, QuotesSubMainActivity.class);
                    CallAct1();
                }else {
                    Intent intent = new Intent(mcontext, QuotesSubMainActivity.class);
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

    private void CallAct1() {
        SplashLaunchActivity.InterstitialAdsCall(mcontext, intentMain);
    }

}
