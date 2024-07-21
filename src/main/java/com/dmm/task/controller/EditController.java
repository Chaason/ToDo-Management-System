package com.dmm.task.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.service.AccountUserDetails;
import com.dmm.task.service.TaskService;

@Controller
public class EditController {
	@Autowired
	private TaskService ts;

	@PostMapping("/main/edit/{id}")
	// 更新画面
	public String updateTask(@PathVariable("id") Integer id, @RequestParam("title") String title,
			@AuthenticationPrincipal AccountUserDetails user,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
			@RequestParam("text") String text, @RequestParam(value = "done", required = false) boolean done,
			Model model) {
		Tasks task = new Tasks();
		model.addAttribute("task", task);
		Tasks taskData = new Tasks();
		taskData.setTitle(title);
		taskData.setName(user.getName());
		taskData.setText(text);
		taskData.setDate(date.atStartOfDay());
		taskData.setDone(done);
		ts.updateTask(id, taskData);
		return "redirect:/main";
	}

	@PostMapping("/main/delete/{id}")
	// 削除画面
	public String deleteTask(@PathVariable("id") Integer id, Model model) {
		Tasks task = ts.getTaskById(id);
		model.addAttribute("task", task);
		ts.deleteTask(id);
		return "redirect:/main";
	}

}
