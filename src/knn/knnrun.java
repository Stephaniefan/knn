package knn;

import Data;
import DataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class knnrun {
	public void read(List<KNNNode> datas, String path) {

	}

	public static void main(String[] args) {
		DataSet trainData = new DataSet();
		trainData.readDataFromFile("trainProdSelection.arff");
		List<KNNNode> traindata = new ArrayList<KNNNode>();
		traindata = datatoknn(trainData);
		List<KNNNode> random = new ArrayList<KNNNode>();
		for (int i = 0; i < traindata.size(); i++) {
			random.add(traindata.get(i));
		}
		List<KNNNode> temp = new ArrayList<KNNNode>();
		while (random.size() > 0) {
			int parameter = (int) (Math.random() * random.size());
			temp.add(random.remove(parameter));
		}
		for (int i = 0; i < temp.size(); i++) {
			random.add(temp.get(i));
		}
		List<KNNNode> train = new ArrayList<KNNNode>();
		proceed(random);
		System.out.println(random.size()+"is");
		KNN knn = new KNN();
		double num = 0.0;
		double accuracy = 0.0;
		double average = 0.0;
		double total = 0.0;
		for (int i = 0; i < 10; i++) {
			List<KNNNode> testdata = new ArrayList<KNNNode>();
			for (int j = 0; j < 18; j++) {
				KNNNode tmp = random.remove(0);
				testdata.add(tmp);
			}
			train = random;
			// initialize a new knn class
			for (int k = 0; k < testdata.size(); k++) { // run by test nodes
				KNNNode testnode = testdata.get(k);
				String result = knn.knn(train, testnode);
				if (result.equals(testnode.getLabel())) {
					num++;
				}
				accuracy = Math.round(num * 100 / 17.00);
				total += accuracy;
			}
			traindata.addAll(testdata);
			System.out.println(num + "is" + accuracy + "%");
			System.out.println();
			num = 0.0;
		}
		average = total / 100;
		System.out.println(average + "%");
	}
	
	
	private static List<KNNNode> proceed(List<KNNNode> train) {
		for (int i = 0; i < train.size(); i++) {
			KNNNode temp = train.get(i);
			if (temp.getVacation() > 60 || temp.getEcredit() > 3000
					|| temp.getEcredit() < 5 || temp.getSalary() < 10
					|| temp.getSalary() > 40 || temp.getProperty() > 20) {
				train.remove(temp);
			}
		}
		return train;
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

    public static double accuracy (DataSet dataSet, int k) {
    	double total = 0, correct = 0;
    	for(int i = 0; i < k; i++){
    		ArrayList<Data> tmp = new ArrayList<Data>();
    		for(int j = tmp.size()/k * i; j < tmp.size()/k * (i+1); j++){
    			Data data = dataSet.getData();
    		}
    		
    	}
    		for (Data data : dataSet.getData()) {
        	if (validation(root, data, dataSet.getObjective())){
        		correct++;
        	}
        	total++;
        }
        return correct / total;
    }
	
    public static boolean validation (List<KNNNode> data, KNNNode target) {
    	String tmp = target.getLabel();
    		for (KN key : root.children.keySet()) {
    			if (key.equals("left")) {
    				if (Double.parseDouble(data.getData(root.attribute)) <=Double.parseDouble(root.children.get(key).value)) {
    					root = root.children.get(key);
    					break;
    				}
    			} else if (key.equals("right")) {
    				if (Double.parseDouble(data.getData(root.attribute)) > Double.parseDouble(root.children.get(key).value)) {
    					root = root.children.get(key);
    					break;
    				}
    			} else {
    				if (key.equals(data.getData(root.attribute))) {
    					root = root.children.get(key);
    					break;
    				}
    			}
    		}
    	return root == null ? false : root.decision.equals(data.getData(target));
    }
    
}
