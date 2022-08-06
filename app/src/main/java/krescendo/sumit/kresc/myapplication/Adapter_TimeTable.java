package krescendo.sumit.kresc.myapplication;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 9/3/2016.
 */
public class Adapter_TimeTable extends ArrayAdapter<String> {
    String[] name = {};
    //String[] teams = {};
    String[] goals = {};
    int[] imgs = {};
    Context context;
    LayoutInflater layoutInflater;

    public Adapter_TimeTable(Context context, String[] name, String[] goals, int[] imgs) {
        super(context, R.layout.model_new, name);

        this.context = context;
        this.name = name;
      //  this.teams = teams;
        this.goals = goals;
        this.imgs = imgs;
    }

    public class ViewHolder {
        TextView txtname, team, goal;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.model_new, null);

        }
        final ViewHolder viewHolder = new ViewHolder();
        viewHolder.txtname = (TextView) convertView.findViewById(R.id.txtname);
        viewHolder.goal = (TextView) convertView.findViewById(R.id.txtgoals);
       // viewHolder.team = (TextView) convertView.findViewById(R.id.txtteam);
       // viewHolder.img = (ImageView) convertView.findViewById(R.id.img_ph);


      //  viewHolder.img.setImageResource(imgs[position]);
      //  viewHolder.team.setText(teams[position]);

        viewHolder.txtname.setText(name[position]);
        viewHolder.goal.setText(Html.fromHtml(""+goals[position]));
      //  viewHolder.goal.setBackgroundResource(R.drawable.arrow);

        return convertView;


    }
}
