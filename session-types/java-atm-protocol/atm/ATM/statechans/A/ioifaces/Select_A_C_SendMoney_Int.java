package atm.ATM.statechans.A.ioifaces;

import atm.ATM.roles.*;
import atm.ATM.statechans.A.*;

public interface Select_A_C_SendMoney_Int<__Succ1 extends Succ_Out_C_SendMoney_Int> extends Out_C_SendMoney_Int<__Succ1>, Succ_In_C_Deposit, Succ_Out_C_SendBankAddress_String {
	public static final Select_A_C_SendMoney_Int<?> cast = null;

	@Override
	default Select_A_C_SendMoney_Int<?> to(Select_A_C_SendMoney_Int<?> cast) {
		return (Select_A_C_SendMoney_Int<?>) this;
	}

	default ATM_A_3 to(ATM_A_3 cast) {
		return (ATM_A_3) this;
	}

	default ATM_A_6 to(ATM_A_6 cast) {
		return (ATM_A_6) this;
	}
}