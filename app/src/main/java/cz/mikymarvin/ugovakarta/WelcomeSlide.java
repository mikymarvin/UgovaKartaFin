package cz.mikymarvin.ugovakarta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.text.HtmlCompat;

import org.w3c.dom.Text;

public class WelcomeSlide extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;

    private Button mNextBtn;
    private Button mBackBtn;
    private Button mBegin;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_slide);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        mNextBtn = (Button) findViewById(R.id.nextBtn);
        mBackBtn = (Button) findViewById(R.id.prevBtn);
        mBegin = (Button) findViewById(R.id.begin);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSlideViewPager.setCurrentItem(mCurrentPage + 1);

            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        mBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToMain = new Intent(WelcomeSlide.this, Home.class);
                startActivity(intToMain);
            }
        });


    }

    public  void addDotsIndicator(int position){

        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for(int i = 0; i < mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(HtmlCompat.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparent));


            mDotLayout.addView(mDots[i]);

        }

        if (mDots.length > 0) {

            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));

        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);
            mCurrentPage = i;

            if (i == 0) {

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);
                mBegin.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Další");
                mBackBtn.setText("");

            } else if (i == mDots.length - 1 ) {

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("");
                mBackBtn.setText("Zpět");
                mBegin.setVisibility(View.VISIBLE);

            } else {

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mBegin.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Další");
                mBackBtn.setText("Zpět");

            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
