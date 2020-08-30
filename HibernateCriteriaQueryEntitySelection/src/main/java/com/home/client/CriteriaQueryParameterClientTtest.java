package com.home.client;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.swing.Spring;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.home.entities.Employee;
import com.home.util.HibernateUtil;

public class CriteriaQueryParameterClientTtest {
	
	public static void main(String[] args) {
		
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			String email="prabhat.singh@gmail.com";
			CriteriaBuilder builder=session.getCriteriaBuilder();
			CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
			Root<Employee> root=criteriaQuery.from(Employee.class);
			ParameterExpression<String> emailParameter = builder.parameter(String.class);
			criteriaQuery.where(builder.equal(root.get("email"),emailParameter));
			Query<Employee> query = session.createQuery(criteriaQuery);
			query.setParameter(emailParameter, email);
			List<Employee> resultList = query.getResultList();
			resultList.forEach(System.out::println);
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
	}

}
