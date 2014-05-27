package jp.ktsystem.kadai201403.e_yamaguchi;

/**
 * <h3>エラーを返すクラス</h3>
 *
 * @author e_yamaguti
 * @since 2013/07/30
 */
public class KadaiException extends Exception{

	private static final long serialVersionUID = 1L;
	private String errorCode;

	/**
	 * エラーコードを取得
	 *
	 * @return エラーコード
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * エラーコードを指定してオブジェクトを構築
	 *
	 * @param error エラーコード
	 */
	public KadaiException(String error) {
		errorCode = error;
	}
}
