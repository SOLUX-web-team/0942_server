package smwu._back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu._back.domain.User;
import smwu._back.repository.RegisterRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AddressService {

    private final RegisterRepository registerRepository;

    public void saveCurrentAddress(String userId, String address) {
        User user = findUser(userId);
        if (user == null) return;

        if (address != null) {
            user.setCurrent_addr(address);
        }
    }

    public boolean certifyAddress(String userId, String addressInfo) {
        User user = findUser(userId);
        if (user == null) return false;

        String currentAddr = user.getCurrent_addr();
        if (!currentAddr.equals(addressInfo)) {
            user.setCurrent_addr(addressInfo);
            currentAddr = addressInfo;
        }
        String address = user.getAddr();
        String[] addrs = currentAddr.split(" ");

        for (String addr : addrs) {
            if (!address.contains(addr)) {
                return false;
            }
        }
        return true;
    }

    //==유저 찾기==//
    private User findUser(String userId) {
        List<User> users = registerRepository.listUserinfoWithID(userId);
        User user;
        try {
            user = users.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return user;
    }
}
