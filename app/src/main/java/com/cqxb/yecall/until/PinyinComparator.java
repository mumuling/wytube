package com.cqxb.yecall.until;

import java.util.Comparator;

import com.cqxb.yecall.bean.UserBean;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<UserBean> {

	public int compare(UserBean o1, UserBean o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
