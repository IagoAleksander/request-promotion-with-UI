package UMLRequestsPromo;


public class Invoice {

	Client c;
	private double amount;
	private boolean paymentMade;

	public Invoice (double amount, Client c) 
	{
		this.amount = amount;
		this.paymentMade = false;
		this.c = c;
	}
	
	public boolean isPaymentMade() {
		return paymentMade;
	}

	public void setPaymentMade(boolean paymentMade) {
		this.paymentMade = paymentMade;
	}
	
}
