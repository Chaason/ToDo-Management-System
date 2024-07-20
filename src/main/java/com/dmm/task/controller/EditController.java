package com.dmm.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.service.TaskService;

@Controller
public class EditController {
	@Autowired
	private TaskService ts;
	
	@GetMapping("/main/edit/{id}")
	//idに対応したタスクを取得してedit.htmlに反映させる
	public String edit(@PathVariable("id") Integer id, Model model) {
		Tasks task = ts.getTaskById(id);
		model.addAttribute("task", task);
		return "edit";
	}
}
