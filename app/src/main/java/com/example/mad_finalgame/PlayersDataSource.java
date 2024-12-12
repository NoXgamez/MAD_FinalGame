package com.example.mad_finalgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PlayersDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_SCORE };

    public PlayersDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Player createPlayer(String name, int score) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_SCORE, score);

        long insertId = database.insert(MySQLiteHelper.TABLE_PLAYERS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PLAYERS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Player newPlayer = cursorToPlayer(cursor);
        cursor.close();
        return newPlayer;
    }

    public void deletePlayer(Player player) {
        long id = player.getId();
        System.out.println("Player deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_PLAYERS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<Player>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PLAYERS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Player player = cursorToPlayer(cursor);
            players.add(player);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return players;
    }

    private Player cursorToPlayer(Cursor cursor) {
        Player player = new Player();
        player.setId(cursor.getLong(0));
        player.setName(cursor.getString(1));
        player.setScore(cursor.getInt(2));
        return player;
    }
}