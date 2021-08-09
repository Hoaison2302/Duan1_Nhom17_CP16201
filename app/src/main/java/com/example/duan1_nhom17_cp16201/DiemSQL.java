package com.example.duan1_nhom17_cp16201;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DiemSQL extends SQLiteOpenHelper {

    private static final String DIEM_TABLE ="DIEMTABLE" ;
    private static String TAG = "DataBaseHelper";
    private static String DB_PATH = "";
    private static String DB_NAME = "quanlydiem.db";// Database name
    private SQLiteDatabase mDataBase;
    private Context mContext;

    private String AV_TABLE = "av";

    public DiemSQL(Context context) {
        super(context, DB_NAME, null, 2);// 1? Its database Version
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }
    public void createDataBase() {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }
    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public List<Diem> getAll(){
        List<Diem> diemList=new ArrayList<>();
        mDataBase=getReadableDatabase();
        String SQL="SELECT * FROM DIEMTABLE ";
        Cursor cursor=mDataBase.rawQuery(SQL, null);

        if(cursor !=null){
            if(cursor.getCount()>0){

                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    Diem diem=new Diem();

                    diem.setSothutu(cursor.getInt(0));
                    diem.setDiemtong(cursor.getInt(3));
                    diem.setTennguoichoi(cursor.getString(1));
                    diem.setNgay(cursor.getString(2));

                    diemList.add(diem);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
        return diemList;
    }

    public long insertDiem(Diem diem){
        mDataBase=getReadableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put("id",diem.getSothutu());
        contentValues.put("diem",diem.getDiemtong());
        contentValues.put("tennguoichoi",diem.getTennguoichoi());
        contentValues.put("ngaychoi",diem.getNgay());

        long result=mDataBase.insert(DIEM_TABLE,null,contentValues);
        mDataBase.close();
        return result;
    }
}
