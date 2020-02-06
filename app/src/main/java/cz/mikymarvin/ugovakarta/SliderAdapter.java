package cz.mikymarvin.ugovakarta;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter (Context context) {

        this.context = context;

    }

    public int[] slide_images = {

            R.drawable.ugokarta_slide,
            R.drawable.ugoblog_slide,
            R.drawable.logougo

    };

    public String[] slide_headings = {

            "VÍTEJ VE SVĚTĚ UGO KARTY!",
            "BLOG, KTERÝ MÁ ŠŤÁVU!",
            "U VŠECH FRESHŮ! "

    };



    public String[] slide_decs = {

            "S touhle mobilní aplikací už můžeš nechat peněženku doma. UGO karta je od teď součástí tvého telefonu a není nic jednoduššího, než se u obsluhy prokázat mobilní appkou, ne?",

            "V aplikaci nenajdeš pouze svoji kartu, ale také všechny novinky ze světa UGO. Co právě frčí, na jaký drink zaskočit nebo co se zrovna chystá. Těšit se můžeš také na zajímavé akce, které pro tebe UGO pravidelně připravuje.",

            "Ač je nám to moc líto, aplikaci nevlastní společnost UGO trade s.r.o. ani její společník Kofola ČeskoSlovensko a.s., aplikaci tvoří nadšenci do zdravé výživy a chutných freshů. A v případě potřeby, jsme tu pro tebe!"

    };



    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (ConstraintLayout) o;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);


        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_decs[position]);

        container.addView(view);

        return view;
    };

    @Override
    public void destroyItem(ViewGroup container, int position,Object object) {

        container.removeView((ConstraintLayout)object);
    }
}
