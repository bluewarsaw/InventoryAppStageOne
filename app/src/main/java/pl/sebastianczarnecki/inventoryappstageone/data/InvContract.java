package pl.sebastianczarnecki.inventoryappstageone.data;

import android.provider.BaseColumns;

public class InvContract {

    public InvContract() {
    }

    public final static class InventoryEntry implements BaseColumns {

        public final static String TABLE_NAME = "product";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "product_name";
        public final static String COLUMN_PRODUCT = "price";
        public final static String COLUMN_QUANTITY = "quantity";
        public final static String COLUMN_SUPPLIER_NAME = "supplier_name";
        public final static String COLUMN_SUPPLIER_PHONE = "supplier_phone_number";

        public final static int SUPPLIER_SPINNER_UNKNOWN = 0;
        public final static int SUPPLIER_SPINNER_AMAZON = 1;
        public final static int SUPPLIER_SPINNER_ALLEGRO = 2;
        public final static int SUPPLIER_SPINNER_EBAY = 3;
    }
}
