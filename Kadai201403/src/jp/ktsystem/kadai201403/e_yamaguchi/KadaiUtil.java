package jp.ktsystem.kadai201403.e_yamaguchi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h3>時刻, ファイル名チェック</h3>
 *
 * @author e_yamaguti
 * @since 2013/12/08
 */
public class KadaiUtil {


	/**
	 * 時間をDate型に変換
	 * <br>
	 * @param time 時間
	 * @return 変換した時間
	 */
	public static Date changeTime(String time) {

		try {
			SimpleDateFormat timeFormat = new SimpleDateFormat(KadaiConstants.TIME_FORMAT);
			return timeFormat.parse(time);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 時刻不正チェック
	 * <br>
	 * @param time チェックする時刻
	 * @return boolean
	 * @throws KadaiException
	 */
	public static boolean validate(String time) {
		return null == time || time.isEmpty();
	}

    /**
	 * ファイル名チェック
	 * <br>
	 * @param anFilePath   ファイル名
	 * @return boolean
	 * @throws KadaiException
	 */
	public static boolean checkFile(String aFilePath) {
		return null == aFilePath || aFilePath.isEmpty();
	}

	/**
	 * エラーコードをbeanにセット
	 * <br>
	 * @param errorCode エラーコード
	 * @return エラーbean
	 */
	public static WorkTime setErrorCode(String errorCode) {
		WorkTime workTime = new WorkTime();
		workTime.setErrorCode(errorCode);

		return workTime;
	}

	/**
	 * データから日付を取得
	 * <br>
	 * @param data 取得データ
	 * @param start 開始位置
	 * @return 日付
	 */
	public static String obtainDate(String data, int start) {
		return data.replace(KadaiConstants.DATE_COLUMN, KadaiConstants.BLANK_CHAR).substring(start).trim();
	}

	/**
	 * データから日付を取得
	 * <br>
	 * @param data 取得データ
	 * @param start 開始位置
	 * @param end   終了位置
	 * @return 日付
	 */
	public static String obtainDate(String data, int start, int end) {
		return data.replace(KadaiConstants.DATE_COLUMN, KadaiConstants.BLANK_CHAR).substring(start, end).trim();
	}
}
