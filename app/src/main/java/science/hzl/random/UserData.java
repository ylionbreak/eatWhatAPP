package science.hzl.random;

import cn.bmob.v3.BmobObject;

/**
 * Created by YLion on 2015/4/18.
 */
public class UserData extends BmobObject {
	public String hisUseWay;

	public UserData(String hisUseWay) {
		this.hisUseWay = hisUseWay;
	}

	public String getHisUseWay() {
		return hisUseWay;
	}

	public void setHisUseWay(String hisUseWay) {
		this.hisUseWay = hisUseWay;
	}
}
