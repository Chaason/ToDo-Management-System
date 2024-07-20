package com.dmm.task.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CalendarService {
	public List<List<LocalDate>> createCalendar(int year, int month) {
		// 1.二次元表となるListのList
		List<List<LocalDate>> calendar = new ArrayList<>();
		// 2.1週間分のLocalDateを格納するList
		List<LocalDate> week = new ArrayList<>();
		
		YearMonth yearMonth = YearMonth.of(year, month);

		// 3.当月の1日のLocalDateを取得（例：今日が7月なら2024年7月1日）
		LocalDate firstOfMonth = yearMonth.atDay(1);
		// 4.当月1日の曜日を取得（例：2024年7月1日の曜日）
		DayOfWeek w = firstOfMonth.getDayOfWeek();
		// 4.週頭のLocalDateを取得（例：d=2024/7/1 なら2024年6月30日)
		LocalDate day = firstOfMonth.minusDays(w.getValue());

		//表示するカレンダーの範囲を決めてループさせる
		while (day.isBefore(firstOfMonth.plusMonths(1).withDayOfMonth(1).plusWeeks(1))) {
			week.add(day); // 5. 1日ずつ増やしてLocalDateを求めていき、Listへ格納する
			if (day.getDayOfWeek() == DayOfWeek.SATURDAY) {
				calendar.add(week); // 1週間分詰めたら1.のリストへ格納する
				week = new ArrayList<>(); // 新しいListを生成する
			}
			day = day.plusDays(1); // 1日ずつ増やす
		}
		return calendar;
	}
}
