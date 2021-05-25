package com.atguigu.eduservice.ucenter.service;

import com.atguigu.eduservice.ucenter.entity.UcenterMember;
import com.atguigu.eduservice.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-05-14
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String signIn(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getByOpenId(String openid);
}
