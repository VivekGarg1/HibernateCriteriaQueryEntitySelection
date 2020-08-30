package com.home.client;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.home.dto.CallInfo;
import com.home.entities.Call;
import com.home.entities.Phone;
import com.home.util.HibernateUtil;

public class CriteriaQueryForeignKeyJoinClientTtest {

	public static void main(String[] args) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
			Root<Call> rootCall = criteriaQuery.from(Call.class);
			Root<Phone> rootPhone = criteriaQuery.from(Phone.class);
			criteriaQuery.multiselect(rootCall,rootPhone);
			criteriaQuery.where(builder.equal(rootCall.get("phone"), rootPhone.get("phoneId")));
			List<Object[]> resultList = session.createQuery(criteriaQuery).getResultList();
			for (Object[] objects : resultList) {
				Call call=(Call) objects[0];
				Phone phone=(Phone) objects[1];
				System.out.println(call);
				System.out.println(phone);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

}
