package com.cqxb.yecall.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

import com.cqxb.yecall.bean.ContactBean;
import com.cqxb.yecall.t9search.model.Contacts;

public class SearchHelper {
	/**
	 * 按号码-拼音搜索联系人
	 * 
	 * @param str
	 */
	public static ArrayList<Contacts> search(final String str,
			final List<Contacts> allContacts) {
		ArrayList<Contacts> contactList = new ArrayList<Contacts>();
		// 如果搜索条件以0 1 +开头则按号码搜索
		if (str.startsWith("0") || str.startsWith("1") || str.startsWith("+")) {
			for (Contacts contact : allContacts) {
				if (contact.getNumber() != null && contact.getContactName() != null) {
					if (contact.getNumber().contains(str)
							|| contact.getContactName().contains(str)) {
						contact.setGroup(str);
						contactList.add(contact);
					}
				}
			}
			return contactList;
		}

		// final ChineseSpelling finder = ChineseSpelling.getInstance();
		// finder.setResource(str);
		// final String result = finder.getSpelling();
		// 先将输入的字符串转换为拼音
		// final String result = PinYinUtil.getFullSpell(str);
		final String result = PinYin.getPinYin(str);
		for (Contacts contact : allContacts) {
			if (contains(contact, result)) {
				contactList.add(contact);
			}
		}

		return contactList;
	}


	/**
	 * 根据拼音搜索
	 * 
	 * @param str
	 *            正则表达式
	 * @param pyName
	 *            拼音
	 * @param isIncludsive
	 *            搜索条件是否大于6个字符
	 * @return
	 */
	public static boolean contains(Contacts contact, String search) {
		if (TextUtils.isEmpty(contact.getContactName()) || TextUtils.isEmpty(search)) {
			return false;
		}

		boolean flag = false;

		// 简拼匹配,如果输入在字符串长度大于6就不按首字母匹配了
		if (search.length() < 6) {
			// String firstLetters = FirstLetterUtil.getFirstLetter(contact
			// .getName());
			// 获得首字母字符串
			String firstLetters = UnicodeGBK2Alpha
					.getSimpleCharsOfString(contact.getContactName());
			// String firstLetters =
			// PinYinUtil.getFirstSpell(contact.getName());
			// 不区分大小写
			Pattern firstLetterMatcher = Pattern.compile("^" + search,
					Pattern.CASE_INSENSITIVE);
			flag = firstLetterMatcher.matcher(firstLetters).find();
		}

		if (!flag) { // 如果简拼已经找到了，就不使用全拼了
			// 全拼匹配
			// ChineseSpelling finder = ChineseSpelling.getInstance();
			// finder.setResource(contact.getName());
			// 不区分大小写
			Pattern pattern2 = Pattern
					.compile(search, Pattern.CASE_INSENSITIVE);
			Matcher matcher2 = pattern2.matcher(PinYin.getPinYin(contact
					.getContactName()));
			flag = matcher2.find();
		}

		return flag;
	}
}
