package test;

public class ElectricityCalcualtor {
	
	private float unitsUncovered = 0;
	private float calculatedCharge = 0;
	
	public float calculationForUnits(float units){
		clearClaculatorMemory();
		if(units == 0) {
			return 0.0f;
		}
		calculationForBand1(units);
		calculationForBand2(unitsUncovered);
		calculationForBand3(unitsUncovered);
		calculationForBand4(unitsUncovered);
		calculationForBand5(unitsUncovered);
		return calculatedCharge;
	}
	

	public void calculationForBand1(float unitsConsumed){
		if(unitsConsumed == 0)
			return;
		float unitPrice = 5;
		int maxUnits = 200;
		float perPersonUnits = maxUnits/3;
		 calculationForBands(unitsConsumed, perPersonUnits, unitPrice);
	}
	
	public void calculationForBand2(float unitsConsumed){
		if(unitsConsumed == 0)
			return;
		float unitPrice = 7.20f;
		int maxUnits = 100;
		float perPersonUnits = maxUnits/3;
		 calculationForBands(unitsConsumed, perPersonUnits, unitPrice);
	}
	
	public void calculationForBand3(float unitsConsumed){
		if(unitsConsumed == 0)
			return;
		float unitPrice = 8.50f;
		int maxUnits = 100;
		float perPersonUnits = maxUnits/3;
		 calculationForBands(unitsConsumed, perPersonUnits, unitPrice);
	}
	
	public void calculationForBand4(float unitsConsumed){
		if(unitsConsumed == 0)
			return;
		float unitPrice = 9;
		int maxUnits = 400;
		float perPersonUnits = maxUnits/3;
		 calculationForBands(unitsConsumed, perPersonUnits, unitPrice);
	}
	
	public void calculationForBand5(float unitsConsumed){
		if(unitsConsumed == 0)
			return;
		float unitPrice = 9.50f;
		int maxUnits = 400;
		float perPersonUnits = maxUnits/3;
		 calculationForBands(unitsConsumed, perPersonUnits, unitPrice);
	}
	
	
	
	//res[0] = units remaining
	//res[1] = charge to be added
	private void calculationForBands(float unitsConsumed, float perPersonUnits, float unitCost){
		float[] res = new float[2];
		if(unitsConsumed>perPersonUnits){
			unitsUncovered = unitsConsumed-perPersonUnits;
			calculatedCharge += perPersonUnits*unitCost;
		} else {
			unitsUncovered = 0;
			calculatedCharge += unitsConsumed*unitCost;
			}
	}
	
	private float calculateTotal(float[] charge){
		if(charge.length < 3) {
			return 0;
		}
		float res = 0;
		for(int i =0 ;i<charge.length ;i++) {
			res+=charge[i];
		}
		return res;
	}
	
	public float[] splitRemainingEqually(float[]charge, float total){
		float calculatedTotal = calculateTotal(charge);
		float remainingTotal = total-calculatedTotal;
		if(remainingTotal < 0){
			System.out.println("error in tallying as the sub total exceeds the total");
		}
		float eqiCharge = remainingTotal/3;
		for(int i=0;i<charge.length;i++){
			charge[i]+=eqiCharge;
		}
		return charge;
	}

	private void clearClaculatorMemory(){
		unitsUncovered = 0;
		calculatedCharge = 0;
	}

}
