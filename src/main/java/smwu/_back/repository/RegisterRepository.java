package smwu._back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smwu._back.domain.UserInfoVO;

import java.util.List;

@Repository
public interface RegisterRepository  extends JpaRepository<UserInfoVO, String> {
//    public List<UserInfoVO> findByID(String id);

    @Query("SELECT e FROM Users  e WHERE e.id= :id")
    public List<UserInfoVO> listUserinfoWithID (@Param("id") String id);

    @Query("SELECT e FROM Users  e WHERE e.email= :email")
    public List<UserInfoVO> listUserinfoWithEMAIL (@Param("email") String email);

    @Query("SELECT e FROM Users  e WHERE e.phone= :phone")
    public List<UserInfoVO> listUserinfoWithPHONE (@Param("phone") String phone);

}
