package novoda.lib.sqliteprovider.util.analyzer;

import android.database.Cursor;

import novoda.lib.sqliteprovider.sqlite.IDatabaseMetaInfo;

class TableColumnQuery implements Query<Column> {
    private final String table;

    public TableColumnQuery(String table) {
        this.table = table;
    }

    @Override
    public String getSqlStatement() {
        return String.format("PRAGMA table_info(\"%1$s\");", table);
    }

    @Override
    public Column parseRow(Cursor cursor) {
        return new Column(getColumnName(cursor), getColumnType(cursor));
    }

    private String getColumnName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow("name"));
    }

    private IDatabaseMetaInfo.SQLiteType getColumnType(Cursor cursor) {
        return getColumnType(getColumnTypeString(cursor));
    }

    private String getColumnTypeString(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow("type"));
    }

    private IDatabaseMetaInfo.SQLiteType getColumnType(String type) {
        return IDatabaseMetaInfo.SQLiteType.valueOf(type.toUpperCase());
    }
}
