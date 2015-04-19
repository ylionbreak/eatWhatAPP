package science.hzl.random;

import cn.bmob.v3.BmobObject;

/**
 * Created by YLion on 2015/4/12.
 */
public class Circle extends BmobObject {
	public int id;
	public String name;
	public int belongPlace;

	public Circle() {
	}

	public Circle(int id, String name, int belongPlace) {
		this.id = id;
		this.name = name;
		this.belongPlace = belongPlace;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getBelongPlace() {
		return belongPlace;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBelongPlace(int belongPlace) {
		this.belongPlace = belongPlace;
	}
}
