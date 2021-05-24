package com.atguigu.eduservice.front.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.front.entity.EduComment;
import com.atguigu.eduservice.front.service.EduCommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-24
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/educomment")
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;

    @GetMapping("{courseId}/{page}/{limit}")
    public R index(@PathVariable Long page,@PathVariable Long limit,@PathVariable String courseId){
        Page<EduComment> pageParam=new Page<>(page,limit);
        Map<String,Object> map=commentService.getIndex(pageParam,courseId);
        return R.ok().data(map);
    }

    @PostMapping("auth/save")
    public R save(@RequestBody EduComment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);
        System.out.println("memberId"+memberId);
//        UcenterMember ucenterInfo = ucenterClient.getUcenter(memberId);
//        comment.setNickname(ucenterInfo.getNickname());
  //      comment.setAvatar(ucenterInfo.getAvatar());
        commentService.save(comment);
        return R.ok();
    }
}

