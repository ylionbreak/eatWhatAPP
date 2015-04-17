package science.hzl.random;

import android.util.Log;

import java.util.List;
import java.util.Random;

public class RandomChoose {

	public String getRandomChooseResult(List<String> strings) {
		Random random = new Random();
		return strings.get( Math.abs( random.nextInt() % strings.size() ) );
	}


}
