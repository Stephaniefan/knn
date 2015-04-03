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
		HashMap<String, double[]> minmaxmap = knn.getMinMax(trainData);// get
																		// min
																		// and
																		// max
		knn.normalization(trainData, minmaxmap);// do normalization
		// ArrayList<Data> train = trainData.getData();
		// for(int i = 0; i < train.size(); i++){
		// System.out.println("label    "+ i + "     " +
		// train.get(i).getData(trainData.getObjective()));
		// }

		System.out.println("accuracy" + accuracy(trainData, 10, knn));

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

	public static double accuracy(DataSet dataSet, int k, KNN knn, HashMap<String, Double> weight) {
		dataSet.setData(shuffle(dataSet.getData()));
		ArrayList<Data> stroage = dataSet.getData();
		
		
		
		
		int totalcorrect = 0;
		
		 int num = stroage.size()/k;
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

			// train.remove(1);
			// System.out.println("dataSet.size()1" + dataSet.size());
			// System.out.println("train.size()" + train.size());

			for (int j = (stroage.size() / k) * (i + 1); j >= (stroage.size() / k)
					* i; j--) {
				// System.out.println("train.get(j)" + train.get(j));
				test.add(train.remove(j));

			}
			// System.out.println("test" + test.size());
			DataSet tmp = new DataSet();
			tmp = dataSet;
			tmp.setData(train);

			// System.out.println("train.size()" + train.size());
			// System.out.println("dataSet.size()3" + dataSet.size());
			// System.out.println("stroage.size()" + stroage.size());
			for (int x = 0; x < test.size(); x++) {
				// for (String attribute : testData.getAttributeList()) {
				// System.out.println("d" + d);
				//KNN knn = new KNN();
				// //double tmp1 = );
				if (test.get(x).getData(objective) == map.get(knn.knn(tmp,
						test.get(x), weight))) {
					totalcorrect++;
					correct++;
				}
				total++;
				// num++;
				// System.out.println("Data " + "    " + test.get(x));
				// System.out.println("round " + "    " + tmp1);
				// }
			}
			dataSet.setData(stroage);
			 System.out.println("round " + i + "     accuracy is   " +
			 correct/num );
		}
		System.out.println("total" + total);
		System.out.println("CORRECT" + totalcorrect);

		return totalcorrect / total;
	}
}
