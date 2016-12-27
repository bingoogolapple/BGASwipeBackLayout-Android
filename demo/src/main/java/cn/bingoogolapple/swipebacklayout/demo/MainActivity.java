package cn.bingoogolapple.swipebacklayout.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_main_demo1) {
            startActivity(new Intent(this, DemoScrollViewActivity.class));
        }
    }
}
