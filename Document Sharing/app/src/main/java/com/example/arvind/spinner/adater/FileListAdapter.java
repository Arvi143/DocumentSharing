package com.example.arvind.spinner.adater;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.arvind.spinner.R;
import com.example.arvind.spinner.ShowUploadFile;

import java.util.List;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.MyViewHolder> {

    private List<ShowUploadFile> m_list;
    private Context context;
    private AdapterListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView semdetail, remarks, uploadby;
        private Button downloadbutton;


        public MyViewHolder(View view) {
            super(view);
            semdetail = (AppCompatTextView) view.findViewById(R.id.semdetail);
            remarks = (AppCompatTextView) view.findViewById(R.id.remarks);
            uploadby = (AppCompatTextView) view.findViewById(R.id.uploadby);
            downloadbutton = (Button) view.findViewById(R.id.downloadbutton);

            downloadbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.downloadClicked(m_list.get(getAdapterPosition()));
                }
            });
        }
    }


    public FileListAdapter(List<ShowUploadFile> m_list,AdapterListener listener) {
        this.m_list = m_list;
        this.listener=listener;
    }

    @Override
    public FileListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upload_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FileListAdapter.MyViewHolder holder, int position) {
        ShowUploadFile ob = m_list.get(position);
        holder.uploadby.setText(ob.getAdminname());
        holder.remarks.setText(ob.getRemarks());
        holder.semdetail.setText(ob.getSemestername());
    }

    @Override
    public int getItemCount() {
        return m_list.size();
    }

    public interface AdapterListener {
        void downloadClicked(ShowUploadFile showUploadFile);
    }
}
