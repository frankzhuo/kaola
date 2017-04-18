package crawler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {
	public static void main(String[] args) {
		SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String today = myFmt1.format(cal.getTime());
		System.out.println("today" + today);
		cal.add(cal.DAY_OF_MONTH, -1);

		String yesterday = myFmt1.format(cal.getTime());
		System.out.println("yesterday" + yesterday);
		
		
		
		System.out.println("www.Remind.ddd".indexOf("remind"));
	}
}
