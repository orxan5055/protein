package com.protein.servis;

import com.protein.domain.Payment;
import com.protein.domain.UserPayment;

public interface PaymentService {
	Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
