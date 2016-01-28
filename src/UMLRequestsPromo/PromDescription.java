package UMLRequestsPromo;


public class PromDescription {
	
	private String type;
	private String cost;
	private String endDate;
	private String details;
	
	public PromDescription(String type, String cost, String endDate, String details)
	{
		this.type = type;
		this.cost = cost;
		this.endDate = endDate;
		this.details = details;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
