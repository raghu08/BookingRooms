package com.roombooking.ui.roomdetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roombooking.R;
import com.roombooking.custom.ViewPagerImageAdapter;
import com.roombooking.databinding.ActivityRoomDetailBinding;
import com.roombooking.model.ItemParcelable;
import com.roombooking.model.SendPassModel;
import com.roombooking.model.TimeBound;
import com.roombooking.ui.addparticipant.AddParticipantActivity;
import com.roombooking.ui.roomdetail.adapter.TimesAdapter;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class RoomDetailActivity extends AppCompatActivity implements TimesAdapter.NotifyDataToUi {

    private ViewPager pager;
    private String[] times;
    private ArrayList<TimeBound> bounds = new ArrayList<>();
    private String dateStr;
    private String startTime;
    private String endTime;
    private TextView setTime;
    private TextView reservation;
    private Button reset;
    private RecyclerView rv;
    public final static String PARCEL = "parcel";
    public final static String DATE_STRING = "dateStr";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        dateStr = getIntent().getExtras().getString(DATE_STRING);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void init() {
        final ItemParcelable item = getIntent().getExtras().getParcelable(PARCEL);
        ActivityRoomDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_room_detail);
        binding.setItem(item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.room_detail_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView location = (TextView) findViewById(R.id.location);
        setTime = (TextView) findViewById(R.id.setTime);
        reservation = (TextView) findViewById(R.id.reservation);
        reset = (Button) findViewById(R.id.reset);

      //  TextView name = (TextView) findViewById(R.id.room_name);
        TextView equipment = (TextView) findViewById(R.id.equipment_list);
     //   TextView size = (TextView) findViewById(R.id.size);
      //  TextView capacity = (TextView) findViewById(R.id.capacity);
        Button book = (Button) findViewById(R.id.book_room);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reset.getVisibility()==View.GONE){
                    Toast.makeText(RoomDetailActivity.this,R.string.time_set_msg,Toast.LENGTH_SHORT).show();
                    return;
                }
                SendPassModel model = new SendPassModel();
                model.setName(item.getName());
                model.setStartTime(startTime);
                model.setEndTime(endTime);
                model.setDate(dateStr);
                Intent intent = new Intent(RoomDetailActivity.this, AddParticipantActivity.class);
                intent.putExtra(AddParticipantActivity.SEND_PASS, model);
                startActivity(intent);
            }
        });
      //  location.setText(item.getLocation());
      //  name.setText(item.getName());
        for (String equipmentStr : item.getEquipment()) {
            equipment.append(equipmentStr + "\n");
        }

        updateTimebounds(item);


       // size.setText(item.getSize());
      //  capacity.setText(item.getCapacity());
        pager = (ViewPager) findViewById(R.id.pager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        ViewPagerImageAdapter adapter = new ViewPagerImageAdapter(RoomDetailActivity.this, item.getImages());
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());

        rv = (RecyclerView) findViewById(R.id.recyclerView);

        final TimesAdapter timeAdapter = new TimesAdapter(this, bounds, this);
        rv.setAdapter(timeAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
       reset.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               bounds.clear();
               rv.setVisibility(View.VISIBLE);
               reset.setVisibility(View.GONE);
               setTime.setVisibility(View.GONE);
               reservation.setText(R.string.reservation_info);
               updateTimebounds(item);
               timeAdapter.notifyData(bounds);
           }
       });


    }

    private void updateTimebounds(ItemParcelable item){
        for (String time : item.getAvail()) {
            times = time.split("-");
            TimeBound bound = new TimeBound();
            bound.startTime = times[0];
            bound.endTime = times[1];
            bounds.add(bound);

        }
    }


    @Override
    public void updateStatus(String startTime, String endTime) {
        this.startTime = dateStr + startTime;
        this.endTime = dateStr + endTime;
        rv.setVisibility(View.GONE);
        setTime.setVisibility(View.VISIBLE);
        reset.setVisibility(View.VISIBLE);
        reservation.setText(R.string.time_set);
        setTime.setText(startTime.substring(0, 6) + " - " + endTime.substring(0, 6));

    }
}
