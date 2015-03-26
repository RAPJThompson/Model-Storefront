package model;

public class Item {
	String upc;
	String name;
	String title;
	String type;
	String category;
	String company;
	String year;
	Double price;
	int stock;
	int quantity;

	public Item(String upc) {		
		this.upc = upc;
		this.name = "";
		this.title = "";
		this.type = "";
		this.category = "";
		this.company = "";
		this.year = "";
		this.price = 0.0;
		this.stock = 0;
		this.quantity=0;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setYear (String year) {
		this.year = year;
	}

	public void setPrice (Double price) {
		this.price = price;
	}

	public void setStock (int stock) {
		this.stock = stock;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUPC() {
		return this.upc;
	}

	public String getName() {
		return this.name;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	public String getCategory() {
		return this.category;
	}

	public String getCompany() {
		return this.company;
	}

	public String getYear() {
		return this.year;
	}

	public Double getPrice() {
		return this.price;
	}
	public int getStock() {
		return this.stock;
	}

	public int getQuantity() {
		return this.quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((upc == null) ? 0 : upc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (upc == null) {
			if (other.upc != null)
				return false;
		} else if (!upc.equals(other.upc))
			return false;
		return true;
	}

}
