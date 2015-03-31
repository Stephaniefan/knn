package knn;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class KNN {

	// calculation of similarity between two nodes
	public double calSimilarity(KNNNode d1, KNNNode d2) {
		double similarity = Math.pow(d1.getType() - d2.getType(), 2)
				+ Math.pow(d1.getLifeStyle() - d2.getLifeStyle(), 2)
				+ Math.pow(d1.getVacation() - d2.getVacation(), 2)
				+ Math.pow(d1.getEcredit() - d2.getEcredit(), 2)
				+ Math.pow(d1.getSalary() - d2.getSalary(), 2)
				+ Math.pow(d1.getProperty() - d2.getProperty(), 2);
		return 1 / similarity;
	}

	private Comparator<KNNNode> comparator = new Comparator<KNNNode>() {
		public int compare(KNNNode node1, KNNNode node2) {
			if (node1.getSimilarity() >= node2.getSimilarity())
				return 1;
			else
				return -1;
		}
	};

	// body of knn
	public String knn(List<KNNNode> traindata, KNNNode testnode) {
		String category = null;
		int k = 3; // set k;
		PriorityQueue<KNNNode> queue = new PriorityQueue<KNNNode>(k, comparator);
		for (int i = 0; i < k; i++) { // add first k nodes from traindata to
										// queue;
			KNNNode tmpnode = traindata.get(i);
			tmpnode.setSimilarity(calSimilarity(testnode, tmpnode));
			queue.add(tmpnode);
		}
		for (int i = 0; i < traindata.size(); i++) {// modify queue to k most
													// similar nodes
			KNNNode tmp = traindata.get(i);
			double similarity = calSimilarity(testnode, tmp);
			KNNNode queuetop = queue.peek();
			if (queuetop.getSimilarity() < similarity) {
				tmp.setSimilarity(similarity);
				queue.poll();
				queue.add(tmp);
			}
		}
		category = getCategory(queue); // category calculation method
		System.out.print(testnode.getType() + "    ");
		System.out.print(testnode.getLifeStyleString() + "    ");
		System.out.print(testnode.getVacation() + "    ");
		System.out.print(testnode.getEcredit() + "    ");
		System.out.print(testnode.getSalary() + "    ");
		System.out.print(testnode.getProperty() + "    ");
		System.out.println(category);
		return category;
	}

	// input the queue to find the category
	private String getCategory(PriorityQueue<KNNNode> queue) {
		// map to storelabel and total score
		Map<String, Double> map = new HashMap<String, Double>();
		String rs = null;
		// put label and total score pair into map
		for (int i = 0; i < queue.size(); i++) {
			KNNNode tmp = queue.poll();
			String category = tmp.getLabel();
			double similarity = tmp.getSimilarity();
			if (map.containsKey(category)) {
				similarity += map.get(category);
				map.put(category, similarity);
			} else {
				map.put(category, similarity);
			}
		}
		double score = Integer.MIN_VALUE; // total score for rs

		// find the label with highest score
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			Double value = entry.getValue();
			if (value > score) {
				rs = key;
				score = value;
			}
		}
		return rs;
	}
}
