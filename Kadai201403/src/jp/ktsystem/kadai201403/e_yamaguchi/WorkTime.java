package jp.ktsystem.kadai201403.e_yamaguchi;

import java.io.Serializable;

/**
 * <h3>ファイル出力項目</h3>
 *
 * @author e_yamaguti
 * @since 2013/12/08
 */
public class WorkTime implements Serializable {

	private String workDate;
	private String workTime;
	private String sumWorkTime;
	private String errorCode;

	public WorkTime() {
	}

	public WorkTime(String error) {
		errorCode = error;
	}

	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getSumWorkTime() {
		return sumWorkTime;
	}
	public void setSumWorkTime(String sumWorkTime) {
		this.sumWorkTime = sumWorkTime;
	}



}

