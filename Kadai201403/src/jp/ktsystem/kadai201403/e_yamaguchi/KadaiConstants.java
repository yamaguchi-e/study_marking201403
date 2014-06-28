package jp.ktsystem.kadai201403.e_yamaguchi;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * <h3>定数クラス</h3>
 *
 * @author e_yamaguti
 * @since 2013/12/08
 */
public class KadaiConstants {

	/** SUCCESS  エラー無し */
	public static final int SUCCESS = 0;

	/** INPUT_NULL_OR_BLANK_ERROR  入力文字列がnull、または空文字である場合のエラー */
	public static final String INPUT_NULL_OR_BLANK_ERROR = "1";

	/** INPUT_CONTROL_ERROR  入力文字が不正である場合のエラー  */
	public static final String INPUT_CONTROL_ERROR = "2";

	/** INPUT_TIME_CONTROL_ERROR  入力文字列が出社時刻、退社時刻として不正である場合のエラー */
	public static final String INPUT_TIME_CONTROL_ERROR = "3";

	/** END_TIME_BEFORE_START_ERROR  退社時刻が出社時刻以前である場合のエラー */
	public static final String END_TIME_BEFORE_START_ERROR = "4";

	/** INPUT_FILE_FORMAT_ERROR  入力ファイルのレコードが不正である場合のエラー */
	public static final String INPUT_FILE_FORMAT_ERROR = "5";

	/** INPUT_FILE_NULL_ERROR  入力ファイルのパスがnullの場合のエラー */
	public static final String INPUT_FILE_NULL_ERROR = "6";

	/** INPUT_FILE_NOT_FOUND_ERROR  入力ファイルが存在しない場合のエラー */
	public static final String INPUT_FILE_NOT_FOUND_ERROR = "7";

	/** INPUT_FILE_READ_ERROR  入力ファイルの読み込みに失敗した場合のエラー */
	public static final String INPUT_FILE_READ_ERROR = "8";

	/** OUTPUT_FILE_NULL_ERROR  出力ファイルのパスがnullの場合のエラー */
	public static final String OUTPUT_FILE_NULL_ERROR = "9";

	/** OUTPUT_FILE_WRITE_ERROR  ファイルの出力に失敗した場合のエラー */
	public static final String OUTPUT_FILE_WRITE_ERROR = "10";

	/** OUTPUT_CONTROL_CHAR_ERROR ファイル内に制御文字が含まれている場合のエラー */
	public static final String FAIL_CONTROL_CHAR_ERROR = "11";

	/** OUTPUT_OVERLAP_DATE_ERROR  日付の値が重複する場合のエラー */
	public static final String OUTPUT_OVERLAP_DATE_ERROR = "12";

	/**  PATTERN   数字  */
	public static final Pattern PATTERN = Pattern.compile("^\\d{4}$");

	/**  DATE_PATTERN   日付(yyyyMMdd)  */
	public static final Pattern DATE_PATTERN = Pattern.compile("^\\d{8}$");

	/**  MONTH_PATTERN   日付(yyyyMM)  */
	public static final Pattern MONTH_PATTERN = Pattern.compile("^\\d{6}$");

	/**  CHARACTER_CODE  文字コード  */
	public static final String CHARACTER_CODE = "UTF-8";

	/**  TIME_COUNT  時間の桁数  */
	public static final int TIME_COUNT = 2;

	/**  MINUTE_COUNT  時間(分)の桁数  */
	public static final int MINUTE_COUNT = 4;

	/**  DATE_COUNT  日付の桁数  */
	public static final int DATE_COUNT = 8;

	/**  START_TIME_LIMIT  出社時刻限度時間  */
	public static final Date START_TIME_LIMIT = KadaiUtil.changeTime("2359");

	/**  END_TIME_LIMIT  退社時刻限度時間  */
	public static final Date END_TIME_LIMIT = KadaiUtil.changeTime("3259");

	/**  START_TIME  出社時刻  */
	public static final Date START_TIME = KadaiUtil.changeTime("0900");

	/**  FIRST_REST_TIME_START  休憩開始時間  */
	public static final Date FIRST_REST_TIME_START = KadaiUtil.changeTime("1200");

	/**  FIRST_REST_TIME_END 休憩終了時間  */
	public static final Date FIRST_REST_TIME_END = KadaiUtil.changeTime("1300");

	/**  SECOND_REST_TIME_START  休憩開始時間  */
	public static final Date SECOND_REST_TIME_START = KadaiUtil.changeTime("1800");

	/**  SECOND_REST_TIME_END  休憩終了時間  */
	public static final Date SECOND_REST_TIME_END = KadaiUtil.changeTime("1830");

	/**  MINUTE 時間(分)の限度  */
	public static final int MINUTE = 60;

	/**  COMMA カンマ区切り  */
	public static final String COMMA = ",";

	/**  DELIMITER 区切り  */
	public static final String DELIMITER = "},";

	/**  COLON コロン  */
	public static final String COLON = ":";

	/**  BOM_PATTERN    BOM  */
	public static final char BOM_PATTERN = 0xFEFF;

	/** BLANK_CHAR 空文字 */
	public static final String BLANK_CHAR = "";

	/** SPACE 空白 */
	public static final String SPACE = " ";

	/** QUOTATION クォーテーション */
	public static final String QUOTATION = "\"";

	/** START_BRACE 括弧始まり */
	public static final String START_BRACE = "{";

	/** END_BRACE 括弧終わり */
	public static final String END_BRACE = "}";

	/** DATE_START_BRACE 括弧始まり */
	public static final String DATE_START_BRACE = "[";

	/** DATE_END_BRACE 括弧終わり */
	public static final String DATE_END_BRACE = "]";

	/** DATE 日付 */
	public static final String DATE = "date";

	/** START 開始時間 */
	public static final String START = "start";

	/** END 終了時間 */
	public static final String END = "end";

	/** ELEMENT_COUNT 要素数 */
	public static final int ELEMENT_COUNT = 3;

	/**  WORK_DAY  日付  */
	public static final int WORK_DAY = 0;

	/**  WORK_START_TIME  出社時刻  */
	public static final int WORK_START_TIME = 1;

	/**  WORK_END_TIME  退社時刻  */
	public static final int WORK_END_TIME = 2;

	/**  MINUTE_CHANGE  分単位に変換  */
	public static final int MINUTE_CHANGE = 1000;

	/**  OUTPUT_FORMAT  出力フォーマット  */
	public static final String OUTPUT_FORMAT = "{\"date\":%s,\"workTime\":%s,\"total\":%s}";

	/** DATE_START_POSITION 日付開始位置 */
	public static final int DATE_START_POSITION = 1;

	/** DATE_END_POSITION 日付終了位置 */
	public static final int DATE_END_POSITION = 3;

	/** ONE_DATE_DATA_START_POSITION 1日分データ開始位置 */
	public static final int ONE_DATE_DATA_START_POSITION = 2;

	/** ONE_DATE_DATA_END_POSITION 1日分データ終了位置 */
	public static final int ONE_DATE_DATA_END_POSITION = 1;
}