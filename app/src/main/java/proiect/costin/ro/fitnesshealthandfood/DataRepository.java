package proiect.costin.ro.fitnesshealthandfood;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
*
* clasa include metode privind lucrul cu baza de date
*
* */

public class DataRepository implements Constante {

    private SQLiteDatabase database;
    private DatabaseController databaseController;

    public DataRepository(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void open() {
        try {
            database = databaseController.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            database.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // metode INSERT
    public long insertMasa(Masa masa) {
        if(masa == null) {
            return -1;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MASA_DENUMIRE, masa.getNumeMancare());
        contentValues.put(COLUMN_MASA_PERIOADA, masa.getPerioadaMesei());
        contentValues.put(COLUMN_MASA_CALORII, masa.getNrCalorii());
        contentValues.put(COLUMN_MASA_PORTII, masa.getNrPortii());

        String dataMesei = new SimpleDateFormat
                (ActivitateMeseAdd.DATE_FORMAT, Locale.US).format(masa.getDataMesei());
        String dataReversed = dataReverse(dataMesei);
        contentValues.put(COLUMN_MASA_DATA, dataReversed);

        return database.insert(
                TABLE_NAME_MESE,
                null,
                contentValues);
    }

    public long insertFitness(Fitness fitness) {
        if(fitness == null) {
            return -1;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FITNESS_DENUMIRE, fitness.getDenumireSport());
        contentValues.put(COLUMN_FITNESS_CALORII, fitness.getNrCalorii());
        contentValues.put(COLUMN_FITNESS_MINUTE, fitness.getNrMinute());

        String dataActivitate = new SimpleDateFormat
                (ActivitateFitnessAdd.DATE_FORMAT, Locale.US).format(fitness.getDataActivitatii());
        String dataReversed = dataReverse(dataActivitate);
        contentValues.put(COLUMN_FITNESS_DATA, dataReversed);

        return database.insert(
                TABLE_NAME_FITNESS,
                null,
                contentValues);
    }

    // metode UPDATE
    public int updateMasa(Masa masa) {
        if(masa == null) {
            return -1;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MASA_DENUMIRE, masa.getNumeMancare());
        contentValues.put(COLUMN_MASA_PERIOADA, masa.getPerioadaMesei());
        contentValues.put(COLUMN_MASA_CALORII, masa.getNrCalorii());
        contentValues.put(COLUMN_MASA_PORTII, masa.getNrPortii());

        String dataMesei = new SimpleDateFormat
                (ActivitateMeseAdd.DATE_FORMAT, Locale.US).format(masa.getDataMesei());
        String dataReversed = dataReverse(dataMesei);
        contentValues.put(COLUMN_MASA_DATA, dataReversed);
        Long id = masa.getIdMasa();

        return database.update(
                TABLE_NAME_MESE,
                contentValues,
                COLUMN_MASA_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public int updateFitness(Fitness fitness) {
        if(fitness == null) {
            return -1;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FITNESS_DENUMIRE, fitness.getDenumireSport());
        contentValues.put(COLUMN_FITNESS_CALORII, fitness.getNrCalorii());
        contentValues.put(COLUMN_FITNESS_MINUTE, fitness.getNrMinute());

        String dataActivitate = new SimpleDateFormat
                (ActivitateFitnessAdd.DATE_FORMAT, Locale.US).format(fitness.getDataActivitatii());
        String dataReversed = dataReverse(dataActivitate);
        contentValues.put(COLUMN_FITNESS_DATA, dataReversed);
        Long id = fitness.getIdFitness();

        return database.update(
                TABLE_NAME_FITNESS,
                contentValues,
                COLUMN_FITNESS_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // metode DELETE
    public int deleteMasa(Long id) {
        return database.delete(
                TABLE_NAME_MESE,
                COLUMN_MASA_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public  int deleteFitness(Long id) {
        return database.delete(
                TABLE_NAME_FITNESS,
                COLUMN_FITNESS_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // metode SELECT *
    public List<Masa> findAllMese() {

        List<Masa> lista = new ArrayList<>();
        Cursor cursor = database
                .query(TABLE_NAME_MESE,
                        null,
                        null,
                        null,
                        null,
                        null,
                        COLUMN_MASA_DATA);

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_MASA_ID));
            String numeMancare = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_DENUMIRE));
            String perioadaMesei = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_PERIOADA));
            Integer calorii = cursor.getInt(cursor.getColumnIndex(COLUMN_MASA_CALORII));
            Float portii = cursor.getFloat(cursor.getColumnIndex(COLUMN_MASA_PORTII));

            Date dataMesei = null;
            String extractedDate = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_DATA));
            String correctDate = dataCorrect(extractedDate);
            try {
                dataMesei = new SimpleDateFormat(ActivitateMeseAdd.DATE_FORMAT, Locale.US)
                        .parse(correctDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Masa masa = new Masa(numeMancare, perioadaMesei, calorii, portii, dataMesei, id);
            lista.add(masa);
        }
        cursor.close();
        return lista;
    }

    public List<Fitness> findAllFitness() {

        List<Fitness> lista = new ArrayList<>();
        Cursor cursor = database
                .query(TABLE_NAME_FITNESS,
                        null,
                        null,
                        null,
                        null,
                        null,
                        COLUMN_FITNESS_DATA);

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_FITNESS_ID));
            String denumireSport = cursor.getString(cursor.getColumnIndex(COLUMN_FITNESS_DENUMIRE));
            Integer calorii = cursor.getInt(cursor.getColumnIndex(COLUMN_FITNESS_CALORII));
            Integer nrMinute = cursor.getInt(cursor.getColumnIndex(COLUMN_FITNESS_MINUTE));

            Date dataActivitatii = null;
            String extractedDate = cursor.getString(cursor.getColumnIndex(COLUMN_FITNESS_DATA));
            String correctDate = dataCorrect(extractedDate);
            try {
                dataActivitatii = new SimpleDateFormat(ActivitateFitnessAdd.DATE_FORMAT, Locale.US)
                        .parse(correctDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Fitness fitness = new Fitness(denumireSport, calorii, nrMinute, dataActivitatii, id);
            lista.add(fitness);
        }
        cursor.close();
        return lista;
    }

    // metoda de selectie pentru raportul 1. Se vor selecta randurile care contin intrarile
    // fitness pe luna curenta
    public List<Fitness> raportLunaCurenta(String searchString) {

        List<Fitness> lista = new ArrayList<>();

        String reversedSearchString = dataReverse(searchString);
        // selecteaza numai luna si anul curent:
        searchString = reversedSearchString.substring(0, 7);

        String sqlString =
                "SELECT * FROM " +
                TABLE_NAME_FITNESS +
                " WHERE " +
                COLUMN_FITNESS_DATA +
                " LIKE '" +
                searchString +
                "%'";

        Cursor cursor = database.rawQuery(sqlString, null);

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_FITNESS_ID));
            String denumireSport = cursor.getString(cursor.getColumnIndex(COLUMN_FITNESS_DENUMIRE));
            Integer calorii = cursor.getInt(cursor.getColumnIndex(COLUMN_FITNESS_CALORII));
            Integer nrMinute = cursor.getInt(cursor.getColumnIndex(COLUMN_FITNESS_MINUTE));

            Date dataActivitatii = null;
            String extractedDate = cursor.getString(cursor.getColumnIndex(COLUMN_FITNESS_DATA));
            String correctDate = dataCorrect(extractedDate);
            try {
                dataActivitatii = new SimpleDateFormat(ActivitateFitnessAdd.DATE_FORMAT, Locale.US)
                        .parse(correctDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Fitness fitness = new Fitness(denumireSport, calorii, nrMinute, dataActivitatii, id);
            lista.add(fitness);
        }
        cursor.close();
        return lista;
    }

    //metoda de selectie care returneaza o lista de mese la o data furnizata (raport 2)
    public List<Masa> bilantMese(String searchString) {
        List<Masa> lista = new ArrayList<>();

        String reversedSearchString = dataReverse(searchString);

        String sqlString =
                "SELECT * FROM " +
                        TABLE_NAME_MESE +
                        " WHERE " +
                        COLUMN_MASA_DATA +
                        " = '" +
                        reversedSearchString +
                        "'";

        Cursor cursor = database.rawQuery(sqlString, null);

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_MASA_ID));
            String numeMancare = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_DENUMIRE));
            Integer calorii = cursor.getInt(cursor.getColumnIndex(COLUMN_MASA_CALORII));
            Float nrPortii = cursor.getFloat(cursor.getColumnIndex(COLUMN_MASA_PORTII));
            String perioadaMesei = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_PERIOADA));

            Date dataMesei = null;
            String extractedDate = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_DATA));
            String correctDate = dataCorrect(extractedDate);
            try {
                dataMesei = new SimpleDateFormat(ActivitateFitnessAdd.DATE_FORMAT, Locale.US)
                        .parse(correctDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Masa masa = new Masa(numeMancare, perioadaMesei, calorii, nrPortii, dataMesei, id);
            lista.add(masa);
        }
        cursor.close();
        return lista;
    }

    //metoda de selectie care returneaza o lista de activitati fizice la o data furnizata (raport 2)
    public List<Fitness> bilantFitness(String searchString) {
        List<Fitness> lista = new ArrayList<>();

        String reversedSearchString = dataReverse(searchString);

        String sqlString =
                "SELECT * FROM " +
                        TABLE_NAME_FITNESS +
                        " WHERE " +
                        COLUMN_FITNESS_DATA +
                        " = '" +
                        reversedSearchString +
                        "'";

        Cursor cursor = database.rawQuery(sqlString, null);

        while (cursor.moveToNext()) {
            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_FITNESS_ID));
            String denumireSport = cursor.getString(cursor.getColumnIndex(COLUMN_FITNESS_DENUMIRE));
            Integer calorii = cursor.getInt(cursor.getColumnIndex(COLUMN_FITNESS_CALORII));
            Integer nrMinute = cursor.getInt(cursor.getColumnIndex(COLUMN_FITNESS_MINUTE));

            Date dataActivitatii = null;
            String extractedDate = cursor.getString(cursor.getColumnIndex(COLUMN_FITNESS_DATA));
            String correctDate = dataCorrect(extractedDate);
            try {
                dataActivitatii = new SimpleDateFormat(ActivitateFitnessAdd.DATE_FORMAT, Locale.US)
                        .parse(correctDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Fitness fitness = new Fitness(denumireSport, calorii, nrMinute, dataActivitatii, id);
            lista.add(fitness);
        }
        cursor.close();
        return lista;
    }

    // metoda care selecteaza si proceseaza datele pentru BarChart
    public ArrayList<BarEntry> barChart(String dataCautata) {
        float[] calculMese = new float[4];

        String reversedSearchString = dataReverse(dataCautata);
        String sqlString =
                "SELECT * FROM " +
                        TABLE_NAME_MESE +
                        " WHERE " +
                        COLUMN_MASA_DATA +
                        " = '" +
                        reversedSearchString +
                        "'";
        Cursor cursor = database.rawQuery(sqlString, null);

        while (cursor.moveToNext()) {
            Integer caloriiPerPortie = cursor.getInt(cursor.getColumnIndex(COLUMN_MASA_CALORII));
            Float nrPortii = cursor.getFloat(cursor.getColumnIndex(COLUMN_MASA_PORTII));
            String perioadaMesei = cursor.getString(cursor.getColumnIndex(COLUMN_MASA_PERIOADA));
            Float calcul = nrPortii * caloriiPerPortie;

            switch (perioadaMesei) {
                case "Mic dejun": {
                    calculMese[0] += calcul;
                    break;
                }
                case "Pranz": {
                    calculMese[1] += calcul;
                    break;
                }
                case "Cina": {
                    calculMese[2] += calcul;
                    break;
                }
                case "Gustare": {
                    calculMese[3] += calcul;
                    break;
                }
            }
        }
        cursor.close();
        ArrayList<BarEntry> valori = new ArrayList<>();
        valori.add(new BarEntry(0, calculMese[0]));
        valori.add(new BarEntry(1, calculMese[1]));
        valori.add(new BarEntry(2, calculMese[2] ));
        valori.add(new BarEntry(3, calculMese[3]));
        return valori;
    }

    /* data activitatilor va fi stocata in forma yyyy-MM-dd in baza de date. Pentru afisarea
       in ListView in functie de data si tinand cont de luna si an am agreat acest format.
       Atat la introducerea datei in
       DB cat si la selectia ei, se va face aceasta rearanjare de format */
    public String dataReverse(String input) {
        if (input == null) {
            return input;
        }
        String output = "";
        output += input.substring(6);
        output += input.substring(2, 6);
        output += input.substring(0, 2);

        return output;
    }

    public String dataCorrect(String input) {
        if (input == null) {
            return input;
        }
        String output = "";
        output += input.substring(8);
        output += input.substring(4, 8);
        output += input.substring(0, 4);

        return output;
    }
}
