package smwu._back.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import smwu._back.users.service.AddressService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AddressController {

    private final AddressService addressService;

    @ResponseBody
    @PostMapping("/save/current/address/{userId}")
    public void saveAddress(@PathVariable String userId, @RequestBody Map<Object, String> address) {
        System.out.println("현재주소저장" + address.get("dong"));

        String dong = address.get("dong");
        addressService.saveCurrentAddress(userId, dong);
    }

    @ResponseBody
    @GetMapping("/address/certification/{userId}/{address}")
    public boolean certification(@PathVariable String userId, @PathVariable String address) {
        return addressService.certifyAddress(userId, address);
    }
}
