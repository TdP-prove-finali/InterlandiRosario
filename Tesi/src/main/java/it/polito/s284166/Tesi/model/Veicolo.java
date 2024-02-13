package it.polito.s284166.Tesi.model;

public class Veicolo implements Comparable<Veicolo> {
	private Integer id;
	private String make;
	private String model;
	private Integer price;
	private Integer year;
	private Integer kilometer;
	private String fuel_type;
	private String transmission;
	private String location;
	private String color;
	private String owner;
	private String seller_type;
	private Integer engine;
	private Integer bhp;
	private Integer rpm;
	private String driveTrain;
	private Double length;
	private Double width;
	private Double height;
	private Integer seating_capacity;
	private Integer fuel_tank_capacity;

	public Veicolo(Integer id, String make, String model, Integer price, Integer year, Integer kilometer,
			String fuel_type, String transmission, String location, String color, String owner, String seller_type,
			Integer engine, Integer bhp, Integer rpm, String driveTrain, Double length, Double width, Double height,
			Integer seating_capacity, Integer fuel_tank_capacity) {
		super();
		this.id = id;
		this.make = make;
		this.model = model;
		this.price = price;
		this.year = year;
		this.kilometer = kilometer;
		this.fuel_type = fuel_type;
		this.transmission = transmission;
		this.location = location;
		this.color = color;
		this.owner = owner;
		this.seller_type = seller_type;
		this.engine = engine;
		this.bhp = bhp;
		this.rpm = rpm;
		this.driveTrain = driveTrain;
		this.length = length;
		this.width = width;
		this.height = height;
		this.seating_capacity = seating_capacity;
		this.fuel_tank_capacity = fuel_tank_capacity;
	}

	public Integer getId() {
		return id;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public Integer getPrice() {
		return price;
	}

	public Integer getYear() {
		return year;
	}

	public Integer getKilometer() {
		return kilometer;
	}

	public String getFuel_type() {
		return fuel_type;
	}

	public String getTransmission() {
		return transmission;
	}

	public String getLocation() {
		return location;
	}

	public String getColor() {
		return color;
	}

	public String getOwner() {
		return owner;
	}

	public String getSeller_type() {
		return seller_type;
	}

	public Integer getEngine() {
		return engine;
	}

	public Integer getBhp() {
		return bhp;
	}

	public Integer getRpm() {
		return rpm;
	}

	public String getDriveTrain() {
		return driveTrain;
	}

	public Double getLength() {
		return length;
	}

	public Double getWidth() {
		return width;
	}

	public Double getHeight() {
		return height;
	}

	public Integer getSeating_capacity() {
		return seating_capacity;
	}

	public Integer getFuel_tank_capacity() {
		return fuel_tank_capacity;
	}

	@Override
	public int compareTo(Veicolo o) {
		// TODO Auto-generated method stub
		return this.getId() - o.getId();
	}

	@Override
	public String toString() {
		return this.id + "-" + this.make + ":" + this.model + "(" + this.year + ")";
	}

}
