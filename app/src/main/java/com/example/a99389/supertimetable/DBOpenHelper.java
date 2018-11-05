package com.example.a99389.supertimetable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;


//SQLiteOpenHelper是数据库操作的辅助类，并且它是一个抽象类，所以使用时需要定义其子类，并且在子类中复写相应的抽象方法。
public class DBOpenHelper extends SQLiteOpenHelper {


    private static final String name = "clsInfo";
    private static final int version = 1;


    public DBOpenHelper(Context context) {
        super(context, name, null, version);
    }
    //此方法在第一次使用数据库时调用生成相应的数据库表，但是此方法并不是在实例化SQLiteOpenHelper类的对象时调用，而是通过对象调用了getReadableDatabase()或getWriteableDatabase()方法时才会调用。
    @Override
    public void onCreate(SQLiteDatabase db) {

        String str = "create table Class(name string,cls1 string,cls2 string,cls3 string,cls4 string,cls5 string,cls6 string,cls7 string,cls8 string,cls9 string,cls10 string," +
                "cls11 string,cls12 string,cls13 string,cls14 string,cls15 string,cls16 string,cls17 string,cls18 string,cls19 string,cls20 string," +
                "cls21 string,cls22 string,cls23 string,cls24 string,cls25 string,cls26 string,cls27 string,cls28 string)";
        db.execSQL(str);//完成数据库的创建  Class表含有29个列 第一个列代表教师姓名  后面28个列 代表教师课表信息




    }
    //当数据库升级时会调用此方法，一般可以再此方法中将数据表删除，并且在删除表之后往往会调onCreate()方法重新创建新的数据表。
    //是否升级根据版本号判断

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
