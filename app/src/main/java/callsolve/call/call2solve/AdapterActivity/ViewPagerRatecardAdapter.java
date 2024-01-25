package callsolve.call.call2solve.AdapterActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.List;

import callsolve.call.call2solve.R;
import callsolve.call.call2solve.SetgetActivity.Sliderproduct;

public class ViewPagerRatecardAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
   // private Integer[] images = {R.mipmap.acrepair2, R.mipmap.acrepair3,R.mipmap.acrepair2,R.mipmap.acrepair3, R.mipmap.acrepair2,R.mipmap.acrepair3};
     private List<Sliderproduct> sliderImg;
    public ViewPagerRatecardAdapter(List<Sliderproduct> sliderImg, Context context) {
        this.sliderImg =sliderImg;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sliderImg.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.racardadpter, null);
        Sliderproduct utils = sliderImg.get(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
       // imageView.setImageResource(images[position]);
        Glide.with(context)
                .load(utils.getSliderImageUrl())
                .into(imageView);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}