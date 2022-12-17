package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author STA_张于宴
 * @since 2022-12-17
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);
}