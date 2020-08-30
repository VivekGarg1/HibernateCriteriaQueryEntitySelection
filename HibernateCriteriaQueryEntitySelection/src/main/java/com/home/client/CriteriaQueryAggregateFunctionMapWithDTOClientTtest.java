package com.home.client;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.home.dto.CallInfo;
import com.home.entities.Call;
import com.home.util.HibernateUtil;

public class CriteriaQueryAggregateFunctionMapWithDTOClientTtest {

	public static void main(String[] args) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<CallInfo> criteriaQuery = builder.createQuery(CallInfo.class);
			Root<Call> root = criteriaQuery.from(Call.class);
			Expression<Long> totalNoOfcalls = builder.count(root);
            Expression<Long> totalDistinctPonesFromCalls = builder.countDistinct(root.get("phone"));
			Expression<Integer> maxCallDuration = builder.max(root.get("duration"));
			Expression<Double> avgCallDuration = builder.avg(root.get("duration"));
			Expression<Long> sumCallDuration = builder.sum(root.get("duration"));
			criteriaQuery.select(builder.construct(CallInfo.class, totalNoOfcalls,totalDistinctPonesFromCalls,maxCallDuration,avgCallDuration,sumCallDuration));
			CallInfo singleResult = session.createQuery(criteriaQuery).getSingleResult();
			System.out.println(singleResult);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

}
