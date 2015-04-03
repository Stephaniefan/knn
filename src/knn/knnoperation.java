package knn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class knnoperation {
	public void read(List<KNNNode> datas, String path) {

	}

	public static void main(String[] args) {
		DataSet testData = new DataSet();
		testData.readDataFromFile("testProdSelection.arff");

		DataSet trainData = new DataSet();
		trainData.readDataFromFile("trainProdSelection.arff");
		KNN knn = new KNN();
		HashMap<String, double[]> minmaxmap = knn.getMinMax(trainData);
		knn.normalization(trainData, minmaxmap);
		
		System.out.println("accuracy" + accuracy(trainData, 10));
//		
//		knn.normalization(testData, minmaxmap);
//		// ArrayList<String> attributeList = ;
//		for (Data d : testData.getData()) {
//			// for (String attribute : testData.getAttributeList()) {
//			// System.out.println("d" + d);
//			String label = knn.knn(trainData, d);
//			System.out.print(d + "     ");
//			System.out.println(label);
//			// }
//		}
	}

	public static double accuracy(DataSet dataSet, int k) {
		double total = 0, correct = 0;
		String objective = dataSet.getObjective();
		HashMap<String, Double> map = dataSet.getAttributeMap()
				.get(dataSet.getObjective()).getValueSet();
		for (int i = 0; i < k; i++) {
			ArrayList<Data> test = new ArrayList<Data>();
			ArrayList<Data> train = dataSet.getData();
			for (int j = (dataSet.size() / k) * i; j < (dataSet.size() / k)
					* (i + 1); j++) {
				test.add(train.remove(j));
			}
			DataSet tmp = dataSet;
			tmp.setData(train);
			for (int x = 0; x < test.size(); x++) {
				// for (String attribute : testData.getAttributeList()) {
				// System.out.println("d" + d);
				KNN knn = new KNN();
				if (test.get(x).getData(objective) == map.get(knn.knn(tmp,
						test.get(x)))) {
					correct++;
					total++;
				} else {
					total++;
				}
				// }
			}
		}
		return correct / total;
	}

}
