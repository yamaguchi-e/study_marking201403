package jp.ktsystem.kadai201403.e_yamaguchi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * <h3>日付ソートクラス</h3>
 *
 * @author e_yamaguti
 * @since 2014/04/05
 */
public class DateComparator implements Comparator<String> {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

	public int compare(String arg0, String arg1) {
		try {
			return DATE_FORMAT.parse(arg0).compareTo(DATE_FORMAT.parse(arg1));
		} catch (ParseException e) {
			 throw new RuntimeException(e);
		}
    }

}
