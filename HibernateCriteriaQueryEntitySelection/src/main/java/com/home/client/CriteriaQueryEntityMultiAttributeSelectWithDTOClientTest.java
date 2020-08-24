package com.home.client;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.home.dto.EmployeeDTO;
import com.home.entities.Employee;
import com.home.util.HibernateUtil;

public class CriteriaQueryEntityMultiAttributeSelectWithDTOClientTest {
	
	public static void main(String[] args) {
		
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			CriteriaBuilder builder=session.getCriteriaBuilder();
			CriteriaQuery<EmployeeDTO> query= builder.createQuery(EmployeeDTO.class);
			Root<Employee> root=query.from(Employee.class);
			Path<Object> namePath = root.get("employeeName");
			Path<Object> emailPath = root.get("email");
			Path<Object> salaryPath = root.get("salary");
			
			query.select(builder.construct(EmployeeDTO.class,namePath,emailPath,salaryPath));
			
			Query query2=session.createQuery(query);
			List<EmployeeDTO> empDataList=query2.getResultList();
			for (EmployeeDTO empData : empDataList) {
				System.out.println("Employee name: "+empData.getEmployeeName()); 
				System.out.println("email: "+empData.getEmail()); 
				System.out.println("salary: "+empData.getSalary()); 
			}
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
	}

}
