package com.example.gpcorser.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query ("DRINK",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[] {Integer.toString(drinkNo)},
                    null, null, null);
            // move to first record
            if (cursor.moveToFirst()) {

                // get drink details
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);

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
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        /*
        Drink drink = Drink.drinks[drinkNo];

        //populate drink image
        ImageView photo = (ImageView)findViewById(R.id.photo);
        photo.setImageResource(drink.getImageResourceId()); // change
        photo.setContentDescription(drink.getName()); // change

        TextView name = (TextView)findViewById(R.id.name);
        name.setText(drink.getName()); // change

        TextView description = (TextView)findViewById(R.id.description);
        description.setText(drink.getDescription()); // change

        */

    }
}
