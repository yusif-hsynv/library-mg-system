package az.orient.librarymgsystem.service;

import az.orient.librarymgsystem.dto.request.ReqUser;
import az.orient.librarymgsystem.dto.response.RespUser;
import az.orient.librarymgsystem.dto.response.Response;

import java.util.List;

public interface UserService {
    Response<List<RespUser>> userList();

    Response<RespUser> userById(Long id);

    Response createUser(ReqUser reqUser);

    Response updateUser(ReqUser reqUser);

    Response deleteUser(Long id);
}
