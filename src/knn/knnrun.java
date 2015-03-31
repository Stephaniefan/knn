package knn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class knnrun {
	public void read(List<KNNNode> datas, String path) {

	}

	public static void main(String[] args) {
		DataSet trainData = new DataSet();
		trainData.readDataFromFile("trainProdSelection.arff"); 
		
        DataSet testData = new DataSet();
        testData.readDataFromFile("testProdSelection.arff");
		
		List<KNNNode> traindata = new ArrayList<KNNNode>();
		
		traindata = datatoknn(trainData);
		List<KNNNode> testdata = new ArrayList<KNNNode>();
		testdata = datatoknn(testData);

		KNN knn = new KNN();// initialize a new knn class
		for (int i = 0; i < testdata.size(); i++) { // run by test nodes
			KNNNode testnode = testdata.get(i);
			knn.knn(traindata, testnode);
		}
	}

	private static List<KNNNode> datatoknn(DataSet dataSet) {
		List<KNNNode> rs = new ArrayList<KNNNode>();
		for (Data d : dataSet.getData()) {
			
			
			HashMap<String, String> map = d.getData();
			
			KNNNode tmpnode = new KNNNode(null, null, 0, 0, 0, 0, null);
			
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue();
				if (key.equals("Type")) {
					tmpnode.setType(value);
				} else if (key.equals("LifeStyle")) {
					tmpnode.setLifeStyle(value);
				} else if (key.equals("Vacation")) {
					tmpnode.setVacation(Integer.parseInt(value));
				} else if (key.equals("eCredit")) {
					tmpnode.setEcredit(Integer.parseInt(value));
				} else if (key.equals("salary")) {
					tmpnode.setSalary(Double.parseDouble(value));
				} else if (key.equals("property")) {
					tmpnode.setProperty(Double.parseDouble(value));
				} else if (key.equals("label")) {
					tmpnode.setLabel(value);
				}
			}
			rs.add(tmpnode);
		}
		return rs;
	}

}
