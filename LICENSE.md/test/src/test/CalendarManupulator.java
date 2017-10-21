package test;

public class CalendarManupulator {
	
	private Month month;
	private int year;
	
	public String getPerviousMonth(Month month, int year){
		this.month = month;
		this.year = year;
		Month[] months = Month.values();
		int i = 0;
		for(i = 0 ;i<months.length ;i++) {
			System.out.println("saadasd...."+months[i]);
			if(month.equals(months[i])){
				break;
			}
		}
		if(i==0){
			year = year-1;
			return "DEC"+year;
		} else {
			return months[i-1].toString()+year;
		}
	}

}
