package com.sh.djpk.econsole.rest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.djpk.econsole.rest.dao.UserDao;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.util.NumberUtils;
import com.sh.djpk.share.util.StringUtils;

@Service("userSvc")
@Transactional(readOnly = false)
public class UserSvcImpl implements UserSvc {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(UserSvcImpl.class);

	@Autowired
	UserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getAllPaging(int pageSize, int pageSequence,
			String direction, String orderBy, Map<String, Object> params) {
		LOGGER.info(
				"try get All Paging pageSize={}, pageSequence={}, params={}",
				pageSize, pageSequence, params);
		orderBy = StringUtils.nevl(orderBy, "username");
		direction = StringUtils.nevl(direction, "ASC");
		List<HashMap> l = userDao.getAllPaging(pageSize, pageSequence,
				direction, orderBy, params);
		LOGGER.trace("data from db={}", l);
		return l;
	}

	@Override
	public int insert(Map<String, Object> params) {
		LOGGER.info("try insert params={}", params);
		int i = userDao.insertUser(params);
		LOGGER.info("result insert user>>data from db={}", i);
		LOGGER.info("params={}", params);
		if (i > 0) {
			int n = userDao.insertAuthAssignment(params);
			LOGGER.info("result insert auth_assignmet>>data from db={}", n);
			int m = userDao.insertUserPemda(params);
			LOGGER.info("result insert user_pemda>>data from db={}", m);
		}
		return i;
	}

	@Override
	public int update(Map<String, Object> params) {
		LOGGER.info("try update params={}", params);
		int i = userDao.updateUser(params);
		LOGGER.info("result update user>>data from db={}", i);
		LOGGER.info("params={}", params);
		if (i > 0) {
			int n = userDao.updateAuthAssignment(params);
			LOGGER.info("result update auth_assignmet>>data from db={}", n);
			int m = userDao.updateUserPemda(params);
			LOGGER.info("result update user_pemda>>data from db={}", m);
		}
		return i;
	}

	@Override
	public int delete(Map<String, Object> params) {
		LOGGER.info("try delete params={}", params);
		int i = userDao.deleteUser(params);
		LOGGER.info("result insert user>>data from db={}", i);
		int n = userDao.deleteAuthAssignment(params);
		LOGGER.info("result delete auth_assignmet>>data from db={}", n);
		int m = userDao.deleteUserPemda(params);
		LOGGER.info("result delete user_pemda>>data from db={}", m);
		return i;
	}

	@Override
	public RestResponse getAllAuthItem() {
		LOGGER.info("try get all auth item");
		RestResponse restResponse = new RestResponse(
				RestResponse.OK_REST_STATUS, null);
		restResponse.setContents(userDao.getAllAuthItem());
		return restResponse;
	}

	@Override
	@Transactional(readOnly = true)
	public RestResponse login(Map<String, Object> params) {
		LOGGER.info("try login with params={}", params);
		RestResponse restResponse = new RestResponse(
				RestResponse.ERROR_REST_STATUS,
				"Incorrect username or password.");
		List<HashMap> l = userDao.login(params);
		if (l != null && !l.isEmpty() && l.size() == 1) {
			restResponse = new RestResponse(RestResponse.OK_REST_STATUS,
					"Login sukses.");
			restResponse.setContents(l.get(0));
		}
		return restResponse;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public RestResponse getMenuAkses(Map<String, Object> params) {
		LOGGER.info("try getMenuAkses with params={}", params);
		RestResponse restResponse = new RestResponse(RestResponse.OK_REST_STATUS, "OK");
		List<HashMap> l = userDao.getMenuAkses(params);
		if (l != null && !l.isEmpty()) {			
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			for (HashMap m : l) {
				Map<String, Object> parent = findParent(result, "menu_parent",
						NumberUtils.toInteger(m.get("menu_parent")));
				if (parent == null) {
					parent = new HashMap<String, Object>();
					parent.put("menu_parent", m.get("menu_parent"));
					parent.put("menu_parent_name", m.get("menu_parent_name"));
					parent.put("list_menu",
							new ArrayList<Map<String, Object>>());
					result.add(parent);
				} else {
					LOGGER.info("menu parent ada!");
				}

				List<Map<String, Object>> listMenu = (List<Map<String, Object>>) parent
						.get("list_menu");
				listMenu.add(m);
			}
			restResponse.setContents(result);
		}
		return restResponse;
	}

	Map<String, Object> findParent(List<Map<String, Object>> list, String key,
			int val) {
		for (Map<String, Object> m : list) {
			if (NumberUtils.toInteger(m.get(key)) == val) {
				return m;
			}
		}
		return null;
	}

}
