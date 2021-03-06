package com.smartbracelet.com.smartbracelet.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.smartbracelet.com.smartbracelet.R;
import com.smartbracelet.com.smartbracelet.fragment.BlueToothFragment;
import com.smartbracelet.com.smartbracelet.fragment.HomeFragment;
import com.smartbracelet.com.smartbracelet.fragment.StatisFragment;
import com.smartbracelet.com.smartbracelet.model.BaseFragment;
import com.smartbracelet.com.smartbracelet.service.LocationService;
import com.smartbracelet.com.smartbracelet.util.LogUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Yangli on 16-03-20.
 * 这个页面是用于以前调试的页面，是这个应用的最初的beta版本
 * 有3个Fragment组成
 * 不删，当做纪念吧
 */

public class MainActivity extends AppCompatActivity {

    private static final String[] FRAGMENT_TAGS = {"home", "statistics", "setting"};
    private static final int ID_HOME = 0;
    private static final int ID_STATISTICS = 1;
    private static final int ID_SETTING = 2;
    private BaseFragment mCurrentFragment;
    private int mCrrrentFragmentId = ID_HOME;
    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;
    private LocationService locationService;
    public static double latitude;
    public static double longtitude;
    private int currentIndexFragment = 0;
    private final  int INDEX_FRAGMENT_HOME = 0;
    private final  int INDEX_FRAGMENT_STAT = 1;
    private final  int INDEX_FRAGMENT_SETT = 2;

    private MainActivity pThis;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pThis = this;

        ButterKnife.bind(this);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.tab_bar);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_home:
                        currentIndexFragment = INDEX_FRAGMENT_HOME;
                        switchToFragment(ID_HOME);
                        break;
                    case R.id.tab_statis:
                        currentIndexFragment = INDEX_FRAGMENT_STAT;
                        switchToFragment(ID_STATISTICS);
                        break;
                    case R.id.tab_setting:
                        currentIndexFragment = INDEX_FRAGMENT_SETT;
                        switchToFragment(ID_SETTING);
                        break;
                }
            }
        });
        switchToFragment(ID_HOME);
        getPersimmions();

    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d("onStart");
        // -----------location config ------------
        locationService = ((App) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
			/*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }

        }else{
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*****
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sbLongtitude = new StringBuffer(256);
                StringBuffer sbLatitude = new StringBuffer(256);
                //sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                //sb.append(location.getTime());
                latitude = location.getLatitude();
                sbLatitude.append("latitude :\n ");
                sbLatitude.append(latitude);
                Message message = new Message();
                Message message2 = new Message();

                if (INDEX_FRAGMENT_STAT == currentIndexFragment) {
                    message.what = 1;

                    message.obj = sbLatitude.toString();
                    ((StatisFragment)mCurrentFragment).mStatisHandler.sendMessage(message);
                }

                LogUtil.d(sbLatitude.toString());
                longtitude = location.getLongitude();
                sbLongtitude.append("lontitude :\n");
                sbLongtitude.append(longtitude);

                if (INDEX_FRAGMENT_STAT == currentIndexFragment) {
                    message2.what = 2;
                    message2.obj = sbLongtitude.toString();
                    ((StatisFragment)mCurrentFragment).mStatisHandler.sendMessage(message2);
                }


                /*if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }*/

            }
        }

    };


    private void switchToFragment(int id) {
        mCurrentFragment = (BaseFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAGS[id]);
        mCrrrentFragmentId = id;
        if (mCurrentFragment == null) {
            switch (id) {
                case ID_HOME:
                    mCurrentFragment = new HomeFragment();

                    break;
                case ID_STATISTICS:
                    mCurrentFragment = new StatisFragment(locationService);

                    break;
                case ID_SETTING:
                    mCurrentFragment = new BlueToothFragment(pThis);

                    break;

            }
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_layout, mCurrentFragment, FRAGMENT_TAGS[id]).commit();
    }


}
