package ch.xog.hibernate.hhh9576;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Query;
import org.hibernate.jpa.internal.QueryImpl;
import org.junit.Before;
import org.junit.Test;

public class CriteriaParameterTest {

    private EntityManager entityManager;

    @Before
    public void setUp() {
	EntityManagerFactory entityManagerFactory = Persistence
		.createEntityManagerFactory("hhh9576");
	entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void testPredicateWithString() {
	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	CriteriaQuery<Animal> criteriaQuery = criteriaBuilder
		.createQuery(Animal.class);
	Root<Animal> from = criteriaQuery.from(Animal.class);

	Predicate namePredicate = criteriaBuilder.equal(from.get("name"),
		"horse");

	criteriaQuery.select(from).where(namePredicate);
	TypedQuery<Animal> typedQuery = entityManager
		.createQuery(criteriaQuery);

	Query query = typedQuery.unwrap(QueryImpl.class).getHibernateQuery();
	System.out.println(query.getQueryString());
	assertTrue(query.getQueryString().contains("name=:param"));
    }

    @Test
    public void testPredicateWithInteger() {
	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	CriteriaQuery<Animal> criteriaQuery = criteriaBuilder
		.createQuery(Animal.class);
	Root<Animal> from = criteriaQuery.from(Animal.class);

	Predicate agePredicate = criteriaBuilder.equal(from.get("age"),
		Integer.valueOf(42));

	criteriaQuery.select(from).where(agePredicate);
	TypedQuery<Animal> typedQuery = entityManager
		.createQuery(criteriaQuery);

	Query query = typedQuery.unwrap(QueryImpl.class).getHibernateQuery();
	System.out.println(query.getQueryString());
	assertTrue(query.getQueryString().contains("age=:param"));
    }
}
