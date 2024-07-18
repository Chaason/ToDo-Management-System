package com.dmm.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.TaskForm;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class CreateController {

	@Autowired
	private TasksRepository repo;

	@GetMapping("/main/create/{date}")
	public String create() {
		return "create";
	}

	// 投稿を作成
	@PostMapping("/main/create/{date}")
	public String register(@Validated TaskForm taskForm,BindingResult bindingResult, @AuthenticationPrincipal AccountUserDetails user, Model model) {
		Tasks task = new Tasks();
		task.setTitle(taskForm.getTitle());
		task.setName(user.getName());
		task.setText(taskForm.getText());
		task.setDate(taskForm.getDate());
		task.setDone(taskForm.isDone());

		repo.save(task);

		return "redirect:/main";
	}
}
