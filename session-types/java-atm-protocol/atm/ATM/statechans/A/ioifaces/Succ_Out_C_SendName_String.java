package atm.ATM.statechans.A.ioifaces;

public interface Succ_Out_C_SendName_String {

	default Branch_A_C_Deposit__C_Quit__C_Transfer<?, ?, ?> to(Branch_A_C_Deposit__C_Quit__C_Transfer<?, ?, ?> cast) {
		return (Branch_A_C_Deposit__C_Quit__C_Transfer<?, ?, ?>) this;
	}
}