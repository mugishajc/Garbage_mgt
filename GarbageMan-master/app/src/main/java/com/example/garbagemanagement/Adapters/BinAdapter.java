package com.example.garbagemanagement.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.garbagemanagement.Models.Account;
import com.example.garbagemanagement.Models.Bin;
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

public class BinAdapter extends FirebaseRecyclerAdapter<Bin,BinAdapter.myviewholder> {

    public BinAdapter(@NonNull FirebaseRecyclerOptions<Bin> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final BinAdapter.myviewholder holder, final int position, @NonNull final Bin model)
    {
        holder.name.setText(model.getUseremail());
        holder.district.setText(model.getCity());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogbin))
                        .setExpanded(true,2000)
                        .create();

                View myview=dialogPlus.getHolderView();
                final EditText email=myview.findViewById(R.id.buemail);
                final Spinner city=myview.findViewById(R.id.ucity);
                final EditText sector=myview.findViewById(R.id.usector);
                final EditText streetno=myview.findViewById(R.id.ustreetno);
                Button submit=myview.findViewById(R.id.usubmit);

                email.setText(model.getUseremail());
                sector.setText(model.getSector());
                streetno.setText(model.getStreetno());
                city.setPrompt(model.getCity());

                dialogPlus.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("city",city.getSelectedItem().toString());
                        map.put("streetno",streetno.getText().toString());
                        map.put("sector",sector.getText().toString());
                        map.put("useremail",email.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("bin")
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
                builder.setTitle("Delete Bin");
                builder.setMessage("Are You Sure You want to Delete this Bin?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("bin")
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
    public BinAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlebin,parent,false);
        return new BinAdapter.myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView edit,delete;
        TextView name,district;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.buemail);
            district=(TextView)itemView.findViewById(R.id.busector);
            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }
}
