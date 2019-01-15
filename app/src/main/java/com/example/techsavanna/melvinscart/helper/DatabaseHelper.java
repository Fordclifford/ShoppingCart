package com.example.techsavanna.melvinscart.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.techsavanna.melvinscart.adapters.OrderA;
import com.example.techsavanna.melvinscart.adapters.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Constants for Database name, table name, and column names
    public static final String DB_NAME = "MelvisDB";
    public static final String TABLE_NAME = "customer";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "firstname";
    public static final String COLUMN_LAST_NAME = "lastname";
    public static final String MAILINGCITY = "mailingcity";
    public static final String PHONE = "phone";
    public static final String OTHERSTREET= "otherstreet";


    public static final String TABLE_CUSTOMER = "current";

    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_FIRST_NAME = "customer_firstname";
    public static final String CUSTOMER_LAST_NAME = "customer_lastname";
    public static final String CUSTOMER_MAILINGCITY = "customer_mailingcity";
    public static final String CUSTOMER_PHONE = "customer_phone";
    public static final String CUSTOMER_OTHERSTREET= "customer_otherstreet";


    public static final String TABLE_PRODUCT= "product";

    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_NAME = "productname";
    public static final String PRODUCT_UNITPRICE = "unit_price";
    public static final String PRODUCT_QTYINSTOCK = "qtyinstock";
    public static final String PRODUCT_CATEGORY = "productcategory";
    public static final String PRODUCT_CURRECYCODE= "currency_code";
    public static final String PRODUCT_QTY= "quantity";


    public static final String TABLE_ORDER= "ordertable";

    public static final String ORDER_ID = "order_id";
    public static final String ORDER_NAME = "ordername";
    public static final String ORDER_UNITPRICE = "order_unit_price";
    public static final String ORDER_QTYCARTON = "order_carton";
    public static final String ORDER_QTYPIECE = "order_piece";


    public static int PRODUCT_TOTAL= 0;

    //database version
    private static final int DB_VERSION = 1;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIRST_NAME +
                " VARCHAR, " + COLUMN_LAST_NAME+
                " VARCHAR, " + MAILINGCITY+
                " VARCHAR, " + PHONE+
                " VARCHAR, " + OTHERSTREET+
                " TINYINT);";
        db.execSQL(sql);

        String sql1 = "CREATE TABLE " + TABLE_CUSTOMER
                + "(" + CUSTOMER_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + CUSTOMER_FIRST_NAME +
                " VARCHAR, " + CUSTOMER_LAST_NAME+
                " VARCHAR, " + CUSTOMER_MAILINGCITY+
                " VARCHAR, " + CUSTOMER_PHONE+
                " VARCHAR, " + CUSTOMER_OTHERSTREET+
                " VARCHAR);";

        System.out.println("Table name:"+sql1);
        db.execSQL(sql1);


        String sql2 = "CREATE TABLE " + TABLE_PRODUCT
                + "(" + PRODUCT_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + PRODUCT_NAME +
                " VARCHAR, " + PRODUCT_UNITPRICE+
                " VARCHAR, " + PRODUCT_QTYINSTOCK+
                " VARCHAR, " + PRODUCT_CATEGORY+
                " VARCHAR, " + PRODUCT_CURRECYCODE+
                " VARCHAR, " + PRODUCT_QTY+
                " VARCHAR);";

        System.out.println("Table name:"+sql2);
        db.execSQL(sql2);

        String sql3 = "CREATE TABLE " + TABLE_ORDER
                + "(" + ORDER_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + ORDER_NAME +
                " VARCHAR, " + ORDER_UNITPRICE+
                " VARCHAR, " + ORDER_QTYCARTON+
                " VARCHAR, " + ORDER_QTYPIECE+
                " VARCHAR);";

        System.out.println("Table name:"+sql2);
        db.execSQL(sql3);
    }
public void updateQty(String  name,int qty){
        SQLiteDatabase db = this.getWritableDatabase();

       db.execSQL("UPDATE "+TABLE_PRODUCT+" SET "+ PRODUCT_QTY+"="+qty+" WHERE "+PRODUCT_NAME+"='"+name+"'");

}

    public void updateOrderCartonQty(String  name,int qty){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE "+TABLE_ORDER+" SET "+ ORDER_QTYCARTON +"="+qty+" WHERE "+ORDER_NAME+"='"+name+"'");

    }

    public void updateOrderPieceQty(String  name,int qty){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE "+TABLE_ORDER+" SET "+ ORDER_QTYPIECE +"="+qty+" WHERE "+ORDER_NAME+"='"+name+"'");

    }
    public void deleteQty(String  name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, PRODUCT_NAME + "=?", new String[]{name});
    }
    public void deleteOrderQty(String  name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER, ORDER_NAME+ "=?", new String[]{name});
    }
    public void updateUnitprice(String  name,int amount){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE "+TABLE_PRODUCT+" SET "+ PRODUCT_UNITPRICE+"="+amount+" WHERE "+PRODUCT_NAME+"='"+name+"'");



    }
    public void updateOrderUnitprice(String  name,int amount){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE "+TABLE_ORDER+" SET "+ ORDER_UNITPRICE+"="+amount+" WHERE "+ORDER_NAME+"='"+name+"'");



    }

    public  List<OrderItem> getAllProductInCart(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="SELECT "+PRODUCT_NAME +","+ PRODUCT_UNITPRICE +","+ PRODUCT_QTY+","+PRODUCT_CATEGORY+" FROM "+ TABLE_PRODUCT+" ";
        Cursor c = db.rawQuery(sql, null);
        List<OrderItem> orderItems= new ArrayList();
        if (c.moveToFirst()) {
            do {
                OrderItem orderItem = new OrderItem();
                orderItem.setName(c.getString(0));
                orderItem.setAmount(c.getString(1));
                orderItem.setQuatity(c.getString(2));

                orderItem.setCategory(c.getString(3));

                orderItems.add(orderItem);
                System.out.println("Fetch Data : :" + c.getString(1));
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return orderItems;



    }
    public int totalamount(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT SUM("+PRODUCT_UNITPRICE+") FROM "+ TABLE_PRODUCT+ "", null);
        if(cur.moveToFirst())
        {
            PRODUCT_TOTAL=cur.getInt(0);
        }
        return PRODUCT_TOTAL;
    }
    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Persons";
        db.execSQL(sql);
        onCreate(db);
    }

    /*
     * This method is taking two arguments
     * first one is the name that is to be saved
     * second one is the status
     * 0 means the name is synced with the server
     * 1 means the name is not synced with the server
     * */
    public boolean addName(String firstname, String lastname,String mailingcity,String phone,String otherstreet ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FIRST_NAME, firstname);
        contentValues.put(COLUMN_LAST_NAME, lastname);
        contentValues.put(MAILINGCITY, mailingcity);
        contentValues.put(PHONE, phone);
        contentValues.put(OTHERSTREET, otherstreet);
        //System.out.println("String nn"+contentValues);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public boolean addProduct(String productname, String unitprice,String qtyinstock,String productcategory,String currencycode ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PRODUCT_NAME, productname);
        contentValues.put(PRODUCT_UNITPRICE, unitprice);
        contentValues.put(PRODUCT_QTYINSTOCK, qtyinstock);
        contentValues.put(PRODUCT_CATEGORY, productcategory);
        contentValues.put(PRODUCT_CURRECYCODE, currencycode);
        //System.out.println("String nn"+contentValues);
        db.insert(TABLE_PRODUCT, null, contentValues);
        db.close();
        return true;
    }

    public boolean addOrder(String productname, String unitprice,String qtycarton,String qtypiece ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ORDER_NAME, productname);
        contentValues.put(ORDER_UNITPRICE, unitprice);
        contentValues.put(ORDER_QTYCARTON, qtycarton);
        contentValues.put(ORDER_QTYPIECE, qtypiece);
        //System.out.println("String nn"+contentValues);
        db.insert(TABLE_ORDER, null, contentValues);
        db.close();
        return true;
    }
    public boolean addCustomer(String firstname, String lastname,String mailingcity,String phone,String otherstreet ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CUSTOMER_FIRST_NAME, firstname);
        contentValues.put(CUSTOMER_LAST_NAME, lastname);
        contentValues.put(CUSTOMER_MAILINGCITY, mailingcity);
        contentValues.put(CUSTOMER_PHONE, phone);
        contentValues.put(CUSTOMER_OTHERSTREET, otherstreet);
        System.out.println("String nn"+contentValues);
        db.insert(TABLE_CUSTOMER, null, contentValues);
        db.close();
        return true;
    }
    /*
     * This method taking two arguments
     * first one is the id of the name for which
     * we have to update the sync status
     * and the second one is the status that will be changed
     * */
    public boolean updateNameStatus(int id,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OTHERSTREET, status);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }

    /*
     * this method will give us all the name stored in sqlite
     * */
    public List<Contact> getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        List<Contact> posts = new ArrayList();
        if (c.moveToFirst()) {
            Contact contact = new Contact();
            contact.setFirstName(c.getString(1));
            contact.setLastName(c.getString(2));
            contact.setCity(c.getString(3));
            contact.setPhone(c.getString(4));
            contact.setStreet(c.getString(5));
            posts.add(contact);
            System.out.println("Fetch Data Contact: :" + contact);
        }

        return posts;
    }
    public List<Contact> getTheName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CUSTOMER + " ORDER BY " + CUSTOMER_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        List<Contact> posts = new ArrayList();
        if (c.moveToFirst()) {

            Contact contact = new Contact();
            contact.setFirstName(c.getString(1));
            contact.setLastName(c.getString(2));
            contact.setCity(c.getString(3));
            contact.setPhone(c.getString(4));
            contact.setStreet(c.getString(5));
            posts.add(contact);
            System.out.println("Fetch Data : :" + c.getString(1));
        }


        return posts;
    }

    public List<OrderA> getAllOrder() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_ORDER + " ORDER BY " + ORDER_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        List<OrderA> posts = new ArrayList();
       // System.out.println("Fetch Data : :" + posts);
        if (c.moveToFirst()) {
            while(!c.isAfterLast()) {

                OrderA orderA = new OrderA();
                orderA.setOrder_name(c.getString(1));
                orderA.setOrder_unit_price(c.getString(2));
                orderA.setQtycarton(c.getString(3));
                orderA.setQtypiece(c.getString(4));
                posts.add(orderA);
                //  System.out.println("Fetch Data : :" + c.getString(1));

                c.moveToNext();
            }
        }

        return posts;
    }


    /*
     * this method is for getting all the unsynced name
     * so that we can sync it with database
     * */
    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + OTHERSTREET + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
}
