package services;

import com.hrbuddy.services.dto.UserDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface UserWebService {

    @WebMethod
    UserDTO login(@WebParam(name = "user") UserDTO user);

    @WebMethod
    UserDTO register(@WebParam(name = "user") UserDTO user);
}
