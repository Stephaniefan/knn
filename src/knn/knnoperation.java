package knn;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
/*ebiz Task 11
 * Author: Wei Dai Ningxin Fan
 * class to do major operation
 */
public class knnoperation {
	static DecimalFormat df = new DecimalFormat("0.00");

	public static void main(String[] args) {

		DataSet testData = new DataSet();
		testData.readDataFromFile("testProdSelection.arff");

		DataSet trainData = new DataSet();
		trainData.readDataFromFile("trainProdSelection.arff");

		KNN knn = new KNN();
		HashMap<String, double[]> minmaxmap = knn.getMinMax(trainData);// get
																		// min
																		// and
																		// max
		HashMap<String, double[][]> matrixMap = new HashMap<String, double[][]>();
		double[][] type = { { 1.0, 0.0, 0.0, 0.0, 0.0 },
				{ 0.0, 1.0, 0.0, 0.0, 0.0 }, { 0.0, 0.0, 1.0, 0.0, 0.0 },
				{ 0.0, 0.0, 0.0, 1.0, 0.0 }, { 0.0, 0.0, 0.0, 0.0, 1.0 } };
		matrixMap.put("Type", type);
		double[][] lifestyle = { { 1.0, 0.0, 0.0, 0.0 },
				{ 0.0, 1.0, 0.0, 0.0 }, { 0.0, 0.0, 1.0, 0.0 },
				{ 0.0, 0.0, 0.0, 1.0 } };
		matrixMap.put("LifeStyle", lifestyle);

		HashMap<String, Double> matrix = new HashMap<String, Double>();
		matrix.put("Type", 1.0);
		matrix.put("LifeStyle", 0.05);
		matrix.put("Vacation", 8.0);
		matrix.put("eCredit", 30.0);
		matrix.put("salary", 20.0);
		matrix.put("property", 1.0);

		knn.normalization(trainData, minmaxmap); // do normalization
		knn.normalization(testData, minmaxmap);
<<<<<<< Updated upstream
		for (Data d : testData.getData()) {
			// System.out.println(testData.getData().size());

			String label = null;
			for (String attribute : testData.getAttributeList()) {
				label = knn.knn(trainData, d, matrix, 3, matrixMap);
			}
			System.out.print(d);
			System.out.println(label);
		}

		// System.out
		// .println("accuracy"
		// + df.format(accuracy(trainData, 10, knn, matrix,
		// matrixMap) * 100) + "%");
=======
		
//		for (Data d : testData.getData()) {
//			// System.out.println(testData.getData().size());
//
//		System.out.println("accuracy"
//				+ accuracy(trainData, 10, knn, matrix, matrixMap));
//
//		//
//		// knn.normalization(testData, minmaxmap);
//		
//		// for (Data d : testData.getData()) {
//		// // for (String attribute : testData.getAttributeList()) {
//		// // System.out.println("d" + d);
//		// String label = knn.knn(trainData, d, matrix, 3, matrixMap);
//		// //System.out.print(d + "     ");
//		// System.out.println(label);
//		// // }
//		// }
//			String label = null;
//			for (String attribute : testData.getAttributeList()) {
//				label = knn.knn(trainData, d, matrix, 3, matrixMap);
//			}
//
//			System.out.print(d);
//			System.out.println(label);
//		}

		 System.out
		 .println("accuracy"
		 + df.format(accuracy(trainData, 10, knn, matrix,
		 matrixMap) * 100) + "%");
>>>>>>> Stashed changes

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

	@SuppressWarnings("unchecked")
	public static double accuracy(DataSet dataSet, int k, KNN knn,
			HashMap<String, Double> weight,
			HashMap<String, double[][]> matrixMap) {
		dataSet.setData(shuffle(dataSet.getData()));
		ArrayList<Data> stroage = dataSet.getData();

		int totalcorrect = 0;
		double accuracy = 0.00;
		double total = 0, correct = 0;

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
		// int totalnum = 0;

		String objective = dataSet.getObjective();
		HashMap<String, Double> map = dataSet.getAttributeMap()
				.get(dataSet.getObjective()).getValueSet();
		for (int i = 0; i < k; i++) {
			// System.out.println(stroage.size() / k);
			correct = 0;
			ArrayList<Data> test = new ArrayList<Data>();
			ArrayList<Data> train = new ArrayList<Data>();

			train = (ArrayList<Data>) dataSet.getData().clone();
<<<<<<< Updated upstream
			if (i == k - 1) {
				for (int j = stroage.size() - 1; j >= (stroage.size() / k) * i; j--) {
					test.add(train.remove(j));

					// totalnum++;
				}
				DataSet tmp = new DataSet();
				tmp = dataSet;
				tmp.setData(train);
				for (int x = 0; x < test.size(); x++) {
					if (test.get(x).getData(objective) == map.get(knn.knn(tmp,
							test.get(x), weight, 3, matrixMap))) {
						totalcorrect++;
						correct++;
					}
					total++;
				}
				accuracy += correct / test.size();
			} else {
				for (int j = (stroage.size() / k) * (i + 1) - 1; j >= (stroage
						.size() / k) * i; j--) {
					test.add(train.remove(j));

					// totalnum++;

				}
				DataSet tmp = new DataSet();
				tmp = dataSet;
				tmp.setData(train);

				for (int x = 0; x < test.size(); x++) {
					if (test.get(x).getData(objective) == map.get(knn.knn(tmp,
							test.get(x), weight, 3, matrixMap))) {
						totalcorrect++;
						correct++;
					}
					total++;
=======
			if (i != k - 1) {
				for (int j = (stroage.size() / k) * (i + 1)-1; j >= (stroage
						.size() / k) * i; j--) {
					test.add(train.remove(j));
					//totalnum++;
				}
			} else {
				for (int j = stroage.size() - 1; j >= (stroage.size() / k) * i; j--) {
					test.add(train.remove(j));
					//totalnum++;
				}
			}
			DataSet tmp = new DataSet();
			tmp = dataSet;
			tmp.setData(train);

			for (int x = 0; x < test.size(); x++) {
				if (test.get(x).getData(objective) == map.get(knn.knn(tmp,
						test.get(x), weight, 3, matrixMap))) {
					totalcorrect++;
					correct++;
>>>>>>> Stashed changes
				}
				dataSet.setData(stroage);
				accuracy += correct / test.size();
			}
<<<<<<< Updated upstream
=======
			dataSet.setData(stroage);
			accuracy += correct / test.size();
>>>>>>> Stashed changes
			System.out.println("round " + i + "     accuracy is   "
					+ df.format(correct / test.size() * 100) + "%");
		}
		System.out.println("total" + total);
		System.out.println("correct" + totalcorrect);
<<<<<<< Updated upstream
=======
		//System.out.println("totalnum" + totalnum);
>>>>>>> Stashed changes
		return accuracy / k;
	}
}
