package com.example.giuaky;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.giuaky.entities.Diem;

import java.util.ArrayList;

public class CustomAdapterDiem extends ArrayAdapter<Diem> {
    Context context;
    int resource;
    ArrayList<Diem> data;

    public CustomAdapterDiem(@NonNull Context context, int resource, @NonNull ArrayList<Diem> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);
        LinearLayout itemDiem = convertView.findViewById(R.id.itemDiem);
        TextView tvTenMon = convertView.findViewById(R.id.tvTenMon);
        TextView tvDiem = convertView.findViewById(R.id.tvDiem);

        Diem diem = data.get(position);
        tvTenMon.setText(diem.getTenMon());
        tvDiem.setText(String.valueOf(diem.getDiem()));

        itemDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SuaDiemActivity.class);
                intent.putExtra("diem", diem);
                ((ThongTinHocSinhActivity)context).startActivity(intent);
            }
        });

        return convertView;
    }
}
