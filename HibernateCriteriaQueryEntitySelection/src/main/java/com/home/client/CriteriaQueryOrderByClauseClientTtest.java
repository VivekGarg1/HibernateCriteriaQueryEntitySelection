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

public class CriteriaQueryOrderByClauseClientTtest {

	public static void main(String[] args) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Call> criteriaQuery = builder.createQuery(Call.class);
			Root<Call> root = criteriaQuery.from(Call.class);
			criteriaQuery.select(root);
			criteriaQuery.orderBy(builder.desc(root));
			List<Call> resultList = session.createQuery(criteriaQuery).getResultList();
			resultList.forEach(System.out::println);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

}
