package com.example.a10609516.app.Manager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10609516.app.Basic.MenuActivity;
import com.example.a10609516.app.Basic.QRCodeActivity;
import com.example.a10609516.app.Basic.SignatureActivity;
import com.example.a10609516.app.Basic.VersionActivity;
import com.example.a10609516.app.Clerk.QuotationActivity;
import com.example.a10609516.app.DepartmentAndDIY.CorrectActivity;
import com.example.a10609516.app.DepartmentAndDIY.CustomerActivity;
import com.example.a10609516.app.DepartmentAndDIY.PictureActivity;
import com.example.a10609516.app.DepartmentAndDIY.RecordActivity;
import com.example.a10609516.app.DepartmentAndDIY.UploadActivity;
import com.example.a10609516.app.R;
import com.example.a10609516.app.Tools.ScannerActivity;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PickingActivity extends AppCompatActivity {

    private TextView date_txt;

    private ScrollView picking_scv;
    private LinearLayout warehouse_llt, sale_record_llt, picked_llt;
    private TextView item_number_txt, item_name_txt, item_format_txt, barcode_txt,
            picked_txt, unit_txt1, unit_txt2,
            recent_in_txt, recent_out_txt, last_inventory_txt, loss_txt, record_txt;
    private EditText factory_id_edt, picking_edt;
    private Button factory_id_btn, upload_btn;

    private String COMPANY, MB001, MB002, MB003, MB004, MB064,
            vendor_no, vendor_name, warehouse_no, warehouse_name,
            in, out, inventory, company_ch, user_name,
            item_id, barcode;
    private String w_id, w_name, d_id;

    //轉畫面的Activity參數
    private Class<?> mClss;
    //ZXING_CAMERA權限
    private static final int ZXING_CAMERA_PERMISSION = 1;

    private Context context;
    private Spinner company_spinner, warehouse_spinner;
    private ArrayAdapter<String> company_listAdapter, warehouse_listAdapter;
    private String[] company_list = new String[]{"拓霖", "拓亞鈦", "倍偉特"};
    private String[] empty = new String[]{"(請選擇)"};
    private String[] warehouse_list = new String[]{};

    /**
     * 創建Menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //接收LoginActivity傳過來的值
        SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
        String user_id_data = user_id.getString("ID", "");
        SharedPreferences department_id = getSharedPreferences("department_id", MODE_PRIVATE);
        String department_id_data = department_id.getString("D_ID", "");
        d_id = department_id.getString("D_ID", "");
        if ((user_id_data.toString().equals("09706013")) || user_id_data.toString().equals("09908023") || user_id_data.toString().equals("10010039")
                || user_id_data.toString().equals("10012043") || user_id_data.toString().equals("10101046") || user_id_data.toString().equals("10405235")) {
            getMenuInflater().inflate(R.menu.workers_manager_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("1100")) {
            getMenuInflater().inflate(R.menu.manager_menu, menu);
            return true;
        }else if (department_id_data.toString().equals("2100")) {
            getMenuInflater().inflate(R.menu.clerk_menu, menu);
            return true;
        } else if (department_id_data.toString().equals("2200")) {
            getMenuInflater().inflate(R.menu.diy_menu, menu);
            return true;
        } else if (department_id_data.toString().equals("5200")) {
            getMenuInflater().inflate(R.menu.workers_menu, menu);
            return true;
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
    }

    /**
     * 進入Menu各個頁面
     *
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                Intent intent17 = new Intent(PickingActivity.this, MenuActivity.class);
                startActivity(intent17);
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;  //進入首頁
            case R.id.schedule_item:
                Intent intent7 = new Intent(PickingActivity.this, ScheduleActivity.class);
                startActivity(intent7);
                Toast.makeText(this, "行程資訊", Toast.LENGTH_SHORT).show();
                break; //進入行程資訊頁面
            case R.id.calendar_item:
                Intent intent = new Intent(PickingActivity.this, CalendarActivity.class);
                startActivity(intent);
                Toast.makeText(this, "派工行事曆", Toast.LENGTH_SHORT).show();
                break; //進入派工行事曆頁面
            case R.id.work_item:
                Intent intent1 = new Intent(PickingActivity.this, SearchActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "查詢派工資料", Toast.LENGTH_SHORT).show();
                break; //進入查詢派工資料頁面
            case R.id.signature_item:
                Intent intent2 = new Intent(PickingActivity.this, SignatureActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "客戶電子簽名", Toast.LENGTH_SHORT).show();
                break; //進入客戶電子簽名頁面
            case R.id.record_item:
                Intent intent8 = new Intent(PickingActivity.this, RecordActivity.class);
                startActivity(intent8);
                Toast.makeText(this, "上傳日報紀錄", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報紀錄頁面
            case R.id.picture_item:
                Intent intent3 = new Intent(PickingActivity.this, PictureActivity.class);
                startActivity(intent3);
                Toast.makeText(this, "客戶訂單照片上傳", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單照片上傳頁面
            case R.id.customer_item:
                Intent intent4 = new Intent(PickingActivity.this, CustomerActivity.class);
                startActivity(intent4);
                Toast.makeText(this, "客戶訂單查詢", Toast.LENGTH_SHORT).show();
                break; //進入客戶訂單查詢
            case R.id.upload_item:
                Intent intent5 = new Intent(PickingActivity.this, UploadActivity.class);
                startActivity(intent5);
                Toast.makeText(this, "上傳日報", Toast.LENGTH_SHORT).show();
                break; //進入上傳日報頁面
            case R.id.correct_item:
                Intent intent6 = new Intent(PickingActivity.this, CorrectActivity.class);
                startActivity(intent6);
                Toast.makeText(this, "日報修正", Toast.LENGTH_SHORT).show();
                break; //進入日報修正頁面
            case R.id.about_item:
                Intent intent9 = new Intent(PickingActivity.this, VersionActivity.class);
                startActivity(intent9);
                Toast.makeText(this, "版本資訊", Toast.LENGTH_SHORT).show();
                break; //進入版本資訊頁面
            case R.id.QRCode_item:
                Intent intent10 = new Intent(PickingActivity.this, QRCodeActivity.class);
                startActivity(intent10);
                Toast.makeText(this, "QRCode", Toast.LENGTH_SHORT).show();
                break; //進入QRCode頁面
            case R.id.quotation_item:
                Intent intent11 = new Intent(PickingActivity.this, QuotationActivity.class);
                startActivity(intent11);
                Toast.makeText(this, "報價單審核", Toast.LENGTH_SHORT).show();
                break; //進入報價單審核頁面
            case R.id.points_item:
                Intent intent12 = new Intent(PickingActivity.this, PointsActivity.class);
                startActivity(intent12);
                Toast.makeText(this, "我的點數", Toast.LENGTH_SHORT).show();
                break; //進入查詢工務點數頁面
            case R.id.miss_item:
                Intent intent13 = new Intent(PickingActivity.this, MissCountActivity.class);
                startActivity(intent13);
                Toast.makeText(this, "未回單數量", Toast.LENGTH_SHORT).show();
                break; //進入工務未回單數量頁面
            case R.id.inventory_item:
                Intent intent15 = new Intent(PickingActivity.this, InventoryActivity .class);
                startActivity(intent15);
                Toast.makeText(this, "倉庫盤點", Toast.LENGTH_SHORT).show();
                break; //顯示倉庫盤點
            case R.id.picking_item:
                Toast.makeText(this, "撿料單", Toast.LENGTH_SHORT).show();
                break; //顯示撿料單頁面
            /*case R.id.return_item:
                Intent intent16 = new Intent(InventoryActivity.this, QRCodeActivity.class);
                startActivity(intent16);
                Toast.makeText(this, "倉庫調撥", Toast.LENGTH_SHORT).show();
                break; //進入倉庫調撥管理頁面*/
            case R.id.map_item:
                Intent intent19 = new Intent(PickingActivity.this, GPSActivity.class);
                startActivity(intent19);
                Toast.makeText(this, "工務打卡GPS", Toast.LENGTH_SHORT).show();
                break; //進入GPS地圖頁面
            case R.id.eng_points_item:
                Intent intent18 = new Intent(PickingActivity.this, EngPointsActivity.class);
                startActivity(intent18);
                Toast.makeText(this, "工務點數明細", Toast.LENGTH_SHORT).show();
                break; //進入工務點數明細頁面
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking);
        //動態取得 View 物件
        InItFunction();
        //獲取當天日期
        GetDate();
        //確認是否有最新版本，進行更新
        CheckFirebaseVersion();
    }

    /**
     * 動態取得 View 物件
     */
    private void InItFunction() {
        picking_scv = (ScrollView) findViewById(R.id.picking_scv);
        date_txt = (TextView) findViewById(R.id.date_txt);
        item_number_txt = (TextView) findViewById(R.id.item_number_txt);
        item_name_txt = (TextView) findViewById(R.id.item_name_txt);
        item_format_txt = (TextView) findViewById(R.id.item_format_txt);
        barcode_txt = (TextView) findViewById(R.id.barcode_txt);
        company_spinner = (Spinner) findViewById(R.id.company_spinner);
        warehouse_spinner = (Spinner) findViewById(R.id.warehouse_spinner);
        unit_txt1 = (TextView) findViewById(R.id.unit_txt1);
        unit_txt2 = (TextView) findViewById(R.id.unit_txt2);
        picked_txt = (TextView) findViewById(R.id.picked_txt);
        recent_in_txt = (TextView) findViewById(R.id.recent_in_txt);
        recent_out_txt = (TextView) findViewById(R.id.recent_out_txt);
        last_inventory_txt = (TextView) findViewById(R.id.last_inventory_txt);
        factory_id_edt = (EditText) findViewById(R.id.factory_id_edt);
        picking_edt = (EditText) findViewById(R.id.picking_edt);
        warehouse_llt = (LinearLayout) findViewById(R.id.warehouse_llt);
        sale_record_llt = (LinearLayout) findViewById(R.id.sale_record_llt);
        picked_llt = (LinearLayout) findViewById(R.id.picked_llt);
        factory_id_btn = (Button) findViewById(R.id.factory_id_btn);
        upload_btn = (Button) findViewById(R.id.upload_btn);
        loss_txt = (TextView) findViewById(R.id.loss_txt);
        record_txt = (TextView) findViewById(R.id.record_txt);

        warehouse_llt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

        factory_id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttpForFactoryID();
                picking_edt.setFocusable(true);
                picking_edt.setFocusableInTouchMode(true);
                picking_edt.requestFocus();
                factory_id_edt.setFocusable(true);
                factory_id_edt.setFocusableInTouchMode(true);
                factory_id_edt.requestFocus();
                item_number_txt.setText("");
                item_name_txt.setText("");
                item_format_txt.setText("");
                barcode_txt.setText("");
                picked_txt.setText("");
                picking_edt.setText("");
                recent_in_txt.setText("");
                recent_out_txt.setText("");
                last_inventory_txt.setText("");
                sale_record_llt.removeAllViews();
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendRequestWithOkHttpForWareHouseInventory();
                upload_btn.setVisibility(View.GONE);
                picked_llt.setVisibility(View.GONE);
                picking_edt.setFocusable(false);
                picking_edt.setFocusableInTouchMode(false);
                factory_id_edt.setFocusable(false);
                factory_id_edt.setFocusableInTouchMode(false);
            }
        });

        record_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sale_record_llt.removeAllViews();
                //與WareHouseLog.PHP取得連線
                sendRequestWithOkHttpForWareHouseLog();
            }
        });
    }

    /**
     * 獲取當天日期
     */
    private void GetDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new java.util.Date());
        date_txt.setText(date);
    }

    /**
     * 確認是否有最新版本，進行更新
     */
    private void CheckFirebaseVersion() {
        SharedPreferences fb_version = getSharedPreferences("fb_version", MODE_PRIVATE);
        final String version = fb_version.getString("FB_VER", "");
        Log.e("PickingActivity", version);

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
                Log.e("PickingActivity", "已讀取到值:" + data);
                if (version.equals(data)) {
                } else {
                    new AlertDialog.Builder(PickingActivity.this)
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
                                                    PickingActivity.this.Update();
                                                }
                                            }.start();
                                        }
                                    }).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("PickingActivity", "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * 下載新版本APK
     */
    public void Update() {
        try {
            //URL url = new URL("http://192.168.0.201/wqp_1.9.apk");
            URL url = new URL("http://m.wqp-water.com.tw/wqp_1.9.apk");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            //c.setRequestMethod("GET");
            //c.setDoOutput(true);
            c.connect();

            String PATH = Environment.getExternalStorageDirectory() + "/Download/";
            //String PATH2 = Environment.getExternalStorageDirectory().getPath() + "/Download/";
            //String PATH = System.getenv("SECONDARY_STORAGE") + "/Download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "wqp_1.9.apk");
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
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/" + "wqp_1.9.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            PickingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "開始安裝新版本", Toast.LENGTH_LONG).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("下載錯誤!", e.toString());
            PickingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "更新失敗!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * 取回掃描回傳值或取消掃描
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //ZXing回傳的內容
                String contents = intent.getStringExtra("SCAN_RESULT");
                barcode_txt.setText(intent.getStringExtra("result_text"));
                sendRequestWithOkHttpForWareHouseSearch();
            } else {
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(PickingActivity.this, "取消掃描", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * 公司別的Spinner下拉選單
     */
    private void CompanySpinner() {
        context = this;
        //程式剛啟始時載入第一個下拉選單
        company_listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, company_list);
        company_listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        company_spinner.setAdapter(company_listAdapter);
        company_spinner.setOnItemSelectedListener(selectListener);
        //因為下拉選單第一個為請選擇，所以不載入
        warehouse_listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, empty);
        warehouse_listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        warehouse_spinner.setAdapter(warehouse_listAdapter);
    }

    /**
     * 倉庫庫別
     */
    /**
     * 第一個下拉類別的監看式
     */
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            //讀取第一個下拉選單是選擇第幾個
            //int pos = type_spinner.getSelectedItemPosition();
            //與WareHouse.PHP取得連線
            sendRequestWithOkHttpForWareHouse();
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    };

    /**
     * 與OkHttp建立連線(WareHouse)
     */
    private void sendRequestWithOkHttpForWareHouse() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String spinner_select = String.valueOf(company_spinner.getSelectedItem());
                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("COMPANY", spinner_select)
                            .build();
                    Log.e("InventoryActivity", spinner_select);
                    Request request = new Request.Builder()
                            .url("http://a.wqp-water.com.tw/WQP/Warehouse.php")
                            //.url("http://192.168.0.172/WQP/Warehouse.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("PickingActivity", responseData);
                    parseJSONWithJSONObjectForWareHouse(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 獲得JSON字串並解析成String字串
     *
     * @param jsonData
     */
    private void parseJSONWithJSONObjectForWareHouse(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            ArrayList<String> JArrayList = new ArrayList<String>();
            warehouse_list = new String[]{};
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String warehouse_id = jsonObject.getString("庫別") + "(" + jsonObject.getString("庫名") + ")";
                //JSONArray加入SearchData資料
                JArrayList.add(warehouse_id);
                warehouse_list = JArrayList.toArray(new String[i]);
                //Log.e("PictureActivity3", diy[i]);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //重新產生新的Adapter/*，用的是二維陣列type2[pos]*/
                    warehouse_listAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, warehouse_list);
                    //載入第二個下拉選單Spinner
                    warehouse_spinner.setAdapter(warehouse_listAdapter);
                    warehouse_spinner.setOnItemSelectedListener(selectListener2);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下拉類別的監看式
     */
    private AdapterView.OnItemSelectedListener selectListener2 = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            //讀取下拉選單是選擇第幾個
            //int pos = type_spinner.getSelectedItemPosition();
            picked_txt.setText("");
            picking_edt.setText("");
            //factory_id_edt.setText("");
            recent_in_txt.setText("");
            recent_out_txt.setText("");
            last_inventory_txt.setText("");
            sale_record_llt.removeAllViews();
            PickingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    upload_btn.setVisibility(View.VISIBLE);
                    picking_edt.setFocusable(true);
                    picking_edt.setFocusableInTouchMode(true);
                    picking_edt.requestFocus();
                    factory_id_edt.setFocusable(true);
                    factory_id_edt.setFocusableInTouchMode(true);
                    factory_id_edt.requestFocus();
                    //與WareHouseMoreSearch.PHP取得連線
                    sendRequestWithOkHttpForWareHouseMoreSearch();
                }
            });
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    };

    /**
     * 與OKHttp連線(查詢原廠序號資料)
     */
    private void sendRequestWithOkHttpForFactoryID() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String f_id = factory_id_edt.getText().toString();
                Log.e("PickingActivity8", f_id);

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("item_id", f_id)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://a.wqp-water.com.tw/WQP/WareHouseFactoryID.php")
                            //.url("http://192.168.0.172/WQP/WareHouseFactoryID.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("PickingActivity", responseData);
                    parseJSONWithJSONObjectForFactoryID(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 取得撿貨之公司別、品號、品名、規格、單位、原廠序號、BarCode
     *
     * @param jsonData
     */
    private void parseJSONWithJSONObjectForFactoryID(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                COMPANY = jsonObject.getString("公司別");
                MB001 = jsonObject.getString("品號");
                MB002 = jsonObject.getString("品名");
                MB003 = jsonObject.getString("規格");
                MB004 = jsonObject.getString("單位");
                item_id = jsonObject.getString("原廠序號");
                barcode = jsonObject.getString("BarCode");

                PickingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        item_number_txt.setText(MB001.toString());
                        item_name_txt.setText(MB002.toString());
                        item_format_txt.setText(MB003.toString());
                        unit_txt1.setText(MB004.toString());
                        unit_txt2.setText(MB004.toString());
                        barcode_txt.setText(barcode.toString());
                        company_spinner.setVisibility(View.VISIBLE);
                        warehouse_spinner.setVisibility(View.VISIBLE);
                        //公司別的Spinner下拉選單
                        CompanySpinner();
                        //倉庫庫別的Spinner下拉選單
                        //WareHouseSpinner();
                        //轉換公司別中英文
                        if (COMPANY.toString().equals("WQP")) {
                            company_ch = "拓霖";
                        } else if (COMPANY.toString().equals("TYT")) {
                            company_ch = "拓亞鈦";
                        } else {
                            company_ch = "倍偉特";
                        }
                        //當迴圈與COMPANY內容一致時跳出迴圈 並顯示該公司別的Spinner位置
                        for (int c = 0; c < company_list.length; c++) {
                            if (company_list[c].equals(company_ch)) {
                                company_spinner.setSelection(c, true);
                                break;
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Button的設置
     */
    public void scanCode(View view) {
        //startActivityForResult(new Intent(this, ScannerActivity.class), 1);
        launchActivity(ScannerActivity.class);
        upload_btn.setVisibility(View.VISIBLE);
        picking_edt.setFocusable(true);
        picking_edt.setFocusableInTouchMode(true);
        picking_edt.requestFocus();
        factory_id_edt.setFocusable(true);
        factory_id_edt.setFocusableInTouchMode(true);
        factory_id_edt.requestFocus();
    }

    /**
     * 轉畫面的封包，兼具權限和Intent跳轉畫面
     */
    public void launchActivity(Class<?> clss) {
        //假如還「未獲取」權限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            //startActivity(intent);
            startActivityForResult(intent, ZXING_CAMERA_PERMISSION);
        }
    }

    /**
     * 與OKHttp連線(查詢需撿貨資料)
     */
    private void sendRequestWithOkHttpForWareHouseSearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String barcode = barcode_txt.getText().toString();
                Log.e("PickingActivity8", barcode);

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("barcode", barcode)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://a.wqp-water.com.tw/WQP/WareHouseSearch.php")
                            //.url("http://192.168.0.172/WQP/WareHouseSearch.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("PickingActivity", responseData);
                    parseJSONWithJSONObjectForWareHouseSearch(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 取得撿貨之公司別、品號、品名、規格、單位、原廠序號、BarCode
     *
     * @param jsonData
     */
    private void parseJSONWithJSONObjectForWareHouseSearch(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                COMPANY = jsonObject.getString("公司別");
                MB001 = jsonObject.getString("品號");
                MB002 = jsonObject.getString("品名");
                MB003 = jsonObject.getString("規格");
                MB004 = jsonObject.getString("單位");
                item_id = jsonObject.getString("原廠序號");
                barcode = jsonObject.getString("BarCode");

                PickingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        item_number_txt.setText(MB001.toString());
                        item_name_txt.setText(MB002.toString());
                        item_format_txt.setText(MB003.toString());
                        unit_txt1.setText(MB004.toString());
                        unit_txt2.setText(MB004.toString());
                        factory_id_edt.setText(item_id.toString());
                        company_spinner.setVisibility(View.VISIBLE);
                        warehouse_spinner.setVisibility(View.VISIBLE);
                        //公司別的Spinner下拉選單
                        CompanySpinner();
                        //倉庫庫別的Spinner下拉選單
                        //WareHouseSpinner();
                        //轉換公司別中英文
                        if (COMPANY.toString().equals("WQP")) {
                            company_ch = "拓霖";
                        } else if (COMPANY.toString().equals("TYT")) {
                            company_ch = "拓亞鈦";
                        } else {
                            company_ch = "倍偉特";
                        }
                        //當迴圈與COMPANY內容一致時跳出迴圈 並顯示該公司別的Spinner位置
                        for (int c = 0; c < company_list.length; c++) {
                            if (company_list[c].equals(company_ch)) {
                                company_spinner.setSelection(c, true);
                                break;
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 與OKHttp連線(查詢倉庫盤點資料)
     */
    private void sendRequestWithOkHttpForWareHouseMoreSearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String company_select_more = String.valueOf(company_spinner.getSelectedItem());
                String MB001 = item_number_txt.getText().toString();
                String warehouse_no_select_more = String.valueOf(warehouse_spinner.getSelectedItem());
                w_id = warehouse_no_select_more.substring(0, warehouse_no_select_more.indexOf("("));
                /*if(warehouse_no_select_more.equals("BWT(BWT總倉)")){
                    w_id = "BWT";
                }else if (warehouse_no_select_more.equals("BWT-De(BWT不良品)")){
                    w_id = "BWT-De";
                }else if (warehouse_no_select_more.equals("OM-BZA(委外-鉑中)")){
                    w_id = "OM-BZA";
                }else if (warehouse_no_select_more.equals("WPA(暫存倉)")){
                    w_id = "WPA";
                }else if (warehouse_no_select_more.equals("WQP(拓霖倉)")){
                    w_id = "WQP";
                }*/

                Log.e("PickingActivity", MB001);
                Log.e("PickingActivity2", warehouse_no_select_more);
                Log.e("PickingActivity3", w_id);

                if (company_select_more.toString().equals("拓霖")) {
                    company_select_more = "WQP";
                } else if (company_select_more.toString().equals("拓亞鈦")) {
                    company_select_more = "TYT";
                } else {
                    company_select_more = "BWT";
                }

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("COMPANY", company_select_more)
                            .add("MB001", MB001)
                            .add("WH_NO", w_id)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://a.wqp-water.com.tw/WQP/WareHouseMoreSearch.php")
                            //.url("http://192.168.0.172/WQP/WareHouseMoreSearch.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("PickingActivity_r", responseData);
                    parseJSONWithJSONObjectForWareHouseMoreSearch(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 取得盤點貨物之庫別、最近入庫日、最近出貨日、上次盤點日
     *
     * @param jsonData
     */
    private void parseJSONWithJSONObjectForWareHouseMoreSearch(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                warehouse_no = jsonObject.getString("倉庫代號");
                warehouse_name = jsonObject.getString("倉庫名稱");
                MB064 = jsonObject.getString("數量");
                in = jsonObject.getString("最近入庫日");
                out = jsonObject.getString("最近出貨日");
                inventory = jsonObject.getString("上次盤點日");

                Log.e("InventoryActivity", MB064);

                PickingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        picked_txt.setText(MB064.toString());
                        recent_in_txt.setText(in.toString());
                        recent_out_txt.setText(out.toString());
                        last_inventory_txt.setText(inventory.toString());
                        //使浮標移動到最後
                        picking_edt.setSelection(picking_edt.getText().length());
                        factory_id_edt.setSelection(factory_id_edt.getText().length());
                    }
                });

                /*if (vendor_txt.getText().toString().equals(":|:")){
                    vendor_txt.setText("");
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 與OKHttp連線(查詢倉庫盤點之盤點人員、數量、倉庫名稱、盤點日期的Log)
     */
    private void sendRequestWithOkHttpForWareHouseLog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String company_select_log = String.valueOf(company_spinner.getSelectedItem());
                String MB001 = item_number_txt.getText().toString();
                String warehouse_no_select_log = String.valueOf(warehouse_spinner.getSelectedItem());
                //String w_id_log = warehouse_no_select_log.substring(0, warehouse_no_select_log.indexOf(":|:"));
                if(warehouse_no_select_log.equals("BWT(BWT總倉)")){
                    w_id = "BWT";
                }else if (warehouse_no_select_log.equals("BWT-De(BWT不良品)")){
                    w_id = "BWT-De";
                }else if (warehouse_no_select_log.equals("OM-BZA(委外-鉑中)")){
                    w_id = "OM-BZA";
                }else if (warehouse_no_select_log.equals("WPA(暫存倉)")){
                    w_id = "WPA";
                }else if (warehouse_no_select_log.equals("WQP(拓霖倉)")){
                    w_id = "WQP";
                }

                Log.e("PickingActivity", MB001);
                Log.e("PickingActivity", warehouse_no_select_log);

                if (company_select_log.toString().equals("拓霖")) {
                    company_select_log = "WQP";
                } else if (company_select_log.toString().equals("拓亞鈦")) {
                    company_select_log = "TYT";
                } else {
                    company_select_log = "BWT";
                }

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("COMPANY", company_select_log)
                            .add("MB001", MB001)
                            .add("WH_NO", w_id)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://a.wqp-water.com.tw/WQP/WareHouseLog.php")
                            //.url("http://192.168.0.172/WQP/WareHouseLog.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("PickingActivity", responseData);
                    parseJSONWithJSONObjectForWareHouseLog(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 取得盤點貨物之盤點人員、數量、倉庫名稱、盤點日期的Log
     *
     * @param jsonData
     */
    private void parseJSONWithJSONObjectForWareHouseLog(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSON格式改為字串
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String user_name_log = jsonObject.getString("盤點人員");
                String quantity_log = jsonObject.getString("系統數量");
                String actual_log = jsonObject.getString("實際數量");
                String warehouse_name_log = jsonObject.getString("倉庫名稱");
                String inventory_log = jsonObject.getString("盤點日期");

                Log.e("PickingActivity", quantity_log);

                //JSONArray加入SearchData資料
                ArrayList<String> JArrayList = new ArrayList<String>();
                JArrayList.add(user_name_log);
                JArrayList.add(quantity_log);
                JArrayList.add(actual_log);
                JArrayList.add(warehouse_name_log);
                JArrayList.add(inventory_log);

                //HandlerMessage更新UI
                Bundle b = new Bundle();
                b.putStringArrayList("JSON_data", JArrayList);
                Message msg = mHandler.obtainMessage();
                msg.setData(b);
                msg.what = 1;
                msg.sendToTarget();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新UI
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    LinearLayout small_llt1 = new LinearLayout(PickingActivity.this);
                    small_llt1.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt2 = new LinearLayout(PickingActivity.this);
                    small_llt2.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt3 = new LinearLayout(PickingActivity.this);
                    small_llt3.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt4 = new LinearLayout(PickingActivity.this);
                    small_llt4.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout small_llt5 = new LinearLayout(PickingActivity.this);
                    small_llt5.setOrientation(LinearLayout.HORIZONTAL);

                    Bundle jb = msg.getData();
                    ArrayList<String> JArrayList = new ArrayList<String>();
                    //int i = b.getStringArrayList("JSON_data").size();
                    JArrayList = jb.getStringArrayList("JSON_data");

                    //顯示每筆TableLayout的盤點人員
                    TextView dynamically_name;
                    dynamically_name = new TextView(PickingActivity.this);
                    dynamically_name.setText("撿貨人員 : " + JArrayList.get(0));
                    dynamically_name.setGravity(Gravity.CENTER);
                    //dynamically_name.setWidth(50);
                    dynamically_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                    //顯示每筆TableLayout的數量
                    TextView dynamically_quantity;
                    dynamically_quantity = new TextView(PickingActivity.this);
                    dynamically_quantity.setText("需出貨數量 : " + JArrayList.get(1));
                    dynamically_quantity.setGravity(Gravity.CENTER);
                    dynamically_quantity.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                    //顯示每筆TableLayout的數量
                    TextView dynamically_actual;
                    dynamically_actual = new TextView(PickingActivity.this);
                    dynamically_actual.setText("撿貨數量 : " + JArrayList.get(2));
                    dynamically_actual.setGravity(Gravity.CENTER);
                    dynamically_actual.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                    //顯示每筆TableLayout的庫別名稱
                    TextView dynamically_vendor;
                    dynamically_vendor = new TextView(PickingActivity.this);
                    dynamically_vendor.setText("庫別名稱 : " + JArrayList.get(3));
                    dynamically_vendor.setGravity(Gravity.CENTER);
                    //dynamically_name.setWidth(50);
                    dynamically_vendor.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                    //顯示每筆TableLayout的盤點日期
                    TextView dynamically_date;
                    dynamically_date = new TextView(PickingActivity.this);
                    dynamically_date.setText("撿貨日期 : " + JArrayList.get(4));
                    dynamically_date.setGravity(Gravity.CENTER);
                    dynamically_date.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                    //設置每筆TableLayout的分隔線
                    TextView dynamically_txt = new TextView(PickingActivity.this);
                    dynamically_txt.setBackgroundColor(Color.rgb(220, 220, 220));

                    LinearLayout.LayoutParams small_pm = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

                    small_llt1.addView(dynamically_name, small_pm);
                    small_llt2.addView(dynamically_quantity, small_pm);
                    small_llt3.addView(dynamically_actual, small_pm);
                    small_llt4.addView(dynamically_vendor, small_pm);
                    small_llt5.addView(dynamically_date, small_pm);

                    sale_record_llt.addView(dynamically_txt, LinearLayout.LayoutParams.MATCH_PARENT, 3);
                    sale_record_llt.addView(small_llt1);
                    sale_record_llt.addView(small_llt2);
                    sale_record_llt.addView(small_llt3);
                    sale_record_llt.addView(small_llt4);
                    sale_record_llt.addView(small_llt5);

                    sale_record_llt.getChildAt(0).setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 與OKHttp連線(上傳盤點數量&盤點Log)
     */
    private void sendRequestWithOkHttpForWareHouseInventory() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //接收LoginActivity傳過來的值
                SharedPreferences user_id = getSharedPreferences("user_id_data", MODE_PRIVATE);
                String user_id_data = user_id.getString("ID", "");
                Log.e("InventoryActivity", user_id_data);

                String company_select_inventory = String.valueOf(company_spinner.getSelectedItem());
                String MB001 = item_number_txt.getText().toString();
                String picked = picked_txt.getText().toString();
                String picking = picking_edt.getText().toString();
                String factory_id = factory_id_edt.getText().toString();

                String warehouse_no_select = String.valueOf(warehouse_spinner.getSelectedItem());
                //String w_id_no = warehouse_no_select.substring(0, warehouse_no_select.indexOf(":|:"));
                //String warehouse_name_select = String.valueOf(warehouse_spinner.getSelectedItem());
                //String w_id_name = warehouse_name_select.substring(warehouse_name_select.indexOf(":|:"), warehouse_name_select.length());
                if(warehouse_no_select.equals("BWT(BWT總倉)")){
                    w_id = "BWT";
                    w_name = "BWT總倉";
                }else if (warehouse_no_select.equals("BWT-De(BWT不良品)")){
                    w_id = "BWT-De";
                    w_name = "BWT不良品";
                }else if (warehouse_no_select.equals("OM-BZA(委外-鉑中)")){
                    w_id = "OM-BZA";
                    w_name = "委外-鉑中";
                }else if (warehouse_no_select.equals("WPA(暫存倉)")){
                    w_id = "WPA";
                    w_name = "暫存倉";
                }else if (warehouse_no_select.equals("WQP(拓霖倉)")){
                    w_id = "WQP";
                    w_name = "拓霖倉";
                }

                if (company_select_inventory.toString().equals("拓霖")) {
                    company_select_inventory = "WQP";
                } else if (company_select_inventory.toString().equals("拓亞鈦")) {
                    company_select_inventory = "TYT";
                } else {
                    company_select_inventory = "BWT";
                }

                Log.e("InventoryActivity", MB001);
                Log.e("InventoryActivity", picking);
                Log.e("InventoryActivity", factory_id);
                Log.e("InventoryActivity", w_id);
                Log.e("InventoryActivity", w_name);

                try {
                    OkHttpClient client = new OkHttpClient();
                    //POST
                    RequestBody requestBody = new FormBody.Builder()
                            .add("COMPANY", company_select_inventory)
                            .add("User_id", user_id_data)
                            .add("MB001", MB001)
                            .add("quantity", picked)
                            .add("actual", picking)
                            .add("actual", factory_id)
                            .add("WH_NO", w_id)
                            .add("WH_NAME", w_name)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://a.wqp-water.com.tw/WQP/WareHouseInventory.php")
                            //.url("http://192.168.0.172/WQP/WareHouseInventory.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("InventoryActivity", responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Toast.makeText(this, "送出成功", Toast.LENGTH_SHORT).show();
    }
}
