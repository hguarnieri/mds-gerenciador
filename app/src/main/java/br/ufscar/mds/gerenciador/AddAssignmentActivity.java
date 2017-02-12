package br.ufscar.mds.gerenciador;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.TimeZone;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.ufscar.mds.gerenciador.data.Atividade;
import br.ufscar.mds.gerenciador.data.Curso;
import br.ufscar.mds.gerenciador.data.Semestre;
import br.ufscar.mds.gerenciador.database.DbInterface;
import br.ufscar.mds.gerenciador.utils.CourseArrayAdapter;
import br.ufscar.mds.gerenciador.utils.SemesterArrayAdapter;
import pub.devrel.easypermissions.EasyPermissions;

import static br.ufscar.mds.gerenciador.CalendarConnectActivity.REQUEST_GOOGLE_PLAY_SERVICES;

/**
 * Created by gabri on 29/01/2017.
 */

public class AddAssignmentActivity extends Activity{
    private final String TAG = AddAssignmentActivity.class.getSimpleName();
    private EditText mAssignmentTitle;
    private EditText mAssignmentDetalhes;
    private DatePicker mAssignmentDate;
    private TimePicker mAssignmentTime;
    private CheckBox mAddCalendar;

    private Spinner mCourseSpinner;
    private Spinner mSemesterSpinner;
    private Button mAddAssignment;

    GoogleAccountCredential mCredential;
    private static final String[] SCOPES = { CalendarScopes.CALENDAR };
    static final int REQUEST_AUTHORIZATION = 1001;
    private String mDate;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        if (DbInterface.getAllSemesters(getApplicationContext()).size() <= 0) {
            Semestre semestre1 = new Semestre(0, "Semestre 1", 2016, 2);
            Semestre semestre2 = new Semestre(0, "Semestre 2", 2017, 1);

            DbInterface.saveSemester(getApplicationContext(), semestre1);
            DbInterface.saveSemester(getApplicationContext(), semestre2);
        }

        this.mAssignmentTitle = (EditText) findViewById(R.id.activity_add_assignment_titulo);
        this.mAssignmentDetalhes = (EditText) findViewById(R.id.activity_add_assignment_detalhes);
        this.mCourseSpinner = (Spinner) findViewById(R.id.activity_add_assignment_course);
        this.mSemesterSpinner = (Spinner) findViewById(R.id.activity_add_assignment_semester);
        this.mAssignmentDate = (DatePicker) findViewById(R.id.activity_add_assignment_date);
        this.mAssignmentTime = (TimePicker) findViewById(R.id.activity_add_assignment_time);
        this.mAddAssignment = (Button) findViewById(R.id.activity_add_assignment_button_add_assignment);
        this.mAddCalendar = (CheckBox) findViewById(R.id.cb_calendar);

        renderSpinnerSemesters();
        SharedPreferences settings =
                getSharedPreferences("GerenciadorPrefs",Context.MODE_PRIVATE);
        String accountName = settings.getString("accountName","NotDefined");
        Log.i(TAG,"Accout: " + accountName);
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(accountName);

        this.mSemesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                renderSpinnerCourses(((Semestre) mSemesterSpinner.getSelectedItem()).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        this.mAddAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAssignmentTitle.getText() == null) {
                    mAssignmentTitle.setBackgroundColor(Color.RED);
                } else if (mAssignmentDetalhes.getText() == null) {
                    mAssignmentDetalhes.setBackgroundColor(Color.RED);
                } else {
                    String time = mAssignmentTime.getCurrentHour() + ":" +mAssignmentTime.getCurrentMinute() + ":00";
                    String date = mAssignmentDate.getYear() + "-" + (mAssignmentDate.getMonth() + 1) + "-"
                                + mAssignmentDate.getDayOfMonth() + " " + time;
                    mDate = date;
                    System.out.println(date);
                    Date assignmentDate = null;
                    try {
                        assignmentDate = sdf.parse(date);

                        Atividade assignment = new Atividade(0, ((Curso) mCourseSpinner.getSelectedItem()).getId(),
                                mAssignmentTitle.getText().toString(), mAssignmentDetalhes.getText().toString(), assignmentDate);
                        long id = DbInterface.saveAssignment(getApplicationContext(), assignment);

                        if (id > 0) {
                            Toast.makeText(getApplicationContext(), "Salvo!", Toast.LENGTH_SHORT).show();
                            if (mCredential.getSelectedAccountName() != null && mAddCalendar.isChecked()) {
                                new MakeRequestTask(mCredential).execute();
                            }else if(mAddCalendar.isChecked()){
                                Log.i(TAG,"UE");
                                Toast.makeText(getApplicationContext(), "Erro ao adiconar ao Calendar", Toast.LENGTH_LONG).show();
                            }
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao salvar atividade", Toast.LENGTH_SHORT);
                        }
                    } catch (Exception ex) {
                        System.out.println("Error on parsing date for new Assignment");
                        System.out.println(ex);
                    }
                }
            }
        });

    }

    void renderSpinnerSemesters(){
        List<Semestre> semesters = DbInterface.getAllSemesters(getApplicationContext());
        ArrayAdapter spinnerArrayAdpter =  new SemesterArrayAdapter(this, semesters);
        mSemesterSpinner.setAdapter(spinnerArrayAdpter);
    }

    void renderSpinnerCourses(int semesterId) {
        List<Curso> courses = DbInterface.getAllCoursesBySemester(getApplicationContext(), semesterId);
        ArrayAdapter spinnerArrayAdpter =  new CourseArrayAdapter(this, courses);
        mCourseSpinner.setAdapter(spinnerArrayAdpter);
    }



    private class MakeRequestTask extends AsyncTask<Void, Void, Void> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("GerenciadorAcademico")
                    .build();
        }
        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected Void doInBackground(Void... params) {
            try {
                addEventCalendar();
            } catch (Exception e) {
                mLastError = e;
                Log.e(TAG,"Error:",e);
                cancel(true);
                return null;
            }
            return null;
        }

        void addEventCalendar() throws IOException{
            Event event = new Event()
                    .setSummary(mAssignmentTitle.getText().toString())
                    .setDescription(mAssignmentDetalhes.getText().toString());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateTime startDateTime;
            try {
                startDateTime = new DateTime(format.parse(mDate));
            }catch (ParseException ex){
                Log.e(TAG,"Parse error: ",ex);
                startDateTime = new DateTime("2017-01-01T08:00:00");
            }
            TimeZone tz = TimeZone.getDefault();
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone(tz.getID());
            event.setStart(start);

            EventDateTime end = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone(tz.getID());
            event.setEnd(end);

            EventReminder[] reminderOverrides = new EventReminder[] {
                    new EventReminder().setMethod("popup").setMinutes(30),
            };
            Event.Reminders reminders = new Event.Reminders()
                    .setUseDefault(false)
                    .setOverrides(Arrays.asList(reminderOverrides));
            event.setReminders(reminders);

            String calendarId = "primary";
            event = mService.events().insert(calendarId, event).execute();
            Log.i(TAG,"Evento adicionado ao google: " + event.toString());
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            AddAssignmentActivity.REQUEST_AUTHORIZATION);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext())
                            .setMessage("Evento cancelado")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setMessage("The following error occurred:\n"
                                    + mLastError.getMessage());
                    AlertDialog dialog = builder.create();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext())
                        .setMessage("Evento cancelado")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setMessage("Não Foi possivel adicionar o evento ao calendário.");
                AlertDialog dialog = builder.create();
            }
        }

        void showGooglePlayServicesAvailabilityErrorDialog(
                final int connectionStatusCode) {
            GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
            Dialog dialog = apiAvailability.getErrorDialog(
                    AddAssignmentActivity.this,
                    connectionStatusCode,
                    REQUEST_GOOGLE_PLAY_SERVICES);
            dialog.show();
        }
    }
}
