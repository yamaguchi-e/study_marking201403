package jp.ktsystem.kadai201403.e_yamaguchi;

import junit.framework.TestCase;
/**
 * <h3>テストクラス(課題2)</h3>
 *
 * @author e_yamaguti
 * @since 2014/04/10
 */
public class TestKadai2 extends TestCase {

	private static final String SAMPLE_RESULT_PATH = "c:\\temp\\input2\\";
	private static final String SAMPLE_OUTPUT_PATH = "c:\\temp\\output2\\";

	private void execute(String anInputPath, String anOutputPath) {

		try {
			Kadai.parseWorkTimeDataLv2(anInputPath, anOutputPath);

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
			Kadai.parseWorkTimeDataLv2(anInputPath, anOutputPath);
			fail("なぜ成功する？");
		} catch(KadaiException e) {
			assertEquals(errorCode, e.getErrorCode());
		}
	}

	public void testCase1() {
		execute(SAMPLE_RESULT_PATH + "Case1.txt", SAMPLE_OUTPUT_PATH + "Case1\\");
	}

	public void testCase2() {
		execute(SAMPLE_RESULT_PATH + "Case2.txt", SAMPLE_OUTPUT_PATH + "Case2\\");
	}

	public void testCase3() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case3.txt", SAMPLE_OUTPUT_PATH + "Case3\\", "2");
	}

	public void testCase4() {
		execute(SAMPLE_RESULT_PATH + "Case4.txt", SAMPLE_OUTPUT_PATH + "Case4\\");
	}

	public void testCase5() {
		execute(SAMPLE_RESULT_PATH + "Case5.txt", SAMPLE_OUTPUT_PATH + "Case5\\");
	}

	public void testCase6() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case6.txt", SAMPLE_OUTPUT_PATH, "2");
	}

	public void testCase7() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case7.txt", SAMPLE_OUTPUT_PATH, "2");
	}

	public void testCase8() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case8.txt", SAMPLE_OUTPUT_PATH, "2");
	}

	public void testCase9() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case9.txt", SAMPLE_OUTPUT_PATH, "2");
	}

	public void testCase10() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case10.txt", SAMPLE_OUTPUT_PATH, "2");
	}

	public void testCase11() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case11.txt", SAMPLE_OUTPUT_PATH, "2");
	}

	public void testCase12() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case12.txt", SAMPLE_OUTPUT_PATH, "2");
	}

	public void testCase13() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case13.txt", SAMPLE_OUTPUT_PATH, "2");
	}

	public void testCase14() {
		execute(SAMPLE_RESULT_PATH + "Case14.txt", SAMPLE_OUTPUT_PATH + "Case14\\");
	}

	public void testCase15() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case15.txt", null, "9");
	}

	public void testCase16() {
		executeInvalid(SAMPLE_RESULT_PATH + "Case16.txt", "", "9");
	}

	public void testCase17() {
		executeInvalid(null, "Case17.txt", "6");
	}

	public void testCase18() {
		executeInvalid("", "Case18.txt", "6");
	}
}
