package br.ufscar.mds.gerenciador;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ufscar.mds.gerenciador.data.Atividade;
import br.ufscar.mds.gerenciador.data.Curso;
import br.ufscar.mds.gerenciador.database.DbInterface;
import br.ufscar.mds.gerenciador.utils.ListViewAtividadesAdapter;
import br.ufscar.mds.gerenciador.utils.ListViewCursosAdapter;

public class AtividadesFragment extends Fragment {

    BroadcastReceiver receiver;
    ListView listViewAtividades;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.view_atividades, container, false);

        if (DbInterface.getAllFutureAssignments(getContext()).size() == 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, 2);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(new Date());
            calendar2.add(Calendar.DAY_OF_YEAR, 6);

            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(new Date());
            calendar3.add(Calendar.DAY_OF_YEAR, 15);

            Atividade atividade1 = new Atividade(1, 1, "Fazer o T1 de MDS", "Desenvolver as telas", calendar.getTime());
            Atividade atividade2 = new Atividade(2, 1, "Fazer o T1 de CG", "Desenvolver o jogo", calendar2.getTime());
            Atividade atividade3 = new Atividade(2, 1, "Fazer o T1 de Grafos", "Desenvolver os grafos", calendar3.getTime());

            DbInterface.saveAssignment(getContext(), atividade1);
            DbInterface.saveAssignment(getContext(), atividade2);
            DbInterface.saveAssignment(getContext(), atividade3);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void refreshListView() {
        final List<Atividade> atividades = DbInterface.getAllFutureAssignments(getContext());

        listViewAtividades = (ListView) getActivity().findViewById(R.id.list_view_atividades);
        listViewAtividades.setAdapter(new ListViewAtividadesAdapter(getContext(), atividades));
        listViewAtividades.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int myItemInt, long l) {
                //TODO Criar visualização da atividade e chamar ela
                Log.v("AtividadesFragment","Selecionado o item: " + myItemInt);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshListView();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refreshListView();
            }
        };

        getActivity().registerReceiver(receiver, new IntentFilter("refreshAssignment"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }
}
