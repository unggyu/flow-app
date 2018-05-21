package kr.hs.dgsw.flow.data.sqlite;

public final class DatabaseContract {

    private DatabaseContract() {}

    public static abstract class TokenTable {

        public static final String TABLE_NAME = "token";
        public static final String COLUMN_TOKEN = "token";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_TOKEN + " TEXT PRIMARY KEY );";

        public static final String DELETE_TABLE =
                "DROP TABLE IF EXIST " + TABLE_NAME;
    }

    public static abstract class OutTable {

        public static final String TABLE_NAME = "out";
        public static final String COLUMN_IDX = "idx";
        public static final String COLUMN_TOKEN = "user_token";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_ACCEPT = "accept";
        public static final String COLUMN_OUT = "out_date";
        public static final String COLUMN_IN = "in_date";
        public static final String COLUMN_REASON = "reason";
        public static final String COLUMN_CLASS = "class";
        public static final String COLUMN_EMAIL = "email";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_IDX + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TOKEN + " TEXT, " +
                        COLUMN_TYPE + " INTEGER, " +
                        COLUMN_ACCEPT + " INTEGER, " +
                        COLUMN_OUT + " DATETIME, " +
                        COLUMN_IN + " DATETIME, " +
                        COLUMN_REASON + " TEXT, " +
                        COLUMN_CLASS + " INTEGER, " +
                        COLUMN_EMAIL + " TEXT, " +
                        "CONSTRAINT userType_fk FOREIGN KEY(user_token)" +
                        "REFERENCES "+ TokenTable.TABLE_NAME + "("+ TokenTable.COLUMN_TOKEN + "));";

        public static final String DELETE_TABLE =
                "DROP TABLE IF EXIST " + TABLE_NAME;

    }
}
