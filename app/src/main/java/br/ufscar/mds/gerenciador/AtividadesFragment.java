package br.ufscar.mds.gerenciador;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import br.ufscar.mds.gerenciador.database.AtividadeDbHelper;
import br.ufscar.mds.gerenciador.database.CursoDbHelper;
import br.ufscar.mds.gerenciador.database.GerenciadorContract;
import br.ufscar.mds.gerenciador.utils.ListViewAtividadesAdapter;

public class AtividadesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.view_atividades, container, false);

        ListView listViewAtividades = (ListView) view.findViewById(R.id.list_view_atividades);
        listViewAtividades.setAdapter(new ListViewAtividadesAdapter(getContext()));
        listViewAtividades.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int myItemInt, long l) {
                //TODO Criar visualização da atividade e chamar ela
                Log.v("AtividadesFragment","Selecionado o item: " + myItemInt);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
