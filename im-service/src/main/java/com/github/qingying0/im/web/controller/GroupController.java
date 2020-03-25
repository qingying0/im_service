package com.github.qingying0.im.web.controller;

import com.github.qingying0.im.dto.ResultDTO;
import com.github.qingying0.im.entity.GroupChat;
import com.github.qingying0.im.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private IGroupService groupService;

    @PostMapping
    public ResultDTO saveGroup() {
        GroupChat groupChat = groupService.saveGroup();
        return ResultDTO.okOf(groupChat);
    }

    @PostMapping("/add")
    public ResultDTO addGroup(Long userId, Long groupId, String username, String groupName) {
        System.out.println(userId + "|" +  groupId + "|" + username + "|" + groupName);
        groupService.addGroup(userId, groupId, username, groupName);
        return ResultDTO.okOf();
    }

    @GetMapping
    public ResultDTO getGroup() {
        List<GroupChat> listGroupChat = groupService.getGroup();
        return ResultDTO.okOf(listGroupChat);
    }
}
