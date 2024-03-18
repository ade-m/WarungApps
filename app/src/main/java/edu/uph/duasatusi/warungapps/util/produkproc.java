package edu.uph.duasatusi.warungapps.util;

import java.util.ArrayList;

import edu.uph.duasatusi.warungapps.Model.Produk;
import io.realm.Realm;
import io.realm.RealmResults;

public class produkproc {
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
