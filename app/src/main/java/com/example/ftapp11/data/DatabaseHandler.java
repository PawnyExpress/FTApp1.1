package com.example.ftapp11.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.widget.Toast;
import java.util.Arrays;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class DatabaseHandler extends SQLiteOpenHelper{
    private static final String DB_NAME = "finance";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME_INCOME = "income";
    private static final String TABLE_NAME_EXPENSES = "expenses";
    private static final String TABLE_NAME_SETTINGS = "settings";
    private static final String ID_COL = "id";
    private static final String DESC_COL = "description";
    private static final String DATE_COL = "date";
    private static final String AMOUNT_COL = "amount";

    private static final String FROM_DATE_COL = "fromDate" ;
    private static final String TO_DATE_COL = "toDate" ;
    private static final String NOTIFICATIONS_TOGGLE = "notificationsToggle";
    private static final String INTERVAL_TOGGLE = "intervalToggle";
    private static final int columnCount = 3;
    private static final String incomeTableCreate = "CREATE TABLE " + TABLE_NAME_INCOME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DESC_COL + " TEXT, "
            + DATE_COL + " DATE, "
            + AMOUNT_COL + " INTEGER)";
    private static final String expensesTableCreate = "CREATE TABLE " + TABLE_NAME_EXPENSES + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DESC_COL + " TEXT, "
            + DATE_COL + " DATE, "
            + AMOUNT_COL + " INTEGER)";
    private static final String settingsTableCreate = "CREATE TABLE " + TABLE_NAME_SETTINGS + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FROM_DATE_COL + " TEXT, "
            + TO_DATE_COL + " TEXT, "
            + NOTIFICATIONS_TOGGLE + " BOOLEAN DEFAULT 'true',"
            + INTERVAL_TOGGLE + " BOOLEAN DEFAULT 'false')";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Called once on creation of DB. Used to create income and expenses tables.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(FROM_DATE_COL, "11/05/2023");
        values.put(TO_DATE_COL, "11/15/2023");
        values.put(NOTIFICATIONS_TOGGLE, true);
        values.put(INTERVAL_TOGGLE, false);

        db.execSQL(incomeTableCreate);
        db.execSQL(expensesTableCreate);
        db.execSQL(settingsTableCreate);

        db.insert(TABLE_NAME_SETTINGS, null, values);
    }

    /**
     * Information sent from UI is inputted into database with current date grabbed and added.
     * @param desc String description of income entry.
     * @param amount Integer amount of income entry.
     */
    public void addNewIncome(String desc, Double amount, String date) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            if (date == "") {
                TimeZone tz = TimeZone.getDefault();
                Calendar calendar = new GregorianCalendar(tz);
                date = calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DATE)
                        + "/" + calendar.get(Calendar.YEAR);
            }

            values.put(DESC_COL, desc);
            values.put(DATE_COL, date);
            values.put(AMOUNT_COL, amount);

            db.insert(TABLE_NAME_INCOME, null, values);
            db.close();

    }

    /**
     * Information sent from UI is inputted into database with current date grabbed and added.
     * @param desc String description of expense entry.
     * @param amount Integer amount of expense entry.
     */
    public void addNewExpense(String desc, Double amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        TimeZone tz = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(tz);
        String date = calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DATE)
                + "/" + calendar.get(Calendar.YEAR);

        values.put(DESC_COL, desc);
        values.put(DATE_COL, date);
        values.put(AMOUNT_COL, amount);

        db.insert(TABLE_NAME_EXPENSES, null, values);
        db.close();

    }

    /**
     * Used to grab expense table data.
     * @return 2-Dimensional string array of valid rows.
     */
    public String getExpenses(){
        int[] fromDate, toDate;
        String[] dates = getIntervalDates();
        String fromDateString = dates[0];
        String toDateString = dates[1];

        fromDate = convertDates(fromDateString);
        toDate = convertDates(toDateString);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT FROM_DATE_COL FROM " + TABLE_NAME_EXPENSES + ";", null );


        String[][] expenseData = new String[cursor.getCount()][columnCount];
        if (cursor.moveToFirst()){
            if (cursor.getString(2) != null){
                int i = 0;
                do {
                    int[] checkDate = convertDates(cursor.getString(2));
                    if (checkDate[0] >= fromDate[0] && checkDate[0] <= toDate[0]){
                        if (checkDate[1] >= fromDate[1] && checkDate[1] <= toDate[1]){
                            if (checkDate[2] >= fromDate[2] && checkDate[2] <= toDate[2]) {
                                expenseData[i][0] = cursor.getString(1);
                                expenseData[i][1] = cursor.getString(2);
                                expenseData[i][2] = cursor.getString(3);

                            }
                        }
                    }
                    i++;
                } while (cursor.moveToNext());
            }
        }
        expenseData = cleanEmptyCells(expenseData);
        System.out.println(stringifyData(expenseData, expenseData.length));
        db.close();
        return "Temporary Return";
    }

    /**
     * Used to grab income table data.
     * @return 2-Dimensional string array of valid rows.
     */
    public String getIncome(){
        int[] fromDate, toDate;
        String[] dates = getIntervalDates();
        String fromDateString = dates[0];
        String toDateString = dates[1];

        fromDate = convertDates(fromDateString);
        toDate = convertDates(toDateString);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT FROM_DATE_COL FROM " + TABLE_NAME_INCOME + ";", null );


        String[][] incomeData = new String[cursor.getCount()][columnCount];
        if (cursor.moveToFirst()){
            if (cursor.getString(2) != null){
                int i = 0;
                do {
                    int[] checkDate = convertDates(cursor.getString(2));
                    if (checkDate[0] >= fromDate[0] && checkDate[0] <= toDate[0]){
                        if (checkDate[1] >= fromDate[1] && checkDate[1] <= toDate[1]){
                            if (checkDate[2] >= fromDate[2] && checkDate[2] <= toDate[2]) {
                                incomeData[i][0] = cursor.getString(1);
                                incomeData[i][1] = cursor.getString(2);
                                incomeData[i][2] = cursor.getString(3);

                            }
                        }
                    }
                    i++;
                } while (cursor.moveToNext());
            }
        }
        incomeData = cleanEmptyCells(incomeData);
        System.out.println(stringifyData(incomeData, incomeData.length));
        db.close();
        return "Temporary Return";
    }

    /**
     * Takes a 2-Dimensional array pulled from database and converts the data
     * to a readable string.
     * @param data
     * @param cursorCount
     * @return
     */
    private String stringifyData(String[][] data, int cursorCount){
        String print = "";
        for (int i = 0; i < cursorCount; i++){
            for (int j = 0; j < columnCount; j++){
                if (data[i][j] != null){
                    if (j == 0){
                        print += "Desc: ";
                    } else if (j == 1){
                        print += " Date: ";
                    } else if (j == 2){
                        print += " Amount: ";
                    }
                    print += "" + data[i][j];
                } else {

                }
            }
            print += "\n";
        }
        return print;
    }

    /**
     * Takes in a 2-Dimensional string array that is pulled from a database call
     * then removes all null rows to return a cleaned 2-Dimensional array.
     * @param toClean
     * @return
     */
    private String[][] cleanEmptyCells(String[][] toClean){
        String[][] cleanedArray;
        int count = 0;
        List<Integer> cleanCellList =new ArrayList<Integer>();

        for(int i = 0; i < toClean.length; i++){
            if(toClean[i][0] != null){
                count++;
                cleanCellList.add(i);
            }
        }

        cleanedArray = new String[count][columnCount];

        Object[] test = cleanCellList.toArray();
        for(int i = 0; i < test.length; i++){
            cleanedArray[i] = toClean[Integer.parseInt(test[i].toString())];
        }

        return cleanedArray;
    }

    /**
     * Clears and calls recreateIncomeTable() to rebuild a clean table.
     */
    public void clearIncome(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE " + TABLE_NAME_INCOME + ";");
        recreateIncomeTable();
    }

    /**
     * Clears and calls recreateExpenseTable() to rebuild a clean table.
     */
    public void clearExpenses(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE " + TABLE_NAME_EXPENSES + ";");
        recreateExpenseTable();
    }

    /**
     * Clears and calls recreateSettingsTable() to rebuild a clean table.
     */
    public void clearSettings(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE " + TABLE_NAME_SETTINGS + ";");
        recreateSettingsTable();
    }


    /**
     * Used to recreate income table in case it is cleared for testing.
     */
    private void recreateIncomeTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(incomeTableCreate);
    }

    /**
     * Used to recreate expense table in case it is cleared for testing.
     */
    private void recreateExpenseTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(expensesTableCreate);
    }

    /**
     * Used to recreate settings table in case it is cleared for testing.
     */
    private void recreateSettingsTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(FROM_DATE_COL, "11/05/2023");
        values.put(TO_DATE_COL, "11/15/2023");
        values.put(NOTIFICATIONS_TOGGLE, true);
        values.put(INTERVAL_TOGGLE, false);

        db.execSQL(settingsTableCreate);
        db.insert(TABLE_NAME_SETTINGS, null, values);
    }

    /**
     * Takes a date string and converts it into an integer array, to be used in
     * calculations determining if pulled dates are within given range.
     * @param date ex. "9/20/2023'
     * @return [9, 20, 2023]
     */
    private int[] convertDates(String date){
        int[] convertedDate = new int[3];
        String[] dateString;
        dateString = date.split("/", -2);
        convertedDate[0] = Integer.parseInt(dateString[0]);
        convertedDate[1] = Integer.parseInt(dateString[1]);
        convertedDate[2] = Integer.parseInt(dateString[2]);

        return convertedDate;
    }

    public String[] getIntervalDates(){
        String[] dates = new String[2];
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_SETTINGS + ";", null );
        if (cursor.moveToFirst()){
            dates[0] = cursor.getString(1);
            dates[1] = cursor.getString(2);
        }
        return dates;
    }

    /**
     * Default method that just exists here, not currently used or assumed to be needed in future.
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
