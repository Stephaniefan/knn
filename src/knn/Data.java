package knn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/*ebiz Task 11
 * Author: Wei Dai Ningxin Fan
 * Data class contains attributes for an entry/record 
 * It stores data in a HashMap:
  */

public class Data {
	// key: attribute name
	// value: attribute value, stored in string
	private HashMap<String, Double> data;
	private double similarity;
	private HashMap<String, Attribute> attribute;

	public void setData(String attribute, Double newdata){
		data.put(attribute, newdata);
	}
	
	public void setLabel(Double label, String key) {
		data.put(key, label);
	}

	public HashMap<String, Double> getData() {
		return data;
	}

	public Double getData(String key) {
		return data.get(key);
	}

	public Double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}

	public HashMap<String, Attribute> getAttributes() {
		return attribute;
	}

	public Data(ArrayList<String> attributeList, String content,
			HashMap<String, Attribute> attributeMap) {
		attribute = attributeMap;
		data = new HashMap<String, Double>();
		String[] values = content.split(",");

		for (int i = 0; i < attributeList.size(); ++i) {
			if (attributeMap.get(attributeList.get(i)).isRealNum()) {
				data.put(attributeList.get(i), Double.parseDouble(values[i]));
			} else {

				HashMap<String, Double> valueMap = attributeMap.get(
						attributeList.get(i)).getValueSet();

				data.put(attributeList.get(i), valueMap.get(values[i]));

			}
		}
	}

	@Override
	public String toString() {
		return "Data [data=" + data + "]";
	}

	public static void main(String[] args) {
		@SuppressWarnings("serial")
		ArrayList<String> attributeList = new ArrayList<String>() {
			{
				add("A");
				add("B");
				add("C");
			}
		};
		String content = "Fund,Student,0.64";
		HashMap<String, Attribute> attributeMap = new HashMap<String, Attribute>();
		Attribute at = new Attribute("A", "adsa");
		Attribute at1 = new Attribute("B", "asdasd");
		Attribute at2 = new Attribute("C", "real");
		attributeMap.put("A", at);
		attributeMap.put("B", at1);
		attributeMap.put("C", at2);

		for (Map.Entry<String, Attribute> entry : attributeMap.entrySet()) {
			String key = entry.getKey().toString();
			Attribute value = entry.getValue();
			System.out.println(key);
			System.out.println(value);
		}

		Data d = new Data(attributeList, content, attributeMap);
		System.out.println(d);
	}

}
