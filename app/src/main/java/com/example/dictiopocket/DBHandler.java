package com.example.dictiopocket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
Cette classe sert pour la gestion de la base de données
 */
public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Form.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
    Cette méthode permet de supprimer la table de la base de données
     */
    public void deleteDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Form.TABLE_NAME);
        onCreate(db);
    }

    /*
    Cette méthode est appelée lors de la création de la base de données.
    Elle crée la table dans la base de données
     */
    @Override
    public void onCreate(SQLiteDatabase db) {


        String query = "CREATE TABLE " + DBContract.Form.TABLE_NAME + " (" +
                DBContract.Form.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.Form.COLUMN_QUESTION + " TEXT," +
                DBContract.Form.COLUMN_REP1 + " TEXT," +
                DBContract.Form.COLUMN_REP2 + " TEXT," +
                DBContract.Form.COLUMN_REP + " TEXT)";
        db.execSQL(query);
    }

    /*
    Cette méthode est appelée pour mettre à jour base de données
    */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + DBContract.Form.TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    /*
    Cette méthode permet d'insérer une nouvelle question dans la base
    de données
     */
    public void insertQuestion(String question, String rep1, String rep2, String rep) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();

        try {
            row.put(DBContract.Form.COLUMN_QUESTION, question);
            row.put(DBContract.Form.COLUMN_REP1, rep1);
            row.put(DBContract.Form.COLUMN_REP2, rep2);
            row.put(DBContract.Form.COLUMN_REP, rep);
            db.insert(DBContract.Form.TABLE_NAME, null, row);
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            Log.e("ERROR", "Déja Insert");
        } catch (Exception e) {
            Log.e("ERROR", "Autres problèmes");
        }
    }

    /*
    Cette méthode sélectionne un ID de question au hasard dans la base de données.
    Elle renvoie l'ID de la question sélectionnée
     */
    public int selectRandomQuestionId() {
        SQLiteDatabase db = this.getReadableDatabase();
        int questionId = -1;
        String[] projection = {DBContract.Form.ID};
        String orderBy = "RANDOM()";
        String limit = "1";
        Cursor cursor = db.query(
                DBContract.Form.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                orderBy,
                limit
        );
        if (cursor.moveToFirst()) {
            questionId = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Form.ID));
        }
        cursor.close();
        return questionId;
    }

    /*
    Cette méthode permet d'obtenir la question associée à l'ID de la question donné.
     */
    public String getQuestionById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                DBContract.Form.COLUMN_QUESTION
        };

        String selection = DBContract.Form.ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                DBContract.Form.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String question = null;
        if (cursor.moveToFirst()) {
            question = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_QUESTION));
        }

        cursor.close();


        return question;
    }

    /*
    Cette méthode permet d'obtenir la première réponse associée à l'ID de la question donné
     */
    public String getRep1ById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                DBContract.Form.COLUMN_REP1
        };

        String selection = DBContract.Form.ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                DBContract.Form.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String rep = null;
        if (cursor.moveToFirst()) {
            rep = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_REP1));
        }

        cursor.close();


        return rep;
    }

    /*
    Cette méthode permet d'obtenir la deuxième réponse associée à l'ID de la question donné
     */
    public String getRep2ById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                DBContract.Form.COLUMN_REP2
        };

        String selection = DBContract.Form.ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                DBContract.Form.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String rep = null;
        if (cursor.moveToFirst()) {
            rep = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_REP2));
        }

        cursor.close();


        return rep;
    }

    /*
    Cette méthode permet d'obtenir la bonne reponse associée à l'ID de la question donné
     */
    public String getRepById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                DBContract.Form.COLUMN_REP
        };

        String selection = DBContract.Form.ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                DBContract.Form.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String rep = null;
        if (cursor.moveToFirst()) {
            rep = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_REP));
        }

        cursor.close();


        return rep;
    }
}
