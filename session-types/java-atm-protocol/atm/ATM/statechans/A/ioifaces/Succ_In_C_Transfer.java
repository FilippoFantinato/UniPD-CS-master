package atm.ATM.statechans.A.ioifaces;

public interface Succ_In_C_Transfer {

	default Select_A_C_SendBankAddress_String<?> to(Select_A_C_SendBankAddress_String<?> cast) {
		return (Select_A_C_SendBankAddress_String<?>) this;
	}
}