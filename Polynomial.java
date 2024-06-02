import java.io.*;


public class Polynomial{
	double[] coefficients;
	int[] exponents;

	public Polynomial(double[] coefficients, int[] exponents) {
		if(coefficients.length != exponents.length) {
			System.out.println("What you doin bro?");
		}
		else {
			this.coefficients = new double[coefficients.length];
			for (int i = 0; i < coefficients.length; i++) {
				this.coefficients[i] = coefficients[i];
			}
			this.exponents = new int[exponents.length];
			for (int i = 0; i < exponents.length; i++) {
				this.exponents[i] = exponents[i];
			}
		}
	}

	public Polynomial(File file) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String polynomial = "";
			polynomial = bufferedReader.readLine();
			bufferedReader.close();
			polynomial = polynomial.replace('+', 'd');
			String[] positive = polynomial.split("d", -1);
			boolean checkIfFirstNeg = polynomial.charAt(0) == '-';
			String[][] Negation = new String[positive.length][];
			for (int i = 0; i < positive.length; i++) {
				Negation[i] = positive[i].split("-", -1);
			}
			int totalCoefficients = 0;
			for (int i = 0; i < Negation.length; i++) {
				for (int j = 0; j < Negation[i].length; j++) {
					totalCoefficients++;
				}
			}
			int counter = 0;
			double[] coefficients = new double[totalCoefficients];
			int[] exponents = new int[totalCoefficients];
			for (int i = 0; i < Negation.length; i++) {
				for (int j = 0; j < Negation[i].length; j++) {
					String[] temp;
					temp = Negation[i][j].split("x", -1);
					double tempCo = 0;
					int tempExp = 0;
					if (temp.length == 1) {
						tempExp = 0;
						tempCo = Double.parseDouble(temp[0]);
						if (j > 0) {
							tempCo = (-1) * tempCo;
						}
					} else if (temp.length >= 2) {
						tempCo = Double.parseDouble(temp[0]);
						tempExp = Integer.parseInt(temp[1]);
					}
					coefficients[counter] = tempCo;
					exponents[counter] = tempExp;
					counter++;
				}
			}
			this.coefficients = coefficients;
			this.exponents = exponents;
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public Polynomial(){
		this.coefficients = new double[1];
		this.coefficients[0] = 0;
		this.exponents = new int[1];
		this.exponents[0] = 0;
    }

	private int findMaxExponent(){
		int maxExp = 0;

		for(int i = 0; i < exponents.length; i++){
			maxExp = Math.max(maxExp, exponents[i]);
		}
		return maxExp;
	}

	private int countUniqueExp(Polynomial polynomial){
		int unique = 0;

		for(int i = 0; i < Math.max(findMaxExponent(), polynomial.findMaxExponent()); i++){
			boolean isThere = false;
			for(int j = 0; j < this.exponents.length; j++){
				if(this.exponents[j] == i){
					isThere = true;
				}
			}
			for(int j = 0; j < polynomial.exponents.length; j++){
				if(polynomial.exponents[j] == i){
					isThere = true;
				}
			}
			if(isThere){
				unique++;
			}
		}
		return unique;
	}



	public Polynomial add(Polynomial polynomial) {
		int c = countUniqueExp(polynomial);
		int a = 0;
		double[] newCoefficients = new double[c];
		int[] newExponents = new int[c];

		for(int i = 0; i < Math.max(findMaxExponent(), polynomial.findMaxExponent()); i++) {
			boolean isThere = false;
			for (int j = 0; j < this.exponents.length; j++){
				if(this.exponents[j] == i){
					isThere = true;
					newCoefficients[a] += this.coefficients[j];
					newExponents[a] = i;
				}
			}
			for (int j = 0; j < polynomial.exponents.length; j++){
				if(polynomial.exponents[j] == i){
					isThere = true;
					newCoefficients[a] += polynomial.coefficients[j];
					newExponents[a] = i;
				}
			}
			if(isThere){
				a++;
			}
		}
		return new Polynomial(newCoefficients, newExponents);
	}

	private int findInArray(int[] array, int value){
		for(int i = 0; i < array.length; i++){
			if(array[i] == value){
				return i;
			}
		}
		return -1;
	}

	public Polynomial multiply(Polynomial polynomial){
		double[] newCoefficients = new double[this.coefficients.length * polynomial.coefficients.length];
		int[] newExponents = new int[this.exponents.length * polynomial.exponents.length];
		for(int i = 0; i < newExponents.length; i++){
			newExponents[i] = -1;
		}
		int a = 0;
		for(int i = 0; i < this.exponents.length; i++){
			for(int j = 0; j < polynomial.exponents.length; j++){
				double nextCoefficient = this.coefficients[i] * polynomial.coefficients[j];
				int nextExponent = this.exponents[i] + polynomial.exponents[j];
				int whereInArray = findInArray(newExponents, nextExponent);
				if(whereInArray == -1){
					newExponents[a] = nextExponent;
					newCoefficients[a] = nextCoefficient;
					a++;
				}
				else{
					newCoefficients[whereInArray] += nextCoefficient;
				}
			}
		}
		int firstRedundant = findInArray(newExponents, -1);
		if(firstRedundant >= 0) {
			double[] finalCoefficients = new double[firstRedundant];
			int[] finalExponents = new int[firstRedundant];
			for(int i = 0; i < firstRedundant; i++){
				finalCoefficients[i] = newCoefficients[i];
				finalExponents[i] = newExponents[i];
			}
			return new Polynomial(finalCoefficients, finalExponents);
		}
		return new Polynomial(newCoefficients, newExponents);
	}
	
	public double evaluate(double value) {
		double result = 0;
		for(int i = 0; i < this.coefficients.length; i++) {
			result += coefficients[i] * Math.pow(value, exponents[i]);
		}
		return result;
	}

	public boolean hasRoot(double value){
		return Math.abs(evaluate(value)) <= 0.00001;
	}

	public void saveToFile(String fileName) {
		String polymonial = "";
		String temp = "";
		for(int i = 0; i < coefficients.length; i++){
			if(coefficients[i] != 0) {
				temp = (Math.abs(coefficients[i])) + "";
				if (exponents[i] > 0) {
					temp = temp + "x" + exponents[i];
				}
				if (coefficients[i] < 0) {
					temp = "-" + temp;
				} else {
					temp = "+" + temp;
				}
			}
			polymonial = polymonial + temp;
		}
		if(polymonial.charAt(0) == '+'){
			polymonial = polymonial.substring(1);
		}
		try {
			FileWriter writer = new FileWriter(fileName);
			writer.write(polymonial);
			writer.close();
		} catch (IOException e){
			e.printStackTrace();
		}

	}

	public void printPolynomial(){
		for(int i = 0; i < exponents.length; i++){
			String temp = "";
			if(coefficients[i] != 0) {
				temp = (Math.abs(coefficients[i])) + "";
				if (exponents[i] > 0) {
					temp = temp + "x" + exponents[i];
				}
				if (coefficients[i] < 0) {
					temp = "-" + temp;
				} else {
					temp = "+" + temp;
				}
			}
			System.out.print(temp);
		}
	}

}