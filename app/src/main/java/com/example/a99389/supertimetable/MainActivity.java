package com.example.a99389.supertimetable;
/*
***
* time:2018/10/31
* topic:超级课程表
* author:ben
* emali:993894611@qq.com
 */
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // 左边一节课的高度
    private float mLeftHeight;
    // 左边一节课的宽度
    private float mLeftWidth;
    //存储cookie
    private String cookie;
    //存储验证码
    private String yzm;
    //存储课表信息
    private String TableInfo="";
    //存储教师信息
    private String TeacherInfo;
    //显示验证码图片
    private ImageView IVyzm;
    //查询按钮
    private Button BtnQuery;
    //验证码输入框
    private EditText ETyzm;
    //课表左边头  1  2  3  4
    private LinearLayout mLeftNo;
    //教师输入框  可自动补全
    private AutoCompleteTextView ATTeacher;
    //两个数组  用来存储解析后的教师列表 姓名和 编号
    private String[] TeacherName;
    private String[] TeacherNum = null;
    ArrayAdapter<String> adapter;

    private TextView tv;// = new TextView(this);
   // private TextView tv = new TextView(this);
    //七个LinearLayout用来显示每天课表信息  每个LinearLayout含有4个textview用于装在课程基本信息
    private LinearLayout mMonday;
    private LinearLayout mTuesday;
    private LinearLayout mWednesday;
    private LinearLayout mThursday;
    private LinearLayout mFirday;
    private LinearLayout mSaturday;
    private LinearLayout mWeekend;


    private TextView m1;
    private TextView m2;
    private TextView m3;
    private TextView m4;
    //周二
    private TextView tu1;
    private TextView tu2;
    private TextView tu3;
    private TextView tu4;
    private TextView w1;
    private TextView w2;
    private TextView w3;
    private TextView w4;
    //周四
    private TextView th1;
    private TextView th2;
    private TextView th3;
    private TextView th4;
    private TextView f1;
    private TextView f2;
    private TextView f3;
    private TextView f4;
    private TextView s1;
    private TextView s2;
    private TextView s3;
    private TextView s4;
    private TextView we1;
    private TextView we2;
    private TextView we3;
    private TextView we4;
    private String clsInfo;

    //GetData类用于爬取信息
    GetData getData;

//    //课程表
//    DBOpenHelper helper;
//    SQLiteDatabase dbClass;

    @SuppressLint("HandlerLeak")


    //用于更新ui
    //子线程不能更新ui
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            //设置验证码图片 更新ui
            if (msg.what == 100) {

                double p = 2;//改变验证码图片大小
                Bitmap bitmap = BitmapFactory.decodeFile( Environment.getExternalStorageDirectory().getAbsolutePath() + "/supertimetable/pic/yzm.png");
                Bitmap newBmp = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*p),(int)(bitmap.getHeight()*p), true);
                IVyzm.setImageBitmap(newBmp);
                //更新老师姓名列表
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, TeacherName);
                ATTeacher.setAdapter(adapter);
            }
            //验证码错误   输入提示符

            if(msg.what == 101){

                Toast.makeText(getApplicationContext(),
                        "验证码错误，请重新输入",Toast.LENGTH_LONG).show();

            }
            //显示课程信息
            if(msg.what==102){
                //解析课程表列表
                Document doc = Jsoup.parse(TableInfo);

                Elements ele = doc.select("td[width=13%]td[valign=top]");
                int i = 0;
                int len = 0;
                //遍历  将课表信息装在到textview
                for(Element link:ele){
                    i++;
                    clsInfo=link.text();

                    //Log.i("Ben",i+clsInfo+"!");
                    switch (i){
                        case 1 :m1.setText(clsInfo);break;
                        case 2:tu1.setText(clsInfo);break;
                        case 3:w1.setText(clsInfo);break;
                        case 4:th1.setText(clsInfo);break;
                        case 5:f1.setText(clsInfo);break;
                        case 6:s1.setText(clsInfo);break;
                        case 7:we1.setText(clsInfo);break;

                        case 8:m2.setText(clsInfo);break;
                        case 9:tu2.setText(clsInfo);break;
                        case 10:w2.setText(clsInfo);break;
                        case 11:th2.setText(clsInfo);break;
                        case 12:f2.setText(clsInfo);break;
                        case 13:s2.setText(clsInfo);break;
                        case 14:we2.setText(clsInfo);break;

                        case 15:m3.setText(clsInfo);break;
                        case 16:tu3.setText(clsInfo);break;
                        case 17:w3.setText(clsInfo);break;
                        case 18:th3.setText(clsInfo);break;
                        case 19:f3.setText(clsInfo);break;
                        case 20:s3.setText(clsInfo);break;
                        case 21:we3.setText(clsInfo);break;

                        case 22:m4.setText(clsInfo);break;
                        case 23:tu4.setText(clsInfo);break;
                        case 24:w4.setText(clsInfo);break;
                        case 25:th4.setText(clsInfo);break;
                        case 26:f4.setText(clsInfo);break;
                        case 27:s4.setText(clsInfo);break;
                        case 28:we4.setText(clsInfo);break;
                    }
                }

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IVyzm=(ImageView)findViewById(R.id.yzm);
        ETyzm=(EditText)findViewById(R.id.ETyzm);
        mLeftNo = (LinearLayout) findViewById(R.id.leftNo);
        ATTeacher = (AutoCompleteTextView)findViewById(R.id.ATTeacher);



        //绑定
        inti();

//        //创建一个数据库
//         helper = new DBOpenHelper(this);
//        // 调用 getWritableDatabase()或者 getReadableDatabase()其中一个方法将数据库建立
//
//
//        dbClass= helper.getWritableDatabase();  //得到的是SQLiteDatabase对象
//
        getData = new GetData();

        // 绘制左边的课程节数
        //drawLeftNo(51);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    cookie = getData.getValidateAndCookie();
                    TeacherInfo = getData.getTeacher();
                   // Log.i("Ben",TeacherInfo);
                    //通过换行符  将 教师信息 以数组形式存储
                    //   TeacherName   和   TeacherNum根据下标一一对应
                    TeacherName=TeacherInfo.split("\n");
                    TeacherNum=TeacherInfo.split("\n");
                    for(int i = 0;i<TeacherName.length;i++){
                        //将字符串截取为 工号  +  姓名
                        TeacherNum[i]=TeacherName[i].substring(0,TeacherName[i].indexOf('>'));
                        TeacherName[i] = TeacherName[i].substring(TeacherName[i].indexOf('>')+1,TeacherName[i].length());
                        //Log.i("Ben",TeacherNum[i]+"..."+TeacherName[i]);

                    }

                    //通知主线程 更新  教师列表
                    mHandler.sendEmptyMessage(100);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }).start();


    }

    //绑定
    private void inti() {
        IVyzm=(ImageView)findViewById(R.id.yzm);
        ETyzm=(EditText)findViewById(R.id.ETyzm);
        mLeftNo = (LinearLayout) findViewById(R.id.leftNo);
        ATTeacher = (AutoCompleteTextView)findViewById(R.id.ATTeacher);
        mMonday = (LinearLayout) findViewById(R.id.monday);
        mTuesday = (LinearLayout) findViewById(R.id.tuesday);
        mWednesday = (LinearLayout) findViewById(R.id.wednesday);
        mThursday = (LinearLayout) findViewById(R.id.thursday);
        mFirday = (LinearLayout) findViewById(R.id.firday);
        mSaturday = (LinearLayout) findViewById(R.id.saturday);
        mWeekend = (LinearLayout) findViewById(R.id.weekend);
        //monday
        m1 = (TextView)findViewById(R.id.m1);
        m2 = (TextView)findViewById(R.id.m2);
        m3 = (TextView)findViewById(R.id.m3);
        m4 = (TextView)findViewById(R.id.m4);
        //thursday
        th1 = (TextView)findViewById(R.id.th1);
        th2 = (TextView)findViewById(R.id.th2);
        th3 = (TextView)findViewById(R.id.th3);
        th4 = (TextView)findViewById(R.id.th4);
        //mWednesday
        w1 = (TextView)findViewById(R.id.w1);
        w2 = (TextView)findViewById(R.id.w2);
        w3 = (TextView)findViewById(R.id.w3);
        w4 = (TextView)findViewById(R.id.w4);
        //mTuesday
        tu1 = (TextView)findViewById(R.id.tu1);
        tu2 = (TextView)findViewById(R.id.tu2);
        tu3 = (TextView)findViewById(R.id.tu3);
        tu4 = (TextView)findViewById(R.id.tu4);
        //mFirday
        f1 = (TextView)findViewById(R.id.f1);
        f2 = (TextView)findViewById(R.id.f2);
        f3 = (TextView)findViewById(R.id.f3);
        f4 = (TextView)findViewById(R.id.f4);
        //mSaturday

        s1 = (TextView)findViewById(R.id.s1);
        s2 = (TextView)findViewById(R.id.s2);
        s3 = (TextView)findViewById(R.id.s3);
        s4 = (TextView)findViewById(R.id.s4);
        //mWeekend

        we1 = (TextView)findViewById(R.id.we1);
        we2 = (TextView)findViewById(R.id.we2);
        we3 = (TextView)findViewById(R.id.we3);
        we4 = (TextView)findViewById(R.id.we4);

    }
    //画左边数字  用于提示是当天哪节课
    private void drawLeftNo(int num) {

        //mLeftWidth = getResources().getDimension(R.dimen.left_width);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                (int) mLeftWidth, num*4);
        for (int i = 1; i <= 4; i++) {
            TextView tv = new TextView(this);
            tv.setText(i + "");
            tv.setGravity(Gravity.CENTER);
            tv.setHeight(num);
            mLeftNo.addView(tv, lp);
        }
    }

    //查询课表
    public void QueryTable(View view) throws Exception {
        //testDB();
        //获取
        //getData.setyzm(ETyzm.getText().toString());
        //验证码未输入
        //验证码未输入
        if(ETyzm.getText().toString().isEmpty()){
            Toast.makeText(this,"请输入验证码",Toast.LENGTH_LONG).show();

        }
        else{
            String Temp =  ATTeacher.getText().toString();
            Log.i("Ben","查询"+TeacherName.length);
            int i;
            //遍历整个教师列表  获取对应下标值  用来获取该老师编号
            for(i =0;i<TeacherName.length;i++)
            {
                //查到匹配的就break
                if(TeacherName[i].compareTo(Temp)==0)
                {
                    Log.i("Ben","ddd");
                    break;
                }

            }
            if(i == TeacherName.length)
            {
                Toast.makeText(this,"没有该老师",Toast.LENGTH_LONG).show();
            }
            else
                 getSchedule(view,TeacherNum[i]);

        }

    }
    //获取教师课表信息
    //Num为教师编号
    public void getSchedule(final View view, final String Num) {
        new Thread(){
            @Override
            public void run() {
                try {
                    String yzm=ETyzm.getText().toString();

                    //教务处网站 基本信息
                    String referer="http://121.248.70.120/jwweb/ZNPK/TeacherKBFB.aspx";
                    String address="http://121.248.70.120/jwweb/ZNPK/TeacherKBFB_rpt.aspx";

                    URL url=new URL(address);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Cookie", cookie);
                    con.setRequestProperty("Referer", referer);
                    con.setRequestMethod("POST");
                    StringBuilder builder=new StringBuilder();
                    //查询条件 Num即为教师编号
                    String str = "Sel_XNXQ=20180&Sel_JS="+Num+"&type=1&txt_yzm="+yzm;

                    builder.append(str);
                    OutputStream netOut=con.getOutputStream();
                    netOut.write(builder.toString().getBytes());
                    int len=con.getContentLength();
                    System.out.println(len);
                    byte[] buf=new byte[1024];
                    //获取输入流的时候，完成请求
                    InputStream input=con.getInputStream();
                    TableInfo="";//每次查询后都清空
                    String temp;
                    //转码
                    while((len=input.read(buf))!=-1) {
                        temp=new String(buf,"GB2312");
                        TableInfo+=temp;
                        TableInfo+="\r\n";

                    }

                    input.close();
                    Log.i("Ben",TableInfo);

                    //若验证码错误  则网站提示 “验证码错误”
                    //判断爬取的信息是否含有验证码错误   来判断用于是否错误输入验证码

                    int isFind = TableInfo.indexOf("验证码错误");
                    //  isFInd代表查询结果     isFInd!= -1 验证码错误
                    if(isFind!=-1){
                        //通知主线程 提示验证码错误
                        mHandler.sendEmptyMessage(101);
                        Log.i("Ben","yzm err");
                    }
                    else
                        //通知主线程  更新课表ui
                        mHandler.sendEmptyMessage(102);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    //数据库
    //代码未完成
    public void testDB() {
//        DBOpenHelper helper;
//        SQLiteDatabase dbClass;
//        final String TABLE_NAME = "Class";
//        final String NAME = "cs1";
//        final String AGE = "cs2";
//
//        //实例化常量值
//        ContentValues cValue = new ContentValues();
//        for(int i =0;i<29;i++)
//        {
//            cValue.put("cls" + i, "I am " + i);
//        }
//        //调用insert()方法插入数据
//        dbClass.insert(TABLE_NAME, null, cValue);
//
//
//        for(int i =0;i<29;i++)
//        {
//            cValue.put("Taaa" + i, "I am " + i);
//        }
//        //调用insert()方法插入数据
//        dbClass.insert(TABLE_NAME, null, cValue);
//
//        //查询获得游标
//        Cursor cursor = dbClass.rawQuery("select * from Class",null);
//        //判断游标是否为空
//        if (cursor.moveToFirst()) {
//            //遍历游标
//            for (int i = 0; i < cursor.getCount(); i++) {
//
//                cursor.move(i);
//                //获得ID
//
//                //获得用户名
//                String username = cursor.getString(1);
//                //获得密码
//                String age = cursor.getString(2);
//                //输出用户信息
//                Log.i("Benz",":" + username + ":" + age);
//
//            }
//
//
//        }
//
  }
}
