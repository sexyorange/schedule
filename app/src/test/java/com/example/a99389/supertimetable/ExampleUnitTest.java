package com.example.a99389.supertimetable;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    void testDB() {
        final String DB_NAME = "DB.DB";//数据库名称
        final int DB_VERSION = 1;//数据库版本号
        final String TABLE_NAME = "t_person";
        final String NAME = "name";
        final String AGE = "age";

        String sql = "create table " +
                TABLE_NAME +
                "(_id integer primary key autoincrement, " +
                NAME + " varchar, " +
                AGE + " varchar"
                + ")";

        SQLiteDatabase db = null;
        db.execSQL(sql);
        //实例化常量值
        ContentValues cValue = new ContentValues();
        //添加用户名
        cValue.put(NAME, "xiaoming");
        //添加密码
        cValue.put(AGE, "01005");
        //调用insert()方法插入数据
        db.insert(TABLE_NAME, null, cValue);

        //添加用户名
        cValue.put(NAME, "zh");
        //添加密码
        cValue.put(AGE, "123");
        //调用insert()方法插入数据
        db.insert(TABLE_NAME, null, cValue);

        //添加用户名
        cValue.put(NAME, "fwe");
        //添加密码
        cValue.put(AGE, "324");
        //调用insert()方法插入数据
        db.insert(TABLE_NAME, null, cValue);


        //查询获得游标
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        //判断游标是否为空
        if (cursor.moveToFirst()) {
            //遍历游标
            for (int i = 0; i < cursor.getCount(); i++) {

                cursor.move(i);
                //获得ID
                int id = cursor.getInt(0);
                //获得用户名
                String username = cursor.getString(1);
                //获得密码
                String age = cursor.getString(2);
                //输出用户信息
                System.out.println(id + ":" + username + ":" + age);

            }


        }

    }
}