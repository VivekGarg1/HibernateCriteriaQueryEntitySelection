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

public class CriteriaQueryGroupByAndHavingClauseClientTtest {

	public static void main(String[] args) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
			Root<Call> root = criteriaQuery.from(Call.class);
			criteriaQuery.multiselect(builder.count(root), builder.sum(root.get("duration")), root.get("phone"));
			criteriaQuery.groupBy(root.get("phone"));
			criteriaQuery.having(builder.greaterThan(builder.sum(root.get("duration")), 40));
			List<Object[]> resultList = session.createQuery(criteriaQuery).getResultList();
			for (Object[] objects : resultList) {
				Phone phone=(Phone) objects[2];
				System.out.println(phone);
				Long count=(Long)objects[0];
				System.out.println("Count: "+count);
				Long duration=(Long)objects[1];
				System.out.println("Total call duration: "+duration);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

}
