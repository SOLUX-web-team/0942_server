package smwu._back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smwu._back.domain.User;

import java.util.List;

@Repository
public interface RegisterRepository  extends JpaRepository<User, String> {
//    public List<UserInfoVO> findByID(String id);

    @Query("SELECT e FROM Users  e WHERE e.id= :id")
    public List<User> listUserinfoWithID (@Param("id") String id);

    @Query("SELECT e FROM Users  e WHERE e.email= :email")
    public List<User> listUserinfoWithEMAIL (@Param("email") String email);

    @Query("SELECT e FROM Users  e WHERE e.phone= :phone")
    public List<User> listUserinfoWithPHONE (@Param("phone") String phone);

}
