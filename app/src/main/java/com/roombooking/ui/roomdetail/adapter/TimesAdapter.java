package com.roombooking.ui.roomdetail.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.roombooking.R;
import com.roombooking.model.TimeBound;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TimesAdapter extends RecyclerView.Adapter<TimesAdapter.TimesViewHolder> {

    private List<TimeBound> participantList;
    private List<TimeBound> timeList = new ArrayList<>();
    private Context context;
    private NotifyDataToUi uiListener;
    private Date startTime;
    private Date endTime;
    private String dateFormatStart = "";
    private String dateFormatEnd = "";
    private TimePickerDialog timePicker;
    private Date startDate;
    private Date endDate;
    private Date selectedDate;
    private boolean isStartDatePicked;
    private boolean isEndDatePicked;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);


    public TimesAdapter(Context context, List<TimeBound> itemList, NotifyDataToUi uiListener) {
        this.participantList = itemList;
        timeList = TimeBound.createDeepcopy(participantList,timeList);
        this.context = context;
        this.uiListener = uiListener;

    }

    public interface NotifyDataToUi{
        void updateStatus(String startTime,String endTime);
    }

    public void notifyData(List<TimeBound> participantList){
        this.participantList = participantList;
        timeList.clear();
        timeList = TimeBound.createDeepcopy(participantList,timeList);
        startTime=null;
        endTime=null;
        isStartDatePicked=false;
        isEndDatePicked=false;
        notifyDataSetChanged();

    }

    @Override
    public TimesViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_view_row, null);
        TimesViewHolder timesViewHolder = new TimesViewHolder(layoutView);
        return timesViewHolder;
    }

    @Override
    public void onBindViewHolder(final TimesViewHolder holder, int position) {
        Calendar currentTime = Calendar.getInstance();
       final int hour = currentTime.get(Calendar.HOUR_OF_DAY);
       final  int minute = currentTime.get(Calendar.MINUTE);

        final TimeBound bookedSlot = getItem(position);
        final TimeBound selectedTime = timeList.get(position);
        holder.startTimeButton.setText(bookedSlot.startTime);
        holder.endTimeButton.setText(bookedSlot.endTime);


        holder.startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime = pickTime(selectedMinute,selectedHour,selectedTime);
                        if(startTime==null){
                            return;
                        }
                        if(endTime!=null) {

                            if (!startTime.before(endTime)) {
                                Toast.makeText(context, context.getString(R.string.before_end_msg), Toast.LENGTH_SHORT).show();
                                return;
                            }else{
                                dateFormatEnd = " "+sdf.format(endTime)+" +0200";
                                bookedSlot.endTime=dateFormatEnd;

                            }
                        }else{
                            dateFormatEnd = " "+sdf.format(endDate)+" +0100";
                            bookedSlot.endTime=dateFormatEnd;

                        }
                        isStartDatePicked=true;

                        bookedSlot.startTime = " "+sdf.format(startTime)+" +0100";
                        holder.startTimeButton.setText(selectedHour+" : "+selectedMinute);
                    }
                }, hour, minute, true);//


                timePicker.setTitle("Select Time");
                timePicker.show();
            }
        });
       holder.setTimeButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!isStartDatePicked){
                   try {
                       startDate = sdf.parse(bookedSlot.startTime);
                       bookedSlot.startTime = " "+sdf.format(startDate)+" +0100";

                       if(!isEndDatePicked) {
                           endDate = sdf.parse(bookedSlot.endTime);
                           bookedSlot.endTime = " " + sdf.format(endDate) + " +0100";
                       }
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
               }
               uiListener.updateStatus(bookedSlot.startTime,bookedSlot.endTime);

           }
       });
       holder.endTimeButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               timePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                       endTime = pickTime(selectedMinute,selectedHour,selectedTime);
                       if(endTime==null){
                           return;
                       }
                       if(startTime!=null) {
                           if (!endTime.after(startTime)) {
                               Toast.makeText(context, context.getString(R.string.after_start_msg), Toast.LENGTH_SHORT).show();
                               return;
                           }else{
                               dateFormatStart = " "+sdf.format(startTime)+" +0100";
                               bookedSlot.startTime=dateFormatStart;
                           }
                       }else{
                           dateFormatStart = " "+sdf.format(startDate)+" +0100";
                           bookedSlot.startTime=dateFormatStart;
                       }
                        isEndDatePicked = true;
                       holder.endTimeButton.setText(selectedHour+" : "+selectedMinute);
                       bookedSlot.endTime=" "+sdf.format(endTime)+" +0100";;
                   }
               }, hour, minute, true);//Yes 24 hour time

               timePicker.setTitle("Select Time");
               timePicker.show();
           }


       });

    }

    private Date pickTime(int selectedMinute, int selectedHour, TimeBound selectedTimeStr) {

        if(selectedMinute==0){
            selectedMinute=00;
        }


        String time = "0"+selectedHour+":"+selectedMinute;

        try {
            startDate = sdf.parse(selectedTimeStr.startTime);
            endDate = sdf.parse(selectedTimeStr.endTime);
            selectedDate = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(!selectedDate.after(startDate)){
            Toast.makeText(context,context.getString(R.string.after_start_msg),Toast.LENGTH_SHORT).show();
            return null;
        }
        else  if(!selectedDate.before(endDate)&&!selectedDate.equals(endDate)){
            Toast.makeText(context,context.getString(R.string.before_end_msg),Toast.LENGTH_SHORT).show();
            return null;
        }
        return selectedDate;
    }


    @Override
    public int getItemCount() {
        return this.participantList.size();
    }

    private TimeBound getItem(int position) {
        return participantList.get(position);
    }


    class TimesViewHolder extends RecyclerView.ViewHolder {
        public Button startTimeButton;
        public Button endTimeButton;
        public Button setTimeButton;
        public TimesViewHolder(View itemView) {
            super(itemView);
            startTimeButton = (Button) itemView.findViewById(R.id.starttime);
            endTimeButton = (Button) itemView.findViewById(R.id.endtime);
            setTimeButton = (Button) itemView.findViewById(R.id.setTimeButton);
        }
    }
}