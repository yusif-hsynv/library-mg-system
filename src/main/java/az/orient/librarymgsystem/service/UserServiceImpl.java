package az.orient.librarymgsystem.service;

import az.orient.librarymgsystem.dto.request.ReqUser;
import az.orient.librarymgsystem.dto.response.RespStatus;
import az.orient.librarymgsystem.dto.response.RespUser;
import az.orient.librarymgsystem.dto.response.Response;
import az.orient.librarymgsystem.entity.User;
import az.orient.librarymgsystem.enums.EnumAvailableStatus;
import az.orient.librarymgsystem.exception.ExceptionConstants;
import az.orient.librarymgsystem.exception.LibraryException;
import az.orient.librarymgsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Response<List<RespUser>> userList() {
        Response<List<RespUser>> response = new Response<>();
        try {
            List<User> userList = userRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (userList.isEmpty()) {
                throw new LibraryException(ExceptionConstants.USER_NOT_FOUND, "User not found");
            }
            List<RespUser> respUserList = userList.stream().map(this::mapping).toList();
            response.setT(respUserList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (LibraryException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    private RespUser mapping(User user) {
        return RespUser.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .contactPhone(user.getContactPhone())
                .address(user.getAddress())
                .build();
    }

    @Override
    public Response<RespUser> userById(Long id) {
        Response<RespUser> response = new Response<>();
        try {
            if (id == null) {
                throw new LibraryException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            User user = userRepository.findUserByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (user == null) {
                throw new LibraryException(ExceptionConstants.USER_NOT_FOUND, "User not found");
            }
            RespUser respUser = mapping(user);
            response.setT(respUser);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (LibraryException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response createUser(ReqUser reqUser) {
        Response response = new Response();
        try {
            String name = reqUser.getName();
            String surname = reqUser.getSurname();
            if (name == null || surname == null) {
                throw new LibraryException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            User user = User.builder()
                    .name(name)
                    .surname(surname)
                    .contactPhone(reqUser.getContactPhone())
                    .address(reqUser.getAddress())
                    .build();
            userRepository.save(user);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (LibraryException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response updateUser(ReqUser reqUser) {
        Response response = new Response();
        try {
            Long userId = reqUser.getId();
            String name = reqUser.getName();
            String surname = reqUser.getSurname();
            if (userId == null || name == null || surname == null) {
                throw new LibraryException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            User user = userRepository.findUserByIdAndActive(userId, EnumAvailableStatus.ACTIVE.value);
            if (user == null) {
                throw new LibraryException(ExceptionConstants.USER_NOT_FOUND, "User not found");
            }
            user.setName(name);
            user.setSurname(surname);
            user.setContactPhone(reqUser.getContactPhone());
            user.setAddress(reqUser.getAddress());
            userRepository.save(user);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (LibraryException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response deleteUser(Long id) {
        Response response = new Response();
        try {
            if (id == null) {
                throw new LibraryException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            User user = userRepository.findUserByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (user == null) {
                throw new LibraryException(ExceptionConstants.USER_NOT_FOUND, "User not found");
            }
            user.setActive(EnumAvailableStatus.DEACTIVE.value);
            userRepository.save(user);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (LibraryException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }
}
