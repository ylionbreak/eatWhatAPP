package science.hzl.random;

/**
 * Created by YLion on 2015/4/12.
 */
public class Restaurant {
	public int _id;
	public String name;
	public int belongCircle;
	public String isCheck ="1";
	public Restaurant() {
	}

	public Restaurant(int _id, String name, int belongCircle) {
		this._id = _id;
		this.name = name;
		this.belongCircle = belongCircle;
	}
}
