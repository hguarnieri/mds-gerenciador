package br.ufscar.mds.gerenciador.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.api.client.util.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ufscar.mds.gerenciador.R;
import br.ufscar.mds.gerenciador.data.Atividade;
import br.ufscar.mds.gerenciador.database.DbInterface;

/**
 * Created by Henrique on 14/01/2017.
 */

public class ListViewAtividadesAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    List<Atividade> atividadeList = new ArrayList<Atividade>();

    public ListViewAtividadesAdapter(Context context, List<Atividade> atividades) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        this.atividadeList = atividades;
    }

    @Override
    public int getCount() {
        return atividadeList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView textViewTitle;
        TextView textViewDate;
        View viewCor;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
        Atividade atividade  = atividadeList.get(position);
        Holder holder = new Holder();
        View rowView  = inflater.inflate(R.layout.list_atividade_item, null);

        holder.textViewTitle = (TextView) rowView.findViewById(R.id.list_atividade_item_titulo);
        holder.textViewDate  = (TextView) rowView.findViewById(R.id.list_atividade_item_data);
        holder.viewCor       = rowView.findViewById(R.id.list_atividade_item_cor);

        holder.textViewTitle.setText(atividade.getTitulo());
        holder.textViewDate.setText(sdf.format(atividade.getData()));

        //Aqui vai a cor relacionada a atividade
        holder.viewCor.setBackgroundColor(rowView.getResources().getColor(getColorBasedOnDate(atividade.getData())));

        return rowView;
    }

    public int getColorBasedOnDate(Date date) {
        Calendar twoDayCalendar = Calendar.getInstance();
        twoDayCalendar.add(Calendar.DAY_OF_YEAR, 2);

        Calendar oneWeekCalendar = Calendar.getInstance();
        oneWeekCalendar.add(Calendar.DAY_OF_YEAR, 7);

        Calendar twoWeekCalendar = Calendar.getInstance();
        twoWeekCalendar.add(Calendar.DAY_OF_YEAR, 14);

        if (date.after(twoWeekCalendar.getTime())) {
            return R.color.dark_green;
        } else if (date.after(oneWeekCalendar.getTime())) {
            return R.color.yellow;
        } else if (date.after(twoDayCalendar.getTime())) {
            return R.color.orange;
        } else {
            return R.color.red;
        }
    }


}