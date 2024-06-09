package webapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
//省略した場合は、クラス名を大文字にした名前のテーブルにMappingされる
@Table(name = "t_task")
@Data
public class TaskList {
//	PRIMARY KEY の付いているカラムに@Idを付ける
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //DBが自動生成する場合
	@Column(name = "task_id") //変数名とDBの列名が異なる場合
	private int taskId;
	
	@Column(name = "dept_name")
	private String deptName;
	
	@Column(name = "doc_name")
	private String docName;
	
	@Column(name = "task_status")
	private String taskStatus;
	
	@Column(name = "reg_username") 
	private String username;
	
	@Column(name = "reg_datetime")
	private String datetime;
	
	@Column(name = "upd_username") 
	private String upd_username;
	
	@Column(name = "upd_datetime")
	private String upd_datetime;
}
