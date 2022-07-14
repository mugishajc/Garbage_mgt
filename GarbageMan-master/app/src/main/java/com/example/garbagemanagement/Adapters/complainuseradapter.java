package com.example.garbagemanagement.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagemanagement.Models.Complaints;
import com.example.garbagemanagement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class complainuseradapter extends FirebaseRecyclerAdapter<Complaints,complainuseradapter.myviewholder> {

    public complainuseradapter(@NonNull FirebaseRecyclerOptions<Complaints> options)
    {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final complainuseradapter.myviewholder holder, final int position, @NonNull final Complaints model) {
        holder.name.setText(model.getEmail());
        holder.district.setText(model.getSector());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogusercomplain))
                        .setExpanded(true, 2000)
                        .create();

                View myview = dialogPlus.getHolderView();
                final EditText email = myview.findViewById(R.id.cuemail);
                final EditText phoneno = myview.findViewById(R.id.cphne);
                final Spinner city = myview.findViewById(R.id.ccity);
                final EditText sector = myview.findViewById(R.id.csector);
                final EditText streetno = myview.findViewById(R.id.cstno);
                final EditText dremai = myview.findViewById(R.id.cdemail);
                final Spinner status = myview.findViewById(R.id.cstatus);
                final EditText msg = myview.findViewById(R.id.cmessage);
                Button submit = myview.findViewById(R.id.usubmit);

                ArrayAdapter<String> array_spinner = (ArrayAdapter<String>) status.getAdapter();

                email.setText(model.getEmail());
                phoneno.setText(model.getPhone());
                city.setPrompt(model.getCity());
                sector.setText(model.getSector());
                streetno.setText(model.getStreetno());
                dremai.setText(model.getDriveremail());
                status.setSelection(array_spinner.getPosition(model.getStatus()));
                msg.setText(model.getMessage());

                dialogPlus.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("email", email.getText().toString());
                        map.put("phoneNumber", phoneno.getText().toString());
                        map.put("city", city.getSelectedItem().toString());
                        map.put("streetnumber", streetno.getText().toString());
                        map.put("driveremail", dremai.getText().toString());
                        map.put("status", status.getSelectedItem().toString());
                        map.put("message", msg.getText().toString());

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

    }
    @NonNull
    @Override
    public complainuseradapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleusercomplain,parent,false);
        return new complainuseradapter.myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView edit;
        TextView name,district;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.nametext);
            district=itemView.findViewById(R.id.district);
            edit=(ImageView)itemView.findViewById(R.id.editicon);
        }
    }
}
