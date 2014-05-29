package kr.androy.example.func.tracker;

import kr.androy.R;
import kr.androy.example.tracker.LocationTrackingService;
import kr.androy.example.tracker.TrackingConstants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestTrackingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_tracking);	
		initView();
	}
	private void initView() {
		Button trackingStartBtn = (Button)findViewById(R.id.tracking_start_btn);
		trackingStartBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TrackingConstants.TRACKING_SERVICE_NAME);
				startService(intent);
			}
		});
		Button trackingStopBtn = (Button)findViewById(R.id.tracking_stop_btn);
		trackingStopBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TrackingConstants.TRACKING_SERVICE_NAME);
				stopService(intent);
			}
		});
	}
}
