public class Polynomial{
	double[] coefficients;
	
	public Polynomial(double[] coefficients){
		this.coefficients = new double[coefficients.length];
		for(int i = 0; i < coefficients.length; i++){
			this.coefficients[i] = coefficients[i];
		}
	}

	public Polynomial(){
		coefficients = new double[0];
	}

	public Polynomial add(Polynomial polynomial) {
		double[] newCoefficients = new double[Math.max(polynomial.coefficients.length, this.coefficients.length)];
		int min = Math.min(polynomial.coefficients.length, this.coefficients.length);
		for(int i = 0; i < min; i++) {
			newCoefficients[i] = polynomial.coefficients[i] + this.coefficients[i];
		}
		if(polynomial.coefficients.length > this.coefficients.length) {
			for(int i = min; i < polynomial.coefficients.length; i++) {
				newCoefficients[i] = polynomial.coefficients[i];
			}
		}
		else {
			for(int i = min; i < this.coefficients.length; i++) {
				newCoefficients[i] = this.coefficients[i];
			}
		}
		return new Polynomial(newCoefficients);
	}
	
	public double evaluate(double value) {
		double result = 0;
		for(int i = 0; i < this.coefficients.length; i++) {
			result += coefficients[i] * Math.pow(value, i);
		}
		return result;
	}

	public boolean hasRoot(double value){
		return Math.abs(evaluate(value)) <= 0.00001;
	}

}