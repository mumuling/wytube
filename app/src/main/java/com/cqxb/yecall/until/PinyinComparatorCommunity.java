package com.cqxb.yecall.until;

import java.util.Comparator;

import com.cqxb.yecall.t9search.model.Contacts;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparatorCommunity implements Comparator<Contacts> {

	public int compare(Contacts o1, Contacts o2) {
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
