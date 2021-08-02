package com.example.duan1_nhom17_cp16201;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiemAdapter extends RecyclerView.Adapter<DiemAdapter.DiemHolder>{

    private Context context;
    private List<Diem> diemList;
    private DiemSQL diemSQL;
    public DiemAdapter (Context context,List<Diem> diemList){
        this.context=context;
        this.diemList=diemList;
    }

    @NonNull
    @Override
    public DiemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row,viewGroup,false);
        return new DiemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiemHolder holder, int position) {

        diemSQL=new DiemSQL(context);
        diemList=diemSQL.getAll();

        holder.tv_stt.setText(String.valueOf(diemList.get(position).getSothutu()));
        holder.tv_name.setText(String.valueOf(diemList.get(position).getTennguoichoi()));
        holder.tv_diem.setText(String.valueOf(diemList.get(position).getDiemtong()));
        holder.tvNgay.setText(String.valueOf(diemList.get(position).getNgay()));
    }

    @Override
    public int getItemCount() {
        return diemList.size();
    }


    public static class DiemHolder extends RecyclerView.ViewHolder {
        private TextView tv_stt,tv_diem,tv_name,tvNgay;
        public DiemHolder(@NonNull View itemView) {
            super(itemView);
            tv_stt=itemView.findViewById(R.id.tv_stt_Cao);
            tv_name=itemView.findViewById(R.id.tv_Ten_Cao);
            tv_diem=itemView.findViewById(R.id.tv_Diem_Cao);
            tvNgay=itemView.findViewById(R.id.tvNgay);
        }
    }
}
