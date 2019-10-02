package com.allianz.sd.core.jpa.model;

import com.allianz.sd.core.jpa.listener.CreateUpdateTimeListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 1:43
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="TW_LH_SD_Language_Text_Mapping")
public class LanguageTextMapping {

    @EmbeddedId()
    private LanguageTextMappingIdentity identity = null;

	@Column(name = "Text")
	private String text = null;

//	@Column(name = "PageName")
//	private String pageName = null;

	public LanguageTextMappingIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(LanguageTextMappingIdentity identity) {
		this.identity = identity;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

//	public String getPageName() {
//		return pageName;
//	}
//
//	public void setPageName(String pageName) {
//		this.pageName = pageName;
//	}
}
