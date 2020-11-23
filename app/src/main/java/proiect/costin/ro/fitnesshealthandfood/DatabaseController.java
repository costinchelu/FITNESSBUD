package proiect.costin.ro.fitnesshealthandfood;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
*
* clasa singleton pentru instantele SQLiteOpenHelper pentru evitarea memory leaks
*
* */

public class DatabaseController extends SQLiteOpenHelper implements Constante {

    private static DatabaseController controller;

    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseController getInstance(Context context) {
        if (controller == null) {
            synchronized (DatabaseController.class) {
                if (controller == null) {
                    controller = new DatabaseController(context);
                }
            }
        }
        return controller;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_MESE);
            db.execSQL(CREATE_TABLE_FITNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE_MESE);
            db.execSQL(DROP_TABLE_FITNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
