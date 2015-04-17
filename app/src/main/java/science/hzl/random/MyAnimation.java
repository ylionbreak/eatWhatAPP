package science.hzl.random;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by YLion on 2015/4/16.
 */
public class MyAnimation {

	public MyAnimation() {
	}

	public TranslateAnimation getToLeftAnimation(){
		TranslateAnimation animation = new TranslateAnimation(0, -800, 0, 0);
		animation.setDuration(800);
		animation.setInterpolator(new AccelerateInterpolator());
		return animation;
	}

	public TranslateAnimation getToRightAnimation(){
		TranslateAnimation animation = new TranslateAnimation(-800, 0, 0, 0);
		animation.setDuration(800);
		animation.setInterpolator(new AccelerateInterpolator());
		return animation;
	}

	public AnimationSet getBeSmallAndDisappear(){
		AlphaAnimation alphaAnimation=new AlphaAnimation(1,0);
		alphaAnimation.setDuration(800);
		ScaleAnimation scaleAnimation=new ScaleAnimation(1,0.618f,1,0.618f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		scaleAnimation.setDuration(800);
		AnimationSet animationSet=new AnimationSet(true);
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(scaleAnimation);
		return animationSet;
	}

	public AnimationSet getBeBigAndAppear(){
		AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
		alphaAnimation.setDuration(800);
		ScaleAnimation scaleAnimation = new ScaleAnimation(0.618f,1,0.618f,1, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		scaleAnimation.setDuration(800);
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(scaleAnimation);
		return animationSet;
	}
	public TranslateAnimation getButtonUp() {
		TranslateAnimation twoButtonAnimation = new TranslateAnimation(0, 0, 400, 0);
		twoButtonAnimation.setDuration(800);//设置动画持续时间
		twoButtonAnimation.setRepeatCount(0);//设置重复次数
		twoButtonAnimation.setInterpolator(new AccelerateInterpolator());
		return twoButtonAnimation;
	}
	public TranslateAnimation getButtonDown() {
		TranslateAnimation twoButtonAnimation = new TranslateAnimation(0, 0, 0, 400);
		twoButtonAnimation.setDuration(800);//设置动画持续时间
		twoButtonAnimation.setRepeatCount(0);//设置重复次数
		twoButtonAnimation.setInterpolator(new AccelerateInterpolator());
		return twoButtonAnimation;
	}
}
