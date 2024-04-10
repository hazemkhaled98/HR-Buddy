package services.implementations;

import com.hrbuddy.services.UserService;
import com.hrbuddy.services.dto.UserDTO;
import com.hrbuddy.util.JwtUtil;
import jakarta.jws.WebService;
import services.UserWebService;

@WebService(endpointInterface = "services.UserWebService")
public class UserWebServiceImpl implements UserWebService {

    @Override
    public UserDTO login(UserDTO userDTO) {
        UserDTO loggedUser = UserService.login(userDTO);
        loggedUser.setToken(JwtUtil.generateToken(userDTO.getRole()));
        return loggedUser;
    }

    @Override
    public UserDTO register(UserDTO  userDTO) {
        UserDTO registeredUser = UserService.register(userDTO);
        registeredUser.setToken(JwtUtil.generateToken(userDTO.getRole()));
        return registeredUser;
    }
}
