package com.dmm.task.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;

@Service
public class TaskService {

	@Autowired
	private TasksRepository taskRepository;

	public List<Tasks> getTasksByAllUser(LocalDateTime from, LocalDateTime to) {
		return taskRepository.findAllByDateBetween(from, to);
	}

	
	public List<Tasks> getTasksByUser(LocalDateTime from, LocalDateTime to, String name) {
		return taskRepository.findByDateBetween(from, to, name);
	}

	public Tasks getTaskById(Integer id) {
		return taskRepository.findById(id).orElse(null);
	}
	
	public Tasks createTask(Tasks task) {
		return taskRepository.save(task);
	}

	public Tasks updateTask(Integer id, Tasks taskDetails) {
		Tasks task = getTaskById(id);
		if (task != null) {
			task.setTitle(taskDetails.getTitle());
			task.setName(taskDetails.getName());
			task.setDate(taskDetails.getDate());
			task.setText(taskDetails.getText());
			task.setDone(taskDetails.isDone());

			taskRepository.save(task);
		}
		return task;
	}

	public void deleteTask(Integer id) {
		taskRepository.deleteById(id);
	}
}