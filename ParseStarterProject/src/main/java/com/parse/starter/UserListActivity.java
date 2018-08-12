package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        final ArrayList<String> usernames = new ArrayList<>();

        final ListView userListView = (ListView) findViewById(R.id.userListView);


        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, usernames);

        {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.addAscendingOrder("username");
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override

                public void done(List<ParseUser> objects, ParseException e) {
                     int count=0;
                    if (e == null) {

                        if (objects.size() > 0) {

                            for (ParseUser user : objects) {
                                usernames.add(user.getUsername());
                                count++;
                            }
                            userListView.setAdapter(arrayAdapter);
                          //  Toast.makeText(UserListActivity.this, count ,Toast.LENGTH_LONG).show();
                        }
                    } else

                    {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}
