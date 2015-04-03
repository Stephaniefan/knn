package knn;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to hold attribute data
 * */
public class Attribute {
	public enum Type {
		REAL, CLASS
	}

	private String name; // Attribute name
	private Type type; // real number or several classes
	private HashMap<String, Double> valueMap; // for classes, set of possible
												// values

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public HashMap<String, Double> getValueSet() {
		return valueMap;
	}

	public Attribute(String name, String type) {
		this.name = name;
		if (type.equals("real")) {
			this.type = Type.REAL;
			valueMap = null;
		} else {
			this.type = Type.CLASS;
			valueMap = new HashMap<String, Double>();
			String[] typeset = type.substring(1, type.length() - 1).split(",");

			for (int i = 0; i < typeset.length; i++) {
				String s = typeset[i];
				valueMap.put(s, (double) i);
			}
		}
	}

	public boolean isRealNum() {
		return this.type == Type.REAL;
	}

	@Override
	public String toString() {
		return "Attribute [name=" + name + ", type=" + type + ", valueSet="
				+ valueMap + "]";
	}

	public static void main(String[] args) {
		Attribute a = new Attribute("Test",
				"{Student,Business,Other,Doctor,Professional}");
		HashMap<String, Double> map = a.getValueSet();
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			Double value = entry.getValue();
			System.out.println(key);
			System.out.println(value);
		}
	}
}
