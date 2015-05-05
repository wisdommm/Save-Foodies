package com.hzh.savefoodies;

import com.lx.scancode.CaptureActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.example.listviewfilter.*;

public class foodMainTabFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		
		View foodView= inflater.inflate(R.layout.tab02,container,false);
		
	Button btnFood =(Button)foodView.findViewById(R.id.buttonFood);
	btnFood.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				startActivity(new Intent(getActivity(), com.example.listviewfilter.MainActivity .class));  
			}
		});
		return foodView;
	}
}