package com.example.garbagemanagement.Adapters;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.garbagemanagement.Activities.ComplainRegister;
import com.example.garbagemanagement.Models.Complaints;
import com.example.garbagemanagement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class myadapter extends FirebaseRecyclerAdapter<Complaints,myadapter.myviewholder>
{
    private int mHour, mMinute,mSecond,mYear, mMonth, mDay;
    private Context context;

    public myadapter(@NonNull FirebaseRecyclerOptions<Complaints> options)
    {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Complaints model)
    {
        holder.name.setText(model.getEmail());
        holder.district.setText(model.getCity());
        holder.messag.setText(model.getMessage());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,2000)
                        .create();

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                View myview=dialogPlus.getHolderView();
                final EditText email=myview.findViewById(R.id.cuemail);
                final EditText phoneno=myview.findViewById(R.id.cphne);
                final Spinner city=myview.findViewById(R.id.ccity);
                final EditText sector=myview.findViewById(R.id.csector);
                final EditText streetno=myview.findViewById(R.id.cstno);
                final EditText dremai=myview.findViewById(R.id.cdemail);
                final Spinner status=myview.findViewById(R.id.cstatus);
                final EditText msg=myview.findViewById(R.id.cmessage);
                final EditText dat=myview.findViewById(R.id.bindate);
                final EditText repdat=myview.findViewById(R.id.binrepdate);

                Button submit=myview.findViewById(R.id.usubmit);

                ArrayAdapter<String> array_spinner=(ArrayAdapter<String>)status.getAdapter();

                email.setText(model.getEmail());
                phoneno.setText(model.getPhone());
                city.setPrompt(model.getCity());
                sector.setText(model.getSector());
                streetno.setText(model.getStreetno());
                dremai.setText(model.getDriveremail());
                status.setSelection(array_spinner.getPosition(model.getStatus()));
                msg.setText(model.getMessage());
                dat.setText(model.getGivendate());
                repdat.setText(model.getReplialdate());

                dialogPlus.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("email",email.getText().toString());
                        map.put("phoneNumber",phoneno.getText().toString());
                        map.put("city",city.getSelectedItem().toString());
                        map.put("streetnumber",streetno.getText().toString());
                        map.put("driveremail",dremai.getText().toString());
                        map.put("status",status.getSelectedItem().toString());
                        map.put("message",msg.getText().toString());
                        map.put("givendate",dat.getText().toString());
                        map.put("replialdate",repdat.getText().toString());

                        if (phoneno.length() < 10) {
//                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (phoneno.length() > 10) {
//                    Toast.makeText(getAdapter(), "Phone Number should be of 10 Digits!", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        FirebaseDatabase.getInstance().getReference().child("complaints")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Delete Complain");
                builder.setMessage("Are You Sure You want to Delete this Complain?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("complaints")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlecomplain,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView edit,delete;
        TextView name,district,messag;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.nametext);
            district=(TextView)itemView.findViewById(R.id.district);
            messag=(TextView)itemView.findViewById(R.id.messag);
            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }

}
