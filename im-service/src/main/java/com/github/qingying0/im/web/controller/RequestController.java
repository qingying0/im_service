package com.github.qingying0.im.web.controller;

import com.github.qingying0.im.dto.ResultDTO;
import com.github.qingying0.im.entity.Request;
import com.github.qingying0.im.service.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private IRequestService requestService;

    @GetMapping
    public ResultDTO getRequest() {
        return ResultDTO.okOf(requestService.getRequests());
    }

    @PostMapping
    public ResultDTO saveFriendRequest(Request request) {
        requestService.saveFriendRequest(request);
        return ResultDTO.okOf();
    }

    @PutMapping
    public ResultDTO updateFriendRequest(Long requestId,Integer status) {
        return ResultDTO.okOf(requestService.updateFriendRequest(requestId, status));
    }



}
