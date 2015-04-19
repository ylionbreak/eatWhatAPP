package science.hzl.random;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import java.util.Timer;

/**
 * Created by YLion on 2015/4/16.
 */
public class MyAnimation {

	public MyAnimation() {
	}
	public AnimationSet getUpAndBig(){
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0,800, 0);
		translateAnimation.setDuration(800);
		translateAnimation.setInterpolator(new AccelerateInterpolator());
		ScaleAnimation scaleAnimation=new ScaleAnimation(0.618f,1,0.618f,1, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		scaleAnimation.setDuration(800);
		AnimationSet animationSet=new AnimationSet(true);
		animationSet.addAnimation(translateAnimation);
		animationSet.addAnimation(scaleAnimation);
		return animationSet;
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

	public AnimationSet getBeSmallAndDisappearAndDown(){
		AlphaAnimation alphaAnimation=new AlphaAnimation(1,0);
		alphaAnimation.setDuration(800);
		ScaleAnimation scaleAnimation=new ScaleAnimation(1,0.618f,1,0.618f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		scaleAnimation.setDuration(800);
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 800);
		translateAnimation.setDuration(800);//设置动画持续时间

		AnimationSet animationSet=new AnimationSet(true);
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(translateAnimation);
		return animationSet;
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
		TranslateAnimation twoButtonAnimation = new TranslateAnimation(0, 0, 800, 0);
		twoButtonAnimation.setDuration(800);//设置动画持续时间
		twoButtonAnimation.setRepeatCount(0);//设置重复次数
		twoButtonAnimation.setInterpolator(new AccelerateInterpolator());
		return twoButtonAnimation;
	}

	public TranslateAnimation getButtonDown() {
		TranslateAnimation twoButtonAnimation = new TranslateAnimation(0, 0, 0, 800);
		twoButtonAnimation.setDuration(800);//设置动画持续时间
		twoButtonAnimation.setRepeatCount(0);//设置重复次数
		twoButtonAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		return twoButtonAnimation;
	}

	public AnimationSet getRandomEffect(final Timer timer,final Button button){
		AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
		alphaAnimation.setDuration(450);

		ScaleAnimation scaleAnimation = new ScaleAnimation(0.618f,1,0.618f,1, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		scaleAnimation.setDuration(450);

		TranslateAnimation translateAnimation1 = new TranslateAnimation( 0, 0, 0, -50);
		translateAnimation1.setDuration(10);

		TranslateAnimation translateAnimation2 = new TranslateAnimation( 0, 0, -50, 50);
		translateAnimation2.setDuration(20);
		translateAnimation2.setRepeatCount(20);
		translateAnimation2.setRepeatMode(Animation.REVERSE);
		translateAnimation2.setStartOffset(10);

		TranslateAnimation translateAnimation3 = new TranslateAnimation( 0, 0, 50, 0);
		translateAnimation3.setDuration(10);
		translateAnimation3.setStartOffset(410);

		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(translateAnimation1);
		animationSet.addAnimation(translateAnimation2);
		animationSet.addAnimation(translateAnimation3);
		animationSet.addAnimation(scaleAnimation);
		animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
		animationSet.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				button.setEnabled(true);
				timer.cancel();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		return animationSet;
	}

//	public AnimationSet getUpAndBig(){
//		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 400);
//		translateAnimation.setDuration(800);//设置动画持续时间
//		translateAnimation.setRepeatCount(0);//设置重复次数
//		translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//		ScaleAnimation scaleAnimation=new ScaleAnimation(1,0.618f,1,0.618f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//		scaleAnimation.setDuration(800);
//		AnimationSet animationSet=new AnimationSet(true);
//		animationSet.addAnimation(translateAnimation);
//		animationSet.addAnimation(scaleAnimation);
//		return animationSet;
//	}
}
