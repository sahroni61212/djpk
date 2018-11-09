package com.sh.djpk.econsole.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sh.djpk.share.model.RestResponse;

public interface UserGroupSvc {

	List<HashMap> getAll();

	RestResponse insert(HashMap<String, Object> params);

	public RestResponse update(Map<String, Object> params);

	public RestResponse delete(Map<String, Object> params);

	public RestResponse getAllMenu(int kdUser);

	public RestResponse getRefUserMenu(int kdUser);

	/***
	 * 
	 * @param action 1=kasih akses, 0=hapus akses
	 * @param params
	 * @return
	 */
	public RestResponse submitRefUserMenu(int action,Map<String, Object> params);

}
