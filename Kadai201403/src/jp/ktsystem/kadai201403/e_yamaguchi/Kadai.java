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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;

/**
 * <h3>勤務時間算出、ファイル入出力クラス</h3>
 *
 * @author e_yamaguti
 * @since 2014/04/05
 */
public class Kadai {

	/**
	 * 勤務時間の算出
	 *
	 * @param aStartTime 出社時刻
	 * @param aEndTime   退社時刻
	 * @return 勤務時間
	 * @throws KadaiException
	 */
	private static String calcWorkTime(String aStartTime, String aEndTime) throws KadaiException {

		// 時刻null, 空文字チェック
		if (KadaiUtil.validate(aStartTime) || KadaiUtil.validate(aEndTime)) {
			throw new KadaiException(KadaiConstants.INPUT_NULL_OR_BLANK_ERROR);
		}

		// 不正時刻チェック
		checkTime(aStartTime, aEndTime);

		Date start = KadaiUtil.changeTime(aStartTime);
		Date end = KadaiUtil.changeTime(aEndTime);

		// 出社時刻が9時より前の場合
		if (start.before(KadaiConstants.START_TIME)) {
			// 出社時刻を9時にセット
			start = KadaiConstants.START_TIME;
		}

		// 出社・退社時刻チェック
		checkStartEndTime(start, end);

		// 休憩時間(12時～13時)取得時の算出
		long firstRestTime = calcRestTime(start, end,
				KadaiConstants.FIRST_REST_TIME_START, KadaiConstants.FIRST_REST_TIME_END);

		// 休憩時間(18時～18時30分)取得時の算出
		long secondRestTime = calcRestTime(start, end,
				KadaiConstants.SECOND_REST_TIME_START, KadaiConstants.SECOND_REST_TIME_END);

		// 勤務時間 = (退社時刻 - 出社時刻) - 休憩時間
		long passTime = (end.getTime() - start.getTime())
				/ 1000 / 60 - (firstRestTime + secondRestTime);

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
	 * <br>
	 * 課題2は出力ファイルに出力する日付(answerMapに渡す日付)が
	 * 日付順にソートされていることが前提である。
	 *
	 * @param anInputPath 入力ファイル
	 * @param anOutputPath 出力ファイル
	 * @throws KadaiException
	 */
	public static void parseWorkTimeDataLv2(String anInputPath, String anOutputPath) throws KadaiException {

		// 入力ファイルnull・空文字チェック
		if (KadaiUtil.checkFile(anInputPath)) {
			throw new KadaiException(KadaiConstants.INPUT_FILE_NULL_ERROR);
		}

		// 出力ファイルnull・空文字チェック
		if (KadaiUtil.checkFile(anOutputPath)) {
			throw new KadaiException(KadaiConstants.OUTPUT_FILE_NULL_ERROR);
		}

		// 入力ファイルの読み込み
		Map<String, List<WorkTime>> answerMap = readWorkTimeFileLv2(anInputPath);

		SimpleDateFormat monthFormat = new SimpleDateFormat(KadaiConstants.MONTH_FORMAT);
		for (Entry<String, List<WorkTime>> entry : answerMap.entrySet()) {
			try {
				// 日付として成立する値であるかを調べる
				monthFormat.setLenient(false);
				monthFormat.parse(entry.getKey());

			// 日付がyyyyMMの形式で入力されていない場合エラー
			} catch (ParseException pe) {
				throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);

			// 日付として成立する値が設定されていない場合エラー
			} catch (IllegalArgumentException iae) {
				throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
			}

			// 日付重複チェック
			checkOverlapDate(answerMap, entry.getKey());

			// ファイル書き込み
			writeWorkTimeFile(anOutputPath + entry.getKey(), entry.getValue());
		}
	}

	/**
	 * ファイル読み込み(課題2)
	 *
	 * @param anInputPath 入力ファイル
	 * @return 勤務時間のリスト
	 * @throws KadaiException
	 */
	private static Map<String, List<WorkTime>> readWorkTimeFileLv2(String anInputPath) throws KadaiException {

		// 行ごとに勤務時間を入れるリスト
		Map<String, List<WorkTime>> answerMap = new HashMap<String, List<WorkTime>>();

		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(anInputPath), KadaiConstants.CHARACTER_CODE));
			Map<String, String> workTimeMap = new HashMap<String, String>();

			String oneRecord = null;

			// 入力ファイルを１行ずつ読み込む
			while (null != (oneRecord = bufferedReader.readLine())) {

				if (oneRecord.endsWith(KadaiConstants.DELIMITER)) {
					// 行の末尾がカンマの場合削除
					oneRecord = oneRecord.substring(0, oneRecord.length()-1);
				}

				if (oneRecord.startsWith(KadaiConstants.DATE_START)
						|| oneRecord.startsWith(KadaiConstants.DATE_END)) {
					continue;
				}

				List<WorkTime> answerList = new ArrayList<WorkTime>();
				String month = null;

				// 勤務時間の累計
				int sumWorkTime = 0;

				try {
					if (!oneRecord.startsWith(KadaiConstants.DATE_END)) {
						// 改行または空白の場合は次の行へ
						if (oneRecord.trim().isEmpty()) {
							continue;
						}

						// BOM、制御文字チェック
						checkRecord(oneRecord);

						// コロンの位置
						int index = oneRecord.indexOf(KadaiConstants.ITEM_AND_VALUE_DELIMITER);

						// 年月を取得
						month = KadaiUtil.obtainDate(oneRecord, KadaiConstants.YEAR_MONTH_START_POSITION,
								index - KadaiConstants.YEAR_MONTH_END_POSITION);

						index = oneRecord.replace(KadaiConstants.SPACE,
								KadaiConstants.BLANK_CHAR).indexOf(KadaiConstants.ITEM_AND_VALUE_DELIMITER);

						// 日付囲みで囲まれてない場合エラー
						if (!KadaiConstants.COLUMN_START.equals(oneRecord.replace(KadaiConstants.SPACE,
								KadaiConstants.BLANK_CHAR).substring(index + KadaiConstants.COLUMN_POSITION_START,
										index + KadaiConstants.COLUMN_POSITION_END))
										|| !oneRecord.replace(KadaiConstants.SPACE,
										KadaiConstants.BLANK_CHAR).endsWith(KadaiConstants.COLUMN_END)) {
							throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
						}

						index = oneRecord.indexOf(KadaiConstants.ITEM_AND_VALUE_DELIMITER);

						// 1か月分のデータを取得
						String monthData = oneRecord.substring(index + KadaiConstants.MONTH_DATA_START_POSITION,
								oneRecord.length() - KadaiConstants.MONTH_DATA_END_POSITION);

						String[] workTimeInfo = monthData.split(KadaiConstants.DATE_DELIMITER, -1);

						Map<String, String> oneWorkDateMap = new TreeMap<String, String>();

						for (String workTime : workTimeInfo) {
							index = workTime.indexOf(KadaiConstants.ITEM_AND_VALUE_DELIMITER);

							// 日にちを取得
							String date = KadaiUtil.obtainDate(workTime, KadaiConstants.DATE_START_POSITION,
									index - KadaiConstants.DATE_END_POSITION);

							// 1日分のデータを取得
							String data = workTime.substring(index + KadaiConstants.ONE_DATE_DATA_START_POSITION,
									workTime.length() - KadaiConstants.ONE_DATE_DATA_END_POSITION);

							if (!KadaiConstants.DATE_START.equals(data.replace(
									KadaiConstants.SPACE, KadaiConstants.BLANK_CHAR).substring(0, 1))) {
								throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
							}

							oneWorkDateMap.put(date, data);
						}

						for(Entry<String, String> entry : oneWorkDateMap.entrySet()) {

							// カンマごとに区切る
							String[] workDayInfo = entry.getValue().split(KadaiConstants.DELIMITER, -1);
							workTimeMap.put(KadaiConstants.DATE, entry.getKey());

							for (String workDay : workDayInfo) {
								index = workDay.indexOf(KadaiConstants.ITEM_AND_VALUE_DELIMITER);

								// 勤務開始時間、終了時間を取得
								String key = KadaiUtil.obtainDate(workDay, KadaiConstants.ITEM_NAME_START_POSITION,
										index - KadaiConstants.ITEM_NAME_END_POSITION);
								String value = KadaiUtil.obtainDate(workDay, index - KadaiConstants.VALUE_START_POSITION);
								workTimeMap.put(key, value);
							}

							// 日付のnull・空白チェック
							if (KadaiUtil.validate(month) || month.equals("null")) {
								throw new KadaiException(KadaiConstants.INPUT_NULL_OR_BLANK_ERROR);
							}

							// 日付とデータ数のチェック
							checkDateAndDataCount(workTimeMap);

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
					WorkTime workTime = KadaiUtil.setErrorCode(ke.getErrorCode());
					answerList.add(workTime);
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
		return answerMap;
	}

	/**
	 * ファイル読み込み(課題1)
	 *
	 * @param anInputPath 入力ファイル
	 * @return 勤務時間のリスト
	 * @throws KadaiException
	 */
	private static List<WorkTime> readWorkTimeFile(String anInputPath) throws KadaiException {

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
					 if (!oneRecord.contains(KadaiConstants.DATE_END)) {

						// 改行、空白の場合次の行へ
						if (oneRecord.trim().isEmpty()) {
							continue;
						}

						// BOM、制御文字チェック
						checkRecord(oneRecord);

						if (oneRecord.contains(KadaiConstants.DATE_START)) {
							continue;
						} else {
							oneRecord = oneRecord.replace(KadaiConstants.DELIMITER, KadaiConstants.BLANK_CHAR);

							// 文字列が空文字の場合エラー
							if (oneRecord.trim().isEmpty()) {
								throw new KadaiException(KadaiConstants.INPUT_NULL_OR_BLANK_ERROR);
							}
						}

						int index = oneRecord.indexOf(KadaiConstants.ITEM_AND_VALUE_DELIMITER);
						String key = KadaiUtil.obtainDate(oneRecord, KadaiConstants.LV2_ITEM_NAME_START_POSITION,
								index - KadaiConstants.ITEM_NAME_END_POSITION);
						String value = KadaiUtil.obtainDate(oneRecord, index - KadaiConstants.VALUE_START_POSITION);

						workTimeMap.put(key, value);

						continue;
					}

					// 日付とデータ数のチェック
					checkDateAndDataCount(workTimeMap);

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
					WorkTime workTime = KadaiUtil.setErrorCode(ke.getErrorCode());
					answerList.add(workTime);

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
	private static void writeWorkTimeFile(String anOutputPath, List<WorkTime> answerList) throws KadaiException {
		BufferedWriter bufferedWriter = null;

		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(anOutputPath, true), KadaiConstants.CHARACTER_CODE));

			bufferedWriter.write(KadaiConstants.ATTENDANCE_START);
			bufferedWriter.newLine();

			for (WorkTime workTime : answerList) {

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
					bufferedWriter.write(KadaiConstants.DELIMITER);
				}

				bufferedWriter.newLine();

			}
			bufferedWriter.write(KadaiConstants.ATTENDANCE_END);
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

		Matcher startMatch = KadaiConstants.HOUR_PATTERN.matcher(aStartTime);
		Matcher endMatch = KadaiConstants.HOUR_PATTERN.matcher(aEndTime);

		// 数字以外の文字が含まれている場合エラー
		if (!startMatch.matches() || !endMatch.matches()) {
			throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
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
	private static void checkStartEndTime(Date aStartTime, Date aEndTime) throws KadaiException {

		// 出社時刻が24時以降の場合エラー
		if (aStartTime.after(KadaiConstants.START_TIME_LIMIT)) {
			throw new KadaiException(KadaiConstants.INPUT_TIME_CONTROL_ERROR);
		}

		// 退社時刻が次の日の9時以降の場合エラー
		if (aEndTime.after(KadaiConstants.END_TIME_LIMIT)) {
			throw new KadaiException(KadaiConstants.INPUT_TIME_CONTROL_ERROR);
		}

		// 出社時刻が退社時刻以前の時刻の場合エラー
		if (aStartTime.after(aEndTime) || aStartTime.equals(aEndTime)) {
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
			restTime = (aEndTime.getTime() - restStartTime.getTime()) / 1000 / 60;

			// 休憩終了時間 - 休憩開始時間
			long restMinute = (restEndTime.getTime() - restStartTime.getTime()) / 1000 / 60;

			if (restMinute < restTime) {
				restTime = restMinute;
			}

			// 出社時刻が休憩時間より後の場合
			if (aStartTime.after(restStartTime)) {
				// 出社時刻 - 休憩開始時間
				long startRestTime = (aStartTime.getTime() - restStartTime.getTime()) / 1000 / 60;
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
	 * 日付とデータ数のチェック
	 *
	 * @param workTimeMap 勤怠データ
	 * @throws KadaiException
	 */
	private static void checkDateAndDataCount(Map<String, String> workTimeMap) throws KadaiException {

		// 要素が3以外の場合エラー
		if (KadaiConstants.ELEMENT_COUNT != workTimeMap.size()) {
			throw new KadaiException(KadaiConstants.INPUT_FILE_FORMAT_ERROR);
		}

		List<String> keyList = new ArrayList<String>();
		keyList.add(KadaiConstants.DATE);
		keyList.add(KadaiConstants.START);
		keyList.add(KadaiConstants.END);

		for (Entry<String, String> entry : workTimeMap.entrySet()) {
			// 日付のnull・空白チェック
			if (KadaiUtil.validate(entry.getValue()) || entry.getValue().equals("null")) {
				throw new KadaiException(KadaiConstants.INPUT_NULL_OR_BLANK_ERROR);
			}

			if (!keyList.contains(entry.getKey())) {
				throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
			}
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(KadaiConstants.DATE_FORMAT);
		try {
			// 日付として成立する値であるかを調べる
			dateFormat.setLenient(false);
			dateFormat.parse(workTimeMap.get(KadaiConstants.DATE));

		// 日付がyyyyMMddの形式で入力されていない場合エラー
		} catch (ParseException pe) {
			throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);

		// 日付として成立する値が設定されていない場合エラー
		} catch (IllegalArgumentException iae) {
			throw new KadaiException(KadaiConstants.INPUT_CONTROL_ERROR);
		}
	}

	/**
	 * 日付重複チェック
	 *
	 * @param answerMap 勤怠データ
	 * @param key 月
	 * @throws KadaiException
	 */
	 private static void checkOverlapDate(Map<String, List<WorkTime>> answerMap, String key) {
	 	String date = KadaiConstants.BLANK_CHAR;
	 	for (WorkTime workTime : answerMap.get(key)) {

		 	// 日付が重複する場合はエラー
		 	if(date.equals(workTime.getWorkDate())) {
		 		workTime.setErrorCode(KadaiConstants.OUTPUT_OVERLAP_DATE_ERROR);
			}
		 	date = workTime.getWorkDate();
	 	}
	 }
}