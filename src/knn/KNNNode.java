package knn;

public class KNNNode {
	private int type;
	private int lifeStyle;
	private int vacation;
	private int ecredit;
	private double salary;
	private double property;
	private String label;
	private double similarity; //document the distance to curr test node

	public KNNNode(String type, String lifeStyle, int vacation, int ecredit,
			double salary, double property, String label) {
		this.type = calType(type);
		this.lifeStyle = calLifeStyle(lifeStyle);
		this.vacation = vacation;
		this.ecredit = ecredit;
		this.salary = salary;
		this.property = property;
		this.label = label;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public double getSimilarity() {
		return this.similarity;
	}
	
	public void setType(String type) {
		this.type = calType(type);
	}

	public int getType() {
		return this.type;
	}

	public String getTypeString() {
		return toType(this.type);
	}
	
	public void setLifeStyle(String lifeStyle) {
		this.lifeStyle = calLifeStyle(lifeStyle);
	}

	public int getLifeStyle() {
		return this.lifeStyle;
	}
	
	public String getLifeStyleString() {
		return toLifeStyle(this.lifeStyle);
	}

	public void setVacation(int vacation) {
		this.vacation = vacation;
	}

	public int getVacation() {
		return this.vacation;
	}

	public void setEcredit(int ecredit) {
		this.ecredit = ecredit;
	}

	public int getEcredit() {
		return this.ecredit;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getSalary() {
		return this.salary;
	}

	public void setProperty(double property) {
		this.property = property;
	}

	public double getProperty() {
		return this.property;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	private int calType(String type) {
		if (type.equals("student")) {
			return 1; // change the number when doing calculation
		} else if (type.equals("engineer")) {
			return 2; // change the number when doing calculation
		} else if (type.equals("librarian")) {
			return 3; // change the number when doing calculation
		} else if (type.equals("professor")) {
			return 4; // change the number when doing calculation
		} else if (type.equals("doctor")) {
			return 5; // change the number when doing calculation
		} else {
			return 0;// When there is a bug;
		}
	}

	private String toType(int type) {
		if (type == 1) {
			return "student"; // change the number when doing calculation
		} else if (type == 2) {
			return "engineer"; // change the number when doing calculation
		} else if (type == 3) {
			return "librarian"; // change the number when doing calculation
		} else if (type == 4) {
			return "professor"; // change the number when doing calculation
		} else if (type == 5) {
			return "doctor"; // change the number when doing calculation
		} else {
			return null;// When there is a bug;
		}
	}

	private int calLifeStyle(String lifeStyle) {
		if (lifeStyle.equals("spend>saving")) {
			return 1; // change the number when doing calculation
		} else if (lifeStyle.equals("spend<saving")) {
			return 2; // change the number when doing calculation
		} else if (lifeStyle.equals("spend>>saving")) {
			return 3; // change the number when doing calculation
		} else if (lifeStyle.equals("spend<<saving")) {
			return 4; // change the number when doing calculation
		} else {
			return 0;// When there is a bug;
		}
	}

	private String toLifeStyle(int lifeStyle) {
		if (lifeStyle == 1) {
			return "spend>saving"; // change the number when doing calculation
		} else if (lifeStyle == 2) {
			return "spend<saving"; // change the number when doing calculation
		} else if (lifeStyle == 3) {
			return "spend>>saving"; // change the number when doing calculation
		} else if (lifeStyle == 4) {
			return "spend<<saving"; // change the number when doing calculation
		} else {
			return null;// When there is a bug;
		}
	}
}
