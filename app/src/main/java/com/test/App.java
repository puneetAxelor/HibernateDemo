package com.test;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.test.entity.Employee;
import com.test.entity.Laptop;

//Inheritance mapping strategy

@WebServlet("save")
public class App extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().print("Saved to the database");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		
		
		Configuration config = null;
		SessionFactory sessionFactory = null;
		Session session = null;

		Transaction tx = null;
	
		
		try { 
			config = new Configuration().configure().addAnnotatedClass(Employee.class).addAnnotatedClass(Laptop.class);
			sessionFactory = config.buildSessionFactory();
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			Laptop laptop = new Laptop();
			laptop.setLid(101);
			laptop.setLname("Dell");
			
			
			Employee emp = new Employee();
			emp.setId(5);
			emp.setName("newN");
			emp.setEmail("pdaf");
			emp.getLaptop().add(laptop);
			
			laptop.getEmployee().add(emp); 
			
			session.save(laptop);
			session.save(emp);
		/*	
			Query q1 = session1.createQuery("from Employee where id=5");
			// a = (Employee) session1.get(Employee.class, 5);
			q1.setCacheable(true);
			a = (Employee)q1.uniqueResult();
			System.out.println(a);
			
			Query q2 = session2.createQuery("from Employee where id=101");
			// a = (Employee) session2.get(Employee.class, 5);
			q2.setCacheable(true);
			a2 = (Employee)q2.uniqueResult();
			System.out.println(a2);
			*/
			
			tx.commit();
			
			request.getRequestDispatcher("response.jsp").forward(request, response);
		
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			
			sessionFactory.close();
			
		}
	}

}
