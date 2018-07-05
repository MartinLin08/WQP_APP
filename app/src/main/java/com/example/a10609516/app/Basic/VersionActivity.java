package com.example.a10609516.app.Basic;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10609516.app.Clerk.QuotationActivity;
import com.example.a10609516.app.DepartmentAndDIY.CustomerActivity;
import com.example.a10609516.app.DepartmentAndDIY.PictureActivity;
import com.example.a10609516.app.R;
import com.example.a10609516.app.Workers.CalendarActivity;
import com.example.a10609516.app.Workers.PointsActivity;
import com.example.a10609516.app.Workers.ScheduleActivity;
import com.example.a10609516.app.Workers.SearchActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class VersionActivity extends AppCompatActivity {

    private TextView detail_txt0, detail_txt1, detail_txt2, detail_txt3, detail_txt4;
    private LinearLayout detail_llt0, detail_llt1, detail_llt2, detail_llt3, detail_llt4;
    private Button version_btn0, version_btn1, version_btn2, version_btn3, version_btn4;
    private Button version_up_btn0, version_up_btn1, version_up_btn2, version_up_btn3, version_up_btn4;


    /**
     * 創建Menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences department_id = getSharedPreferences("department_id" , MODE_PRIVATE);
        String department_id_data = department_id.getString("D_ID" , "");
        if (department_id_data.toString().equals("2100")) {
            getMenuInflater().inflate(R.menu.clerk_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("2200")) {
            getMenuInflater().inflate(R.menu.diy_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("5200")) {
            getMenuInflater().inflate(R.menu.workers_menu, menu);
            return true;
        }else{
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
    }

    /**
     * 進入Menu各個頁面
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                Intent intent = new Intent(VersionActivity.this, MenuActivity.class);
                startActivity(intent);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                finish();
                break; //返回首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(VersionActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊",Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent1 = new Intent(VersionActivity.this, CalendarActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent2 = new Intent(VersionActivity.this, SearchActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            /*case R.id.signature_item:
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //顯示客戶電子簽名*/
            /*case R.id.record_item:
                Intent intent8 = new Intent(VersionActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄",Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面*/
            case R.id.picture_item:
                Intent intent3 = new Intent(VersionActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(VersionActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢頁面
            /*case R.id.upload_item:
                Intent intent5 = new Intent(VersionActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(VersionActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面*/
            case R.id.about_item:
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //顯示版本資訊
            case R.id.QRCode_item:
                Intent intent10 = new Intent(VersionActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.quotation_item:
                Intent intent11 = new Intent(VersionActivity.this, QuotationActivity.class);
                startActivity(intent11);
                Toast.makeText(this, "報價單審核", Toast.LENGTH_SHORT).show();
                break; //進入報價單審核頁面
            case R.id.points_item:
                Intent intent12 = new Intent(VersionActivity.this, PointsActivity.class);
                startActivity(intent12);
                Toast.makeText(this, "我的點數", Toast.LENGTH_SHORT).show();
                break; //進入查詢工務點數頁面
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        //動態取得 View 物件
        InItFunction();
        //版本詳細資訊
        DetailOfVersion();
        //查看版本
        CheckWhatDetail();
        //確認是否有最新版本，進行更新
        CheckFirebaseVersion();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction(){
        detail_txt0 = (TextView)findViewById(R.id.detail_txt0);
        detail_txt1 = (TextView)findViewById(R.id.detail_txt1);
        detail_txt2 = (TextView)findViewById(R.id.detail_txt2);
        detail_txt3 = (TextView)findViewById(R.id.detail_txt3);
        detail_txt4 = (TextView)findViewById(R.id.detail_txt4);
        detail_llt0 = (LinearLayout)findViewById(R.id.detail_llt0);
        detail_llt1 = (LinearLayout)findViewById(R.id.detail_llt1);
        detail_llt2 = (LinearLayout)findViewById(R.id.detail_llt2);
        detail_llt3 = (LinearLayout)findViewById(R.id.detail_llt3);
        detail_llt4 = (LinearLayout)findViewById(R.id.detail_llt4);
        version_btn0 = (Button)findViewById(R.id.version_btn0);
        version_btn1 = (Button)findViewById(R.id.version_btn1);
        version_btn2 = (Button)findViewById(R.id.version_btn2);
        version_btn3 = (Button)findViewById(R.id.version_btn3);
        version_btn4 = (Button)findViewById(R.id.version_btn4);
        version_up_btn0 = (Button)findViewById(R.id.version_up_btn0);
        version_up_btn1 = (Button)findViewById(R.id.version_up_btn1);
        version_up_btn2 = (Button)findViewById(R.id.version_up_btn2);
        version_up_btn3 = (Button)findViewById(R.id.version_up_btn3);
        version_up_btn4 = (Button)findViewById(R.id.version_up_btn4);
    }

    /**
     * 版本詳細資訊
     */
    private void DetailOfVersion(){
        detail_txt0.setText("1.新增工務部 - 行程資訊、派工行事曆、查詢派工資料 \n" +
                            "2.新增工務部 - 出勤維護回報功能 \n" +
                            "3.新增推播功能-工務(新派工、更新派工、取消派工) \n" +
                            "4.新增客戶電子簽名、QRCode功能");
        detail_txt1.setText("1.新增業務部 - 報價單審核");
        detail_txt2.setText("1.關閉工務部 - 客戶電子簽名功能 \n" +
                            "2.新增工務部 - 回報派工頁面自動帶入當天日期與客戶預約時間 \n" +
                            "3.新增工務部 - 派工地址可長按點擊複製");
        detail_txt3.setText("1.新增APP線上更新功能 \n" +
                            "2.新增工務部 - 出勤回報的付款方式可更改(現金、匯款、支票、信用卡)");
        detail_txt4.setText("1.新增工務部 - 可檢視當天當工的工務點數與工務獎金 \n" +
                            "2.新增工務部 - 工務點數獎金查詢(可查詢A點數、B點數、D點數、AB點數合計、工務獎金) \n" +
                            "3.新增業務部 - 報價單已讀功能");
    }

    /**
     * 查看版本
     */
    private void CheckWhatDetail() {
        version_btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt0.setVisibility(View.VISIBLE);
                version_up_btn0.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt0.setVisibility(View.GONE);
                version_up_btn0.setVisibility(View.GONE);
            }
        });
        version_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt1.setVisibility(View.VISIBLE);
                version_up_btn1.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt1.setVisibility(View.GONE);
                version_up_btn1.setVisibility(View.GONE);
            }
        });
        version_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt2.setVisibility(View.VISIBLE);
                version_up_btn2.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt2.setVisibility(View.GONE);
                version_up_btn2.setVisibility(View.GONE);
            }
        });
        version_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt3.setVisibility(View.VISIBLE);
                version_up_btn3.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt3.setVisibility(View.GONE);
                version_up_btn3.setVisibility(View.GONE);
            }
        });
        version_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt4.setVisibility(View.VISIBLE);
                version_up_btn4.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt4.setVisibility(View.GONE);
                version_up_btn4.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 確認是否有最新版本，進行更新
     */
    private void CheckFirebaseVersion() {
        SharedPreferences fb_version = getSharedPreferences("fb_version", MODE_PRIVATE);
        final String version = fb_version.getString("FB_VER", "");
        Log.e("VersionActivity", version);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("WQP");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Log.d("現在在根結點上的資料是:", "Value is: " + value);
                Map<String, String> map = (Map) dataSnapshot.getValue();
                String data = map.toString().substring(9, 12);
                Log.e("VersionActivity", "已讀取到值:" + data);
                if (version.equals(data)) {
                } else {
                    new AlertDialog.Builder(VersionActivity.this)
                            .setTitle("更新通知")
                            .setMessage("檢測到軟體重大更新\n請更新最新版本")
                            .setIcon(R.drawable.bwt_icon)
                            .setNegativeButton("確定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            new Thread() {
                                                @Override
                                                public void run() {
                                                    super.run();
                                                    VersionActivity.this.Update();
                                                }
                                            }.start();
                                        }
                                    }).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("VersionActivity", "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * 下載新版本APK
     */
    public void Update() {
        try {
            //URL url = new URL("http://192.168.0.201/wqp_1.5.apk");
            URL url = new URL("http://m.wqp-water.com.tw/wqp_1.5.apk");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            //c.setRequestMethod("GET");
            //c.setDoOutput(true);
            c.connect();

            //String PATH = Environment.getExternalStorageDirectory() + "/download/";
            String PATH = Environment.getExternalStorageDirectory().getPath() + "/Download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "wqp_1.5.apk");
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();//till here, it works fine - .apk is download to my sdcard in download file

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/" + "wqp_1.5.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            VersionActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "開始安裝新版本", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("下載錯誤!", e.toString());
            VersionActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "更新失敗!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
