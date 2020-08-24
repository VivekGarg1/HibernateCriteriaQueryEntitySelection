package com.home.client;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.home.entities.Employee;
import com.home.util.HibernateUtil;

public class CriteriaQueryEntityMultiAttributeSelectClientTest {
	
	public static void main(String[] args) {
		
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			CriteriaBuilder builder=session.getCriteriaBuilder();
			CriteriaQuery<Object[]> query= builder.createQuery(Object[].class);
			Root<Employee> root=query.from(Employee.class);
			Path<Object> namePath = root.get("employeeName");
			Path<Object> emailPath = root.get("email");
			Path<Object> salaryPath = root.get("salary");
			
			//query.select(builder.array(namePath,emailPath,salaryPath));
			query.multiselect(namePath,emailPath,salaryPath);
			
			Query query2=session.createQuery(query);
			List<Object[]> empDataList=query2.getResultList();
			for (Object[] empData : empDataList) {
				System.out.println("Employee name: "+empData[0]); 
				System.out.println("email: "+empData[1]); 
				System.out.println("salary: "+empData[2]); 
			}
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
	}

}
