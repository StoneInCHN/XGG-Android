/*******************************************	
* Copyright©2016 All Rights Reserved	
* 创建日期：2016年9月17日 上午9:49:17	
* 作       者：bbliu@hentica.com	
* 功能描述：	
********************************************/	

package com.plant.app.common.persistence;

import java.io.Serializable;

public abstract class PlantDataEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 实体编号（唯一标识）
	 */
	protected long id;

	public PlantDataEntity() {
		super();
	}

	public PlantDataEntity(long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
}
