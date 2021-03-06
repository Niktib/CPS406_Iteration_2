package ca.ryerson.scs.iteration2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;

public class MembersActivity extends AppCompatActivity {

    public ConnectToDatabase Conn = new ConnectToDatabase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        // Query database
        String[] members = new String[]{
            "Billy",
            "John",
            "Suzy",
            "Phillis",
            "Rico",
            "Alfred",
            "Sam"
        };

        // Populate a List from Array elements
        final List<String> membersList = new ArrayList<String>(Arrays.asList(members));

        gridview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, membersList) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;

                RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT
                );
                tv.setLayoutParams(lp);

                // Get the TextView LayoutParams
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();

                // Set the TextView layout parameters
                tv.setLayoutParams(params);

                // Display TextView text in center position
                tv.setGravity(Gravity.CENTER);

                // Set the TextView text font family and text size
                tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);

                // Set the TextView text (GridView item text)
                tv.setText(membersList.get(position));

                // Set the TextView background color
                tv.setBackgroundColor(Color.parseColor("#FF65C6EB"));

                // Return the TextView widget as GridView item
                return tv;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    //Creates a List<String> of customer names and returns it
    private List<String> CustomerList(){
        String sql = "SELECT Name " +
                    "FROM Customer";

        ResultSet rs = Conn.RetrieveData(sql);
        List<String> Names = new LinkedList<String>();
        try {
            while (rs.next()) {
                Names.add(rs.getString("Name"));
            }
            return Names;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //Creates a List<String> of customer names who owe money
    //Also retrieves the emails
    private List<String> CustomerDebt(){
        String sql = "SELECT Name, Email " +
                    "FROM Customers " +
                    "WHERE Consec_Pay < 0";

        ResultSet rs = Conn.RetrieveData(sql);
        List<String> Names = new LinkedList<String>();
        Integer i = 0;
        try {
            while (rs.next()) {
                Names.add("");
                Names.set(i, rs.getString("Name") + "," +rs.getString("Email"));
                i++;
            }
            return Names;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
