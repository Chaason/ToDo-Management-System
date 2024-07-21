package com.dmm.task.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.service.AccountUserDetails;
import com.dmm.task.service.TaskService;

@Controller
public class CreateController {

	@Autowired
	private TaskService ts;

	// 投稿を作成
	@PostMapping("/main/create")
	public String register(@RequestParam("title") String title, @AuthenticationPrincipal AccountUserDetails user,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
			@RequestParam("text") String text) {
		Tasks task = new Tasks();
		task.setTitle(title);
		task.setName(user.getName());
		task.setText(text);
		task.setDate(date.atStartOfDay());
		task.setDone(false);

		ts.createTask(task);

		return "redirect:/main";
	}
}
