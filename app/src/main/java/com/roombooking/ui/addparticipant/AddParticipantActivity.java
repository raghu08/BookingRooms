package com.roombooking.ui.addparticipant;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.roombooking.R;
import com.roombooking.RoomBookingApplication;
import com.roombooking.model.Participant;
import com.roombooking.model.SendPassModel;
import com.roombooking.ui.addparticipant.adapter.AddParticipantAdapter;
import com.roombooking.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AddParticipantActivity extends AppCompatActivity
        implements AddParticipantContract.View,AddParticipantAdapter.NotifyDataToUi{
    private EditText phoneEt;
    private EditText nameEt;
    private EditText emailEt;
    private AddParticipantAdapter adapter;
    private List<Participant> participantList  = new ArrayList<>();
    private ProgressBar progressBar;
    private TextView participantLabel;
    private Button sendPassButton;
    private SendPassModel model;
    public final static String SEND_PASS  = "sendpass";
    @Inject
     AddParticipantContract.UserActionsListener presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        ((RoomBookingApplication)getApplication()).getAppComponent().inject(this);
        presenter.setView(this);
        model = getIntent().getExtras().getParcelable(SEND_PASS);
        initUi();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void initUi() {
        phoneEt = (EditText) findViewById(R.id.phoneEt);
        nameEt = (EditText) findViewById(R.id.nameEt);
        emailEt = (EditText) findViewById(R.id.emailEt);
        participantLabel = (TextView) findViewById(R.id.addParticipantLabel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.add_participant_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new AddParticipantAdapter(this,participantList,this);
        rv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
         sendPassButton = (Button) findViewById(R.id.sendPasses);
        sendPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendPasses(model,participantList);

            }
        });
        Button addParticipantButton = (Button) findViewById(R.id.addParticipantButton);
        addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               validateUserTextFields();
            }
        });


    }

    private void validateUserTextFields(){
        String strEmail = emailEt.getText().toString();
        String strPhone = phoneEt.getText().toString();
        String strName = nameEt.getText().toString();
        if(strName == null || strName.contentEquals(""))
        {
            Toast.makeText(this,R.string.name_error_msg,Toast.LENGTH_SHORT).show();

            nameEt.setText("");
            nameEt.requestFocus();
            return;
        }
        if(strEmail == null || strEmail.contentEquals(""))
        {
            Toast.makeText(this,R.string.email_error_msg,Toast.LENGTH_SHORT).show();

            emailEt.setText("");
            emailEt.requestFocus();
            return;
        }
        if(strPhone == null || strPhone.contentEquals(""))
        {
            Toast.makeText(this,R.string.phone_error_msg,Toast.LENGTH_SHORT).show();

            phoneEt.setText("");
            phoneEt.requestFocus();
            return;
        }
        if(!Validator.validateEmail(strEmail))
        {
            Toast.makeText(this,R.string.email_validation_error_msg,Toast.LENGTH_SHORT).show();

            emailEt.setText("");
            emailEt.requestFocus();
            return;
        }
        else
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(emailEt.getWindowToken(), 0);

            Participant participant = new Participant();
            participant.setEmail(strEmail);
            participant.setPhone(strPhone);
            participant.setName(strName);
            if(participantList.contains(participant)){
                Toast.makeText(this,getString(R.string.already_added_particpant),Toast.LENGTH_SHORT).show();
                return;

            }
            participantList.add(participant);
            sendPassButton.setVisibility(View.VISIBLE);
            participantLabel.setVisibility(View.VISIBLE);
            adapter.notifyData(participantList);

        }


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
    public void SendPassSuccess(String message) {
        if(message !=null){
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,R.string.pass_sent_success_msg,Toast.LENGTH_SHORT).show();

        }
        finish();

    }

    @Override
    public void failedAttemptToSend(String message) {
        if(message !=null){
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,R.string.failure_message,Toast.LENGTH_SHORT).show();

        }
        finish();
    }

    @Override
    public void updateStatus() {
        if(participantList.size()==0){
            participantLabel.setVisibility(View.GONE);
            sendPassButton.setVisibility(View.GONE);

        }
        else{
            participantLabel.setVisibility(View.VISIBLE);
            sendPassButton.setVisibility(View.VISIBLE);
        }
    }
}
