package net.agusharyanto.belajararraylist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText editTextNIM;
    private EditText editTextNama;
    private EditText editTextJurusan;
    private Button buttonSave;
    private Button buttonHapus;
    private TextView textViewDataMahasiswa;

    ArrayList<Mahasiswa> mahasiswaArrayList = new ArrayList<Mahasiswa>();
    private RecyclerView mRecyclerView;
    private MahasiswaAdapter rvAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context = MainActivity.this;
    private Mahasiswa currMahasiswa;
    private String actflag ="";
    private int currposition = 0;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        // create an array list
        ArrayList al = new ArrayList();
        System.out.println("Initial size of al: " + al.size());

        // add elements to the array list
        al.add("C");
        al.add("A");
        al.add("E");
        al.add("B");
        al.add("D");
        al.add("F");
        al.add(1, "A2");
        System.out.println("Size of al after additions: " + al.size());

        // display the array list
        System.out.println("Contents of al: " + al);

        // Remove elements from the array list
        al.remove("F");
        al.remove(2);
        System.out.println("Size of al after deletions: " + al.size());
        System.out.println("Contents of al: " + al);

        ArrayList<Mahasiswa> mahasiswaArrayList = new ArrayList<Mahasiswa>();
        System.out.println("Size of mahasiswaArrayList before additions: " + mahasiswaArrayList.size());
        mahasiswaArrayList.add(new Mahasiswa("1","10001","Budi","MI"));
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setId("2");
        mahasiswa.setNim("10002");
        mahasiswa.setNama("Doni");
        mahasiswa.setJurusan("AK");
        mahasiswaArrayList.add(mahasiswa);
        mahasiswaArrayList.add(new Mahasiswa("3","10003","Abdul","TI"));
        System.out.println("Size of mahasiswaArrayList after additions: " + mahasiswaArrayList.size());
        System.out.println("Contents of mahasiswaArrayList: " + mahasiswaArrayList);
        for (int i=0; i<mahasiswaArrayList.size(); i++){
            System.out.println(mahasiswaArrayList.get(i).toString());
        }
        */

        currMahasiswa = new Mahasiswa();
        actflag="add";
        findViews();



    }


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-01-29 14:35:53 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        editTextNIM = (EditText)findViewById( R.id.editTextNIM );
        editTextNama = (EditText)findViewById( R.id.editTextNama );
        editTextJurusan = (EditText)findViewById( R.id.editTextJurusan );
        buttonSave = (Button)findViewById( R.id.buttonSave );
        buttonHapus = (Button)findViewById( R.id.buttonHapus );
        textViewDataMahasiswa = (TextView)findViewById( R.id.textViewDataMahasiswa );

        buttonSave.setOnClickListener( this );
        buttonHapus.setOnClickListener( this );

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMahasiswa);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        //initializeData();
        initDataMahasiswa();
        gambarDatakeRecyclerView();
    }

    private void resetData(){
        currMahasiswa=new Mahasiswa();
        actflag="add";
        setData(currMahasiswa);
        gambarDatakeRecyclerView();
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-01-29 14:35:53 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == buttonSave ) {
            // Handle clicks for buttonSave
            saveData();
        } else if ( v == buttonHapus ) {
            // Handle clicks for buttonHapus
            //mahasiswaArrayList.remove(currMahasiswa);
            databaseHelper.deleteMahasiswa(currMahasiswa, db);
            resetData();
        }
    }

    private void saveData() {
        String nim = editTextNIM.getText().toString();
        String nama = editTextNama.getText().toString();
        String jurusan = editTextJurusan.getText().toString();

        String id = mahasiswaArrayList.size() + 1+"";


       // mahasiswaArrayList.add(new Mahasiswa(id, nim, nama, jurusan));

        currMahasiswa.setNim(nim);
        currMahasiswa.setNama(nama);
        currMahasiswa.setJurusan(jurusan);
        if (actflag.equals("add")){
            currMahasiswa.setId(id);
           // mahasiswaArrayList.add(currMahasiswa);
            databaseHelper.insertMahasiswa(currMahasiswa, db);
        }else{
           //mahasiswaArrayList.set(currposition, currMahasiswa);
            databaseHelper.updateMahasiswa(currMahasiswa,db);
        }
       resetData();

    }
    private void initDataMahasiswa(){
        /*for (int i=1; i<5; i++) {
            String id = i+"";
            String nim = 1000+i+"";
            String nama = "Joni "+i;
            mahasiswaArrayList.add(new Mahasiswa(id, nim, nama, "MI"));
        }*/

        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();

        mahasiswaArrayList = databaseHelper.getDataMahasiswa(db);

    }

    private void printData() {
        String data = "";
        for (int i=0; i<mahasiswaArrayList.size(); i++){
            //System.out.println(mahasiswaArrayList.get(i).toString());
            data +=mahasiswaArrayList.get(i).toString()+"\n";
        }
        textViewDataMahasiswa.setText(data);
    }


    private void gambarDatakeRecyclerView(){
        mahasiswaArrayList = databaseHelper.getDataMahasiswa(db);
        rvAdapter = new MahasiswaAdapter(mahasiswaArrayList, context);
        mRecyclerView.setAdapter(rvAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        currposition = position;
                        Mahasiswa mahasiswa = rvAdapter.getItem(position);
                        Toast.makeText(context, "Name :" + mahasiswa.getNama(), Toast.LENGTH_SHORT).show();
                        actflag="edit";
                        setData(mahasiswa);

                        // selectedPosition = position;
                       /* Intent intent = new Intent(MainActivity.this, MahasiswaActivity.class);
                        intent.putExtra("mahasiswa", mahasiswa);
                        startActivityForResult(intent, REQUEST_CODE_EDIT);*/
                    }
                })
        );


    }


    private void setData(Mahasiswa objmahasiswa){
        currMahasiswa = objmahasiswa;
        editTextNIM.setText(currMahasiswa.getNim());
        editTextNama.setText(currMahasiswa.getNama());
        editTextJurusan.setText(currMahasiswa.getJurusan());

    }
    /*Tugas buatlah project baru untuk Barang yang atributnya id,kode,nama,harga */

    @Override
    public void onDestroy(){
        db.close();
        databaseHelper.close();
        super.onDestroy();
    }

}
