package pl.sebastianczarnecki.inventoryappstageone;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import pl.sebastianczarnecki.inventoryappstageone.data.InvContract;
import pl.sebastianczarnecki.inventoryappstageone.data.InvDbHelper;

public class AddActivity extends AppCompatActivity {

    private EditText productNameET;
    private EditText productPriceET;
    private EditText productQuantityET;
    private Spinner supplierNameSpinner;
    private EditText supplierNumberET;

    private int supplierName = InvContract.InventoryEntry.SUPPLIER_SPINNER_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        productNameET = findViewById(R.id.product_name_ET);
        productPriceET = findViewById(R.id.product_price_ET);
        productQuantityET = findViewById(R.id.product_quantity_ET);
        supplierNameSpinner = findViewById(R.id.product_supplier_ET);
        supplierNumberET = findViewById(R.id.product_supplier_phone_ET);
        setupSpinner();
    }

    private void insertProduct() {

        String nameString = productNameET.getText().toString().trim();

        String priceString = productPriceET.getText().toString().trim();
        int productPriceInteger = Integer.parseInt(priceString);

        String quantityString = productQuantityET.getText().toString().trim();
        int productQuantityInteger = Integer.parseInt(quantityString);

        String supplierPhoneString = supplierNumberET.getText().toString().trim();
        int productSupplierPhoneNumberInteger = Integer.parseInt(supplierPhoneString);

        InvDbHelper mDbHelper = new InvDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InvContract.InventoryEntry.COLUMN_NAME, nameString);
        values.put(InvContract.InventoryEntry.COLUMN_PRODUCT, productPriceInteger);
        values.put(InvContract.InventoryEntry.COLUMN_QUANTITY, productQuantityInteger);
        values.put(InvContract.InventoryEntry.COLUMN_SUPPLIER_NAME, supplierName);
        values.put(InvContract.InventoryEntry.COLUMN_SUPPLIER_PHONE, productSupplierPhoneNumberInteger);

        long newRowId = db.insert(InvContract.InventoryEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, (getString(R.string.error)), Toast.LENGTH_SHORT).show();
            Log.d((getString(R.string.error_message)), (getString(R.string.no_row)));

        } else {
            Toast.makeText(this, (getString(R.string.product_save)) + newRowId, Toast.LENGTH_SHORT).show();
            Log.d((getString(R.string.success)), (getString(R.string.success)));
        }
    }

    private void setupSpinner() {

        ArrayAdapter supplierSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_suppliers, android.R.layout.simple_spinner_item);

        supplierSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        supplierNameSpinner.setAdapter(supplierSpinnerAdapter);

        supplierNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.supplier_amazon))) {
                        supplierName = InvContract.InventoryEntry.SUPPLIER_SPINNER_AMAZON;
                    } else if (selection.equals(getString(R.string.supplier_allegro))) {
                        supplierName = InvContract.InventoryEntry.SUPPLIER_SPINNER_ALLEGRO;
                    } else if (selection.equals(getString(R.string.supplier_ebay))) {
                        supplierName = InvContract.InventoryEntry.SUPPLIER_SPINNER_EBAY;
                    } else {
                        supplierName = InvContract.InventoryEntry.SUPPLIER_SPINNER_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                supplierName = InvContract.InventoryEntry.SUPPLIER_SPINNER_UNKNOWN;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        Log.d((getString(R.string.message)), (getString(R.string.open_app_activity)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                insertProduct();
                finish();
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
