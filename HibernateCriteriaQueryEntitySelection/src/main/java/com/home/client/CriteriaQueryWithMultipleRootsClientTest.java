package com.home.client;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.home.dto.EmployeeDTO;
import com.home.entities.Employee;
import com.home.entities.Partner;
import com.home.entities.Phone;
import com.home.util.HibernateUtil;

public class CriteriaQueryWithMultipleRootsClientTest {
	
	public static void main(String[] args) {
		
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			CriteriaBuilder builder=session.getCriteriaBuilder();
			CriteriaQuery<Tuple> query= builder.createQuery(Tuple.class);
			Root<Employee> employeeRoot=query.from(Employee.class);
			Root<Partner> partnerRoot=query.from(Partner.class);
			query.multiselect(employeeRoot,partnerRoot);
			
			Predicate employeeRestriction= builder.and(
					builder.equal(employeeRoot.get("employeeName"), "Prabhat Singh"),
					builder.isNotEmpty(employeeRoot.get("phone"))
					);
			Predicate partnerRestriction= builder.and(
					builder.like(partnerRoot.get("partnerName"), "%Vi%"),
					builder.equal(partnerRoot.get("version"),1)
					);
			
			query.where(builder.and(employeeRestriction,partnerRestriction));
			
			List<Tuple> tuples = session.createQuery(query).getResultList();
			for (Tuple tuple : tuples) {
			    Employee employee=(Employee) tuple.get(0);
			    if(employee !=null) {
			    	System.out.println(employee);
			    	List<Phone> phones = employee.getPhone();
			    	for (Phone phone : phones) {
						System.out.println(phone.getPhoneNumber()+"\t"+phone.getPhoneType());
					}
			    }
			    Partner partner=(Partner) tuple.get(1);
			    System.out.println(partner.getPartnerName()+"\t"+partner.getVersion());
			}
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
	}

}
