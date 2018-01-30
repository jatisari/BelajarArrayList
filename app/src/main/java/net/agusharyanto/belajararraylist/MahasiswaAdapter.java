package net.agusharyanto.belajararraylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Lenovo on 29/01/2018.
 */

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder> {
    List<Mahasiswa> mahasiswas;
    Context context;

    public MahasiswaAdapter(List<Mahasiswa> mahasiswas, Context pcontext) {
        this.mahasiswas = mahasiswas;
        this.context = pcontext;
    }

    @Override
    public MahasiswaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_mahasiswa, viewGroup, false);
        MahasiswaViewHolder mahasiswaViewHolder = new MahasiswaViewHolder(v);
        return mahasiswaViewHolder;
    }

    @Override
    public void onBindViewHolder(MahasiswaViewHolder mahasiswaViewHolder, int i) {

        //root.setBackgroundColor(getResources().getColor(color.white));
        if (i%2==0) {
            mahasiswaViewHolder.relativeLayoutRowMhs.setBackgroundColor(context.getResources().getColor(R.color.green));
        }else{
                mahasiswaViewHolder.relativeLayoutRowMhs.setBackgroundColor(context.getResources().getColor(R.color.lightgreen));


        }

        mahasiswaViewHolder.mahasiswaName.setText(mahasiswas.get(i).getNama());
        mahasiswaViewHolder.mahasiswaJurusan.setText(mahasiswas.get(i).getJurusan());
        mahasiswaViewHolder.mahasiswaNim.setText(mahasiswas.get(i).getNim());
    }

    @Override
    public int getItemCount() {
        return mahasiswas.size();
    }

    public Mahasiswa getItem(int position) {
        return mahasiswas.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayoutRowMhs;
        TextView mahasiswaName;
        TextView mahasiswaJurusan;
        TextView mahasiswaNim;

        MahasiswaViewHolder(View itemView) {
            super(itemView);
            relativeLayoutRowMhs = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutRowMhs);
            mahasiswaName = (TextView) itemView.findViewById(R.id.textViewRowNama);
            mahasiswaJurusan = (TextView) itemView.findViewById(R.id.textViewRowJurusan);
            mahasiswaNim = (TextView) itemView.findViewById(R.id.textViewRowNim);
        }
    }
}