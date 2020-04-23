package phamthanhhuong.com.baitapkaraoke;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import phamthanhhuong.com.adapter.BaiHatAdapter;
import phamthanhhuong.com.model.BaiHat;

public class MainActivity extends AppCompatActivity {

    public static String DATABASE_NAME="Arirang.sqlite";
    public static SQLiteDatabase database=null;
    public static String TableName="ArirangSongList";
    String DB_PATH_SUFFIX="/database/";

    TabHost tabHost;
    ListView lvAll;
    BaiHatAdapter adapterAll;
    ListView lvLove;
    BaiHatAdapter adapterLove;
    public static int selectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processCopy();
        setUpTabHost();
        addControls();
        hienThiBaiHat();
        addEvents();

    }

    private void addEvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if(s.equals("tab1"))
                {
                    hienThiBaiHat();
                    selectedTab=0;
                }
                else
                {
                    hienThiBaiHatYeuThich();
                    selectedTab=1;
                }
            }
        });
    }

    private void hienThiBaiHatYeuThich() {
        Cursor c=database.query(TableName,null,"YEUTHICH",new String[]{"1"},null,null,null);
        adapterLove.clear();
        while(c.moveToNext())
        {
            String ma=c.getString(0);
            String ten=c.getString(1);
            String casi=c.getString(3);
            int thich=c.getInt(5);
            BaiHat bh=new BaiHat(ma,ten,casi,thich);
            adapterLove.add(bh);
        }
        c.close();
    }

    private void setUpTabHost() {
        tabHost =findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec tab1=tabHost.newTabSpec("tab1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("All");
        tabHost.addTab(tab1);
        TabHost.TabSpec tab2=tabHost.newTabSpec("tab2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Love");
        tabHost.addTab(tab2);
    }

    private void hienThiBaiHat() {
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor c=database.query(TableName,null,null,null,null,null,null);
        adapterAll.clear();
        while(c.moveToNext())
        {
            String ma=c.getString(0);
            String ten=c.getString(1);
            String casi=c.getString(3);
            int thich=c.getInt(5);
            BaiHat bh=new BaiHat(ma,ten,casi,thich);
            adapterAll.add(bh);
        }
        c.close();
    }

    private void addControls() {
        lvAll=findViewById(R.id.lvAll);
        adapterAll=new BaiHatAdapter(MainActivity.this,R.layout.item);
        lvAll.setAdapter(adapterAll);
        lvLove=findViewById(R.id.lvLove);
        adapterLove=new BaiHatAdapter(MainActivity.this,R.layout.item);
        lvLove.setAdapter(adapterLove);
    }

    private void processCopy()
    {
        try {
            File dbFile=getDatabasePath(DATABASE_NAME);
            if(!dbFile.exists())
            {
                copyDatabaseFromAsset();
            }
        }
        catch (Exception ex)
        {
            Log.e("LOI",ex.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem mnuSearch=menu.findItem(R.id.mnuSearch);
        SearchView searchView= (SearchView) mnuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                xuLyTimKiem(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void xuLyTimKiem(String s) {
        Cursor c=database.query(TableName,null,"MABH like ? or TENBH like ? or TACGIA like ?",
                new String[]{"%"+s+"%","%"+s+"%","%"+s+"%"},
                null,null,null);
        adapterAll.clear();
        while(c.moveToNext())
        {
            String ma=c.getString(0);
            String ten=c.getString(1);
            String casi=c.getString(3);
            int thich=c.getInt(5);
            BaiHat bh=new BaiHat(ma,ten,casi,thich);
            adapterAll.add(bh);
        }
        c.close();
    }

    private String getDatabasePath()
    {
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }
    private void copyDatabaseFromAsset() {
        try {
            InputStream myInput=getAssets().open(DATABASE_NAME);
            String outFileName=getDatabasePath();
            File f=new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists())
                f.mkdir();
            OutputStream myOutput=new FileOutputStream(outFileName);
            byte[]buffer=new byte[1024];
            int length;
            while ((length=myInput.read(buffer))>0)
            {
                myOutput.write(buffer,0,length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (Exception ex)
        {
            Log.e("LOI",ex.toString());
        }
    }
}
