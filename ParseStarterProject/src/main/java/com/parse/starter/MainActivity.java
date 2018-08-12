
package com.parse.starter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
  String abc = ParseUser.getCurrentUser().getUsername();
  String def= ParseUser.getCurrentUser().getObjectId();

  Boolean signUpModeActive= true;
  TextView changeSignupModeTextView;
  EditText passwordEditText;

  public void showUserList(){

    Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
    startActivity(intent);

  }

  @Override
  public boolean onKey(View view , int i, KeyEvent keyEvent)
  {

    if (i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN)
    {
      signUp(view);
    }
    return false;
  }





@Override
public void onClick (View view) {

  if (view.getId()== R.id.changeSignupModeTextView) {
    Button signupButton = (Button) findViewById(R.id.signupButton);

    if (signUpModeActive)
    {
      signUpModeActive=false;
      signupButton.setText("DBA LOGIN");
      changeSignupModeTextView.setText("User? -> ");
    }
    else
    {
      signUpModeActive=true;
      signupButton.setText("MARK PRESENT");
      changeSignupModeTextView.setText("DBA? -> ");
    }
  }

}

  public void signUp(View view) {
    final EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
    EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

    if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {
      Toast.makeText(this, "Username & Pass req. ", Toast.LENGTH_SHORT).show();
    } else {
      if (signUpModeActive) {
        ParseUser user = new ParseUser();

        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {

            if (e == null) {
              Toast.makeText(MainActivity.this, "Attendance Marked ->  " + usernameEditText.getText().toString(), Toast.LENGTH_SHORT).show();

              Log.i("Signup", "OK");
              if(abc=="Chitkara") {
                Toast.makeText(MainActivity.this, "DBA Login Successful" , Toast.LENGTH_SHORT).show();

                showUserList();
              }

            } else {
              //Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
              Toast.makeText(MainActivity.this, "Already Marked the attendance!" , Toast.LENGTH_SHORT).show();

            }
          }

        });
      } else {
        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {

            if (user !=null) {
              Log.i("Signup", "Success");
              if (usernameEditText.getText().toString().matches("Chitkara")) {
                showUserList();
              }
              else
              {
                //Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "You're not a DBA!!" , Toast.LENGTH_SHORT).show();

              }

            }
            else
            {
              //Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
              Toast.makeText(MainActivity.this, "You're not a DBA!!" , Toast.LENGTH_SHORT).show();

            }

          }
        });
      }
      }

  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
changeSignupModeTextView = (TextView) findViewById(R.id.changeSignupModeTextView);
changeSignupModeTextView.setOnClickListener(this);

    //RelativeLayout backgroundRelativeLayout = (RelativeLayout) findViewById(R.id.backgroundRelativeLayout);
    //ImageView logoImageView= ()

    passwordEditText= (EditText) findViewById(R.id.passwordEditText);
    passwordEditText.setOnKeyListener(this);


    if (abc=="Chitkara") {
      showUserList();
    }
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }
}

