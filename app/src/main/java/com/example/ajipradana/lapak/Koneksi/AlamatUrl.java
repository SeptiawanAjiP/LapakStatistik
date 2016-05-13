package com.example.ajipradana.lapak.Koneksi;


/**
 * Created by AJI PRADANA on 4/5/2016.
 */
public class AlamatUrl {

    public final static String ALAMAT_URL = "http://192.168.1.100/lapak/koneksi.php";
    public final static String KODE_MENU_UTAMA      = "0";
    public final static String KODE_DAFTAR_USER     = "1";
    public final static String KODE_LOGIN           = "2";
    public final static String KODE_JUAL_BARANG     = "3";
    public final static String KODE_DAFTAR_BARANG   = "4";
    public final static String KODE_PEMBELIAN       = "5";
    public final static String KODE_KERANJANG_BELANJA   = "6";
    public final static String KODE_DETAIL_BARANG   = "7";
    private final static String KODE_PILIHAN_KATEGORI = "9";

    public String getKodePilihanKategori() {
        return KODE_PILIHAN_KATEGORI;
    }


    public String getKodeMenuUtama() {
        return KODE_MENU_UTAMA;
    }

    public String getKodeDetailBarang() {
        return KODE_DETAIL_BARANG;
    }

    public String getAlamatUrl(){
        return ALAMAT_URL;
    }

    public String getKodeDaftarUser (){
        return KODE_DAFTAR_USER;
    }

    public String getKodeLogin(){
        return KODE_LOGIN;
    }

    public String getKodeJualBarang(){
        return KODE_JUAL_BARANG;
    }

    public String getKodeDaftarBarang(){
        return KODE_DAFTAR_BARANG;
    }

    public String getKodePembelian(){
        return KODE_PEMBELIAN;
    }

    public String getKodeKeranjangBelanja(){
        return KODE_KERANJANG_BELANJA;
    }




}

