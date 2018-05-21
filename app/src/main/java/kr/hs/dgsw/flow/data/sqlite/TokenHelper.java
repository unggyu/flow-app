package kr.hs.dgsw.flow.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TokenHelper {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public TokenHelper(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
        this.sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public long insertToken(String token) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.TokenTable.COLUMN_TOKEN, token);

        return this.sqLiteDatabase.insert(
                DatabaseContract.TokenTable.TABLE_NAME,
                null,
                contentValues
        );
    }

    public String getLastToken() {
        Cursor cursor = sqLiteDatabase.query(
                DatabaseContract.TokenTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        String token = null;
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToLast();

            int index = cursor.getColumnIndex(DatabaseContract.TokenTable.COLUMN_TOKEN);
            token = cursor.getString(index);
        }
        cursor.close();
        return token;
    }
}
