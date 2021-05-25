package com.atguigu.eduservice.ucenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.ucenter.entity.UcenterMember;
import com.atguigu.eduservice.ucenter.entity.vo.RegisterVo;
import com.atguigu.eduservice.ucenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-05-14
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/ucentermember")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member){
        String token=memberService.signIn(member);
        return R.ok().data("token",token);
    }

    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String memberId= JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member=memberService.getById(memberId);
        return R.ok().data("userInfo",member);

    }

}

