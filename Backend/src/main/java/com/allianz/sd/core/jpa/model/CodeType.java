package com.allianz.sd.core.jpa.model;

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
@Table(name="TW_LH_SD_Code_Type")
public class CodeType extends CreateUpdateInfo{

    @EmbeddedId
    private CodeTypeIdentity codeTypeIdentity = null;

    @Column(name = "TypeName")
    private String typeName = null;


    public CodeType() {
    }

    public CodeTypeIdentity getCodeTypeIdentity() {
        return codeTypeIdentity;
    }

    public void setCodeTypeIdentity(CodeTypeIdentity codeTypeIdentity) {
        this.codeTypeIdentity = codeTypeIdentity;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
