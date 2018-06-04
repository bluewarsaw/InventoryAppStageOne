package pl.sebastianczarnecki.inventoryappstageone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InvDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = InvDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    public InvDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_PRODUCT_TABLE = "CREATE TABLE " + InvContract.InventoryEntry.TABLE_NAME + "("
                + InvContract.InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InvContract.InventoryEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + InvContract.InventoryEntry.COLUMN_PRODUCT + " INTEGER NOT NULL, "
                + InvContract.InventoryEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, "
                + InvContract.InventoryEntry.COLUMN_SUPPLIER_NAME + " INTEGER NOT NULL DEFAULT 0, "
                + InvContract.InventoryEntry.COLUMN_SUPPLIER_PHONE + " INTEGER);" ;

        db.execSQL(SQL_CREATE_PRODUCT_TABLE);

        Log.d("success" , "created table inventory.db");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
