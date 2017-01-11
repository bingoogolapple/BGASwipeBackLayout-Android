package cn.bingoogolapple.swipebacklayout.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import cn.bingoogolapple.swipebacklayout.demo.R;

public class IndexView extends View {
    public static final String[] mData = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    private int mSelected = -1;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Delegate mDelegate;
    private TextView mTipTv;
    private int mNormalTextColor;
    private int mPressedTextColor;

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mNormalTextColor = context.getResources().getColor(R.color.colorPrimary);
        mPressedTextColor = context.getResources().getColor(R.color.colorPrimaryDark);

        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(sp2px(14));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int singleHeight = getHeight() / mData.length;

        for (int i = 0; i < mData.length; i++) {
            mPaint.setColor(mNormalTextColor);
            mPaint.setTypeface(Typeface.DEFAULT);
            if (i == mSelected) {
                mPaint.setColor(mPressedTextColor);
                mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            }
            float xPos = width / 2 - mPaint.measureText(mData[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(mData[i], xPos, yPos, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int newSelected = (int) (event.getY() / getHeight() * mData.length);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mSelected = -1;
                invalidate();
                if (mTipTv != null) {
                    mTipTv.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                if (mSelected != newSelected) {
                    if (newSelected >= 0 && newSelected < mData.length) {
                        if (mDelegate != null) {
                            mDelegate.onIndexViewSelectedChanged(this, mData[newSelected]);
                        }
                        if (mTipTv != null) {
                            mTipTv.setText(mData[newSelected]);
                            mTipTv.setVisibility(View.VISIBLE);
                        }
                        mSelected = newSelected;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public float sp2px(float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResources().getDisplayMetrics());
    }

    public void setDelegate(Delegate delegate) {
        mDelegate = delegate;
    }

    public void setTipTv(TextView tipTv) {
        mTipTv = tipTv;
    }

    public interface Delegate {
        void onIndexViewSelectedChanged(IndexView indexView, String text);
    }

}