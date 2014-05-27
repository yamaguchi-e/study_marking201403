package jp.ktsystem.kadai201403.e_yamaguchi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class DateComparator implements Comparator<String> {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

	public int compare(String arg0, String arg1) {
		try {
			return DATE_FORMAT.parse(arg0).compareTo(DATE_FORMAT.parse(arg1));
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return 0;
    }

}
