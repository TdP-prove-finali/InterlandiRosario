package it.polito.s284166.Tesi.model;

public class TableModel {
	private Integer id;
	private String make;
	private String model;
	private Integer engine;
	private Integer year;
	private Integer kilometer;
	private Integer price;
	public TableModel(Integer id, String make, String model, Integer engine, Integer year, Integer kilometer,
			Integer price) {
		super();
		this.id = id;
		this.make = make;
		this.model = model;
		this.engine = engine;
		this.year = year;
		this.kilometer = kilometer;
		this.price = price;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getEngine() {
		return engine;
	}
	public void setEngine(Integer engine) {
		this.engine = engine;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getKilometer() {
		return kilometer;
	}
	public void setKilometer(Integer kilometer) {
		this.kilometer = kilometer;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}

	
}
