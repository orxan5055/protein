package com.protein.servis;

import java.util.Set;

import com.protein.domain.User;
import com.protein.domain.UserBilling;
import com.protein.domain.UserPayment;
import com.protein.domain.UserShipping;
import com.protein.domain.tehlukesizlik.PasswordResetToken;
import com.protein.domain.tehlukesizlik.UserRole;

public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User findByUsername(String username);
	
	User findByEmail (String email);
	
	User findById(Long id);
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);
	
	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);
	
	void updateUserShipping(UserShipping userShipping, User user);
	
	void setUserDefaultPayment(Long userPaymentId, User user);
	
	void setUserDefaultShipping(Long userShippingId, User user);
}
