package net.agusharyanto.belajararraylist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Lenovo on 29/01/2018.
 */

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder> {
    List<Mahasiswa> mahasiswas;

    public MahasiswaAdapter(List<Mahasiswa> mahasiswas) {
        this.mahasiswas = mahasiswas;
    }

    @Override
    public MahasiswaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_mahasiswa, viewGroup, false);
        MahasiswaViewHolder mahasiswaViewHolder = new MahasiswaViewHolder(v);
        return mahasiswaViewHolder;
    }

    @Override
    public void onBindViewHolder(MahasiswaViewHolder mahasiswaViewHolder, int i) {
        mahasiswaViewHolder.mahasiswaName.setText(mahasiswas.get(i).getNama());
        mahasiswaViewHolder.mahasiswaJurusan.setText(mahasiswas.get(i).getJurusan());
        mahasiswaViewHolder.mahasiswaNim.setText(mahasiswas.get(i).getNim());
    }

    @Override
    public int getItemCount() {
        return mahasiswas.size();
    }

    public static class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        //  CardView cv;
        TextView mahasiswaName;
        TextView mahasiswaJurusan;
        TextView mahasiswaNim;

        MahasiswaViewHolder(View itemView) {
            super(itemView);
            //      cv = (CardView) itemView.findViewById(R.id.cv);
            mahasiswaName = (TextView) itemView.findViewById(R.id.textViewRowNama);
            mahasiswaJurusan = (TextView) itemView.findViewById(R.id.textViewRowJurusan);
            mahasiswaNim = (TextView) itemView.findViewById(R.id.textViewRowNim);
        }
    }
}