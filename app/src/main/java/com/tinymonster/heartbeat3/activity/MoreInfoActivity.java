package com.tinymonster.heartbeat3.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.entity.JsonRate;
import com.tinymonster.heartbeat3.entity.Rate;
import com.tinymonster.heartbeat3.entity.entity_device;
import com.tinymonster.heartbeat3.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MoreInfoActivity extends AppCompatActivity {
    private LineChart rate_chart;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Rate> list=new ArrayList<>();
    private String device_name;
    private entity_device device;
    private LineData lineData=new LineData();
    XAxis xAxis;
    YAxis yAxis;
    List<String> Xlist=new ArrayList<>();
    List<Entry> Ylist=new ArrayList<>();
    private ImageView moreInfo_Finish_ImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        Intent intent=getIntent();
        device_name=intent.getStringExtra("device_name");
        device=StartActivity.getDeviceByName(device_name);
        initView();

    }
    private void initView(){
        rate_chart=(LineChart)findViewById(R.id.moreInfo_LineChart);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.moreInfo_SwipeRefreshLayout);
        moreInfo_Finish_ImageView=(ImageView)findViewById(R.id.moreInfo_Finish_ImageView);
        initChart();
        list=StartActivity.getAllRateByDeviceName(device_name);
        if(!CheckListEmpty(list)){
            Log.e("初始化","list为null1");
        }else {
            Log.e("初始化","list不为null2");
            if(list.size()>4){
                float ratio=(float)list.size()/(float)4;
                rate_chart.zoom(ratio,1f,0,0);
            }
            lineData=getChartData(list);
            rate_chart.setData(lineData);
            rate_chart.invalidate();
        }
        /*
        点击事件
         */
        moreInfo_Finish_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }
    /*
    刷新
     */
    public void refresh(){
        Log.e("HeartRateActivity","刷新"+device_name);
        String url="http://39.105.104.164/heartbeat/getbeat/"+device_name;
        HttpUtil.sendOkHttpGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MoreInfoActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json=response.body().string();
                Log.e("HeartRateActivity",json);
                try {
                    Gson gson = new Gson();
                    JsonRate ratetemp = gson.fromJson(json, JsonRate.class);
                    StartActivity.UpdateDeviceRateByAccount(device_name,String.valueOf(ratetemp.getRate()));
                    boolean state=StartActivity.insertRate(ratetemp.getRate(), ratetemp.getBeatTime(),device_name);
                    if(!state){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MoreInfoActivity.this,"暂无新信息",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(list!=null&&list.size()>0){
                                list.clear();
                            }
                            list = StartActivity.getAllRateByDeviceName(device_name);
                            Log.e("刷新后list大小"," "+list.size());
                            if (!CheckListEmpty(list)) {
                                Log.e("刷新","list为空");
                            } else {
                                Log.e("HRA","刷新数据");
                                lineData = getChartData(list);
                                rate_chart.setData(lineData);
                                rate_chart.invalidate();
                            }
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MoreInfoActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }
    /*
    chart初始化
     */
    private void initChart(){
        //Description
        Description description=new Description();
        description.setText("心率历史值");
        description.setTextColor(R.color.google_blue);
        rate_chart.setDescription(description);
        //
        rate_chart.setBackgroundColor(Color.WHITE);
        rate_chart.setGridBackgroundColor(Color.GREEN);
        //滑动相关
        rate_chart.setTouchEnabled(true);
        rate_chart.setDragEnabled(true);
        rate_chart.setScaleEnabled(true);   // 两个轴上的缩放,X,Y分别默认为true
        rate_chart.setScaleXEnabled(true);  // X轴上的缩放,默认true
        rate_chart.setDoubleTapToZoomEnabled(true); // 双击缩放,默认true
        rate_chart.setDragDecelerationEnabled(true);    // 抬起手指，继续滑动,默认true
        rate_chart.setDragDecelerationFrictionCoef(0.9f);   // 摩擦系数,[0-1]，较大值速度会缓慢下降，0，
        //轴
        xAxis = rate_chart.getXAxis();    // 获取X轴
        yAxis = rate_chart.getAxisLeft(); // 获取Y轴
        yAxis.setAxisMinimum(50);
        yAxis.setAxisMaximum(150);
        yAxis.addLimitLine(new LimitLine(70,"正常心跳"));
        rate_chart.getAxisRight().setEnabled(false);    // 不绘制右侧的轴线

//        rate_chart.getXAxis().setEnabled(false);
        xAxis.setEnabled(false); // 轴线是否可编辑,默认true
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);  // 是否绘制标签,默认true
        xAxis.setDrawAxisLine(true);    // 是否绘制坐标轴,默认true
        xAxis.setDrawGridLines(false);   // 是否绘制网格线，默认true
        xAxis.setLabelCount(4);
        xAxis.setGranularity(1f);
//        xAxis.setGranularityEnabled(true);
        /*???
        xAxis.setAxisMaximum(10); // 此轴能显示的最大值；
        xAxis.resetAxisMaximum();   // 撤销最大值；
        xAxis.setAxisMinimum(1);    // 此轴显示的最小值；
        xAxis.resetAxisMinimum();   // 撤销最小值；
        */
    }
    private LineData getChartData(List<Rate> list){
        Xlist.clear();
        Ylist.clear();
        Xlist.add("start");
        for(int i=0;i<list.size();i++){
            String time_result="";
            String time=list.get(i).getBeatTime();
            String[] time_list=time.split(" ");
            if(time_list.length==2){
                String[] day_list=time_list[0].split("-");
                time_result=day_list[1]+day_list[2];
                String[] hour_list=time_list[1].split(":");
                time_result=time_result+"/"+hour_list[0]+":"+hour_list[1];
            }
            Log.e("格式化x轴数据：",time_result);
            Log.e("格式化y轴数据",""+list.get(i).getBeatNum());
            Xlist.add(time_result);
            Ylist.add(new Entry((float) i,list.get(i).getBeatNum()));
        }
        Xlist.add("end");
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.e("value值:"," "+value);
                Log.e("Xlist大小:"," "+Xlist.size());
                return Xlist.get((int)value+1);
            }
        });
        LineDataSet LineDataSet=new LineDataSet(Ylist,"心率值");
        LineDataSet.setColor(R.color.red);
        LineDataSet.setDrawValues(true);
        yAxis.setValueFormatter(new IAxisValueFormatter() { // 与上面值转换一样，这里就是转换出轴上label的显示。也有几个默认的，不多说了。
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "bps";
            }
        });
        List<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(LineDataSet);
        LineData lineData=new LineData(dataSets);
        return lineData;
    }
    /*
    检查有无信息
     */
    private boolean CheckListEmpty(List<Rate> list){
        if(list==null||list.size()==0){
            Toast.makeText(MoreInfoActivity.this,"暂无心率信息，请刷新!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
}
