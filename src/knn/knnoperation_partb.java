package knn;

import java.util.ArrayList;
import java.util.HashMap;

public class knnoperation_partb {

	public static void main(String[] args) {

		// DataSet testData = new DataSet();
		// testData.readDataFromFile("testProdIntro.real.arff");

		DataSet trainData = new DataSet();
		trainData.readDataFromFile("trainProdIntro.binary.arff");

		KNN knn = new KNN();
		HashMap<String, double[]> minmaxmap = knn.getMinMax(trainData);// get
																		// min
																		// and
																		// max
		HashMap<String, double[][]> matrixMap = new HashMap<String, double[][]>();
		double[][] Service_type = { { 1.0, 0.0, 0.1, 0.3, 0.2 },
				{ 0.0, 1.0, 0.0, 0.0, 0.0 }, { 0.1, 0.0, 1.0, 0.2, 0.2 },
				{ 0.3, 0.0, 0.2, 1.0, 0.1 }, { 0.2, 0.0, 0.2, 0.1, 1.0 } };
		matrixMap.put("Service_type", Service_type);
		double[][] Customer = { { 1.0, 0.2, 0.1, 0.2, 0.0 },
				{ 0.2, 1.0, 0.2, 0.1, 0.0 }, { 0.1, 0.2, 1.0, 0.1, 0.0 },
				{ 0.2, 0.1, 0.1, 1.0, 0.0 }, { 0.0, 0.0, 0.0, 0.0, 1.0 } };
		matrixMap.put("Customer", Customer);
		double[][] Size = { { 1.0, 0.1, 0.0 }, { 0.1, 1.0, 0.1 },
				{ 0.0, 0.1, 1.0 } };
		matrixMap.put("Size", Size);
		double[][] Promotion = { { 1.0, 0.8, 0.0, 0.0 },
				{ 0.8, 1.0, 0.1, 0.5 }, { 0.0, 0.1, 1.0, 0.4 },
				{ 0.0, 0.5, 0.4, 1.0 } };
		matrixMap.put("Promotion", Promotion);

		HashMap<String, Double> matrix = new HashMap<String, Double>();
		matrix.put("Service_type", 1.0);
		matrix.put("Customer", 1.0);
		matrix.put("Monthly_fee", 1.0);
		matrix.put("Advertisement_budget", 1.0);
		matrix.put("Size", 1.0);
		matrix.put("Promotion", 1.0);
		matrix.put("Interest_rate", 1.0);
		matrix.put("Period", 1.0);

		knn.normalization(trainData, minmaxmap); // do normalization

		System.out.println("accuracy"
				+ accuracy(trainData, 10, knn, matrix, matrixMap));

		//
		// knn.normalization(testData, minmaxmap);
		// // ArrayList<String> attributeList = ;
		// for (Data d : testData.getData()) {
		// // for (String attribute : testData.getAttributeList()) {
		// // System.out.println("d" + d);
		// String label = knn.knn(trainData, d);
		// System.out.print(d + "     ");
		// System.out.println(label);
		// // }
		// }
	}

	public static ArrayList<Data> shuffle(ArrayList<Data> datalist) {
		ArrayList<Data> random = new ArrayList<Data>();
		for (int i = 0; i < datalist.size(); i++) {
			random.add(datalist.get(i));
		}
		ArrayList<Data> temp = new ArrayList<Data>();
		while (random.size() > 0) {
			int parameter = (int) (Math.random() * random.size());
			temp.add(random.remove(parameter));
		}
		for (int i = 0; i < temp.size(); i++) {
			random.add(temp.get(i));
		}
		return random;
	}

	public static double accuracy(DataSet dataSet, int k, KNN knn,
			HashMap<String, Double> weight,
			HashMap<String, double[][]> matrixMap) {
		dataSet.setData(shuffle(dataSet.getData()));
		ArrayList<Data> stroage = dataSet.getData();

		int totalcorrect = 0;
		double accuracy = 0.00;
		double total = 0, correct = 0;
		String objective = dataSet.getObjective();
		HashMap<String, Double> map = dataSet.getAttributeMap()
				.get(dataSet.getObjective()).getValueSet();
		System.out.println(dataSet.size());
		for (int i = 0; i < k; i++) {
			correct = 0;
			ArrayList<Data> test = new ArrayList<Data>();
			ArrayList<Data> train = new ArrayList<Data>();

			train = (ArrayList<Data>) dataSet.getData().clone();

			for (int j = (stroage.size() / k) * (i + 1); j >= (stroage.size() / k)
					* i; j--) {
				if (train.size() > j)
					test.add(train.remove(j));

			}
			DataSet tmp = new DataSet();
			tmp = dataSet;
			tmp.setData(train);

			for (int x = 0; x < test.size(); x++) {
				if (!dataSet.getAttributeMap().get(objective).isRealNum()) {
					if (test.get(x).getData(objective) == map.get(knn.knn(tmp,
							test.get(x), weight, 5, matrixMap))) {
						totalcorrect++;
						correct++;
					}
				}else{
					System.out.print("test.get(x).getData(objective).toString()"+test.get(x).getData(objective).toString());
					String a = knn.knn(tmp,test.get(x), weight, 5, matrixMap);
					System.out.print("knn.knn(tmp,test.get(x), weight, 5, matrixMap)"+ a);
					
					if (test.get(x).getData(objective).toString().equals(a)) {
						totalcorrect++;
						correct++;
					}
				}
				total++;
			}
			dataSet.setData(stroage);
			accuracy += correct / test.size();
			System.out.println("round " + i + "     accuracy is   " + correct
					/ test.size());
		}
		System.out.println("total" + total);
		System.out.println("correct" + totalcorrect);

		return accuracy / k;
	}
}
