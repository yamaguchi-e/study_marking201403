package jp.ktsystem.kadai201403.e_yamaguchi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * <h3>勤務時間算出、ファイル入出力クラス</h3>
 *
 * @author e_yamaguti
 * @since 2014/04/05
 */
public class Kadai {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("yyyyMM");
	private static boolean checkLv2Flag = false;

	/**
	 * 勤務時間の算出
	 *
	 * @param aStartTime 出社時刻
	 * @param aEndTime   退社時刻
	 * @return 勤務時間
	 * @throws KadaiException
	 */
	public static String calcWorkTime(String aStartTime, String aEndTime) throws KadaiException {

		// 時刻null, 空文字チェック
		if (KadaiUtil.validate(aStartTime) || KadaiUtil.validate(aEndTime)) {
			throw new KadaiException(KadaiConstants.INPUT_NULL_OR_BLANK_ERROR);
		}

		// 不正時刻チェック
		checkTime(aStartTime, aEndTime);

		Date start = KadaiUtil.changeTime(aStartTime);

		// 出社時刻が9時より前の場合
		if (start.before(KadaiConstants.START_TIME)) {
			// 出社時刻を9時にセット
			start = KadaiConstants.START_TIME;
		}

		// 出社・退社時刻チェック
		checkStartEndTime(start, aEndTime);

		Date end = KadaiUtil.changeTime(aEndTime);

		// 休憩時間(12時～13時)取得時の算出
		long firstRestTime = calcRestTime(start, end,
				KadaiConstants.FIRST_REST_TIME_START, KadaiConstants.FIRST_REST_TIME_END);

		// 休憩時間(18時～18時30分)取得時の算出
		long secondRestTime = calcRestTime(start, end,
				KadaiConstants.SECOND_REST_TIME_START, KadaiConstants.SECOND_REST_TIME_END);

		// 勤務時間 = (退社時刻 - 出社時刻) - 休憩時間
		long passTime = (end.getTime() - start.getTime())
				/ KadaiConstants.MINUTE_CHANGE / KadaiConstants.MINUTE - (firstRestTime + secondRestTime);

		return String.valueOf(passTime);
	}

	/**
	 * 課題1
	 *
	 * @param anInputPath 入力ファイル
	 * @param anOutputPath 出力ファイル
	 * @throws KadaiException
	 */
	public static void parseWorkTimeData(String anInputPath, String anOutputPath) throws KadaiException {

		// 入力ファイルnull・空文字チェック
		if (KadaiUtil.checkFile(anInputPath)) {
			throw new KadaiException(KadaiConstants.INPUT_FILE_NULL_ERROR);
		}

		// 出力ファイルnull・空文字チェック
		if (KadaiUtil.checkFile(anOutputPath)) {
			throw new KadaiException(KadaiConstants.OUTPUT_FILE_NULL_ERROR);
		}

		// 入力ファイルの読み込み
		List<WorkTime> answerList = readWorkTimeFile(anInputPath);

		// 出力ファイルへの書き込み
		writeWorkTimeFile(anOutputPath, answerList);
	}

	/**
	 * 課題2
	 *
	 * @param anInputPath 入力ファイル
	 * @param anOutputPath 出力ファイル
	 * @throws KadaiException
	 */
	public static void parseWorkTimeDataLv2(String anInputPath, String anOutputPath) throws KadaiException {

		// 行ごとに勤務時間を入れるリスト
		Map<String, List<WorkTime>> answerMap = new HashMap<String, List<WorkTime>>();

		BufferedReader bufferedReader = null;
		String month = null;

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(anInputPath), KadaiConstants.CHARACTER_CODE));
			Map<String, String> workTimeMap = new HashMap<String, String>();

			String oneRecord = null;
			String monthData = null;

			// 入力ファイルを１行ずつ読み込む
			while (null != (oneRecord = bufferedReader.readLine())) {

				List<WorkTime> answerList = new ArrayList<WorkTime>();

				// 勤務時間の累計
				int sumWorkTime = 0;

				// コロンの位置
				int index = oneRecord.replace(KadaiConstants.SPACE,
						KadaiConstants.BLANK_CHAR).indexOf(KadaiConstants.COLON);

				if (oneRecord.endsWith(KadaiConstants.COMMA)) {
					// 行の末尾がカンマの場合削除
					oneRecord = oneRecord.substring(0, oneRecord.length()-1);
				}

				if (oneRecord.startsWith(KadaiConstants.START_BRACE)
						|| oneRecord.startsWith(KadaiConstants.END_BRACE)) {
					continue;
				} else {
					if (!"[{".equals(oneRecord.replace(KadaiConstants.SPACE,
							KadaiConstants.BLANK_CHAR).substring(index+1, index+3))
							|| !oneRecord.replace(KadaiConstants.SPACE,
									KadaiConstants.BLANK_CHAR).endsWith("}]")) {
						throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
					}
				}

				try {
					if (!oneRecord.startsWith(KadaiConstants.END_BRACE)) {
						// 改行または空白の場合は次の行へ
						if (0 == oneRecord.trim().length()) {
							continue;
						}

						// BOM、制御文字チェック
						checkRecord(oneRecord);

						index = oneRecord.indexOf(KadaiConstants.COLON);

						// 年月を取得
						month = KadaiUtil.obtainDate(oneRecord, 0, index- 2);

						// 1か月分のデータを取得
						monthData = oneRecord.substring(index +4, oneRecord.length() -3);

						String[] workTimeInfo = monthData.split(KadaiConstants.DELIMITER, -1);

						Map<String, String> oneWorkDateMap = new HashMap<String, String>();
						List<String> oneWorkDateList = new ArrayList<String>();

						for (String workTime : workTimeInfo) {
							index = workTime.indexOf(KadaiConstants.COLON);

							// 日にちを取得
							String date = KadaiUtil.obtainDate(workTime, 1, index - 3);

							// 1日分のデータを取得
							String data = workTime.substring(index + 2, workTime.length()-1);

							if (!KadaiConstants.START_BRACE.equals(data.replace(
									KadaiConstants.SPACE, KadaiConstants.BLANK_CHAR).substring(0, 1))) {
								throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
							}

							oneWorkDateMap.put(date, data);
							oneWorkDateList.add(date);
						}

						// 日付の順にソート
						Collections.sort(oneWorkDateList, new DateComparator());

						for(String oneWorkDay : oneWorkDateList) {

							// カンマごとに区切る
							String[] workDayInfo = oneWorkDateMap.get(oneWorkDay).split(KadaiConstants.COMMA, -1);
							workTimeMap.put(KadaiConstants.DATE, oneWorkDay);

							for (String workDay : workDayInfo) {
								index = workDay.indexOf(KadaiConstants.COLON);

								// 勤務開始時間、終了時間を取得
								String key = KadaiUtil.obtainDate(workDay, 1, index -2);
								String value = KadaiUtil.obtainDate(workDay, index -1);

								// 項目名が重複する場合エラー
								if (workTimeMap.containsKey(key)) {
									throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
								}

								workTimeMap.put(key, value);
							}

							// 日付のnull・空白チェック
							if (KadaiUtil.validate(month) || month.equals("null")) {
								throw new KadaiException(KadaiConstants.INPUT_NULL_OR_BLANK_ERROR);
							}

							validate(workTimeMap);

							// 勤務時間の算出
							String answer = calcWorkTime(workTimeMap.get(KadaiConstants.START),
									workTimeMap.get(KadaiConstants.END));

							// 勤務時間の累計算出
							sumWorkTime += Integer.parseInt(answer);

							WorkTime workTime = new WorkTime();
							workTime.setWorkDate(workTimeMap.get(KadaiConstants.DATE).toString());
							workTime.setWorkTime(answer);
							workTime.setSumWorkTime(String.valueOf(sumWorkTime));
							workTime.setErrorCode(KadaiConstants.BLANK_CHAR);

							answerList.add(workTime);
							workTimeMap.clear();
							answerMap.put(month, answerList);
						}
					}
				} catch (KadaiException ke) {
					KadaiUtil.setErrorCode(ke.getErrorCode(), answerList);
					answerMap.put(month, answerList);

					// エラーが発生した場合、処理打ち切り
					break;
				}
			}
		} catch (FileNotFoundException fne) {
			throw new KadaiException(KadaiConstants.INPUT_FILE_NOT_FOUND_ERROR);
		} catch (IOException e) {
			throw new KadaiException(KadaiConstants.INPUT_FILE_READ_ERROR);
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					throw new KadaiException(KadaiConstants.INPUT_FILE_READ_ERROR);
				}
			}
		}

		for (String key : answerMap.keySet()) {
			try {
				// 日付として成立する値であるかを調べる
				MONTH_FORMAT.setLenient(false);
				MONTH_FORMAT.parse(key);

				Matcher match = KadaiConstants.MONTH_PATTERN.matcher(key);

				// 数字以外の文字が含まれている場合エラー
				if (!match.matches()) {
					throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
				}

			// 日付がyyyyMMddの形式で入力されていない場合エラー
			} catch (ParseException pe) {
				throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);

			// 日付として成立する値が設定されていない場合エラー
			} catch (IllegalArgumentException iae) {
				throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
			}

			checkLv2Flag = true;

			// ファイル書き込み
			writeWorkTimeFile(anOutputPath + key, answerMap.get(key));
		}
	}

	/**
	 * ファイル読み込み
	 *
	 * @param anInputPath 入力ファイル
	 * @return 勤務時間のリスト
	 * @throws KadaiException
	 */
	public static List<WorkTime> readWorkTimeFile(String anInputPath) throws KadaiException {

		// 行ごとに勤務時間を入れるリスト
		List<WorkTime> answerList = new ArrayList<WorkTime>();

		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(anInputPath), KadaiConstants.CHARACTER_CODE));
			String oneRecord = null;
			Map<String, String> workTimeMap = new HashMap<String, String>();

			// 勤務時間の累計
			int sumWorkTime = 0;

			// 入力ファイルを１行ずつ読み込む
			while (null != (oneRecord = bufferedReader.readLine())) {
				try {
					 if (!oneRecord.contains(KadaiConstants.END_BRACE)) {

						 // 改行、空白の場合次の行へ
						if (0 == oneRecord.trim().length()) {
							continue;
						}

						// BOM、制御文字チェック
						checkRecord(oneRecord);

						if (oneRecord.contains(KadaiConstants.START_BRACE)) {
							continue;
						} else {
							oneRecord = oneRecord.replace(KadaiConstants.COMMA, KadaiConstants.BLANK_CHAR);
						}

						try {
							int index = oneRecord.indexOf(KadaiConstants.COLON);
							String key = KadaiUtil.obtainDate(oneRecord, 0, index -2);
							String value = KadaiUtil.obtainDate(oneRecord, index -1);

							// 項目名が重複する場合エラー
							if (workTimeMap.containsKey(key)) {
								throw new KadaiException(KadaiConstants.INPUT_FILE_FORMAT_ERROR);
							}

							workTimeMap.put(key, value);
						} catch (IndexOutOfBoundsException iob) {
							throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
						}
						continue;
					}

					validate(workTimeMap);

					// 勤務時間の算出
					String answer = calcWorkTime(workTimeMap.get(KadaiConstants.START),
							workTimeMap.get(KadaiConstants.END));

					// 勤務時間の累計算出
					sumWorkTime += Integer.parseInt(answer);

					WorkTime workTime = new WorkTime();
					workTime.setWorkDate(workTimeMap.get(KadaiConstants.DATE).toString());
					workTime.setWorkTime(answer);
					workTime.setSumWorkTime(String.valueOf(sumWorkTime));
					workTime.setErrorCode(KadaiConstants.BLANK_CHAR);

					answerList.add(workTime);
					workTimeMap.clear();
				} catch (KadaiException ke) {
					KadaiUtil.setErrorCode(ke.getErrorCode(), answerList);

					// エラーが発生した場合、処理打ち切り
					break;
				}
			}
		} catch (FileNotFoundException fne) {
			throw new KadaiException(KadaiConstants.INPUT_FILE_NOT_FOUND_ERROR);
		} catch (IOException e) {
			throw new KadaiException(KadaiConstants.INPUT_FILE_READ_ERROR);
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					throw new KadaiException(KadaiConstants.INPUT_FILE_READ_ERROR);
				}
			}
		}
		return answerList;
	}

	/**
	 * ファイル書き込み
	 *
	 * @param anOutputPath 出力ファイル
	 * @param answerList 勤務時間のリスト
	 * @throws KadaiException
	 */
	public static void writeWorkTimeFile(String anOutputPath, List<WorkTime> answerList) throws KadaiException {
		BufferedWriter bufferedWriter = null;

		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(anOutputPath, true), KadaiConstants.CHARACTER_CODE));

			bufferedWriter.write(KadaiConstants.DATE_START_BRACE);
			bufferedWriter.newLine();

			String date = KadaiConstants.BLANK_CHAR;

			for (WorkTime workTime : answerList) {

				// 日付が重複する場合はエラー
				if(checkLv2Flag && date.equals(workTime.getWorkDate())) {
					workTime.setErrorCode(KadaiConstants.OUTPUT_OVERLAP_DATE_ERROR);
				}

				date = workTime.getWorkDate();

				if (!workTime.getErrorCode().isEmpty()) {
					// エラーコード書き込み
					bufferedWriter.write(workTime.getErrorCode());
					bufferedWriter.newLine();
					break;
				}

				// １行ずつ書き込み
				bufferedWriter.write(String.format(KadaiConstants.OUTPUT_FORMAT,
						workTime.getWorkDate(), workTime.getWorkTime(), workTime.getSumWorkTime()));

				if (!workTime.equals(answerList.get(answerList.size()-1))) {
					bufferedWriter.write(KadaiConstants.COMMA);
				}

				bufferedWriter.newLine();

			}
			bufferedWriter.write(KadaiConstants.DATE_END_BRACE);
			bufferedWriter.newLine();
			bufferedWriter.flush();

		} catch (IOException e) {
			throw new KadaiException(KadaiConstants.OUTPUT_FILE_WRITE_ERROR);

		} finally {
			if (null != bufferedWriter) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					throw new KadaiException(KadaiConstants.OUTPUT_FILE_WRITE_ERROR);
				}
			}
		}
	}

	/**
	 * 不正時刻チェック
	 *
	 * @param aStartTime
	 *            出社時刻
	 * @param aEndTime
	 *            　退社時刻
	 * @throws KadaiException
	 */
	private static void checkTime(String aStartTime, String aEndTime) throws KadaiException {

		Matcher startMatch = KadaiConstants.PATTERN.matcher(aStartTime);
		Matcher endMatch = KadaiConstants.PATTERN.matcher(aEndTime);

		// 数字以外の文字が含まれている場合エラー
		if (!startMatch.matches() || !endMatch.matches()) {
			throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
		}

		// 時刻の分部分が60分以上の場合エラー
		if (KadaiConstants.MINUTE <= Integer.parseInt(aStartTime.substring(
				KadaiConstants.TIME_COUNT, KadaiConstants.MINUTE_COUNT))
				|| KadaiConstants.MINUTE <= Integer.parseInt(aEndTime
						.substring(KadaiConstants.TIME_COUNT, KadaiConstants.MINUTE_COUNT))) {
			throw new KadaiException(KadaiConstants.INPUT_TIME_CONTROL_ERROR);
		}
	}

	/**
	 * 出社・退社時刻チェック
	 *
	 * @param aStartTime
	 *            出社時刻
	 * @param aEndTime
	 *            　退社時刻
	 * @throws KadaiException
	 */
	private static void checkStartEndTime(Date aStartTime, String aEndTime) throws KadaiException {

		// 出社時刻が24時以降の場合エラー
		if (aStartTime.after(KadaiConstants.START_TIME_LIMIT)) {
			throw new KadaiException(KadaiConstants.INPUT_TIME_CONTROL_ERROR);
		}

		Date end = KadaiUtil.changeTime(aEndTime);

		// 退社時刻が次の日の9時以降の場合エラー
		if (end.after(KadaiConstants.END_TIME_LIMIT)) {
			throw new KadaiException(KadaiConstants.INPUT_TIME_CONTROL_ERROR);
		}

		// 出社時刻が退社時刻以前の時刻の場合エラー
		if (aStartTime.after(end) || aStartTime.equals(end)) {
			throw new KadaiException(KadaiConstants.END_TIME_BEFORE_START_ERROR);
		}
	}

	/**
	 * 休憩時間算出
	 *
	 * @param aStartTime
	 *            出社時刻
	 * @param aEndTime
	 *            　 退社時刻
	 * @param restStartTime
	 *            休憩開始時間
	 * @param restEndTime
	 *            　休憩終了時間
	 * @return 休憩取得時間
	 * @throws KadaiException
	 */
	private static long calcRestTime(Date aStartTime, Date aEndTime,
			Date restStartTime, Date restEndTime) throws KadaiException {

		// 休憩取得時間
		long restTime = 0;

		// 休憩時間を取っている場合
		if (aStartTime.before(restEndTime) && aEndTime.after(restStartTime)) {
			// 退社時刻 - 休憩開始時間
			restTime = (aEndTime.getTime() - restStartTime.getTime())
					/ KadaiConstants.MINUTE_CHANGE / KadaiConstants.MINUTE;

			// 休憩終了時間 - 休憩開始時間
			long restMinute = (restEndTime.getTime() - restStartTime.getTime())
					/ KadaiConstants.MINUTE_CHANGE / KadaiConstants.MINUTE;

			if (restMinute < restTime) {
				restTime = restMinute;
			}

			// 出社時刻が休憩時間より後の場合
			if (aStartTime.after(restStartTime)) {
				// 出社時刻 - 休憩開始時間
				long startRestTime = (aStartTime.getTime() - restStartTime.getTime())
						/ KadaiConstants.MINUTE_CHANGE / KadaiConstants.MINUTE;
				restTime -= startRestTime;
			}
		}
		return restTime;
	}

	/**
	 * BOM, 制御文字チェック
	 *
	 * @param oneRecord 勤怠データ
	 * @throws KadaiException
	 */
	private static void checkRecord(String oneRecord) throws KadaiException {

		// BOM除去
		if (KadaiConstants.BOM_PATTERN == oneRecord.charAt(0)) {
			oneRecord = oneRecord.substring(1);
		}

		for (int i = 0; i <= oneRecord.length()-1; i++) {
			if ('\t' == oneRecord.charAt(i) || Character.isWhitespace(oneRecord.charAt(i))) {
				break;
			}

			// 制御文字が含まれている場合エラー
			if (Character.isISOControl(oneRecord.charAt(i))) {
				throw new KadaiException(KadaiConstants.FAIL_CONTROL_CHAR_ERROR);
			}
		}
	}

	/**
	 * validate
	 *
	 * @param workTimeMap 勤怠データ
	 * @throws KadaiException
	 */
	private static void validate(Map<String, String> workTimeMap) throws KadaiException {

		// 要素が3以外の場合エラー
		if (KadaiConstants.ELEMENT_COUNT != workTimeMap.size()) {
			throw new KadaiException(KadaiConstants.INPUT_FILE_FORMAT_ERROR);
		}

		List<String> keyList = new ArrayList<String>();
		keyList.add(KadaiConstants.DATE);
		keyList.add(KadaiConstants.START);
		keyList.add(KadaiConstants.END);

		for (String key : workTimeMap.keySet()) {
			// 日付のnull・空白チェック
			if (KadaiUtil.validate(workTimeMap.get(key))
					|| workTimeMap.get(key).equals("null")) {
				throw new KadaiException(KadaiConstants.INPUT_NULL_OR_BLANK_ERROR);
			}

			if (!keyList.contains(key)) {
				throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
			}
		}

		try {
			// 日付として成立する値であるかを調べる
			DATE_FORMAT.setLenient(false);
			DATE_FORMAT.parse(workTimeMap.get(KadaiConstants.DATE));

			Matcher match = KadaiConstants.DATE_PATTERN.matcher(workTimeMap.get(KadaiConstants.DATE));

			// 数字以外の文字が含まれている場合エラー
			if (!match.matches()) {
				throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
			}

		// 日付がyyyyMMddの形式で入力されていない場合エラー
		} catch (ParseException pe) {
			throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);

		// 日付として成立する値が設定されていない場合エラー
		} catch (IllegalArgumentException iae) {
			throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
		}
	}
}