package com.example.gpcorser.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // get the drink from the intent
        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);

        // create a cursor
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            Cursor cursor = db.query ("DRINK",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String[] {Integer.toString(drinkNo)},
                    null, null, null);
            // move to first record
            if (cursor.moveToFirst()) {

                // get drink details
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);

                // populate drink name
                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);

                // populate drink description
                TextView description = (TextView)findViewById(R.id.description);
                description.setText(descriptionText);

                // populate drink image
                ImageView photo = (ImageView)findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                // populate favorite checkbox
                CheckBox favorite = (CheckBox)findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);

            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    } // end onCreate

    public void onFavoriteClicked(View view) {
        int drinkNo = (Integer)getIntent().getExtras().get("drinkNo");
        CheckBox favorite = (CheckBox)findViewById(R.id.favorite);
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("FAVORITE", favorite.isChecked());
        SQLiteOpenHelper starbuzzDatabaseHelper =
                new StarbuzzDatabaseHelper(DrinkActivity.this);
        try {
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            db.update("DRINK", drinkValues, "_id = ?",
                    new String[] {Integer.toString(drinkNo)});
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
