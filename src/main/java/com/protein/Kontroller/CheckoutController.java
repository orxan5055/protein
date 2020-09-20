package com.protein.Kontroller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.protein.domain.OdemeAdresi;
import com.protein.domain.CartItem;
import com.protein.domain.Sifaris;
import com.protein.domain.Payment;
import com.protein.domain.CatdirilmaAdresi;
import com.protein.domain.ShoppingCart;
import com.protein.domain.User;
import com.protein.domain.UserBilling;
import com.protein.domain.UserPayment;
import com.protein.domain.UserShipping;
import com.protein.servis.OdemeAdresiServis;
import com.protein.servis.CartItemService;
import com.protein.servis.CatdirilmaAdresiServis;
import com.protein.servis.SifarisServis;
import com.protein.servis.PaymentService;
import com.protein.servis.CatdirilmaAdresiServis;
import com.protein.servis.ShoppingCartService;
import com.protein.servis.UserPaymentService;
import com.protein.servis.UserService;
import com.protein.servis.UserShippingService;
import com.protein.utilitiler.MailConstructor;

@Controller
public class CheckoutController {

	private CatdirilmaAdresi catdirilmaAdresi = new CatdirilmaAdresi();
	private OdemeAdresi odemeAdresi = new OdemeAdresi();
	private Payment payment = new Payment();

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private CatdirilmaAdresiServis catdirilmaAdresiServis;

	@Autowired
	private OdemeAdresiServis odemeAdresiServis;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private UserShippingService userShippingService;

	@Autowired
	private UserPaymentService userPaymentService;
	
	@Autowired
	private SifarisServis sifarisServis;

	@RequestMapping("/checkingout")
	public String checkingout(@RequestParam("id") Long cartId,
			@RequestParam(value = "missingRequiredField", required = false) boolean missingRequiredField, Model model,
			Principal principal) {
		User user = userService.findByUsername(principal.getName());

		if (cartId != user.getShoppingCart().getId()) {
			return "badRequestPage";
		}

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

		if (cartItemList.size() == 0) {
			model.addAttribute("emptyCart", true);
			return "forward:/shoppingCart/cart";
		}

		for (CartItem cartItem : cartItemList) {
			if (cartItem.getProtein().getStokdakiSay() < cartItem.getQty()) {
				model.addAttribute("notEnoughStock", true);
				return "forward:/shoppingCart/cart";
			}
		}

		List<UserShipping> userShippingList = user.getUserShippingList();
		List<UserPayment> userPaymentList = user.getUserPaymentList();

		model.addAttribute("userShippingList", userShippingList);
		model.addAttribute("userPaymentList", userPaymentList);

		if (userPaymentList.size() == 0) {
			model.addAttribute("emptyPaymentList", true);
		} else {
			model.addAttribute("emptyPaymentList", false);
		}

		if (userShippingList.size() == 0) {
			model.addAttribute("emptyShippingList", true);
		} else {
			model.addAttribute("emptyShippingList", false);
		}


		for (UserShipping userShipping : userShippingList) {
			if (userShipping.isUserShippingDefault()) {
				catdirilmaAdresiServis.setByUserShipping(userShipping, catdirilmaAdresi);
			}
		}

		for (UserPayment userPayment : userPaymentList) {
			if (userPayment.isDefaultPayment()) {
				paymentService.setByUserPayment(userPayment, payment);
				odemeAdresiServis.setByUserBilling(userPayment.getUserBilling(), odemeAdresi);
			}
		}

		model.addAttribute("catdirilmaAdresi", catdirilmaAdresi);
		model.addAttribute("payment", payment);
		model.addAttribute("odemeAdresi", odemeAdresi);
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", user.getShoppingCart());

		
		model.addAttribute("classActiveShipping", true);

		if (missingRequiredField) {
			model.addAttribute("missingRequiredField", true);
		}

		return "checkingout";

	}

	@RequestMapping(value = "/checkingout", method = RequestMethod.POST)
	public String checkingout(@ModelAttribute("catdirilmaAdresi") CatdirilmaAdresi catdirilmaAdresi,
			@ModelAttribute("odemeAdresi") OdemeAdresi odemeAdresi, @ModelAttribute("payment") Payment payment,
			@ModelAttribute("billingSameAsShipping") String billingSameAsShipping,
			@ModelAttribute("catdirilmaMetodu") String catdirilmaMetodu, Principal principal, Model model) {
		ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		model.addAttribute("cartItemList", cartItemList);

		if (billingSameAsShipping.equals("true")) {
			odemeAdresi.setOdemeAdresiAdi(catdirilmaAdresi.getCatdirilmaAdresiAdi());
			odemeAdresi.setOdemeAdresiKuce1(catdirilmaAdresi.getCatdirilmaAdresiKuce1());
			odemeAdresi.setOdemeAdresiKuce2(catdirilmaAdresi.getCatdirilmaAdresiKuce2());
			odemeAdresi.setOdemeAdresiSeher(catdirilmaAdresi.getCatdirilmaAdresiSeher());
			odemeAdresi.setOdemeAdresiRegion(catdirilmaAdresi.getCatdirilmaAdresiRegion());
			odemeAdresi.setOdemeAdresiOlke(catdirilmaAdresi.getCatdirilmaAdresiOlke());
			odemeAdresi.setOdemeAdresiZipkodu(catdirilmaAdresi.getCatdirilmaAdresiZipkodu());
		}

		if (catdirilmaAdresi.getCatdirilmaAdresiKuce1().isEmpty() || catdirilmaAdresi.getCatdirilmaAdresiSeher().isEmpty()
				|| catdirilmaAdresi.getCatdirilmaAdresiSeher().isEmpty()
				|| catdirilmaAdresi.getCatdirilmaAdresiAdi().isEmpty()
				|| catdirilmaAdresi.getCatdirilmaAdresiZipkodu().isEmpty() || payment.getCardNumber().isEmpty()
				|| payment.getCvc() == 0 || odemeAdresi.getOdemeAdresiKuce1().isEmpty()
				|| odemeAdresi.getOdemeAdresiSeher().isEmpty() || odemeAdresi.getOdemeAdresiRegion().isEmpty()
				|| odemeAdresi.getOdemeAdresiAdi().isEmpty()
				|| odemeAdresi.getOdemeAdresiZipkodu().isEmpty())
			return "redirect:/checkingout?id=" + shoppingCart.getId() + "&missingRequiredField=true";
		
		User user = userService.findByUsername(principal.getName());
		
		Sifaris sifaris = sifarisServis.createOrder(shoppingCart, catdirilmaAdresi, odemeAdresi, payment, catdirilmaMetodu, user);
		
		mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, sifaris, Locale.ENGLISH));
		
		shoppingCartService.clearShoppingCart(shoppingCart);
		
		LocalDate today = LocalDate.now();
		LocalDate estimatedDeliveryDate;
		if (catdirilmaMetodu.equals("groundShipping")) {
			estimatedDeliveryDate = today.plusDays(5);
		} else {
			estimatedDeliveryDate = today.plusDays(3);
			
		}
		
		model.addAttribute("estimatedDeliveryDate", estimatedDeliveryDate);
		
		return "orderSubmittedPage";
	}

	@RequestMapping("/setShippingAddress")
	public String setShippingAddress(@RequestParam("userShippingId") Long userShippingId, Principal principal,
			Model model) {
		User user = userService.findByUsername(principal.getName());
		UserShipping userShipping = userShippingService.findById(userShippingId);

		if (userShipping.getUser().getId() != user.getId()) {
			return "badRequestPage";
		} else {
			catdirilmaAdresiServis.setByUserShipping(userShipping, catdirilmaAdresi);

			List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

			model.addAttribute("catdirilmaAdresi", catdirilmaAdresi);
			model.addAttribute("payment", payment);
			model.addAttribute("odemeAdresi", odemeAdresi);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("shoppingCart", user.getShoppingCart());

			
			List<UserShipping> userShippingList = user.getUserShippingList();
			List<UserPayment> userPaymentList = user.getUserPaymentList();

			model.addAttribute("userShippingList", userShippingList);
			model.addAttribute("userPaymentList", userPaymentList);

			model.addAttribute("catdirilmaAdresi", catdirilmaAdresi);

			model.addAttribute("classActiveShipping", true);

			if (userPaymentList.size() == 0) {
				model.addAttribute("emptyPaymentList", true);
			} else {
				model.addAttribute("emptyPaymentList", false);
			}

			model.addAttribute("emptyShippingList", false);

			return "checkingout";
		}
	}

	@RequestMapping("/setPaymentMethod")
	public String setPaymentMethod(@RequestParam("userPaymentId") Long userPaymentId, Principal principal,
			Model model) {
		User user = userService.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentService.findById(userPaymentId);
		UserBilling userBilling = userPayment.getUserBilling();

		if (userPayment.getUser().getId() != user.getId()) {
			return "badRequestPage";
		} else {
			paymentService.setByUserPayment(userPayment, payment);

			List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

			odemeAdresiServis.setByUserBilling(userBilling, odemeAdresi);

			model.addAttribute("catdirilmaAdresi", catdirilmaAdresi);
			model.addAttribute("payment", payment);
			model.addAttribute("odemeAdresi", odemeAdresi);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("shoppingCart", user.getShoppingCart());

			

			List<UserShipping> userShippingList = user.getUserShippingList();
			List<UserPayment> userPaymentList = user.getUserPaymentList();

			model.addAttribute("userShippingList", userShippingList);
			model.addAttribute("userPaymentList", userPaymentList);

			model.addAttribute("catdirilmaAdresi", catdirilmaAdresi);

			model.addAttribute("classActivePayment", true);

			model.addAttribute("emptyPaymentList", false);

			if (userShippingList.size() == 0) {
				model.addAttribute("emptyShippingList", true);
			} else {
				model.addAttribute("emptyShippingList", false);
			}

			return "checkingout";
		}
	}

}
