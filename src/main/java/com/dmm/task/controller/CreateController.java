package com.dmm.task.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.service.AccountUserDetails;
import com.dmm.task.service.TaskService;

@Controller
public class CreateController {

	@Autowired
	private TaskService ts;

	@GetMapping("/main/create/{date}")
	public String create(@PathVariable("date")String dateStr,Model model) {  
		//↑パスにある日付を取得して、登録画面の日付に反映させる
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(dateStr, formatter);
		model.addAttribute("date", date);
		return "create";
	}

	// 投稿を作成
	@PostMapping("/main/create")
	public String register(@RequestParam("title") String title,@AuthenticationPrincipal AccountUserDetails user, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam("text") String text, RedirectAttributes redirectAttributes){
		Tasks task = new Tasks();
		task.setTitle(title);
		task.setName(user.getName());
		task.setText(text);
		task.setDate(date.atStartOfDay());
		task.setDone(false);

		ts.createTask(task);
		
		redirectAttributes.addFlashAttribute("successMessage", "Task created successfully");
		
		return "redirect:/main";
	}
}
