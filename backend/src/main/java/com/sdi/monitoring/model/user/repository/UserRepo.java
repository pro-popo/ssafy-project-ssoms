package com.sdi.monitoring.model.user.repository;

import com.sdi.monitoring.model.user.entity.SetAlarms;
import com.sdi.monitoring.model.user.entity.UserEntity;
import com.sdi.monitoring.model.user.entity.UserVisitTimes;

public interface UserRepo{
	public void insertUser(UserEntity userEntity);
}
