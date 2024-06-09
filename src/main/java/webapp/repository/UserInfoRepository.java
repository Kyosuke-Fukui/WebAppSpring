package webapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webapp.entity.AuthUser;

@Repository
public interface UserInfoRepository extends JpaRepository<AuthUser, Integer> {
	//Entityで定義したフィールド名に合わせて自分で定義
	Optional<AuthUser> findByUsername(String username);
}


