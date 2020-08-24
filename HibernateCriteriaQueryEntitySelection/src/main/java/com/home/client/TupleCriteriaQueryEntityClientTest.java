package com.home.client;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.home.dto.EmployeeDTO;
import com.home.entities.Employee;
import com.home.util.HibernateUtil;

public class TupleCriteriaQueryEntityClientTest {
	
	public static void main(String[] args) {
		
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			CriteriaBuilder builder=session.getCriteriaBuilder();
			CriteriaQuery<Tuple> query= builder.createQuery(Tuple.class);
			Root<Employee> root=query.from(Employee.class);
			Path<Object> namePath = root.get("employeeName");
			Path<Object> emailPath = root.get("email");
			Path<Object> salaryPath = root.get("salary");
			query.multiselect(namePath,emailPath,salaryPath);
			List<Tuple> tuples = session.createQuery(query).getResultList();
			for (Tuple tuple : tuples) {
				String employeeName= (String) tuple.get(namePath);
				String email= (String) tuple.get(emailPath);
				Double salary= (Double) tuple.get(salaryPath);
				System.out.println(employeeName+"\t"+email+"\t"+salary);
			}
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
	}

}
