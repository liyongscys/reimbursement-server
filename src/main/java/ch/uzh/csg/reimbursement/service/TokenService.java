package ch.uzh.csg.reimbursement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.uzh.csg.reimbursement.model.Token;
import ch.uzh.csg.reimbursement.model.TokenType;
import ch.uzh.csg.reimbursement.model.User;
import ch.uzh.csg.reimbursement.repository.TokenRepositoryProvider;

@Service
public class TokenService {

	@Autowired
	private TokenRepositoryProvider tokenRepository;

	public void create(Token token) {
		tokenRepository.create(token);
	}

	public void delete(Token token) {
		tokenRepository.delete(token);
	}

	public Token findByUid(String uid) {
		return tokenRepository.findByUid(uid);
	}

	public Token findByTypeAndUser(TokenType type, User user) {
		return tokenRepository.findByTypeAndUser(type, user);
	}

	public List<Token> findAll() {
		return tokenRepository.findAll();
	}
}
