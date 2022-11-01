package atm.ATM.statechans.A.ioifaces;

public interface Succ_In_C_Deposit {

	default Select_A_C_SendMoney_Int<?> to(Select_A_C_SendMoney_Int<?> cast) {
		return (Select_A_C_SendMoney_Int<?>) this;
	}
}