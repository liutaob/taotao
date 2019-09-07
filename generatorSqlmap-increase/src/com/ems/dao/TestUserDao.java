package com.ems.dao;

import com.ems.bean.TestUser;
import com.ems.bean.TestUserQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestUserDao {
    int countByExample(TestUserQuery example);

    int deleteByExample(TestUserQuery example);

    int deleteByPrimaryKey(Integer id);

    int insert(TestUser record);

    int insertSelective(TestUser record);

    List<TestUser> selectByExample(TestUserQuery example);

    TestUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TestUser record, @Param("example") TestUserQuery example);

    int updateByExample(@Param("record") TestUser record, @Param("example") TestUserQuery example);

    int updateByPrimaryKeySelective(TestUser record);

    int updateByPrimaryKey(TestUser record);
}