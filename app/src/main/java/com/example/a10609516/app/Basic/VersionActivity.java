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
import com.example.a10609516.app.Manager.InventoryActivity;
import com.example.a10609516.app.R;
import com.example.a10609516.app.Tools.WQPServiceActivity;
import com.example.a10609516.app.Workers.CalendarActivity;
import com.example.a10609516.app.Workers.EngPointsActivity;
import com.example.a10609516.app.Workers.GPSActivity;
import com.example.a10609516.app.Workers.MissCountActivity;
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

public class VersionActivity extends WQPServiceActivity {

    private TextView detail_txt0, detail_txt1, detail_txt2, detail_txt3, detail_txt4, detail_txt5
            , detail_txt6, detail_txt7, detail_txt8, detail_txt9, detail_txt10
            , detail_txt11, detail_txt12, detail_txt13, detail_txt14;
    private LinearLayout detail_llt0, detail_llt1, detail_llt2, detail_llt3, detail_llt4, detail_llt5
            , detail_llt6, detail_llt7, detail_llt8, detail_llt9, detail_llt10
            , detail_llt11, detail_llt12, detail_llt13, detail_llt14;
    private Button version_btn0, version_btn1, version_btn2, version_btn3, version_btn4, version_btn5
            , version_btn6, version_btn7, version_btn8, version_btn9, version_btn10
            , version_btn11, version_btn12, version_btn13, version_btn14;
    private Button version_up_btn0, version_up_btn1, version_up_btn2, version_up_btn3, version_up_btn4, version_up_btn5
            , version_up_btn6, version_up_btn7, version_up_btn8, version_up_btn9, version_up_btn10
            , version_up_btn11, version_up_btn12, version_up_btn13, version_up_btn14;

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
        //CheckFirebaseVersion();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        detail_txt0 = (TextView) findViewById(R.id.detail_txt0);
        detail_txt1 = (TextView) findViewById(R.id.detail_txt1);
        detail_txt2 = (TextView) findViewById(R.id.detail_txt2);
        detail_txt3 = (TextView) findViewById(R.id.detail_txt3);
        detail_txt4 = (TextView) findViewById(R.id.detail_txt4);
        detail_txt5 = (TextView) findViewById(R.id.detail_txt5);
        detail_txt6 = (TextView) findViewById(R.id.detail_txt6);
        detail_txt7 = (TextView) findViewById(R.id.detail_txt7);
        detail_txt8 = (TextView) findViewById(R.id.detail_txt8);
        detail_txt9 = (TextView) findViewById(R.id.detail_txt9);
        detail_txt10 = (TextView) findViewById(R.id.detail_txt10);
        detail_txt11 = (TextView) findViewById(R.id.detail_txt11);
        detail_txt12 = (TextView) findViewById(R.id.detail_txt12);
        detail_txt13 = (TextView) findViewById(R.id.detail_txt13);
        detail_txt14 = (TextView) findViewById(R.id.detail_txt14);
        detail_llt0 = (LinearLayout) findViewById(R.id.detail_llt0);
        detail_llt1 = (LinearLayout) findViewById(R.id.detail_llt1);
        detail_llt2 = (LinearLayout) findViewById(R.id.detail_llt2);
        detail_llt3 = (LinearLayout) findViewById(R.id.detail_llt3);
        detail_llt4 = (LinearLayout) findViewById(R.id.detail_llt4);
        detail_llt5 = (LinearLayout) findViewById(R.id.detail_llt5);
        detail_llt6 = (LinearLayout) findViewById(R.id.detail_llt6);
        detail_llt7 = (LinearLayout) findViewById(R.id.detail_llt7);
        detail_llt8 = (LinearLayout) findViewById(R.id.detail_llt8);
        detail_llt9 = (LinearLayout) findViewById(R.id.detail_llt9);
        detail_llt10 = (LinearLayout) findViewById(R.id.detail_llt10);
        detail_llt11 = (LinearLayout) findViewById(R.id.detail_llt11);
        detail_llt12 = (LinearLayout) findViewById(R.id.detail_llt12);
        detail_llt13 = (LinearLayout) findViewById(R.id.detail_llt13);
        detail_llt14 = (LinearLayout) findViewById(R.id.detail_llt14);
        version_btn0 = (Button) findViewById(R.id.version_btn0);
        version_btn1 = (Button) findViewById(R.id.version_btn1);
        version_btn2 = (Button) findViewById(R.id.version_btn2);
        version_btn3 = (Button) findViewById(R.id.version_btn3);
        version_btn4 = (Button) findViewById(R.id.version_btn4);
        version_btn5 = (Button) findViewById(R.id.version_btn5);
        version_btn6 = (Button) findViewById(R.id.version_btn6);
        version_btn7 = (Button) findViewById(R.id.version_btn7);
        version_btn8 = (Button) findViewById(R.id.version_btn8);
        version_btn9 = (Button) findViewById(R.id.version_btn9);
        version_btn10 = (Button) findViewById(R.id.version_btn10);
        version_btn11 = (Button) findViewById(R.id.version_btn11);
        version_btn12 = (Button) findViewById(R.id.version_btn12);
        version_btn13 = (Button) findViewById(R.id.version_btn13);
        version_btn14 = (Button) findViewById(R.id.version_btn14);
        version_up_btn0 = (Button) findViewById(R.id.version_up_btn0);
        version_up_btn1 = (Button) findViewById(R.id.version_up_btn1);
        version_up_btn2 = (Button) findViewById(R.id.version_up_btn2);
        version_up_btn3 = (Button) findViewById(R.id.version_up_btn3);
        version_up_btn4 = (Button) findViewById(R.id.version_up_btn4);
        version_up_btn5 = (Button) findViewById(R.id.version_up_btn5);
        version_up_btn6 = (Button) findViewById(R.id.version_up_btn6);
        version_up_btn7 = (Button) findViewById(R.id.version_up_btn7);
        version_up_btn8 = (Button) findViewById(R.id.version_up_btn8);
        version_up_btn9 = (Button) findViewById(R.id.version_up_btn9);
        version_up_btn10 = (Button) findViewById(R.id.version_up_btn10);
        version_up_btn11 = (Button) findViewById(R.id.version_up_btn11);
        version_up_btn12 = (Button) findViewById(R.id.version_up_btn12);
        version_up_btn13 = (Button) findViewById(R.id.version_up_btn13);
        version_up_btn14 = (Button) findViewById(R.id.version_up_btn14);
    }

    /**
     * 版本詳細資訊
     */
    private void DetailOfVersion() {
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
        detail_txt5.setText("1.新增工務部 - 可檢視當天當工的A點數與B點數 \n" +
                            "2.新增工務部 - 回報派工頁面可檢視客戶地址 \n" +
                            "3.新增工務部 - 工作說明的留言功能(工務秘書可見)");
        detail_txt6.setText("1.部分內容修正及優化 \n" +
                            "2.修正1.5版本前部分機型無法自動更新的問題");
        detail_txt7.setText("1.部分內容修正bug及優化");
        detail_txt8.setText("1.部分內容修正bug及優化 \n" +
                            "2.新增工務部 - 工務點數明細(可直接查詢一個月內所有派工出勤的點數)");
        detail_txt9.setText("1.部分內容修正bug及優化 \n" +
                            "2.新增管理部 - 盤點單、撿料單");
        detail_txt10.setText("1.部分內容修正bug及優化 \n" +
                             "2.撿料單功能優化");
        detail_txt11.setText("1.部分內容修正bug及優化");
        detail_txt12.setText("1.部分內容修正bug及優化 \n" +
                             "2.盤點單功能優化");
        detail_txt13.setText("1.部分內容修正bug及優化 \n" +
                             "2.撿料單功能優化 \n" +
                             "3.新增 資訊/行銷需求單 及 進度查詢");
        detail_txt14.setText("1.修正 Android Q/10 無法登入問題");
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
        version_btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt5.setVisibility(View.VISIBLE);
                version_up_btn5.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt5.setVisibility(View.GONE);
                version_up_btn5.setVisibility(View.GONE);
            }
        });
        version_btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt6.setVisibility(View.VISIBLE);
                version_up_btn6.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt6.setVisibility(View.GONE);
                version_up_btn6.setVisibility(View.GONE);
            }
        });
        version_btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt7.setVisibility(View.VISIBLE);
                version_up_btn7.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt7.setVisibility(View.GONE);
                version_up_btn7.setVisibility(View.GONE);
            }
        });
        version_btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt8.setVisibility(View.VISIBLE);
                version_up_btn8.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt8.setVisibility(View.GONE);
                version_up_btn8.setVisibility(View.GONE);
            }
        });
        version_btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt9.setVisibility(View.VISIBLE);
                version_up_btn9.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt9.setVisibility(View.GONE);
                version_up_btn9.setVisibility(View.GONE);
            }
        });
        version_btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt10.setVisibility(View.VISIBLE);
                version_up_btn10.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt10.setVisibility(View.GONE);
                version_up_btn10.setVisibility(View.GONE);
            }
        });
        version_btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt11.setVisibility(View.VISIBLE);
                version_up_btn11.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt11.setVisibility(View.GONE);
                version_up_btn11.setVisibility(View.GONE);
            }
        });
        version_btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt12.setVisibility(View.VISIBLE);
                version_up_btn12.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt12.setVisibility(View.GONE);
                version_up_btn12.setVisibility(View.GONE);
            }
        });
        version_btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt13.setVisibility(View.VISIBLE);
                version_up_btn13.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt13.setVisibility(View.GONE);
                version_up_btn13.setVisibility(View.GONE);
            }
        });
        version_btn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt14.setVisibility(View.VISIBLE);
                version_up_btn14.setVisibility(View.VISIBLE);
            }
        });
        version_up_btn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_llt14.setVisibility(View.GONE);
                version_up_btn14.setVisibility(View.GONE);
            }
        });
    }
}
