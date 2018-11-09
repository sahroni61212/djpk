package com.sh.djpk.econsole.rest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.djpk.econsole.rest.dao.PeriodeDao;
import com.sh.djpk.econsole.rest.dao.UserDao;
import com.sh.djpk.econsole.rest.dao.UserGroupDao;
import com.sh.djpk.econsole.rest.dao.WilayahDao;
import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.UserGroupSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.econsole.rest.service.WilayahSvc;
import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.model.User;
import com.sh.djpk.share.util.JsonUtil;
import com.sh.djpk.share.util.NumberUtils;

@Service("userGroupSvc")
@Transactional(readOnly = false)
public class UserGroupSvcImpl implements UserGroupSvc {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(UserGroupSvcImpl.class);

	@Autowired
	UserGroupDao usrGrpDao;

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getAll() {
		LOGGER.info("try get data user group");
		List<HashMap> l = usrGrpDao.getUsrGrp();
		LOGGER.trace("data from db={}", l);
		return l;
	}

	@Override
	public RestResponse insert(HashMap<String, Object> params) {
		LOGGER.info("try insert data user group with params={}", params);
		RestResponse response = new RestResponse(
				RestResponse.ERROR_REST_STATUS, "Data gagal disimpan!");
		int i = usrGrpDao.insert(params);
		if (i > 0) {
			return new RestResponse(RestResponse.OK_REST_STATUS,
					"Data berhasil disimpan!");
		}
		return response;
	}

	@Override
	public RestResponse update(Map<String, Object> params) {
		LOGGER.info("try update data user group with params={}", params);
		RestResponse response = new RestResponse(
				RestResponse.ERROR_REST_STATUS, "Data gagal diubah!");
		int i = usrGrpDao.update(params);
		if (i > 0) {
			return new RestResponse(RestResponse.OK_REST_STATUS,
					"Data berhasil diubah!");
		}
		return response;
	}

	@Override
	public RestResponse delete(Map<String, Object> params) {
		LOGGER.info("try delete data user group with params={}", params);
		RestResponse response = new RestResponse(
				RestResponse.ERROR_REST_STATUS, "Data gagal dihapus!");
		int i = usrGrpDao.delete(params);
		if (i > 0) {
			return new RestResponse(RestResponse.OK_REST_STATUS,
					"Data berhasil dihapus!");
		}
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public RestResponse getAllMenu(int kdUser) {
		LOGGER.info("try get All Menu");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK!");
		List<HashMap> l = usrGrpDao.getAllMenu(kdUser);
		LOGGER.trace("data from db={}", l);
		response.setContents(l);
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public RestResponse getRefUserMenu(int kdUser) {
		LOGGER.info("try get Ref User Menu with kdUser={}", kdUser);
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK!");
		List<HashMap> l = usrGrpDao.getRefUserMenuByKdUser(kdUser);
		LOGGER.trace("data from db={}", l);
		response.setContents(l);
		return response;
	}

	@Override
	public RestResponse submitRefUserMenu(int action, Map<String, Object> params) {
		LOGGER.info("try submitRefUserMenu with action={} and params={}",
				action, params);
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"Proses sukses!");
		if (action == 0) {
			usrGrpDao.deleteRefUserMenu(params);
		} else if (action == 1) {
			List<HashMap> l = usrGrpDao.getRefUserMenuByPk(
					NumberUtils.toInteger(params.get("kd_user")),
					NumberUtils.toInteger(params.get("menu_code")));
			LOGGER.info("data from db={}", JsonUtil.getJson(l));
			if (l == null || l.isEmpty()) {
				LOGGER.info("do insert!");
				usrGrpDao.insertRefUserMenu(params);
			} else {
				LOGGER.info("do update!");
				usrGrpDao.updateRefUserMenu(params);
			}
		} else {
			response.setMessage("Action " + action + " tidak dikenal!");
			response.setStatus(RestResponse.ERROR_REST_STATUS);
		}
		return response;
	}

}
