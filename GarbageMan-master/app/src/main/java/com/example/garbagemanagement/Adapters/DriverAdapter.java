package com.example.garbagemanagement.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.garbagemanagement.Models.Driver;
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

public class DriverAdapter extends FirebaseRecyclerAdapter<Driver,DriverAdapter.myviewholder> {

    public DriverAdapter(@NonNull FirebaseRecyclerOptions<Driver> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Driver model) {
        holder.name.setText(model.getFullName());
        holder.phone.setText(model.getPhoneNumber());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogdriver))
                        .setExpanded(true, 1700)
                        .create();

                View myview = dialogPlus.getHolderView();
                final EditText address = myview.findViewById(R.id.dwrkadd);
                final EditText name = myview.findViewById(R.id.dfullnm);
                final EditText phoneno = myview.findViewById(R.id.dpn);
                final EditText platenumber = myview.findViewById(R.id.dplateno);
                final EditText email = myview.findViewById(R.id.demai);

                Button submit = myview.findViewById(R.id.usubmit);

                address.setText(model.getWorkaddress());
                name.setText(model.getFullName());
                phoneno.setText(model.getPhoneNumber());
                platenumber.setText(model.getPlateNumber());
                email.setText(model.getEmail());

                dialogPlus.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("address", address.getText().toString());
                        map.put("fullName", name.getText().toString());
                        map.put("phoneNumber", phoneno.getText().toString());
                        map.put("plateNumber", platenumber.getText().toString());
                        map.put("email", email.getText().toString());

                        if (phoneno.length() < 10) {
//                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (phoneno.length() > 10) {
//                    Toast.makeText(getAdapter(), "Phone Number should be of 10 Digits!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        FirebaseDatabase.getInstance().getReference().child("drivers")
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
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Delete Driver");
                builder.setMessage("Are You Sure You want to Delete this Person?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("drivers")
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
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder {
        ImageView edit, delete;
        TextView name, phone;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.dname);
            phone = (TextView) itemView.findViewById(R.id.dpho);
            edit = (ImageView) itemView.findViewById(R.id.editicon);
            delete = (ImageView) itemView.findViewById(R.id.deleteicon);
        }
    }
}