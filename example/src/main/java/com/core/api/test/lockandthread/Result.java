package com.core.api.test.lockandthread;

/**
 * 选手某一次跑步的成绩
 * @author yinwenjie
 *
 */
public class Result {

	 /**
     * 记录了本次赛跑的用时情况
     */
    private float time;

	public Result(float time) {
		super();
		this.time = time;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	
}
