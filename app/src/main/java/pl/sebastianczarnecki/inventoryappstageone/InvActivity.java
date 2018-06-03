package pl.sebastianczarnecki.inventoryappstageone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;
import pl.sebastianczarnecki.inventoryappstageone.data.InvContract;
import pl.sebastianczarnecki.inventoryappstageone.data.InvDbHelper;

public class InvActivity extends AppCompatActivity {

    private InvDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv);

        FloatingActionButton plus = findViewById(R.id.add);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InvActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new InvDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                InvContract.InventoryEntry._ID,
                InvContract.InventoryEntry.COLUMN_NAME,
                InvContract.InventoryEntry.COLUMN_PRODUCT,
                InvContract.InventoryEntry.COLUMN_QUANTITY,
                InvContract.InventoryEntry.COLUMN_SUPPLIER_NAME,
                InvContract.InventoryEntry.COLUMN_SUPPLIER_PHONE
        };
        Cursor cursor = db.query(
                InvContract.InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.inventoryTV);

        try {
            displayView.setText((getString(R.string.contains)) + cursor.getCount() + (getString(R.string.products_end)));

            displayView.append(
                    InvContract.InventoryEntry._ID + " | " +
                            InvContract.InventoryEntry.COLUMN_NAME + " | " +
                            InvContract.InventoryEntry.COLUMN_PRODUCT + " | " +
                            InvContract.InventoryEntry.COLUMN_QUANTITY + " | " +
                            InvContract.InventoryEntry.COLUMN_SUPPLIER_NAME + " | " +
                            InvContract.InventoryEntry.COLUMN_SUPPLIER_PHONE + "\n");

            int idColumn = cursor.getColumnIndex(InvContract.InventoryEntry._ID);
            int nameColumn = cursor.getColumnIndex(InvContract.InventoryEntry.COLUMN_NAME);
            int priceColumn = cursor.getColumnIndex(InvContract.InventoryEntry.COLUMN_PRODUCT);
            int quantityColumn = cursor.getColumnIndex(InvContract.InventoryEntry.COLUMN_QUANTITY);
            int supplierNameColumn = cursor.getColumnIndex(InvContract.InventoryEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumn = cursor.getColumnIndex(InvContract.InventoryEntry.COLUMN_SUPPLIER_PHONE);
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumn);
                String currentName =cursor.getString(nameColumn);
                int currentPrice =cursor.getInt(priceColumn);
                int currentQuantity =cursor.getInt(quantityColumn);
                int currentSupplierName =cursor.getInt(supplierNameColumn);
                int currentSupplierPhone =cursor.getInt(supplierPhoneColumn);

                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhone ));
            }

        } finally {
            cursor.close();
        }
    }
}
