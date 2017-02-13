package br.ufscar.mds.gerenciador;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.ufscar.mds.gerenciador.data.Curso;
import br.ufscar.mds.gerenciador.data.Semestre;
import br.ufscar.mds.gerenciador.database.DbInterface;
import br.ufscar.mds.gerenciador.utils.ListViewCursosAdapter;

public class CursosFragment extends Fragment {

    private final String TAG = CursosFragment.class.getSimpleName();

    BroadcastReceiver receiver;
    ListView listViewCursos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        if (DbInterface.getAllCourses(getContext()).size() <= 0) {
            Semestre semestre1 = new Semestre(1,"Semestre2017",2017,1);
            Curso curso1 = new Curso(1, "Computação Gráfica", "Terça 14:00 às 16:00 - DC", "Terça 16:00 às 18:00 - DC", 1, 0);
            Curso curso2 = new Curso(2, "Teoria dos Gráfos", "Segunda 08:00 às 10:00 - AT9 Sala 221", "Segunda 16:00 às 18:00 - AT9 Sala 222", 1, 0);
            Curso curso3 = new Curso(3, "Metodologia de Desenvolvimento de Sistemas", "Segunda 08:00 às 10:00 - DC", "Segunda 10:00 às 12:00 - DC", 1, 0);

            DbInterface.saveSemester(getContext(),semestre1);
            DbInterface.saveCourse(getContext(), curso1);
            DbInterface.saveCourse(getContext(), curso2);
            DbInterface.saveCourse(getContext(), curso3);
        }

        View view = inflater.inflate(R.layout.view_cursos, container, false);


        return view;
    }

    private void refreshListView() {
        final List<Curso> courses = DbInterface.getAllCourses(getContext());

        this.listViewCursos = (ListView) getActivity().findViewById(R.id.list_view_cursos);
        this.listViewCursos.setAdapter(new ListViewCursosAdapter(getContext(), courses));
        this.listViewCursos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Bundle b = new Bundle();
                b.putInt("courseId", courses.get(pos).getId());

                Intent i = new Intent(getActivity(), CursoActivity.class);
                i.putExtras(b);
                startActivity(i);
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

        getActivity().registerReceiver(receiver, new IntentFilter("refreshCourses"));
    }

    @Override
    public void onPause() {
        super.onPause();
        try{
            getActivity().unregisterReceiver(receiver);
        }catch (IllegalArgumentException ex){
            Log.e(TAG,"Error",ex);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            getActivity().unregisterReceiver(receiver);
        }catch (IllegalArgumentException ex){
            Log.e(TAG,"Error",ex);
        }
    }
}
