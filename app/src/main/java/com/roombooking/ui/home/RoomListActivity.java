package com.roombooking.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.roombooking.R;
import com.roombooking.RoomBookingApplication;
import com.roombooking.model.Item;
import com.roombooking.ui.home.adapter.RoomsListAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class RoomListActivity extends AppCompatActivity implements RoomsContract.View {
    private RecyclerView rv;
    private ProgressBar progressBar;
    private RoomsListAdapter adapter;
    private Button selectDate;
    @Inject
     RoomsContract.UserActionsListener presenter;
    private Calendar myCalendar = Calendar.getInstance();
    private List<Item> results = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private DatePickerDialog.OnDateSetListener  date;
    private EditText search;
    private String dateStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        ((RoomBookingApplication)getApplication()).getAppComponent().inject(this);
        initView();
        addTextListener();
        presenter.setView(this);
        //Get Rooms for Today
        presenter.getRooms(getString(R.string.date_today));


    }
    private void initView(){
        search = (EditText) findViewById( R.id.search);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        setTitle(getString(R.string.room_title));
        rv = (RecyclerView)findViewById(R.id.recyclerView);
        selectDate = (Button) findViewById(R.id.selectDate);
        rv.setHasFixedSize(true);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.room_list_title);
        adapter = new RoomsListAdapter(this,results);
        rv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        selectDate();
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RoomListActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }
    private void addTextListener(){

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                query = query.toString().toLowerCase();
                final List<Item> filteredList = new ArrayList<>();
                for(Item item: results){
                    if(item.getName().contains(query)){
                        filteredList.add(item);
                    }
                }
                adapter.notifyData(filteredList);

                if(query.length()==0){
                    adapter.notifyData(results);
                }
            }
        });
    }

    
    private void selectDate(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        cal.add(Calendar.HOUR, 1);
        dateStr = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());
        adapter.setDateStr(dateStr);
       SimpleDateFormat targetFormat1 = new SimpleDateFormat("MMM dd yyyy EEEE",Locale.ENGLISH);

        selectDate.setText(targetFormat1.format(new Date()));
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                monthOfYear = monthOfYear+1;
                String mon = "";
                String day = "";
                if(monthOfYear<10){
                    mon= "0"+monthOfYear;
                }else{
                    mon=""+monthOfYear;
                }
                if(dayOfMonth<10){
                    day= "0"+dayOfMonth;
                }else{
                    day=""+dayOfMonth;
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd yyyy EEEE",Locale.ENGLISH);
                df.format(new Date());

                Date date = null;
                try {
                    dateStr = year+"-"+mon+"-"+day;
                    date = df.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                adapter.setDateStr(dateStr);
                long epoch = date.getTime()/1000;
                presenter.getRooms(""+epoch);
                selectDate.setText(targetFormat.format(date));

            }


        };
    }



    @Override
    public void setProgressIndicator(boolean active) {
        if(active){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadRoomsUi(List<Item> response) {
        if(response !=null){
            results.addAll(response);
            adapter.notifyData(response);
        }
    }

    @Override
    public void failedToFetchRooms() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, getString(R.string.failure_message), Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    


}
