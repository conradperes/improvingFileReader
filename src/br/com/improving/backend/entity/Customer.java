package br.com.improving.backend.entity;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.function.Function;

import org.apache.commons.validator.routines.BigDecimalValidator;
import org.apache.commons.validator.routines.CurrencyValidator;

public class Customer {
	private String description;
	private String monetary;
	private String type;
	private int id;
	private Long fileId;

	private static Customer instance = null;

	// private Repository repository = new RepositoryImplMock();
	public static Customer getInstance() {
		if (instance == null) {
			instance = new Customer();
		}
		return instance;
	}

	/*
	 * public static class Builder { private String description; private String
	 * monetary; private String type; private int id; private Builder instance
	 * =null;; public Customer.Builder getInstance(){ if(instance == null){
	 * instance = new Builder(); } return instance; }
	 */

	public Customer description(String des) {
		this.description = des;
		return this;
	}

	public Customer fileId(Long fileId) {
		this.fileId = fileId;
		return this;
	}

	public Long getFileId() {
		return fileId;
	}

	public Customer id(int id) {
		this.id = id;
		return this;
	}

	public int getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private boolean isString(String des) {
		return des != null && des.contains("^[a-Z]");
	}

	public boolean monetary(String mon) {
		if (isMonetaryValue(mon)) {
			this.monetary = mon;
			// PrintUtils.print("valor = " + this.monetary);
			// Long clientNumber = new Long(id);
			// repository.save(fileId, new Long(id), clientNumber, new
			// BigDecimal(mon));
			return true;
		} else {
			// repository.reportInvalidLine(fileId, id, Field.VALUE, "Dados
			// monetários inválidos");
			return false;
		}
	}

	private boolean isMonetaryValue(String mon) {
		BigDecimalValidator validator = CurrencyValidator.getInstance();

		BigDecimal amount = validator.validate(mon, Locale.US);

		if (amount == null) {
			return false;
		} else
			return true;
		// return mon != null && mon.matches("^(\\S)(.){1,75}(\\S)$");
	}

	public Customer type(String type) {
		if (type != null && type.equals("D")) {
			this.type = type;
			// PrintUtils.print("Valor monetário+" + this.monetary);
			if (isMonetaryValue(monetary)) {
				double resultant = Double.parseDouble(monetary);
				resultant = resultant * (-1);
				this.monetary = Double.toString(resultant);
				// PrintUtils.print("Valor modificado" + monetary);
			}
		} else if (type.equals("C")) {
			// PrintUtils.print("Chama api"+type);
			this.type = type;
		}

		return this;
	}

	// public Customer build() {
	// return new Customer(this);
	// }

	// }

	private Customer() {
		super();
	}

	// private Customer(Customer.Builder builder) {
	// description = builder.description;
	// monetary = builder.monetary;
	// type = builder.type;
	// id = builder.id;
	//
	// }

	public String getDescription() {
		return description;
	}

	public String getMonetary() {
		return monetary;
	}

	public void print() {
		System.out.println("\nDescription: " + description + " " + monetary + "\n" + "Tipo: " + type);
	}

	public String getType() {
		return type;
	}

	public String printCustom(Function<Customer, String> f) {
		return f.apply(this);
	}

	// public int getId() {
	// return id;
	// }

}
