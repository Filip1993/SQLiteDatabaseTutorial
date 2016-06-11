package com.filipkesteli.sqlitedatabasetutorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Klasa SQLiteOpenHelper kao handler pomaze pri kreiranju novih tablica i upravljanju verzijama baza podataka
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    public static final String TABLE_PRODUCTS = "products";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_QUANTITY = "quantity";

    public MyDBHandler(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE =
                "CREATE TABLE "
                        + TABLE_PRODUCTS + "("
                            + COLUMN_ID + "INTEGER PRIMARY KEY, "
                            + COLUMN_PRODUCTNAME + " TEXT, "
                            + COLUMN_QUANTITY + " INTEGER"
                        + ");";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    /**
     * Metodu onUpgrade poziva operativni sustav kad god pozovemo novu verziju
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS); //
        onCreate(db); //opet kreiramo novu tablicu
    }

    /**
     * Metoda koja definira dodavanje novih redaka -> INSERT
     */
    public void INSERT_addProduct(Product product) {
        ContentValues contentValues = new ContentValues(); //ContentValues -> for saving data
        contentValues.put(COLUMN_PRODUCTNAME, product.get_productName());
        contentValues.put(COLUMN_QUANTITY, product.get_quantity());

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase(); //this znaci od ove klase
        sqLiteDatabase.insert(TABLE_PRODUCTS, null, contentValues);
        sqLiteDatabase.close();
    }

    /**
     * Metoda koja definira TRAZENJE elemenata iz tablice -> QUERY -> Mogu biti razne vrste queryja
     */
    public Product QUERY_findProduct(String productName) {
        String QUERY =
                "SELECT * FROM " + TABLE_PRODUCTS
                        + "WHERE " + COLUMN_PRODUCTNAME + " = " + productName;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null); //Cursor Interface is for reading and writing
        Product product = new Product();
        if (cursor.moveToFirst()) { //check if it exists -> move to first
            cursor.moveToFirst(); //odi na prvi element cursor objekta
            product.set_id(Integer.parseInt(cursor.getString(0))); //uzima prvi element objekta cursor, sto znaci da je _id uvijek prvi element tablice!
            product.set_productName(cursor.getString(1));
            product.set_quantity(Integer.parseInt(cursor.getString(2)));
        } else {
            product = null; //ako ne postoji prvi element cursora, onda niti cursor ne postoji, tj. vrijednost mu je null
        }
        sqLiteDatabase.close();
        return product;
    }

    /**
     * Metoda koja definira BRISANJE redaka -> DELETE
     */
    public boolean DELETE_deleteProduct(String productName) {
        boolean result = false;
        String QUERY =
                "SELECT * FROM " + TABLE_PRODUCTS
                        + "WHERE" + COLUMN_PRODUCTNAME + " = " + productName;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY, null);
        Product product = new Product();
        if (cursor.moveToFirst()) {
            product.set_id(Integer.parseInt(cursor.getString(0)));
            sqLiteDatabase.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{String.valueOf(product.get_id())});
            cursor.close();
        }
        sqLiteDatabase.close();
        return result;
    }
}
