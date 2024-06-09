package webapp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import webapp.entity.TaskList;
import webapp.service.TaskListService;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/tasklist")←今回はGetMappingを使う
public class TaskListController {
	private final TaskListService taskListService;

	//全体進捗一覧
	@GetMapping("/tasklist")
	public String getAllTasks(Model model) {
		model.addAttribute("taskList", taskListService.getAllTasks());
		return "task_list"; // task_list.htmlを返す
	}

	//ユーザ進捗一覧
	@GetMapping("/userTasklist")
	public String getUserTasks(@AuthenticationPrincipal User user, Model model) {
		List<TaskList> tasks = taskListService.getTasksByUsername(user.getUsername());
		model.addAttribute("taskList", tasks);
		return "user_task_list";
	}

	//新規業務追加

	//GETリクエストに対して、新しいTaskListオブジェクトをモデルに追加してフォームを表示
	@GetMapping("/userTaskInsert")
	public String showTaskInsertForm(Model model) {
		model.addAttribute("userTaskList", new TaskList());
		return "user_task_insert";
	}

	@PostMapping("/userTaskInsert")
	public String userTaskInsert(@ModelAttribute("userTaskList") TaskList userTaskList,
			@AuthenticationPrincipal User user) {
		userTaskList.setTaskStatus("審査中");
		userTaskList.setUsername(user.getUsername());
		userTaskList.setUpd_username(user.getUsername());
		userTaskList.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		userTaskList.setUpd_datetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		taskListService.saveTask(userTaskList);
		return "user_task_insert";
	}

	//進捗情報更新

	@GetMapping("/taskUpdate")
	public String showTaskUpdateForm(@RequestParam("task_id") int taskId, Model model) {
		TaskList task = taskListService.getTaskById(taskId);
		System.out.println(task);
		model.addAttribute("task", task);
		return "user_task_update";
	}

	@PostMapping("/taskUpdate")
	public String taskUpdate(@RequestParam("taskId") int taskId) {
//		System.out.println(taskId);
		TaskList task = taskListService.getTaskById(taskId);
		task.setTaskStatus("審査済");
		task.setUpd_datetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		taskListService.saveTask(task);
		return "redirect:/userTasklist";
	}
	
	//タスク削除
	@PostMapping("/taskDelete")
	public String taskDelete(@RequestParam("taskId") int taskId) {
//		System.out.println(taskId);
		TaskList task = taskListService.getTaskById(taskId);
		if (task != null) {
            taskListService.deleteTask(task);
        }
		return "redirect:/userTasklist";
	}
}
