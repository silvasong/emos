package com.mpos.dao;




import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mpos.commons.MposException;
import com.mpos.dao.base.BaseDao;
import com.mpos.dto.TadminInfo;

/**
 * Home object for domain model class TadminInfo.
 * @see com.bps.dto.TadminInfo
 * @author Hibernate Tools
 */
@Repository
public class AdminInfoDao extends BaseDao<TadminInfo> {
	
	public void updateInfo(TadminInfo adminInfo){
		try{
		Session session=this.currentSession();
        Query query = session.createQuery("Update TadminInfo t set t.firstName='"+adminInfo.getFirstName()+"',t.lastName='"
				                          +adminInfo.getLastName()+"',t.gender="+adminInfo.getGender()+",t.birthday='"+adminInfo.getBirthday()+"',t.mobile='"
				                          +adminInfo.getMobile()+"',t.position='"+adminInfo.getPosition()+"',t.about='"+adminInfo.getAbout()+"' where admin_id='"+adminInfo.getAdminId()+"'"
				                          );
		query.executeUpdate();
	
		}
		catch(MposException b){
			
		}
	}
	
//	public void updateInfoAvatar(TadminInfo adminInfo){
//		try{
//			Session session = this.currentSession();
//			Query query = session.createQuery("update TadminInfo t set t.avatar = "+adminInfo.getAvatar()+" where admin_id = '"+adminInfo.getAdminId()+"'");
//			query.executeUpdate();
//		}catch(BPSException b){
			
//		}
		
//	}
	
}
