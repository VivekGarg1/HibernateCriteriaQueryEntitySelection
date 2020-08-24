package com.home.client;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.home.entities.Employee;
import com.home.util.HibernateUtil;

public class CriteriaQueryEntitySelectClienTtest {
	
	public static void main(String[] args) {
		
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			CriteriaBuilder builder=session.getCriteriaBuilder();
			CriteriaQuery<Employee> query= builder.createQuery(Employee.class);
			Root<Employee> root=query.from(Employee.class);
			query.select(root);
			//Using where clause
			query.where(builder.equal(root.get("employeeId"),2));
			Query query2=session.createQuery(query);
			List<Employee> empList=query2.getResultList();
			empList.forEach(System.out::println);
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
	}

}
