package science.hzl.random;

import cn.bmob.v3.BmobObject;

/**
 * Created by YLion on 2015/4/12.
 */
public class Place extends BmobObject {
	public int id;
	public String name;

	public Place() {
	}

	public Place(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
