package com.home.client;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import com.home.entities.Call;
import com.home.util.HibernateUtil;

public class CriteriaQueryAggregateFunctionClientTtest {
	
	public static void main(String[] args) {
		
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			getTotalNoOfCalls(session);
			getMaxCallDuration(session);
			getAverageCallDurarion(session);
			getSumOfCallDurations(session);
			getDistinctPhonesFromCalls(session);
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
	}

	private static void getDistinctPhonesFromCalls(Session session) {
		CriteriaBuilder builder=session.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Call> root = criteriaQuery.from(Call.class);
		criteriaQuery.select(builder.countDistinct(root.get("phone")));
		Long singleResult = session.createQuery(criteriaQuery).getSingleResult();
		System.out.println("Distinct no of phones: "+singleResult);
	}

	private static void getSumOfCallDurations(Session session) {
		CriteriaBuilder builder=session.getCriteriaBuilder();
		CriteriaQuery<Integer> criteriaQuery = builder.createQuery(Integer.class);
		Root<Call> root = criteriaQuery.from(Call.class);
		criteriaQuery.select(builder.sum(root.get("duration")));
		Integer singleResult = session.createQuery(criteriaQuery).getSingleResult();
		System.out.println("Total duration of call: "+singleResult);
	}

	private static void getAverageCallDurarion(Session session) {
		CriteriaBuilder builder=session.getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = builder.createQuery(Double.class);
		Root<Call> root = criteriaQuery.from(Call.class);
		criteriaQuery.select(builder.avg(root.get("duration")));
		Double singleResult = session.createQuery(criteriaQuery).getSingleResult();
		System.out.println("Avg duration of call: "+singleResult);
	}

	private static void getMaxCallDuration(Session session) {
			CriteriaBuilder builder=session.getCriteriaBuilder();
			CriteriaQuery<Integer> criteriaQuery = builder.createQuery(Integer.class);
			Root<Call> root = criteriaQuery.from(Call.class);
			criteriaQuery.select(builder.max(root.get("duration")));
			Integer singleResult = session.createQuery(criteriaQuery).getSingleResult();
			System.out.println("Max duration of call: "+singleResult);
	}

	private static void getTotalNoOfCalls(Session session) {
		CriteriaBuilder builder=session.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Call> root = criteriaQuery.from(Call.class);
		criteriaQuery.select(builder.count(root));
		Long singleResult = session.createQuery(criteriaQuery).getSingleResult();
		System.out.println("Total no of calls: "+singleResult);
	}

}
