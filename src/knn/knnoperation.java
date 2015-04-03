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
		HashMap<String, Double> matrix = new HashMap<String, Double>();
		ArrayList<String> attributelist = trainData.getAttributeList();
		matrix.put("Type",1.0);
		matrix.put("LifeStyle",0.05);
		matrix.put("Vacation",8.0);
		matrix.put("eCredit",25.0);
		matrix.put("salary",10.0);
		matrix.put("property",2.0);
		knn.normalization(trainData, minmaxmap); // do normalization

		System.out.println("accuracy" + accuracy(trainData, 10, knn, matrix));

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
			HashMap<String, Double> matrix) {
		dataSet.setData(shuffle(dataSet.getData()));
		ArrayList<Data> stroage = dataSet.getData();

		int totalcorrect = 0;

		int num = stroage.size() / k;
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
				test.add(train.remove(j));

			}
			DataSet tmp = new DataSet();
			tmp = dataSet;
			tmp.setData(train);

			for (int x = 0; x < test.size(); x++) {
				if (test.get(x).getData(objective) == map.get(knn.knn(tmp,
						test.get(x), matrix))) {
					totalcorrect++;
					correct++;
				}
				total++;
			}
			dataSet.setData(stroage);
			System.out.println("round " + i + "     accuracy is   " + correct
					/ num);
		}
		System.out.println("total" + total);
		System.out.println("correct" + totalcorrect);

		return totalcorrect / total;
	}
}
