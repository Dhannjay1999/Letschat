package com.example.lenovo.letschat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.R.attr.onClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public FirebaseDatabase f;
public    DatabaseReference d;



public FirebaseAuth mfirebseauth;
    ValueEventListener a;
    private static final String TAG="Mess";
    public static  final int Default_Msg_Limit=1000;
    public static final int RC_SIGN_IN = 1;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private FirebaseAuth.AuthStateListener mauthstatelistener;
    String al;
    public EditText mytext;
 public ImageButton msgbtn;


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"start point");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        mytext=(EditText)findViewById(R.id.mytext);
//        msgbtn=(ImageButton)findViewById(R.id.msgbtn);
        f=FirebaseDatabase.getInstance();
        d=f.getReference("Chat");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
mfirebseauth=FirebaseAuth.getInstance();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "all are  with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        mytext.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().trim().length() > 0) {
//                    msgbtn.setEnabled(true);
//                } else {
//                    msgbtn.setEnabled(false);
//                }
//            }

//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//        mytext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Default_Msg_Limit)});




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.i(TAG,"middle point");
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



        mauthstatelistener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.i(TAG,"second point");
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MainActivity.this, "Welcome You are logged in", Toast.LENGTH_SHORT).show();

                } else {

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                                    .setProviders(
                                            AuthUI.EMAIL_PROVIDER,

                                            AuthUI.GOOGLE_PROVIDER

                                    )
                                    .build(),
                            RC_SIGN_IN);
            }
        }
    };
}

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            AuthUI.getInstance().signOut(this);

        } else if (id == R.id.nav_slideshow) {
String as=mfirebseauth.getCurrentUser().getDisplayName();
al=mfirebseauth.getCurrentUser().getUid();
            Intent i=new Intent(MainActivity.this,Main2Activity.class);
            i.putExtra("user",as);
            i.putExtra("ids",al);
            startActivity(i);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mfirebseauth.addAuthStateListener(mauthstatelistener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mauthstatelistener!=null){
        mfirebseauth.removeAuthStateListener(mauthstatelistener);

        }
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Not Signed in", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        int no;
        FloatingActionButton grpbtn;
        ListView grplist;
        EditText grptext;
        public FirebaseDatabase f;
        public    DatabaseReference d;
        DatabaseReference Dg;
       ListView mylist;
       static ArrayList<Details> details;
        AdapterList adapterList;
         FirebaseAuth mfirebseauth;
        ValueEventListener a,b;
        public static  final int Default_Msg_Limit=1000;
        public static final int RC_SIGN_IN = 1;
        private FirebaseAuth.AuthStateListener mauthstatelistener;
        DatabaseReference df;
        ArrayList<Message> detailsgroup;
        AdapterMessage adaptergroup;
        public EditText mytext;
        public ImageButton msgbtn;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            f = FirebaseDatabase.getInstance();
            d = f.getReference("Chat");
            df = f.getReference("users");
            Dg=f.getReference("Group Chat");
            mfirebseauth=FirebaseAuth.getInstance();
            df.child(mfirebseauth.getCurrentUser().getUid()).child("name").setValue(mfirebseauth.getCurrentUser().getDisplayName());

             Bundle b = getArguments();

            int selectionNumber = b.getInt(ARG_SECTION_NUMBER);
            detailsgroup=new ArrayList<>();
            adaptergroup=new AdapterMessage(getActivity(),detailsgroup);
            details = new ArrayList<>();
            adapterList = new AdapterList(getActivity(), details);



                     if (selectionNumber == 1) {


                View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
                         onStart();
                return rootView;
                }

//            mytext=(EditText) rootView.findViewById(R.id.mytext);
//            msgbtn=(ImageButton)rootView.findViewById(R.id.msgbtn);

//DatabaseReference users=d.child("users").child(mfirebseauth.getCurrentUser().getUid());
//users.child("Name").setValue(mfirebseauth.getCurrentUser().getDisplayName());
            else if(selectionNumber==2)
            {
                no=2;
                View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
                mylist = (ListView) rootView.findViewById(R.id.mylist);
                mylist.setAdapter(adapterList);
                mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent i1=new Intent(getActivity(), Inbox.class);
                        i1.putExtra("UserNames",details.get(i).getName());
                        i1.putExtra("FireAuth",mfirebseauth.getCurrentUser().getDisplayName());
                        startActivity(i1);
                    }
                });
onStart();
                return rootView;
            }
            else
            {
                View rootView = inflater.inflate(R.layout.activity_inbox, container, false);
                grpbtn=(FloatingActionButton)rootView.findViewById(R.id.msgbtn);
                grptext=(EditText)rootView.findViewById(R.id.mytext);
                grplist=(ListView)rootView.findViewById(R.id.privatemessage);

                 onStart();
                grptext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        grpbtn.setEnabled(false);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.toString().trim().length() > 0) {
                            grpbtn.setEnabled(true);
                        } else {
                            grpbtn.setEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        grpbtn.setEnabled(false);
                    }
                });
                grptext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Default_Msg_Limit)});


                grpbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        detailsgroup.clear();
                        String messages = grptext.getText().toString().trim();

                        String id = Dg.push().getKey();


                        Message message = new Message(id,messages,mfirebseauth.getCurrentUser().getDisplayName(),"Anonymus");
                        Dg.child(id).setValue(message);

                        grptext.setText("");
                    }
                });



                        no = 3;
                grplist.setAdapter(adaptergroup);


                return rootView;
            }
//            mytext.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    if (charSequence.toString().trim().length() > 0) {
//                        msgbtn.setEnabled(true);
//                    } else {
//                        msgbtn.setEnabled(false);
//                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                }
//            });
//            mytext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Default_Msg_Limit)});
//
//
//            msgbtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    String name = mytext.getText().toString().trim();
//                    FirebaseUser as = mfirebseauth.getCurrentUser();
//                    String frnd=as.getDisplayName();
//
//                    String id = d.push().getKey();
//                    Message message = new Message(id, name, frnd,null);
//                    d.child(id).setValue(message);
//                    mytext.setText("");
//                }
//            });
            }



        @Override
        public void onStart() {
            super.onStart();
            if(b==null){
                b=Dg.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        detailsgroup.clear();
                        for(DataSnapshot m: dataSnapshot.getChildren()){
                            Message d1=m.getValue(Message.class);
                            detailsgroup.add(d1);

                        }
                        adaptergroup.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }



           if(a==null){
               Log.i(TAG,"Onstart");


                a=df.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       details.clear();
                       for(DataSnapshot mysnapshot: dataSnapshot.getChildren()){
                           Details detail=mysnapshot.getValue(Details.class);

                               details.add(detail);

                           Log.i(TAG,detail.getName());
                       }
                       adapterList.notifyDataSetChanged();
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
           }

        }



        @Override
        public void onResume() {
            super.onResume();

        }

        @Override
        public void onPause() {
            super.onPause();
            if(a!=null){
                mfirebseauth.removeAuthStateListener(mauthstatelistener);
                detach();
               details.clear();
            }
        }

        private  void detach() {
            if (a != null) {
                df.removeEventListener(a);
            }
            a=null;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
