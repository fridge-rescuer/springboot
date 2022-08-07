package com.fridgerescuer.springboot.data.dao;

import com.fridgerescuer.springboot.data.entity.Member;

public interface MemberDao {
    Member saveMember(Member member);
}
