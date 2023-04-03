package videostatus.setcallertunewallpaper.quotestatus.adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import videostatus.setcallertunewallpaper.CheckInternet;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.quotestatus.model.QuotesTextdata;

import java.util.ArrayList;
import java.util.List;

public class QuotesTextListAdapter extends RecyclerView.Adapter<QuotesTextListAdapter.MyViewHolder> {

    Context mcontext;
    List<QuotesTextdata> mlist = new ArrayList<>();
    android.content.ClipboardManager clipboardManager;


    public QuotesTextListAdapter(Context context, List<QuotesTextdata> list) {

        this.mcontext = context;
        this.mlist = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_quotes_text, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        //holder.imageQuotes.setText(mlist.get(position).getName());

        Typeface face = Typeface.createFromAsset(mcontext.getAssets(), "fonts/avenirltstd_medium.otf");
        holder.Messages.setTypeface(face);

        //String msgMain = message.get(position);
        String MESSAGES = String.valueOf(Html.fromHtml(mlist.get(position).getLink()));
        holder.Messages.setText(MESSAGES);

        holder.Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String html = mlist.get(position).getLink();
                String copy = String.valueOf(Html.fromHtml(html));
                clipboardManager = (android.content.ClipboardManager) mcontext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("COPY", copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(mcontext, "Copy Text", Toast.LENGTH_SHORT).show();

            }
        });

        holder.WhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isNetworkAvailable(mcontext)) {

                    String html = mlist.get(position).getLink();
                    String copy = String.valueOf(Html.fromHtml(html));

                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, copy);
                    try {
                        mcontext.startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(mcontext, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mcontext, "Internet not connected", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckInternet.isNetworkAvailable(mcontext)) {
                    String html = mlist.get(position).getLink();
                    String copy = String.valueOf(Html.fromHtml(html));

                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setPackage("com.facebook.orca");
                    share.putExtra(Intent.EXTRA_TEXT, copy);
                    share.setType("text/plain");
                    try {
                        mcontext.startActivity(share);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(mcontext, "Facebook Messenger have not been installed.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mcontext, "Internet not connected", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isNetworkAvailable(mcontext)) {
                    String html = mlist.get(position).getLink();
                    String copy = String.valueOf(Html.fromHtml(html));
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, copy);
                    shareIntent.setType("text/plain");
                    mcontext.startActivity(shareIntent);
                } else {
                    Toast.makeText(mcontext, "Internet not connected", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Messages;
        LinearLayout Copy, WhatsApp, Facebook, Share;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.Messages = (TextView) itemView.findViewById(R.id.txtMessage);
            this.Copy = (LinearLayout) itemView.findViewById(R.id.llCopy);
            this.WhatsApp = (LinearLayout) itemView.findViewById(R.id.llWhatsapp);
            this.Facebook = (LinearLayout) itemView.findViewById(R.id.llFacebook);
            this.Share = (LinearLayout) itemView.findViewById(R.id.llShare);

        }
    }
}
