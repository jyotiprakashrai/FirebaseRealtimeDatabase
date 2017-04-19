package prakash.jyoti.com.firebaserealtimedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/*
 * Firebase Realtime Database Upload and Retrive data
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextName;
    Spinner spinnerGenres;
    Button addArtist;

    ListView listViewArtists;

    List<Artist> artistList;

    DatabaseReference databaseArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        If you will not pass artists as a reference then you will get root node of your json tree
         */

        artistList=new ArrayList<>();
        databaseArtists= FirebaseDatabase.getInstance().getReference("artists");
        initView();

        addArtist.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artistList.clear();

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                    Artist artist=artistSnapshot.getValue(Artist.class);
                    artistList.add(artist);

                }

                ArtistList adapter=new ArtistList(MainActivity.this, artistList);
                listViewArtists.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void initView(){
                editTextName = (EditText) findViewById(R.id.editTextName);
                spinnerGenres=(Spinner) findViewById(R.id.spinnerGenres);
                addArtist   =(Button) findViewById(R.id.addArtist);
                listViewArtists= (ListView) findViewById(R.id.listViewArtists);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addArtist) {
            addArtist();
        }
    }

        private void addArtist(){
            String name=editTextName.getText().toString().trim();
            String genre=spinnerGenres.getSelectedItem().toString();
            if (!TextUtils.isEmpty(name)){

                //Every time It will generate a unique key so that it can not override
                String id=databaseArtists.push().getKey();
                Artist artist=new Artist(id, name, genre);
                databaseArtists.child(id).setValue(artist);
                Toast.makeText(this, "Artist added", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "You should enter a name", Toast.LENGTH_SHORT).show();

    }

}
