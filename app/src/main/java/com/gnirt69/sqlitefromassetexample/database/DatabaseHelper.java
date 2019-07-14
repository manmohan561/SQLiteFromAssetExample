package com.gnirt69.sqlitefromassetexample.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.gnirt69.sqlitefromassetexample.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "sample.sqlite";
    public static final String DBLOCATION = "/data/data/com.gnirt69.sqlitefromassetexample/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if(mDatabase!=null) {
            mDatabase.close();
        }
    }

    public List<Product> getListProduct() {
        Product product = null;
        List<Product> productList = new ArrayList<>();
        openDatabase();
        try {
            Cursor cursor = mDatabase.rawQuery("SELECT * FROM PRODUCT", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
                productList.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e){
            Toast.makeText(mContext,e.getMessage().trim(),Toast.LENGTH_LONG).show();
        }
        closeDatabase();
        return productList;
    }
}
