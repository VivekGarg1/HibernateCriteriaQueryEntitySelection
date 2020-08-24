package com.home.client;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.home.util.HibernateUtil;

public class UpdateAndDeleteDataClientTest {

	public static void main(String[] args) {
		SessionFactory sf=HibernateUtil.getSessionFactory();
			//updateEmployeeEmailById(sf);
		deleteEmployeeById(sf);
	}

	private static void deleteEmployeeById(SessionFactory sf) {
		int empId=6;
		try(Session session=sf.openSession()){
			String HQL="delete from Employee where employeeId=:empId";
			Query query = session.createQuery(HQL);
			query.setParameter("empId", empId);
			session.beginTransaction();
			int executeUpdate=query.executeUpdate();
			session.getTransaction().commit();
			if(executeUpdate >0)
				System.out.println("Employee deleted successfully!!!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void updateEmployeeEmailById(SessionFactory sf) {
		
		int empId=2;
		String newEmail="prabhat.Singh@gmail.com";
         try(Session session=sf.openSession()){
			String HQL="update Employee set email=:newEmail where employeeId=:empId";
			Query query = session.createQuery(HQL);
			query.setParameter("newEmail", newEmail);
			query.setParameter("empId", empId);
			session.beginTransaction();
			int executeUpdate=query.executeUpdate();
			if(executeUpdate >0)
				System.out.println("Employee records updated successfully!!!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
