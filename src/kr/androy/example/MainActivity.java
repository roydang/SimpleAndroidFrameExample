package kr.androy.example;

import kr.androy.R;
import kr.androy.base.BaseActivity;
import kr.androy.example.func.tracker.TestTrackingActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		initView();
	} 
	private void initView() {
		Button trackingTestBtn = (Button)findViewById(R.id.tracking_test_btn);
		trackingTestBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, TestTrackingActivity.class);
				startActivity(intent);
			}
		});
	}
}
