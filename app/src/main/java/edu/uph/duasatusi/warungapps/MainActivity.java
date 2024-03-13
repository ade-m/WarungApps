package edu.uph.duasatusi.warungapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.uph.duasatusi.warungapps.Adapter.ProdukAdapter;
import edu.uph.duasatusi.warungapps.Model.Produk;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    ArrayList<Produk> produkArrayList;
    private static ProdukAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRealm();

        listview = (ListView) findViewById(R.id.listView);
        produkArrayList = new ArrayList<>();
        produkArrayList = getAllProduk();

//        produkArrayList.add(produk1);
//        produkArrayList.add(produk2);
//        produkArrayList.add(produk3);
//        produkArrayList.add(produk4);
//        produkArrayList.add(produk5);
//        produkArrayList.add(produk6);
//        produkArrayList.add(produk7);
//        produkArrayList.add(produk8);

        adapter = new ProdukAdapter(produkArrayList, getApplicationContext());
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(
                        MainActivity.this,
                        "Nama Produk yang dipilih : "+ adapter.getItem(i).getNamaProduk(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void initRealm(){
        //konfigurasi realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .allowWritesOnUiThread(true)
                .build();
        Realm.setDefaultConfiguration(config);

        clearAllProduk();
        //init data
        simpanDataProduk("Kacang Garuda 375g", 37000, R.drawable.garuda);
        simpanDataProduk("Hit Anti Nyamuk Elektrik", 16900, R.drawable.hit);
        simpanDataProduk("Aqua Air Mineral 600ml", 4400, R.drawable.aqua);
        simpanDataProduk("Pocari Sweat 500ml", 9000, R.drawable.pocari);

        simpanDataProduk("Idomie Ayam Bawang 69g", 3100, R.drawable.indomie);
        simpanDataProduk("Ultra Milk 250ml", 8100, R.drawable.ultra);
        simpanDataProduk("Sania Minyak Goreng 2L", 34200, R.drawable.sania);
        simpanDataProduk("Tropical Minyak Goreng", 35200, R.drawable.tropical);
    }

    public void simpanDataProduk(String namaProduk, int hargaProduk, int gambarProduk){
        Realm realm = Realm.getDefaultInstance();

        //menytmpan data
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Produk produk = realm.createObject(Produk.class);
                produk.setNamaProduk(namaProduk);
                produk.setHargaProduk(hargaProduk);
                produk.setGambarProduk(gambarProduk);
            }
        });

        //tutup koneksi ke dalam database
        realm.close();
    }

    public ArrayList<Produk> getAllProduk(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Produk> produks = realm.where(Produk.class).findAll();
        ArrayList<Produk> produkList = new ArrayList<>(produks);
        return  produkList;
    }

    public void clearAllProduk(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
    }

}