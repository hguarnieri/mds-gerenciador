package br.ufscar.mds.gerenciador;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import br.ufscar.mds.gerenciador.utils.ListViewCursosAdapter;

public class CursosFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.view_cursos, container, false);

        ListView listViewCursos = (ListView) view.findViewById(R.id.list_view_cursos);
        listViewCursos.setAdapter(new ListViewCursosAdapter(getContext()));
        listViewCursos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int myItemInt, long l) {
                //TODO Criar visualização da atividade e chamar ela
                Log.v("CursosFragment","Selecionado o item: " + myItemInt);
            }
        });

        return view;
    }

}
