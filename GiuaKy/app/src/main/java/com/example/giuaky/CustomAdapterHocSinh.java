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
import android.widget.Toast;

import com.example.giuaky.entities.HocSinh;

import java.util.ArrayList;

public class CustomAdapterHocSinh extends ArrayAdapter<HocSinh> {
    Context context;
    int resource;
    ArrayList<HocSinh> data;
    ArrayList<HocSinh> tmpData = new ArrayList<>();


    public CustomAdapterHocSinh(@NonNull Context context, int resource, @NonNull ArrayList<HocSinh> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        tmpData.addAll(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        LinearLayout itemHS = convertView.findViewById(R.id.itemHS);

        TextView tvSTT = convertView.findViewById(R.id.tvSTT);
        TextView tvHoTen = convertView.findViewById(R.id.tvHoTen);
        TextView tvPhai = convertView.findViewById(R.id.tvPhai);
        TextView tvNgaySinh = convertView.findViewById(R.id.tvNgaySinh);


        HocSinh hocSinh = data.get(position);
        tvSTT.setText(String.valueOf(position + 1));
        tvHoTen.setText(hocSinh.getHoTen());
        tvPhai.setText(hocSinh.getGioiTinh());
        tvNgaySinh.setText(hocSinh.getNgaySinh());

        itemHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ThongTinHocSinhActivity.class);
                intent.putExtra("hocSinh", hocSinh);
                ((DanhSachLopActivity)context).startActivity(intent);
            }
        });

        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        LinearLayout itemHS = convertView.findViewById(R.id.itemHS);

        TextView tvSTT = convertView.findViewById(R.id.tvSTT);
        TextView tvHoTen = convertView.findViewById(R.id.tvHoTen);
        TextView tvPhai = convertView.findViewById(R.id.tvPhai);
        TextView tvNgaySinh = convertView.findViewById(R.id.tvNgaySinh);


        HocSinh hocSinh = data.get(position);
        tvSTT.setText(String.valueOf(position + 1));
        tvHoTen.setText(hocSinh.getHoTen());
        tvPhai.setText(hocSinh.getGioiTinh());
        tvNgaySinh.setText(hocSinh.getNgaySinh());

        itemHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ThongTinHocSinhActivity.class);
                intent.putExtra("hocSinh", hocSinh);
                ((DanhSachLopActivity)context).startActivity(intent);
            }
        });

        return convertView;
    }

    public void filter(String text) {
        data.clear();
        text = text.toLowerCase();
        if(text.length() == 0) {
            data.addAll(tmpData);
        } else {
            for(HocSinh hs: tmpData) {
                if(hs.getHoTen().toLowerCase().contains(text)) {
                    data.add(hs);
                }
            }
        }
        notifyDataSetChanged();
    }
}
