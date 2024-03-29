package com.sdi.monitoring.model.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdi.monitoring.model.admin.dto.DeleteForm;
import com.sdi.monitoring.model.admin.repository.AdminSettingsRepo;
import com.sdi.monitoring.model.oracle.dto.OracleDBSettingsDTO;
import com.sdi.monitoring.model.oracle.repository.OracleRepo;
import com.sdi.monitoring.model.user.dto.UserDTO;
import com.sdi.monitoring.model.user.dto.UserPrimitiveDTO;
import com.sdi.monitoring.model.user.entity.UserEntity;
import com.sdi.monitoring.model.user.repository.UserMongoRepo;
import com.sdi.monitoring.util.JsonParser;
import com.sdi.monitoring.util.Mapper;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private AdminSettingsRepo settingRepo;
	
	@Autowired
	private UserMongoRepo userMongoRepo;
	
	@Autowired OracleRepo oracleRepo;
	
	@Override
	public boolean changeAdmin(String prevAdmin, String nextAdmin) {
		UserEntity adminEntity = null;
		adminEntity = userMongoRepo.findUserByEmail(prevAdmin); //
		if(adminEntity == null) {
			return false;
		}
		
		UserEntity userEntity = null;
		userEntity = userMongoRepo.findUserByEmail(nextAdmin);
		if(userEntity == null) {
			return false;
		}
		
		if(!isAdminCheck(adminEntity, userEntity))
			return false;
		
		changeAdmin(adminEntity, userEntity);
		
		// false 던지는 곳이 진짜 계정이 없어서 던지는건지 db에 이상 있는건지 확인하는 로직 추가 필요
		return true;
	}
	
	@Override
	public List<UserDTO> getAllUserList() {
		return EntityListToDTOList(userMongoRepo.findAll()); // 아무것도 없을 때 test
	}

	@Override
	public List<String> getUserVisitedHistory(String email) {
		return null;
	}
	
	@Override
	public JSONObject getSettings() {
		JSONObject jo = null;
		try {
			jo = settingRepo.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}

	@Override
	public boolean setSettingsOracleDB(OracleDBSettingsDTO OracleData) {
		boolean result = false;
		try {
			result = settingRepo.setOracleDB(OracleData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean setSettingsSchema(JSONArray jlist) {
		boolean result = false;
		try {
			result = settingRepo.setSchema(jlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean checkConnection(OracleDBSettingsDTO OracleData) {
		boolean result = false;
		try {
			result = settingRepo.checkConnection(OracleData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public boolean checkDuplicateSchema(String schemaName) {
		List<String> schemaList = JsonParser.getSchemaInfo();
		
		for(String cmpSchemaName : schemaList) {
			if(cmpSchemaName.equalsIgnoreCase(schemaName)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean checkSchemaExistence(String schemaName) {
		if(!oracleRepo.findSchema(schemaName))
			return false;
		
		return true;
	}
	
	@Override
	public List<String> findAllSchema() {
		return oracleRepo.findAllSchema();
	}
	
	private List<UserDTO> EntityListToDTOList(List<UserEntity> userEntityList){
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		for (UserEntity userEntity : userEntityList) {
			UserDTO userDTO = mapper.convertToDTO(userEntity, UserDTO.class);
			userDTOList.add(userDTO);
		}
		
		return userDTOList; 
	}
	
	private boolean isAdminCheck(UserEntity adminEntity, UserEntity userEntity) {
		if(!adminEntity.getInfo().isAdmin())
			return false;
		
		if(userEntity.getInfo().isAdmin())
			return false;
		
		return true;
	}
	
	private void changeAdmin(UserEntity adminEntity, UserEntity userEntity) {
		adminEntity.getInfo().ChangeAdmin();
		userEntity.getInfo().ChangeAdmin();
		
		userMongoRepo.save(adminEntity);
		userMongoRepo.save(userEntity);
	}
	
	@Override
	public int deleteUser(DeleteForm form) {
		UserEntity userEntity = null;
		userEntity = userMongoRepo.findUserByEmail(form.getDeleteemail());
		UserEntity adminEntity = null;
		adminEntity = userMongoRepo.findUserByEmail(form.getAdminemail());
		if(userEntity == null) {
			return 0;
		} if(adminEntity == null) {
			return 1;
		} 
		if(!cmpPasswordWithEncryptionPassword(form.getPw(), adminEntity.getInfo().getPw())) {
			return 2;
		}
		
		userMongoRepo.deleteByEmail(form.getDeleteemail());
		return 3;
	}
	
	private boolean cmpPasswordWithEncryptionPassword(String cmp1, String cmp2) {
		return BCrypt.checkpw(cmp1, cmp2);
	}
}
