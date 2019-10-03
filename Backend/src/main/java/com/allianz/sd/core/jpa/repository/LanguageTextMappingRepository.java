package com.allianz.sd.core.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allianz.sd.core.jpa.model.LanguageTextMapping;
import com.allianz.sd.core.jpa.model.LanguageTextMappingIdentity;

/*
 * 
 */
public interface LanguageTextMappingRepository extends JpaRepository<LanguageTextMapping,LanguageTextMappingIdentity> {
    public List<LanguageTextMapping> findByIdentityLanguageID(String languageID);
}
