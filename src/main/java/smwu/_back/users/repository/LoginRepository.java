package smwu._back.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smwu._back.users.domain.UserInfoVO;

import java.util.List;

@Repository
public interface LoginRepository extends JpaRepository<UserInfoVO, String> {

    @Query("SELECT e FROM Users  e WHERE e.id= :id")
    public UserInfoVO finduserWithID (@Param("id") String id);


}
