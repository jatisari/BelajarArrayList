package net.agusharyanto.belajararraylist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivityServer extends AppCompatActivity implements View.OnClickListener{


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
    private Context context = MainActivityServer.this;
    private Mahasiswa currMahasiswa;
    private String actflag ="";
    private int currposition = 0;

    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currMahasiswa = new Mahasiswa();
        actflag="add";
        pDialog = new ProgressDialog(context);
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

        loadDataServer();
    }

    private void resetData(){
        currMahasiswa=new Mahasiswa();
        actflag="add";
        setData(currMahasiswa);
        loadDataServer();
       /// gambarDatakeRecyclerView();
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
           hapusData();
        }
    }

    private void saveData() {
        String nim = editTextNIM.getText().toString();
        String nama = editTextNama.getText().toString();
        String jurusan = editTextJurusan.getText().toString();

        currMahasiswa.setNim(nim);
        currMahasiswa.setNama(nama);
        currMahasiswa.setJurusan(jurusan);
        if (actflag.equals("add")){
            currMahasiswa.setId("0");
        }


        String url = "http://192.168.0.105/mahasiswa/savedata.php";
        pDialog.setMessage("Save Data Mahasiswa...");
        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        // Log.d("AddEditActivity", "response :" + response);
                        // Toast.makeText(getBaseContext(),"response: "+response, Toast.LENGTH_SHORT).show();
                        processResponseSave(response);
                        resetData();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                Log.d("TAG","cuurmhs:"+currMahasiswa.toString());
                params.put("nama",currMahasiswa.getNama());
                params.put("nim",currMahasiswa.getNim());
                params.put("jurusan",currMahasiswa.getJurusan());
                params.put("id",currMahasiswa.getId());

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
//       resetData();

    }

    private void processResponseSave(String response){
        Log.d("MainActivityServer","responseSave:"+response);

        try {
            JSONObject jsonObj = new JSONObject(response);
            String errormsg = jsonObj.getString("errormsg");
            Toast.makeText(getBaseContext(),"Save Data "+errormsg,Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            Log.d("ViewActivitysave", "errorJSON");
        }

    }

    private void loadDataServer(){


            mahasiswaArrayList.clear();
            String url = "http://192.168.0.105/mahasiswa/listdata.php";
            pDialog.setMessage("Retieve Data Mahasiswa...");
            showDialog();

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("tag","response:"+response);
                            hideDialog();
                            processResponse(response);
                            gambarDatakeRecyclerView();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideDialog();
                            error.printStackTrace();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<>();
                    // the POST parameters:
                    // params.put("id","1");
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(postRequest);

    }

    private void processResponse(String response){

        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONArray jsonArray = jsonObj.getJSONArray("data");
            Log.d("TAG", "data length: " + jsonArray.length());
            Mahasiswa objectmahasiswa = null;
            mahasiswaArrayList.clear();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                objectmahasiswa= new Mahasiswa();
                objectmahasiswa.setId(obj.getString("id"));

                objectmahasiswa.setNama(obj.getString("nama"));
                objectmahasiswa.setNim(obj.getString("nim"));
                objectmahasiswa.setJurusan(obj.getString("jurusan"));


                mahasiswaArrayList.add(objectmahasiswa);
            }

        } catch (JSONException e) {
            Log.d("MainActivity", "errorJSON");
        }

    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void gambarDatakeRecyclerView(){

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



    private void hapusData() {
        new AlertDialog.Builder(this)
                .setTitle("Data Mahasiswa")
                .setMessage("Hapus Data " + currMahasiswa.getNama() + " ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                            hapusDataServer();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }


    private void hapusDataServer(){

        String url = "http://192.168.0.105/mahasiswa/deletedata.php";
        pDialog.setMessage("Hapus Data Mahasiswa...");
        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        Log.d("ViewActivityHapus", "response :" + response);
                        Toast.makeText(getBaseContext(),"Hapus Data "+response, Toast.LENGTH_SHORT).show();
                        //processResponse(response);
                        resetData();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("id",currMahasiswa.getId());

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);


    }


    private void setData(Mahasiswa objmahasiswa){
        currMahasiswa = objmahasiswa;
        editTextNIM.setText(currMahasiswa.getNim());
        editTextNama.setText(currMahasiswa.getNama());
        editTextJurusan.setText(currMahasiswa.getJurusan());

    }
    /*Tugas buatlah project baru untuk Barang yang atributnya id,kode,nama,harga */



}
