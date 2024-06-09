package webapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import webapp.entity.TaskList;
import webapp.repository.TaskListRepository;

//	/* Spring Data JPAがリポジトリの実装を自動的に生成し、
//	 * @Autowiredによってそれをサービスクラスに注入する */
//	@Autowired

@Service
/* private finalで宣言したインスタンスに対して、newしたものを注入する、
 * コンストラクタを実装してくれる */
@RequiredArgsConstructor
public class TaskListService {
	
	private final TaskListRepository repository;

	public List<TaskList> getAllTasks() {
		return repository.findAll();
	}

	public List<TaskList> getTasksByUsername(String username) {
        return repository.findByUsername(username);
    }
	
	public void saveTask(TaskList task) {
		repository.save(task);
    }
	
	public TaskList getTaskById(int taskId) {
        return repository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + taskId));
    }
	
	public void deleteTask(TaskList task) {
        repository.delete(task);
    }
}
