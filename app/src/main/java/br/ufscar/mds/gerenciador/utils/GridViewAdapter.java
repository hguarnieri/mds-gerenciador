package br.ufscar.mds.gerenciador.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufscar.mds.gerenciador.R;
import br.ufscar.mds.gerenciador.data.Nota;


/**
 * Created by gabri on 12/02/2017.
 */

public class GridViewAdapter extends ArrayAdapter<Nota> {
    private final String TAG = GridViewAdapter.class.getSimpleName();
    private Context context;
    private int layoutResourceId;
    private List<Nota> data;



    public GridViewAdapter(Context context, int resource, List<Nota> data) {
        super(context,resource,data);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId,parent,false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        }else{
            holder = (ViewHolder) row.getTag();
        }
        Nota note = data.get(position);
        //Calculate dp values to load thumb image
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        //TODO Pegar data a partir do nome do arquivo
        String pattern = "JPEG_(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(note.getCaminho());
        if (m.find( )) {
            DateFormat formatInpt = new SimpleDateFormat("yyyyMMdd_HHmmss");
            DateFormat formatOutpt = new SimpleDateFormat("dd/MM/yyyy\nHH:mm:ss");
            try {
                Date date = formatInpt.parse(m.group(0).substring(5,21));
                holder.imageTitle.setText(formatOutpt.format(date));
//                holder.imageTitle.setText(m.group(0));
            }catch (ParseException e){
                Log.e(TAG,"Parse Exception",e);
                holder.imageTitle.setText("Xablau");
            }

        }else
            holder.imageTitle.setText("Xablau");
        holder.image.setImageBitmap(note.loadThumbImage(100*dpWidth,100*dpHeight));
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}
