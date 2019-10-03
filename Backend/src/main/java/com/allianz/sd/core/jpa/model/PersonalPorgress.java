package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_Personal_Progress")
public class PersonalPorgress {
	@EmbeddedId
    private PersonalPorgressIdentity identity = null;   

    @Column(name = "Find")
    private int find ;
    
    @Column(name = "Schedule")
    private int schedule ;

    @Column(name = "Meet")
    private int meet ;
    
    @Column(name = "Submit")
    private int sumit;
    
    @Column(name = "Inforce")
    private int inforce;
    
    @Column(name = "CreateBy")
    private String createBy;
    
    @Column(name = "CreateTime")
    private Date createTime;
   
 
    public PersonalPorgressIdentity getIdentity() {
		return identity;
	}
	

	public void setIdentity(PersonalPorgressIdentity Identity) {
		this.identity = Identity;
	}

	

    public int getFind() {
        return find;
    }

    public void setFind(int Find) {
        this.find = Find;
    }

    public int getCchedule() {
        return schedule;
    }

    public void setSchedule(int Schedule) {
        this.schedule = Schedule;
    }

    public int getMeet() {
        return meet;
    }

    public void setMeet(int Meet) {
        this.meet = Meet;
    }
    
    public int getSumit() {
        return sumit;
    }

    public void setSumit(int Sumit) {
        this.sumit = Sumit;
    }
    
    public int getInforce() {
        return inforce;
    }

    public void setInforce(int Inforce) {
        this.inforce = Inforce;
    }
    
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String CreateBy) {
        this.createBy = CreateBy;
    }
    
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date CreateTime) {
        this.createTime = CreateTime;
    }
    
     
    
}
