package kr.hs.dgsw.flow.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import kr.hs.dgsw.flow.data.sqlite.model.OutRow;
import kr.hs.dgsw.flow.view.out.model.Enum.OutType;

public class OutHelper {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public OutHelper(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
        this.sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public long insert(OutRow row) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.OutTable.COLUMN_TOKEN, row.getUserToken());
        contentValues.put(DatabaseContract.OutTable.COLUMN_TYPE, row.getOutType().ordinal());
        contentValues.put(DatabaseContract.OutTable.COLUMN_ACCEPT, row.isAccept());
        contentValues.put(DatabaseContract.OutTable.COLUMN_OUT, row.getOutDateTime());
        contentValues.put(DatabaseContract.OutTable.COLUMN_IN, row.getInDateTime());
        contentValues.put(DatabaseContract.OutTable.COLUMN_REASON, row.getReason());
        contentValues.put(DatabaseContract.OutTable.COLUMN_CLASS, row.getClassNumber());
        contentValues.put(DatabaseContract.OutTable.COLUMN_EMAIL, row.getEmail());

        return sqLiteDatabase.insert(
                DatabaseContract.OutTable.TABLE_NAME,
                null,
                contentValues
        );
    }

    public ArrayList<OutRow> getOutRows() {
        Cursor cursor = sqLiteDatabase.query(
                DatabaseContract.OutTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<OutRow> outRows = new ArrayList<>();
        OutRow row;
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    row = makeOutRow(cursor);
                    outRows.add(row);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            outRows = null;
        }

        return outRows;
    }

    public OutRow getLastOutRow() {
        Cursor cursor = sqLiteDatabase.query(
                DatabaseContract.OutTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        OutRow outRow = null;
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                cursor.moveToLast();
                outRow = makeOutRow(cursor);
            }
            cursor.close();
        }

        return outRow;
    }

    private OutRow makeOutRow(Cursor cursor) {
        OutRow row = new OutRow();

        int outTypeIdx = cursor.getColumnIndex(DatabaseContract.OutTable.COLUMN_TYPE);
        OutType outType = OutType.values()[cursor.getInt(outTypeIdx)];
        row.setOutType(outType);

        int acceptIdx = cursor.getColumnIndex(DatabaseContract.OutTable.COLUMN_ACCEPT);
        boolean accept = cursor.getInt(acceptIdx) != 0;
        row.setAccept(accept);

        int outDateTimeIdx = cursor.getColumnIndex(DatabaseContract.OutTable.COLUMN_OUT);
        String outDateTime = cursor.getString(outDateTimeIdx);
        row.setOutDateTime(outDateTime);

        int inDateTimeIdx = cursor.getColumnIndex(DatabaseContract.OutTable.COLUMN_IN);
        String inDateTime = cursor.getString(inDateTimeIdx);
        row.setInDateTime(inDateTime);

        int reasonIdx = cursor.getColumnIndex(DatabaseContract.OutTable.COLUMN_REASON);
        String reason = cursor.getString(reasonIdx);
        row.setReason(reason);

        int classNumberIdx = cursor.getColumnIndex(DatabaseContract.OutTable.COLUMN_CLASS);
        int classNumber = cursor.getInt(classNumberIdx);
        row.setClassNumber(classNumber);

        int emailIdx = cursor.getColumnIndex(DatabaseContract.OutTable.COLUMN_EMAIL);
        String email = cursor.getString(emailIdx);
        row.setEmail(email);

        return row;
    }
}
