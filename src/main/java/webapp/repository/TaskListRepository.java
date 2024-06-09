package webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webapp.entity.TaskList;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Integer> {
	/* JpaRepositoryを継承する事で、fineOne、findAll、save、deleteの4メソッドが使用できる
	ジェネリクスはエンティティのクラス名と、主キーの型を指定する */
	List<TaskList> findByUsername(String username);
}
