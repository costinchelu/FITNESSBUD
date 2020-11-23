package proiect.costin.ro.fitnesshealthandfood;

public interface Constante {

    // constante pentru JSON:

    String HTTP_TAG_MAGINE = "imagine";
    String HTTP_TAG_NUME = "nume";
    String HTTP_TAG_PORTIE = "portie";
    String HTTP_TAG_CALORII = "calorii";
    String HTTP_TAG_COMPOZITIE = "compozitie";
    String HTTP_TAG_CARBO = "carbohidrati";
    String HTTP_TAG_GRASIMI = "grasimi";
    String HTTP_TAG_PROTEINE = "proteine";

    // constante pentru baza de date:

    String DATABASE_NAME = "fit.db";
    Integer DATABASE_VERSION = 1;

    String TABLE_NAME_MESE = "mese";
    String TABLE_NAME_FITNESS = "fitness";

    String COLUMN_MASA_ID = "id_m";
    String COLUMN_MASA_DENUMIRE = "nume_m";
    String COLUMN_MASA_PERIOADA = "perioada_m";
    String COLUMN_MASA_CALORII = "calorii_m";
    String COLUMN_MASA_PORTII = "nr_portii";
    String COLUMN_MASA_DATA = "data_m";
    String COLUMN_FITNESS_ID = "id_f";
    String COLUMN_FITNESS_DENUMIRE = "nume_f";
    String COLUMN_FITNESS_CALORII = "calorii_f";
    String COLUMN_FITNESS_MINUTE = "minute_f";
    String COLUMN_FITNESS_DATA = "data_f";

    String CREATE_TABLE_MESE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_MESE + " ( " +
                    COLUMN_MASA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_MASA_DENUMIRE + " TEXT, " +
                    COLUMN_MASA_PERIOADA + " TEXT, " +
                    COLUMN_MASA_CALORII + " INTEGER, " +
                    COLUMN_MASA_PORTII + " REAL, " +
                    COLUMN_MASA_DATA + " TEXT);";

    String CREATE_TABLE_FITNESS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FITNESS + " ( " +
                    COLUMN_FITNESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_FITNESS_DENUMIRE + " TEXT, " +
                    COLUMN_FITNESS_CALORII + " INTEGER, " +
                    COLUMN_FITNESS_MINUTE + " INTEGER, " +
                    COLUMN_FITNESS_DATA + " TEXT);";

    String DROP_TABLE_MESE = "DROP TABLE IF EXISTS " + TABLE_NAME_MESE + ";";
    String DROP_TABLE_FITNESS = "DROP TABLE IF EXISTS " + TABLE_NAME_FITNESS + ";";
}
