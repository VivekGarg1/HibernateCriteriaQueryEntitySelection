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
import com.home.entities.Call;
import com.home.entities.Employee;
import com.home.entities.Partner;
import com.home.entities.Phone;
import com.home.util.HibernateUtil;

public class CriteriaQueryWithJoinClientTest {
	
	public static void main(String[] args) {
		
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			CriteriaBuilder builder=session.getCriteriaBuilder();
			CriteriaQuery<Phone> query= builder.createQuery(Phone.class);
			Root<Phone> phoneRoot=query.from(Phone.class);
			//It is Lazily loaded 
			//phoneRoot.join("employee");
			
			//Eagerly loaded
			phoneRoot.fetch("employee");
			phoneRoot.fetch("call");
			
			query.where(builder.isNotEmpty(phoneRoot.get("call")));
			
			List<Phone> phoneList = session.createQuery(query).getResultList();
			for (Phone phone : phoneList) {
			    Employee employee=(Employee) phone.getEmployee();
			    if(employee !=null) 
			    	System.out.println(employee);
			    if(phone !=null) {
			    	System.out.println(phone);
			    }
			    List<Call> calls= phone.getCall();
			    for (Call call : calls) {
					System.out.println(call);
				}
			}
		}
		catch(HibernateException e) {
			e.printStackTrace();
		}
	}

}
