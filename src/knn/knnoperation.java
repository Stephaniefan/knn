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
		// ArrayList<String> attributeList = ;
		for (Data d : testData.getData()) {
			// for (String attribute : testData.getAttributeList()) {
			//System.out.println("d" + d);
			String label = knn.knn(trainData, d);
			System.out.print(d + "     ");
			System.out.println(label);
			// }
		}
	}
	
	
}
