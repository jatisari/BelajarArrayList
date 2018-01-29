package net.agusharyanto.belajararraylist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    }
}
