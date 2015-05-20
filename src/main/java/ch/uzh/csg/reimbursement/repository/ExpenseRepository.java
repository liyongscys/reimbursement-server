package ch.uzh.csg.reimbursement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ch.uzh.csg.reimbursement.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

	@Query("SELECT e FROM Expense e WHERE e.uid = :uid")
	public Expense findByUid(@Param("uid") String uid);

	//TODO @ dave fix query
	//	@Query("SELECT e FROM Expense e WHERE e.user_id = :user_id")
	//	public List<Expense> findAllByUser(@Param("user_id") int user_id);
}