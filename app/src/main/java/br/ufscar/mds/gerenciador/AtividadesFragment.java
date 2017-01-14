package br.ufscar.mds.gerenciador;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.ufscar.mds.gerenciador.utils.ListViewAtividadesAdapter;

public class AtividadesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.view_atividades, container, false);

        ListView listViewAtividades = (ListView) view.findViewById(R.id.list_view_atividades);
        listViewAtividades.setAdapter(new ListViewAtividadesAdapter(getContext()));

        return view;
    }

}
