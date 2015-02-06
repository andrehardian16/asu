package com.andre.store.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andre on 1/20/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    //declare database name and version
    private final static String DATABASE_NAME = "DBSales";
    public final static int DATABASE_VERSION = 1;

    //declare table name
    public final static String USER_TABLE = "tableUser";
    public final static String STORE_TABLE = "tableStore";
    public final static String ORDER_TABLE = "tableOrder";
    public final static String IMAGE_TABLE = "tableImage";
    public final static String HISTORY_TABLE = "tableHistory";
    public final static String EMPLOYEE_TABLE = "tableEmployee";
    public final static String DETAIL_HISTORY_TABLE = "tableDetailHistory";
    public final static String CATEGORY_TABLE = "tableCategory";

    //declare id
    public final static String ID_STORE = "idStore";
    public final static String ID_USER = "idUser";
    public final static String ID_CATEGORY = "idCategory";
    public final static String ID_EMPLOYEE = "idEmployee";
    public final static String ID_IMAGE = "idImage";
    public final static String ID_ORDER = "idOrder";
    public final static String ID_HISTORY = "idHistory";
    public final static String ID_DETAIL_HISTORY = "idDetailHistory";


    //declare row element table
    public final static String ROW_NAME_STORE = "nameStore";
    public final static String ROW_NAME_DETAIL_HISTORY = "nameDetailHistory";
    public final static String ROW_NAME_ORDER = "nameOrder";
    public final static String ROW_NAME_EMLOYEE = "nameEmployee";
    public final static String ROW_CATEGORY = "category";
    public final static String ROW_ADDRESS = "address";
    public final static String ROW_EMAIL = "email";
    public final static String ROW_TELEPHONE_STORE = "telephoneStore";
    public final static String ROW_TELEPHONE_EMPLOYEE = "telephoneEmployee";
    public final static String ROW_LAST_VISIT = "lastVisit";
    public final static String ROW_USER_NAME = "userName";
    public final static String ROW_PASSWORD = "password";
    public final static String ROW_CODE = "code";
    public final static String ROW_STOCK = "stock";
    public final static String ROW_PRICE = "price";
    public final static String ROW_BUY = "buy";
    public final static String ROW_IMAGE_PATH = "imagePath";
    public final static String ROW_QUANTITY = "quantity";
    public final static String ROW_LAST_DATE = "lastDate";
    public final static String ROW_TOTAL = "total";
    public final static String ROW_POSITION = "position";
    public final static String ROW_LONGITUDE = "longitude";
    public final static String ROW_LATITUDE = "latitude";

    //declare for create table

    //create table user for login
    private final String CREATE_TABLE_USER =
            "create table "
                    + USER_TABLE + "("
                    + ID_USER + " Integer primary key autoincrement, "
                    + ROW_USER_NAME + " Text, "
                    + ROW_PASSWORD + " Text );";

    //create table store for additional store location or register store
    private final String CREATE_TABLE_STORE =
            "create table "
                    + STORE_TABLE + "("
                    + ID_STORE + " Integer primary key autoincrement, "
                    + ROW_NAME_STORE + " Text, "
                    + ROW_CATEGORY + " Text, "
                    + ROW_ADDRESS + " Text, "
                    + ROW_EMAIL + " Text, "
                    + ROW_TELEPHONE_STORE + " Text, "
                    + ROW_LAST_VISIT + " Text, "
                    + ROW_LATITUDE + " double,"
                    + ROW_LONGITUDE + " double,"
                    + ID_USER + " Integer, foreign key ( "
                    + ID_USER + ") references "
                    + USER_TABLE + "(" + ID_USER + "));";

    //create table order for order something
    private final String CREATE_TABLE_ORDER =
            "create table "
                    + ORDER_TABLE + "("
                    + ID_ORDER + " Integer primary key autoincrement, "
                    + ROW_NAME_ORDER + " Text, "
                    + ROW_CODE + " Text, "
                    + ROW_STOCK + " Integer, "
                    + ROW_PRICE + " Integer, "
                    + ROW_BUY + " Boolean Integer Default 0);";

    //create table Images for save path image
    private final String CREATE_TABLE_IMAGES =
            "create table "
                    + IMAGE_TABLE + "("
                    + ID_IMAGE + " Integer primary key autoincrement, "
                    + ROW_IMAGE_PATH + " Text, "
                    + ID_STORE + " Integer, foreign key ( "
                    + ID_STORE + ") references "
                    + STORE_TABLE + "(" + ID_STORE + "));";

    //create table history for history transaction
    private final String CREATE_TABLE_HISTORY =
            "create table "
                    + HISTORY_TABLE + "("
                    + ID_HISTORY + " Integer primary key autoincrement, "
                    + ROW_QUANTITY + " Integer, "
                    + ROW_TOTAL + " Integer, "
                    + ROW_LAST_DATE + " Text, "
                    + ID_STORE + " Integer, foreign key ( "
                    + ID_STORE + ") references "
                    + STORE_TABLE + "(" + ID_STORE + "));";

    //create table employee for data employee
    private final String CREATE_TABLE_EMPLOYEE =
            "create table "
                    + EMPLOYEE_TABLE + "("
                    + ID_EMPLOYEE + " Integer primary key autoincrement, "
                    + ROW_TELEPHONE_EMPLOYEE + " Text, "
                    + ROW_NAME_EMLOYEE + " Text, "
                    + ROW_POSITION + " Text, "
                    + ID_STORE + " Integer, foreign key ( "
                    + ID_STORE + ") references "
                    + STORE_TABLE + "(" + ID_STORE + "));";

    //create table detail history for detail history transaction
    private final String CREATE_TABLE_DETAIL_HISTORY =
            "create table "
                    + DETAIL_HISTORY_TABLE + "("
                    + ID_DETAIL_HISTORY + " Integer primary key autoincrement, "
                    + ROW_CODE + " Text, "
                    + ROW_NAME_DETAIL_HISTORY + " Text, "
                    + ROW_PRICE + " Integer, "
                    + ROW_QUANTITY + " Integer, "
                    + ROW_LAST_DATE + " Text, "
                    + ID_STORE + " Integer, foreign key ( "
                    + ID_STORE + ") references "
                    + STORE_TABLE + "(" + ID_STORE + "));";

    //create table category for select category
    private final String CREATE_TABLE_CATEGORY =
            "create table "
                    + CATEGORY_TABLE + "("
                    + ID_CATEGORY + " Integer primary key autoincrement, "
                    + ROW_CATEGORY + " Text);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create table with sqLite database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_STORE);
        sqLiteDatabase.execSQL(CREATE_TABLE_ORDER);
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGES);
        sqLiteDatabase.execSQL(CREATE_TABLE_EMPLOYEE);
        sqLiteDatabase.execSQL(CREATE_TABLE_DETAIL_HISTORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_HISTORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + STORE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + ORDER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + EMPLOYEE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + HISTORY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + DETAIL_HISTORY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + IMAGE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + CATEGORY_TABLE);

        onCreate(sqLiteDatabase);
    }
}
