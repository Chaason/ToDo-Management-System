package com.dmm.task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CalendarTest {
	public static void main(String[] args) {
		// 1.二次元表となるListのList
		List<List<LocalDate>> month = new ArrayList<>();
		// 2.1週間分のLocalDateを格納するList
		List<LocalDate> week = new ArrayList<>();

		// 3.当月の1日のLocalDateを取得（例：今日が7月なら2024年7月1日）
		LocalDate now = LocalDate.now().withDayOfMonth(1);
		// 4.当月1日の曜日を取得（例：2024年7月1日の曜日）
		DayOfWeek w = now.getDayOfWeek();
		// 4.週頭のLocalDateを取得（例：d=2024/7/1 なら2024年6月30日)
		LocalDate day = now.minusDays(w.getValue());
		System.out.println(day);
		// 当月の最終日を取得
		final int lastDay = now.lengthOfMonth();
		System.out.println(lastDay);
		
		/*
		// 最終日までforでループ
		for (int i = 1; i <= lastDay + 1; i++) {
			week.add(day);
			if (day.getDayOfWeek() == DayOfWeek.SATURDAY || day == yearMonth.atEndOfMonth()) {
				month.add(week);
				week = new ArrayList<>();
			}
			day = day.plusDays(1); // 5.1日ずつ増やしてLocalDateを求める
			System.out.println(day);
		}
		*/

		while (day.isBefore(now.plusMonths(1).withDayOfMonth(1).plusWeeks(1))) {
            week.add(day); // 5. 1日ずつ増やしてLocalDateを求めていき、Listへ格納する
            if (day.getDayOfWeek() == DayOfWeek.SATURDAY) {
                month.add(week); // 1週間分詰めたら1.のリストへ格納する
                week = new ArrayList<>(); // 新しいListを生成する
            }
            day = day.plusDays(1); // 1日ずつ増やす
        }

		// テスト用コンソール表示
		for (List<LocalDate> weekList : month) {
			//System.out.println(weekList + " ");
			for (LocalDate date : weekList) {
				System.out.print(date + " ");
			}
			System.out.println();
		}
		
		//カレンダーに年月を表示させるためのフィールド（例:2024年07月）
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月");
				//当月の年月を取得
				String formattedNow = now.format(formatter);
				System.out.println(formattedNow);
				//前月の年月を取得
				LocalDate prev = now.minusMonths(1);
				String formattedPrev = prev.format(formatter);
				System.out.println(formattedPrev);
				//次月の年月を取得
				LocalDate next = now.plusMonths(1);
				String formattedNext = next.format(formatter);
				System.out.println(formattedNext);

	}
}
