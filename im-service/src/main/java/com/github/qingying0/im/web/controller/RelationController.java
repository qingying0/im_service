package com.github.qingying0.im.web.controller;

import com.github.qingying0.im.component.HostHolder;
import com.github.qingying0.im.dto.ResultDTO;
import com.github.qingying0.im.entity.Relation;
import com.github.qingying0.im.service.IRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;

@RestController
@RequestMapping("/friend")
public class RelationController {

    @Autowired
    private IRelationService relationService;

    @Autowired
    private HostHolder hostHolder;

    @GetMapping
    public ResultDTO getFriends() {
        return ResultDTO.okOf(relationService.getFriends());
    }

    @GetMapping("/isFriend")
    public ResultDTO<Object> isFriend(Long userId) {
        System.out.println(userId);
        Relation relation = relationService.getByUserAndFriend(hostHolder.getUser().getId(), userId);
        return ResultDTO.okOf(relation != null);
    }

}
