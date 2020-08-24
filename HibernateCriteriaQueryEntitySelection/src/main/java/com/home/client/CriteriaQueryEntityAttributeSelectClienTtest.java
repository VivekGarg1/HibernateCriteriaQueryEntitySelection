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

public class CriteriaQueryEntityAttributeSelectClienTtest {
	
	public static void main(String[] args) {
		
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			CriteriaBuilder builder=session.getCriteriaBuilder();
			CriteriaQuery<String> query= builder.createQuery(String.class);
			Root<Employee> root=query.from(Employee.class);
			query.select(root.get("employeeName"));
			Query query2=session.createQuery(query);
			List<String> empNameList=query2.getResultList();
			empNameList.forEach(System.out::println);
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
	}

}
