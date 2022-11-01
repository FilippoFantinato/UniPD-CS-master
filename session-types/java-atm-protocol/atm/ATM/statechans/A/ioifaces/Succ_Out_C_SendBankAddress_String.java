package atm.ATM.statechans.A.ioifaces;

public interface Succ_Out_C_SendBankAddress_String {

	default Select_A_C_SendMoney_Int<?> to(Select_A_C_SendMoney_Int<?> cast) {
		return (Select_A_C_SendMoney_Int<?>) this;
	}
}