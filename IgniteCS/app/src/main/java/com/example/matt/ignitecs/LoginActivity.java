package com.example.matt.ignitecs;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnBegin = (Button) findViewById(R.id.btnBegin);

        btnBegin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                final EditText et = new EditText(LoginActivity.this);
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setTitle("Enter Password");
                alertDialogBuilder.setView(et);

                // set dialog box
                alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        String pass = "UWGBIgniteCS2017";
                        String input = et.getText().toString();

                        // checks input against password
                        if (input.contains(pass))
                            startActivity(new Intent(LoginActivity.this, DisplayInformation.class));

                    }
                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
    } // end onCreate

}
