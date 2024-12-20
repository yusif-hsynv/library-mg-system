package az.orient.librarymgsystem.controller;

import az.orient.librarymgsystem.dto.request.ReqUser;
import az.orient.librarymgsystem.dto.response.RespUser;
import az.orient.librarymgsystem.dto.response.Response;
import az.orient.librarymgsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    public Response<List<RespUser>>userList() {
        return userService.userList();
    }

    @GetMapping("byId/{id}")
    public Response<RespUser> userById(@PathVariable Long id) {
        return userService.userById(id);

    }

    @PostMapping("/create")
    public Response createUser(@RequestBody ReqUser reqUser) {
        return userService.createUser(reqUser);
    }

    @PutMapping("/update")
    public Response updateUser(@RequestBody ReqUser reqUser) {
        return userService.updateUser(reqUser);
    }

    @PutMapping("/delete")
    public Response deleteUser(@RequestParam Long id) {
        return userService.deleteUser(id);
    }
}

