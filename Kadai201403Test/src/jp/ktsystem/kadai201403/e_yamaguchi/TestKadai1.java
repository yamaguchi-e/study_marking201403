package jp.ktsystem.kadai201403.e_yamaguchi;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
/**
 * <h3>テストクラス</h3>
 *
 * @author e_yamaguti
 * @since 2014/04/05
 */
public class TestKadai1 extends TestCase {

	private static final String SAMPLE_RESULT_PATH = "c:\\temp\\input\\";
	private static final String SAMPLE_OUTPUT_PATH = "c:\\temp\\output\\";

	/**
	 * 成功ケーステスト
	 * @param anInputPath  入力ファイル
	 * @param anOutputPath 出力ファイル
	 */
	private void execute(String anInputPath, String anOutputPath, List<WorkTime> answer) {

		try {
			Kadai.parseWorkTimeData(anInputPath, SAMPLE_OUTPUT_PATH + anOutputPath);
		} catch(KadaiException e) {
			fail("例外が発生しました（エラーコード：" + e.getErrorCode() + "）");
		} catch(Exception e) {
			fail("例外が発生しました：" + e.getMessage());
		}
	}

	/**
	 * エラーケーステスト
	 * @param anInputPath   入力ファイル
	 * @param anOutputPath  出力ファイル
	 * @param errorCode     エラーコード
	 */
	private void executeInvalid(String anInputPath, String anOutputPath, String errorCode) {
		try {
			Kadai.parseWorkTimeData(anInputPath, anOutputPath);
			fail("なぜ成功する？");
		} catch(KadaiException e) {
			assertEquals(errorCode, e.getErrorCode());
		}
	}

	public void testCase1() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "60", "60", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case1.txt", "Case1.txt", answerList);
	}

	public void testCase2() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "90", "90", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case2.txt", "Case2.txt", answerList);
	}

	public void testCase3() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "180", "180", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case3.txt", "Case3.txt", answerList);
	}

	public void testCase4() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "180", "180", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case4.txt", "Case4.txt", answerList);
	}

	public void testCase5() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "179", "179", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case5.txt", "Case5.txt", answerList);
	}

	public void testCase6() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "1", "1", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case6.txt", "Case6.txt", answerList);
	}

	public void testCase7() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "3", answerList);

		execute(SAMPLE_RESULT_PATH + "Case7.txt", "Case7.txt", answerList);
	}

	public void testCase8() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "59", "59", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case8.txt", "Case8.txt", answerList);
	}

	public void testCase9() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "60", "60", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case9.txt", "Case9.txt", answerList);
	}

	public void testCase10() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "120", "120", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case10.txt", "Case10.txt", answerList);
	}

	public void testCase11() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "599", "599", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case11.txt", "Case11.txt", answerList);
	}

	public void testCase12() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "180", "180", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case12.txt", "Case12.txt", answerList);
	}

	public void testCase13() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "480", "480", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case13.txt", "Case13.txt", answerList);
	}

	public void testCase14() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "300", "300", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case14.txt", "Case14.txt", answerList);
	}

	public void testCase15() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "180", "180", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case15.txt", "Case15.txt", answerList);
	}

	public void testCase16() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "0", "0", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case16.txt", "Case16.txt", answerList);
	}

	public void testCase17() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "90", "90", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case17.txt", "Case17.txt", answerList);
	}

	public void testCase18() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "300", "300", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case18.txt", "Case18.txt", answerList);
	}

	public void testCase19() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "0", "0", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case19.txt", "Case19.txt", answerList);
	}

	public void testCase20() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "510", "510", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case20.txt", "Case20.txt", answerList);
	}

	public void testCase21() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "60", "60", KadaiConstants.BLANK_CHAR, answerList);
		answerList = setList("20140402", "480", "540", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case21.txt", "Case21.txt", answerList);
	}

	public void testCase22() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "60", "60", KadaiConstants.BLANK_CHAR, answerList);
		answerList = setList("20140402", "480", "540", KadaiConstants.BLANK_CHAR, answerList);
		answerList = setList("20140403", "240", "780", KadaiConstants.BLANK_CHAR, answerList);
		answerList = setList("20140404", "150", "930", KadaiConstants.BLANK_CHAR, answerList);
		answerList = setList("20140405", "150", "1080", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case22.txt", "Case22.txt", answerList);
	}

	public void testCase23() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "60", "60", KadaiConstants.BLANK_CHAR, answerList);
		answerList = setList("20140402", "480", "540", KadaiConstants.BLANK_CHAR, answerList);
		answerList = setList("20140403", "240", "780", KadaiConstants.BLANK_CHAR, answerList);
		answerList = setList(null, null, null, "4", answerList);

		execute(SAMPLE_RESULT_PATH + "Case23.txt", "Case23.txt", answerList);
	}

	public void testCase24() {
		List<WorkTime> answerList1 = new ArrayList<WorkTime>();
		answerList1 = setList("20140401", "90", "90", KadaiConstants.BLANK_CHAR, answerList1);


		List<WorkTime> answerList2 = new ArrayList<WorkTime>();
		answerList2 = setList("20140402", "300", "300", KadaiConstants.BLANK_CHAR, answerList2);

		execute(SAMPLE_RESULT_PATH + "Case24.txt", "Case24.txt", answerList1);
		execute(SAMPLE_RESULT_PATH + "Case25a.txt", "Case24.txt", answerList2);
	}

	public void testCase25() {
		List<WorkTime> answerList1 = new ArrayList<WorkTime>();
		answerList1 = setList("20140401", "90", "90", KadaiConstants.BLANK_CHAR, answerList1);
		answerList1 = setList(null, null, null, "2", answerList1);

		List<WorkTime> answerList2 = new ArrayList<WorkTime>();
		answerList2 = setList("20140402", "300", "300", KadaiConstants.BLANK_CHAR, answerList2);
		answerList2 = setList("20140403", "90", "390", KadaiConstants.BLANK_CHAR, answerList2);

		execute(SAMPLE_RESULT_PATH + "Case25b.txt", "Case25.txt", answerList1);
		execute(SAMPLE_RESULT_PATH + "Case25a.txt", "Case25.txt", answerList2);
	}

	public void testCase26() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "1", answerList);

		execute(SAMPLE_RESULT_PATH + "Case26.txt", "Case26.txt", answerList);
	}

	public void testCase27() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "5", answerList);

		execute(SAMPLE_RESULT_PATH + "Case27.txt", "Case27.txt", answerList);
	}

	public void testCase28() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case28.txt", "Case28.txt", answerList);
	}

	public void testCase29() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case29.txt", "Case29.txt", answerList);
	}

	public void testCase30() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case30.txt", "Case30.txt", answerList);
	}

	public void testCase31() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case31.txt", "Case31.txt", answerList);
	}

	public void testCase32() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case32.txt", "Case32.txt", answerList);
	}

	public void testCase33() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case33.txt", "Case33.txt", answerList);
	}

	public void testCase34() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case34.txt", "Case34.txt", answerList);
	}

	public void testCase35() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case35.txt", "Case35.txt", answerList);
	}

	public void testCase36() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case36.txt", "Case36.txt", answerList);
	}

	public void testCase37() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case37.txt", "Case37.txt", answerList);
	}

	public void testCase38() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case38.txt", "Case38.txt", answerList);
	}

	public void testCase39() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case39.txt", "Case39.txt", answerList);
	}

	public void testCase40() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "1", answerList);

		execute(SAMPLE_RESULT_PATH + "Case40.txt", "Case40.txt", answerList);
	}

	public void testCase41() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case41.txt", "Case41.txt", answerList);
	}

	public void testCase42() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case42.txt", "Case42.txt", answerList);
	}

	public void testCase43() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case43.txt", "Case43.txt", answerList);
	}

	public void testCase44() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case44.txt", "Case44.txt", answerList);
	}

	public void testCase45() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case45.txt", "Case45.txt", answerList);
	}

	public void testCase46() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case46.txt", "Case46.txt", answerList);
	}

	public void testCase47() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "3", answerList);

		execute(SAMPLE_RESULT_PATH + "Case47.txt", "Case47.txt", answerList);
	}

	public void testCase48() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "3", answerList);

		execute(SAMPLE_RESULT_PATH + "Case48.txt", "Case48.txt", answerList);
	}

	public void testCase49() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "4", answerList);

		execute(SAMPLE_RESULT_PATH + "Case49.txt", "Case49.txt", answerList);
	}

	public void testCase50() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "4", answerList);

		execute(SAMPLE_RESULT_PATH + "Case50.txt", "Case50.txt", answerList);
	}

	public void testCase51() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "4", answerList);

		execute(SAMPLE_RESULT_PATH + "Case51.txt", "Case51.txt", answerList);
	}

	public void testCase52() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "5", answerList);

		execute(SAMPLE_RESULT_PATH + "Case52.txt", "Case52.txt", answerList);
	}

	public void testCase53() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "5", answerList);

		execute(SAMPLE_RESULT_PATH + "Case53.txt", "Case53.txt", answerList);
	}

	public void testCase55() {
		executeInvalid(null, "Case55.txt", "6");
	}

	public void testCase56() {
		executeInvalid("", "Case56.txt", "6");
	}

	public void testCase57() {
		executeInvalid(SAMPLE_RESULT_PATH + "aaa.txt", "Case57.txt", "7");
	}

	public void testCase58() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case58.txt", null, "9");
	}

	public void testCase59() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case59.txt", "", "9");
	}

	public void testCase60() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case53.txt", "\\fail\\Case60.txt", "10");
	}

	public void testCase61() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "1", answerList);

		execute(SAMPLE_RESULT_PATH + "Case61.txt", "Case61.txt", answerList);
	}

	public void testCase62() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "1", answerList);

		execute(SAMPLE_RESULT_PATH + "Case62.txt", "Case62.txt", answerList);
	}

	public void testCase63() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "1", answerList);

		execute(SAMPLE_RESULT_PATH + "Case63.txt", "Case63.txt", answerList);
	}

	public void testCase64() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "1", answerList);

		execute(SAMPLE_RESULT_PATH + "Case64.txt", "Case64.txt", answerList);
	}

	public void testCase65() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "1", answerList);

		execute(SAMPLE_RESULT_PATH + "Case65.txt", "Case65.txt", answerList);
	}

	public void testCase66() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "1", answerList);

		execute(SAMPLE_RESULT_PATH + "Case66.txt", "Case66.txt", answerList);
	}

	public void testCase67() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case67.txt", "Case67.txt", answerList);
	}

	public void testCase68() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case68.txt", "Case68.txt", answerList);
	}

	public void testCase69() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case69.txt", "Case69.txt", answerList);
	}

	public void testCase70() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case70.txt", "Case70.txt", answerList);
	}

	public void testCase71() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case71.txt", "Case71.txt", answerList);
	}

	public void testCase72() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case72.txt", "Case72.txt", answerList);
	}

	public void testCase73() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "60", "60", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case73.txt", "Case73.txt", answerList);
	}

	public void testCase74() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "60", "60", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case74.txt", "Case74.txt", answerList);
	}

	public void testCase75() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "60", "60", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case75.txt", "Case75.txt", answerList);
	}

	public void testCase76() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "60", "60", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case76.txt", "Case76.txt", answerList);
	}

	public void testCase77() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList("20140401", "60", "60", KadaiConstants.BLANK_CHAR, answerList);

		execute(SAMPLE_RESULT_PATH + "Case77.txt", "Case77.txt", answerList);
	}

	public void testCase78() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case78.txt", "Case78.txt", answerList);
	}

	public void testCase79() {
		List<WorkTime> answerList = new ArrayList<WorkTime>();
		answerList = setList(null, null, null, "2", answerList);

		execute(SAMPLE_RESULT_PATH + "Case79.txt", "Case79.txt", answerList);
	}

	/**
	 * @param workDate
	 * @param workTime
	 * @param sumWorkTime
	 * @param errorCode
	 * @return
	 */
	private List<WorkTime> setList(String workDate, String workTime,
			String sumWorkTime, String errorCode, List<WorkTime> answerList) {
		WorkTime work = new WorkTime();
		work.setWorkDate(workDate);
		work.setWorkTime(workTime);
		work.setSumWorkTime(sumWorkTime);
		work.setErrorCode(errorCode);

		answerList.add(work);
		return answerList;
	}
}
