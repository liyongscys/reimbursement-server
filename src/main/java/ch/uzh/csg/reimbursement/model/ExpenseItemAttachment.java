package ch.uzh.csg.reimbursement.model;

import static java.util.UUID.randomUUID;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "ExpenseItemAttachment")
public class ExpenseItemAttachment{

	@Transient
	private final Logger LOG = LoggerFactory.getLogger(ExpenseItemAttachment.class);

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;

	@Getter
	@Setter
	@Column(nullable = false, updatable = true, unique = false, name = "uid")
	private String uid;

	@Column(nullable = false, updatable = true, unique = false, name = "content_type")
	private String contentType;

	@Column(nullable = false, updatable = true, unique = false, name = "file_size")
	private long fileSize;

	@Getter
	@Setter
	@Column(nullable = false, updatable = true, unique = false, name = "content", columnDefinition = "blob")
	private byte[] content;

	public ExpenseItemAttachment(String contentType, long fileSize, byte[] content) {
		this.uid = randomUUID().toString();
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.content = content;
		LOG.info("ExpenseItemAttachment constructor: ExpenseItemAttachment created");
	}

	public void updateExpenseItemAttachment(String contentType, long fileSize, byte[] content) {
		this.uid = randomUUID().toString();
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.content = content;
		LOG.info("ExpenseItemAttachment updated: ExpenseItemAttachment created");
	}

	/*
	 * The default constructor is needed by Hibernate, but should not be used at all.
	 */
	protected ExpenseItemAttachment() {
	}
}
