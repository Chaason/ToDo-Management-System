package com.dmm.task.form;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskForm {
	private Integer id;
	private String title;
	private String name;
	private String text;
	private LocalDateTime date;
	private boolean done;
}
