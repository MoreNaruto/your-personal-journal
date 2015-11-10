package com.tmorris_athomas.ypj.main;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tmorris_athomas.ypj.R;
import com.tmorris_athomas.ypj.database.DatabaseHelper;

public class MainYPJ extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText userName, email, id;
    Button okButton, viewAllButton, updateButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ypj);

        myDb = new DatabaseHelper(this);
        userName = (EditText) findViewById(R.id.editableName);
        email = (EditText) findViewById(R.id.editableEmail);
        id = (EditText) findViewById(R.id.editableId);
        okButton = (Button) findViewById(R.id.ok_button);
        viewAllButton = (Button) findViewById(R.id.view_all_button);
        updateButton = (Button) findViewById(R.id.update_button);
        deleteButton = (Button) findViewById(R.id.delete_button);

        addData();
        viewAll();
        updateData();
        deleteData();
    }

    private void addData() {
        okButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(userName.getText().toString(),
                                email.getText().toString());
                        if (isInserted) {
                            Toast.makeText(MainYPJ.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainYPJ.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void viewAll() {
        viewAllButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Error", "Nothing Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id: " + res.getString(0) + "\n");
                            buffer.append("Name: " + res.getString(1) + "\n");
                            buffer.append("Email: " + res.getString(2) + "\n\n");
                        }

                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    private void updateData() {
        updateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(id.getText().toString(),
                                userName.getText().toString(),
                                email.getText().toString());
                        if (isUpdate) {
                            Toast.makeText(MainYPJ.this, "Data Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainYPJ.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void deleteData() {
        deleteButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isDelete = myDb.deleteData(id.getText().toString());
                        if (isDelete) {
                            Toast.makeText(MainYPJ.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainYPJ.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_ypj, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
