package com.example.a99389.supertimetable;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 ***
 * time:2018/10/31
 * topic:超级课程表
 * author:666
 * emali:993894611@qq.com
 * 网络爬虫获取数据
 */
public class GetData {
    //private static Properties conf=new Properties();
    //教务处网站基本信息
    private String referer;
    private  String addressYZM;
    private String addressKB;
    private String addressTeacher;
    private  String cookie;
    //存储验证码图片位置
    private  String rootdir;
    private  String yzm;


    //cookie保存到 conf.properities
    GetData(){
        rootdir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/supertimetable/";
        addressYZM = "http://121.248.70.120/jwweb/sys/ValidateCode.aspx?t=770";
        addressKB = "http://121.248.70.120/jwweb/ZNPK/TeacherKBFB_rpt.aspx";
        addressTeacher="http://121.248.70.120/jwweb/ZNPK/Private/List_JS.aspx";
        referer = "http://121.248.70.120/jwweb/ZNPK/TeacherKBFB.aspx";

    }
    public void setyzm(String yzm){
        this.yzm=yzm;

    }
    public String getYZM(){
        return rootdir+"pic/yzm.png";
    }
    public  String getValidateAndCookie() throws Exception {

        // 输出的文件流保存图片至本地
        File file = new File(rootdir+"pic/yzm.png");
        //若失败 则重新创建
        if(!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
            Log.i("Ben","file err");
        }
        OutputStream outPic = new FileOutputStream(file);
        InputStream inputStream;
        URL url = new URL(addressYZM);
        HttpURLConnection con = (HttpURLConnection) url.openConnection(); //打开连接
        //设置超时时间
        con.setConnectTimeout(3000);
        //设置请求头
        con.setRequestProperty("Referer",referer);
        con.setDoInput(true);
        con.connect();
        int responsecode = con.getResponseCode();
        if(responsecode != HttpURLConnection.HTTP_OK){
            Log.i("Ben","con error");
        }
        else{
            Log.i("Ben","con success");
        }
        cookie = con.getHeaderField("Set-Cookie");
        //cookie = cookie.split(";")[0];



        byte[] buf = new byte[512];
        inputStream = con.getInputStream();

        int len = 0;
        //将爬回来的数据进行解析存储
        while ((len = inputStream.read(buf)) != -1) {
            outPic.write(buf, 0, len);

        }
        inputStream.close();
        outPic.close();

        Log.i("Ben","I save the pic");
        return cookie;
    }


    //获取老师列表
    public  String getTeacher() throws Exception {


        InputStream inputStream;
        URL url = new URL(addressTeacher);
        HttpURLConnection con = (HttpURLConnection) url.openConnection(); //打开连接
        //设置超时时间
        con.setConnectTimeout(3000);

        con.setRequestProperty("Referer",referer);
        con.setDoInput(true);
        con.connect();

        int responsecode = con.getResponseCode();
        if(responsecode != HttpURLConnection.HTTP_OK){
            Log.i("Ben","con error");
        }
        else
        {
            Log.i("Ben","con error");
        }
        byte[] buf = new byte[512];
        inputStream = con.getInputStream();
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream,"GB2312"));
        StringBuffer sb = new StringBuffer("");

        String s = "";
        //s存储教师基本信息   num   name
        while ((s=bf.readLine())!=null){
            sb.append(s+"\r\n");
        }
        inputStream.close();

        String TeacherName = sb.toString();
        //js解析
        TeacherName=TeacherName.replace("</option><option value=","\n");
        TeacherName=TeacherName.substring(TeacherName.indexOf("<option>")+9);
        TeacherName=TeacherName.substring(0,TeacherName.indexOf("</option>"));
      //  最终 格式为 num>name  换行存储   教师编号为固定六位
        return TeacherName;
    }


}
