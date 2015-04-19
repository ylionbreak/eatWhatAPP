package science.hzl.random;

import cn.bmob.v3.BmobObject;

/**
 * Created by YLion on 2015/4/12.
 */
public class Restaurant extends BmobObject{
	public int id;
	public String name;
	public int belongCircle;
	public String isCheck ="1";
	public Restaurant() {
	}

	public Restaurant(int id, String name, int belongCircle) {
		this.id = id;
		this.name = name;
		this.belongCircle = belongCircle;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getBelongCircle() {
		return belongCircle;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBelongCircle(int belongCircle) {
		this.belongCircle = belongCircle;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
}
