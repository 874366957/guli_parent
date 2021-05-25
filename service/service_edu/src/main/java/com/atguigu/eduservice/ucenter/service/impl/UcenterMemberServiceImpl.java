package com.atguigu.eduservice.ucenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.eduservice.ucenter.entity.UcenterMember;
import com.atguigu.eduservice.ucenter.entity.vo.RegisterVo;
import com.atguigu.eduservice.mapper.UcenterMemberMapper;
import com.atguigu.eduservice.ucenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-14
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public String signIn(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登陆失败");
        }
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember memberMobile = baseMapper.selectOne(wrapper);
        if(memberMobile==null){
            throw new GuliException(20001,"登陆失败");
        }
        if(!MD5.encrypt(password).equals(memberMobile.getPassword())){
            throw new GuliException(20001,"登陆失败");
        }
        if(memberMobile.getIsDisabled()){
            throw new GuliException(20001,"登陆失败");
        }

        String token=JwtUtils.getJwtToken(memberMobile.getId(),memberMobile.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(code)){
            throw new GuliException(20001,"注册失败");
        }
        String redisKeyValue=redisTemplate.opsForValue().get(mobile);
        if(!redisKeyValue.equals(code)){
            throw new GuliException(20001,"注册失败");
        }
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>=1){
            throw new GuliException(20001,"注册失败");
        }
        UcenterMember ucenterMember=new UcenterMember();
        String encrypt = MD5.encrypt(registerVo.getPassword());
        registerVo.setPassword(encrypt);
        BeanUtils.copyProperties(registerVo,ucenterMember);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("https://edu-guli1010-1.oss-cn-beijing.aliyuncs.com/coursetemplate/default.jpg");
        baseMapper.insert(ucenterMember);
    }

    @Override
    public UcenterMember getByOpenId(String openid) {
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member=baseMapper.selectOne(wrapper);
        return member;
    }
}
