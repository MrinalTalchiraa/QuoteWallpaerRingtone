package videostatus.setcallertunewallpaper.quotestatus.adapter;

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

import java.io.Serializable;
import java.util.List;

import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.quotestatus.model.QuotesImageData;
import videostatus.setcallertunewallpaper.quotestatus.ui.QuotesImageViewActivity;

public class QuotesListAdapter extends RecyclerView.Adapter<QuotesListAdapter.MyViewHolder> {

    Activity mcontext;
    String mCatename;

    Uri ImageLink;

    List<QuotesImageData> list;

    Intent intentMain;

    public QuotesListAdapter(Activity context, String catename, List<QuotesImageData> list) {

        this.mcontext = context;
        this.mCatename = catename;
        this.list = list;

    }

    @NonNull
    @Override
    public QuotesListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.img_list, parent, false);
        return new QuotesListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull QuotesListAdapter.MyViewHolder holder, final int position) {

        Glide.with(mcontext)
                .load(list.get(position).getUri())
                .placeholder(R.drawable.imgload)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageLink = Uri.parse(list.get(position).getUri());


                int remainder = position % 2;

                if(remainder == 0) {

                    intentMain = new Intent(mcontext, QuotesImageViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("arraylist", (Serializable) list);
                    intentMain.putExtras(bundle);
                    intentMain.putExtra("imagelink", ImageLink);
                    intentMain.putExtra("position", position);


                    CallAct1();


                }else {

                    Intent intent = new Intent(mcontext, QuotesImageViewActivity.class);
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

        ImageView imageView;
        //TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image);
            //name = (TextView) itemView.findViewById(R.id.name);

        }
    }

    private void CallAct1() {
        SplashLaunchActivity.InterstitialAdsCall(mcontext, intentMain);
        mcontext.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
