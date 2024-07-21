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

	// 管理者用メソッド。全ユーザーのタスクをDBから取得
	public List<Tasks> getTasksByAllUser(LocalDateTime from, LocalDateTime to) {
		return taskRepository.findAllByDateBetween(from, to);
	}

	// ユーザー用メソッド。ユーザー自身のタスクを取得
	public List<Tasks> getTasksByUser(LocalDateTime from, LocalDateTime to, String name) {
		return taskRepository.findByDateBetween(from, to, name);
	}

	// IDごとにタスクを取得するメソッド
	public Tasks getTaskById(Integer id) {
		return taskRepository.findById(id).orElse(null);
	}

	// タスクの新規登録メソッド
	public Tasks createTask(Tasks task) {
		return taskRepository.save(task);
	}

	// タスクの編集用メソッド
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

	// タスクの削除用メソッド
	public void deleteTask(Integer id) {
		taskRepository.deleteById(id);
	}
}