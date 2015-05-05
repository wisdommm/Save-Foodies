package com.hzh.savefoodies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bottle.ourApp.DBUtil;
import com.lx.scancode.CaptureActivity;


public class MainActivity extends FragmentActivity
{
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment>mDatas;
	
	private TextView mscanTextView;
	private TextView mfoodTextView;
	private TextView msportsTextView;
	private TextView mrecentTextView;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main1);
		initView();
	}
		
		private void initView(){
		// TODO Auto-generated method stub
		mViewPager = (ViewPager) findViewById(R.id.idviewpager);
		
		mscanTextView = (TextView) findViewById(R.id.idscan);
		mfoodTextView = (TextView) findViewById(R.id.idfood);
		msportsTextView = (TextView) findViewById(R.id.idsports);
		mrecentTextView = (TextView) findViewById(R.id.idrecent);
		
		mDatas = new ArrayList<Fragment>();
		scanMainTabFragment Tab01 = new scanMainTabFragment();
		foodMainTabFragment Tab02 = new foodMainTabFragment();
		sportsMainTabFragment Tab03 = new sportsMainTabFragment();
		recentMainTabFragment Tab04 = new recentMainTabFragment();
		mDatas.add(Tab01);
		mDatas.add(Tab02);
		mDatas.add(Tab03);
		mDatas.add(Tab04);
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{
			public int getCount()
			{
				return mDatas.size();
			}
			public Fragment getItem(int arg0)
			{
				return mDatas.get(arg0);
			}
		};
		
		mViewPager.setAdapter(mAdapter);
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int postion) {
				// TODO Auto-generated method stub
				resetTextView();
				switch(postion)
				{
				case 0:
					
					mscanTextView.setTextColor(Color.BLUE);
					break;
				case 1:
					
					mfoodTextView.setTextColor(Color.BLUE);
					break;
				case 2:
					
					msportsTextView.setTextColor(Color.BLUE);
					break;
				case 3:
					
					mrecentTextView.setTextColor(Color.BLUE);
					break;
				
					default:
						break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	protected void resetTextView() {
		// TODO Auto-generated method stub
		mscanTextView.setTextColor(Color.BLACK);
		mfoodTextView.setTextColor(Color.BLACK);
		msportsTextView.setTextColor(Color.BLACK);
		mrecentTextView.setTextColor(Color.BLACK);
		}

} 

//****
//打开扫描
//****

class scanMainTabFragment extends Fragment {
	
	private Button btnScan;
	private DBUtil dbUtil;
	private ListView listView;
	private SimpleAdapter adapter;
	public static final int RESULT_OK = -1;
	private final static int   REQUEST_SUCCESS = 1; 
	private final static int   REQUEST_FALSE = 0;
	

	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		
		View ScanView = inflater.inflate(R.layout.tab01,container,false);
		 
	
		btnScan =(Button)ScanView.findViewById(R.id.buttonScan);
		ButtonListener9 buttonListener9 = new ButtonListener9();
		btnScan.setOnClickListener(buttonListener9);
		dbUtil = new DBUtil();
		listView = (ListView) ScanView.findViewById(R.id.listView1);
		
		return ScanView;
	}
	
	class ButtonListener9 implements OnClickListener{
		
		public void onClick(View v) {
			if(R.id.buttonScan == v.getId()){
				startActivityForResult(new Intent(getActivity(), CaptureActivity .class),0);  
			}
		}
	}
	
	private void RequestData(String Cno)
	{
		dbUtil.arrayList.clear();
		dbUtil.brrayList.clear();
		dbUtil. crrayList.clear();
		
		dbUtil.arrayList.add("Cno");
		dbUtil.brrayList.add(Cno);
		
	    new Thread(new Runnable() {
	         
	        @Override
	        public void run() {
	            // TODO Auto-generated method stub
	        	dbUtil. crrayList = dbUtil.Soap.GetWebServre("insertCargoInfo", dbUtil.arrayList, dbUtil.brrayList);
	            Message msg = new Message(); 
	            if(dbUtil.crrayList.size()>0)
	            {
	                msg.what = REQUEST_SUCCESS; 
	            }
	            else 
	            {
	                msg.what = REQUEST_FALSE; 
	            }
	            // 鍙戯拷?锟芥秷锟�?
	           // textT.setText("SAdfa");
	            mHandler.sendMessage(msg); 
	        }
	    }).start();
	}
	
	  public Handler mHandler = new Handler(){ 
          // 鎺ユ敹娑堟伅 
          @Override 
          public void handleMessage(Message msg) { 
              // TODO Auto-generated method stub  
              super.handleMessage(msg); 
              switch (msg.what)
            {
                case REQUEST_SUCCESS:
                	//textT.setText("SAdfa");
                   setListView();
                    break;
                case REQUEST_FALSE:
                    // 鍋氶敊璇锟�?
                    break;
                default:
                    break;
            }
          } 
            
      }; 
      
  	private void setListView() {
  		
		 listView.setVisibility(View.VISIBLE);
	        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	        list = dbUtil.getAllInfo();
	        adapter = new SimpleAdapter(getActivity(), list, R.layout.adapter_item, 
	        new String[] { "Cno", "Cname"}, //, "Cnum" }, 
	        new int[] { R.id.txt_Cno, R.id.txt_Cname});//, R.id.txt_Cnum });
	        listView.setAdapter(adapter);

	}
  	
  	public void onActivityResult(int requestCode, int resultCode,  
            Intent data){  
			switch (resultCode){  
			case RESULT_OK:  
				btnScan.setVisibility(View.GONE);
				Bundle b = data.getExtras();  

				String string = b.getString("CODE");  
			//	textT.setText(string);

				RequestData(string);  
			}  
	}  
}


//****
//运动界面
//****


class sportsMainTabFragment extends Fragment 
{
	private TextView textView1;
	private Button button1;
	private Button button2;
	int count1=0;
	private TextView textView2;
	private Button button3;
	private Button button4;
	int count2=0;
	private TextView textView3;
	private Button button5;
	private Button button6;
	int count3=0;
	private TextView textView4;
	private Button button7;
	private Button button8;
	int count4=0;
	private Button button9;
	private TextView textView5;
	int count5=0;

	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		
		View rootView = inflater.inflate(R.layout.tab03,container,false);
		
		textView1 = (TextView)rootView.findViewById(R.id.textView1);
		textView2 = (TextView)rootView.findViewById(R.id.textView2);
		textView3 = (TextView)rootView.findViewById(R.id.textView3);
		textView4 = (TextView)rootView.findViewById(R.id.textView4);
		textView5 = (TextView)rootView.findViewById(R.id.textView5);
		
		button1 = (Button)rootView.findViewById(R.id.button1);
		ButtonListener1 buttonListener1 = new ButtonListener1();
		button1.setOnClickListener(buttonListener1);
		
		button2 = (Button)rootView.findViewById(R.id.button2);
		ButtonListener2 buttonListener2 = new ButtonListener2();
		button2.setOnClickListener(buttonListener2);
		
		button3 = (Button)rootView.findViewById(R.id.button3);
		ButtonListener3 buttonListener3 = new ButtonListener3();
		button3.setOnClickListener(buttonListener3);
		
		button4 = (Button)rootView.findViewById(R.id.button4);
		ButtonListener4 buttonListener4 = new ButtonListener4();
		button4.setOnClickListener(buttonListener4);
		
		button5 = (Button)rootView.findViewById(R.id.button5);
		ButtonListener5 buttonListener5 = new ButtonListener5();
		button5.setOnClickListener(buttonListener5);
		
		button6 = (Button)rootView.findViewById(R.id.button6);
		ButtonListener6 buttonListener6 = new ButtonListener6();
		button6.setOnClickListener(buttonListener6);
		
		button7 = (Button)rootView.findViewById(R.id.button7);
		ButtonListener7 buttonListener7 = new ButtonListener7();
		button7.setOnClickListener(buttonListener7);
		
		button8 = (Button)rootView.findViewById(R.id.button8);
		ButtonListener8 buttonListener8 = new ButtonListener8();
		button8.setOnClickListener(buttonListener8);
		
		button9 = (Button)rootView.findViewById(R.id.button9);
		ButtonListener9 buttonListener9 = new ButtonListener9();
		button9.setOnClickListener(buttonListener9);
		
		return rootView;
		}
	
	
	class ButtonListener1 implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(R.id.button1 == v.getId()){
				count1 = count1+1;
				textView1.setText(count1 + "分钟");
				count5 = 3*count1+10*count2+9*count3+11*count4;
				textView5.setText("一共消耗了" + count5 + "卡路里，真棒！");
			}
		}
	}
	class ButtonListener2 implements OnClickListener{

		@Override
		public void onClick(View v1) {
			if(R.id.button2 == v1.getId()){
				count1 = count1-1;
				textView1.setText(count1 + "分钟");
				count5 = 3*count1+10*count2+9*count3+11*count4;
				textView5.setText("一共消耗了" + count5 + "卡路里，真棒！");
			}
		}
	}
	class ButtonListener3 implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(R.id.button3 == v.getId()){
				count2= count2+1;
				textView2.setText(count2 + "分钟");
				count5 = 3*count1+10*count2+9*count3+11*count4;
				textView5.setText("一共消耗了" + count5 + "卡路里，真棒！");
			}
		}
	}
	class ButtonListener4 implements OnClickListener{

		@Override
		public void onClick(View v1) {
			if(R.id.button4 == v1.getId()){
				count2= count2-1;
				textView2.setText(count2 + "分钟");
				count5 = 3*count1+10*count2+9*count3+11*count4;
				textView5.setText("一共消耗了" + count5 + "卡路里，真棒！");
			}
		}
	}
	class ButtonListener5 implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(R.id.button5 == v.getId()){
				count3 = count3+1;
				textView3.setText(count3 + "分钟");
				count5 = 3*count1+10*count2+9*count3+11*count4;
				textView5.setText("一共消耗了" + count5 + "卡路里，真棒！");
			}
		}
	}
	class ButtonListener6 implements OnClickListener{

		@Override
		public void onClick(View v1) {
			if(R.id.button6 == v1.getId()){
				count3 = count3-1;
				textView3.setText(count3 + "分钟");
				count5 = 3*count1+10*count2+9*count3+11*count4;
				textView5.setText("一共消耗了" + count5 + "卡路里，真棒！");
			}
		}
	}
	class ButtonListener7 implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(R.id.button7 == v.getId()){
				count4 = count4+1;
				textView4.setText(count4 + "分钟");
				count5 = 3*count1+10*count2+9*count3+11*count4;
				textView5.setText("一共消耗了" + count5 + "卡路里，真棒！");
			}
		}
	}
	

	
	class ButtonListener8 implements OnClickListener{

		@Override
		public void onClick(View v1) {
			if(R.id.button8 == v1.getId()){
				count4 = count4-1;
				textView4.setText(count4 + "分钟");
				count5 = 3*count1+10*count2+9*count3+11*count4;
				textView5.setText("一共消耗了" + count5 + "卡路里，真棒！");
			}
		}
	}
	class ButtonListener9 implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(R.id.button9 == v.getId()){
				count1=0;
				count2=0;
				count3=0;
				count4=0;
				count5=0;
				textView1.setText(count1 + "分钟");
				textView2.setText(count2 + "分钟");
				textView3.setText(count3 + "分钟");
				textView4.setText(count4 + "分钟");
				textView5.setText("一共消耗了" + count5 + "卡路里！");
			}
		}
	}




}

