package in.om.payload;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBorrow {
	String userName;
	String phone;
	String email;
	String code;
	BigInteger userId;
	Integer categoryId;
    Integer milkCatId;
    Integer foodCatId;
	String categoryName;
	Double totalBorrow;
	Double totalDebit;
	Double debitAmount;
	Double totalCredit;
	Double price;
	Double quantity;
	String paymentMode;
	Timestamp dateTime;
	String colorClass;
	String createdBy;
	String modifiedBy;
	String note;
	List<UserBorrow> userBorrowWithCat;
	
	public Double getTotalBorrow() {
		if(totalDebit != null){
			return (totalDebit - (totalCredit != null ? totalCredit : 0.0));
		} else {
			return totalDebit;
		}
	}

	public Integer getMilkCatId() {
		if(code != null && code.equals("M")) {
			return categoryId;
		} else {
			return milkCatId;
		}
	}

	public Integer getFoodCatId() {
		if(code != null && code.equals("F")) {
			return categoryId;
		} else {
			return foodCatId;
		}
	}
}
