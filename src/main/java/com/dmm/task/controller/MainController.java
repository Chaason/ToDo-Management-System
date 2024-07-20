package com.dmm.task.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.service.CalendarService;
import com.dmm.task.service.TaskService;

@Controller
public class MainController {
	@Autowired
	private CalendarService cs;
	@Autowired
	private TaskService ts;
	

	@GetMapping("/main")
	public String main(@RequestParam(value = "date", required = false) String date, Model model, Principal principal) {
		// 現在の日付を取得
		LocalDate currentDate = (date != null) ? LocalDate.parse(date) : LocalDate.now();
		// currentDateから現在の年月を取得
		YearMonth yearMonth = YearMonth.from(currentDate);
		// CalendarServiceからカレンダーの情報を取得
		List<List<LocalDate>> month = cs.createCalendar(yearMonth.getYear(), yearMonth.getMonthValue());
		// カレンダーに年月を表示させる（例:2024年07月）
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月");
		String formattedMonth = yearMonth.format(formatter);

		// カレンダーの最初と最後の日付を取得
		LocalDateTime firstDate = month.get(0).get(0).atStartOfDay();
		LocalDateTime lastDate = month.get(month.size() - 1).get(month.get(month.size() - 1).size() - 1).atTime(23, 59,59);
		
		//ログインユーザーのロールを取得
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
		
		//タスクを取得
		List<Tasks> tasksList ;
		if (isAdmin) {
			tasksList = ts.getTasksByAllUser(firstDate, lastDate);
		}else {
			tasksList = ts.getTasksByUser(firstDate, lastDate, principal.getName());
		}
		MultiValueMap<LocalDate, Tasks> tasksByDate = new LinkedMultiValueMap<>();
		for(Tasks task : tasksList) {
			tasksByDate.add(task.getDate().toLocalDate(), task);
		}

		model.addAttribute("matrix", month);
		model.addAttribute("month", formattedMonth);
		model.addAttribute("prev", currentDate.minusMonths(1));
		model.addAttribute("next", currentDate.plusMonths(1));
		model.addAttribute("tasks", tasksByDate);

		return "main";
	}
}
