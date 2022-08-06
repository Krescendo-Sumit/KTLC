package krescendo.sumit.kresc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by user on 9/3/2016.
 */
public class Adapter extends ArrayAdapter<String> {
    String[] name = {};
    //String[] teams = {};
    String[] goals = {};
    String[] filepath = {};
    String[] videopath = {};
    String[] vstatus = {};
    String[] vcost = {};
    String[] vid = {};
    int[] imgs = {};
    Context context;
    LayoutInflater layoutInflater;

    public Adapter(Context context, String[] name, String[] goals, int[] imgs,String filepath[],String videopath[],String vstatus[],String vcost[],String vid[]) {
        super(context, R.layout.model, name);

        this.context = context;
        this.name = name;
        //  this.teams = teams;
        this.goals = goals;
        this.imgs = imgs;
        this.filepath=filepath;
        this.videopath=videopath;
        this.vstatus=vstatus;
        this.vcost=vcost;
        this.vid=vid;
    }

    public class ViewHolder {
        TextView txtname, team, goal,txt_play;
        ImageView img;
        WebView web;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
try {
    if (convertView == null) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.model, null);

    }
    final ViewHolder viewHolder = new ViewHolder();
    viewHolder.txtname = (TextView) convertView.findViewById(R.id.txtname);
    viewHolder.goal = (TextView) convertView.findViewById(R.id.txtgoals);
    viewHolder.txt_play = (TextView) convertView.findViewById(R.id.txt_play);
    viewHolder.web = (WebView) convertView.findViewById(R.id.web);
    // viewHolder.team = (TextView) convertView.findViewById(R.id.txtteam);
    // viewHolder.img = (ImageView) convertView.findViewById(R.id.img_ph);
    viewHolder.web.loadData("<html><body><center><img src='" + new JsonBilder().getHostName() + "krishna/imgaudio/" + filepath[position] + "' width='100%' height='150'></img></center></body></html>", "text/html", "UTF-8");
    Log.i("Content Web", "<html><body><center><img src='" + new JsonBilder().getHostName() + "krishna/imgaudio/" + filepath[position] + "' width='100%' height='150'></img></center></body></html>");


    //  viewHolder.img.setImageResource(imgs[position]);
    //  viewHolder.team.setText(teams[position]);

    viewHolder.txtname.setText(name[position]);
    //viewHolder.goal.setText(">");
    //  viewHolder.goal.setBackgroundResource(R.drawable.arrow);
    viewHolder.txt_play.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                Toast.makeText(context, "" + videopath[position], Toast.LENGTH_SHORT).show();
                if (vstatus[position].equals("0")) {
                    Intent i = new Intent(context, AudioPlay.class);
                    i.putExtra("vname", videopath[position].trim());
                    i.putExtra("aname", name[position].trim());
                    i.putExtra("iname",filepath[position].trim());

                    context.startActivity(i);
                } else if (vstatus[position].equals("1")) {

                    Intent i = new Intent(context, Transaction.class);
                    i.putExtra("cost", vcost[position].toString().trim());
                    i.putExtra("vid", vid[position].toString().trim());
                    i.putExtra("vname", videopath[position].trim());
                    i.putExtra("iname",filepath[position].trim());
                    i.putExtra("type", "A");
                    context.startActivity(i);
                } else {
                    Toast.makeText(context, "Sorry Video in Progress", Toast.LENGTH_SHORT).show();
                }
                // new ListVideos().startDownload(new JsonBilder().getHostName()+"video/"+videopath[position].trim(),videopath[position].trim());
            }catch(Exception e)
            {
                Log.i("FLAG ADAPTER",e.getMessage());
                Toast.makeText(context,"Error is "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    });
}catch(Exception e)
{
    Toast.makeText(context,"Error is "+e.getMessage(),Toast.LENGTH_LONG).show();
}
        return convertView;

    }
}
