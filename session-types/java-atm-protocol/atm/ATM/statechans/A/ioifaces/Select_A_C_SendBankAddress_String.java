package atm.ATM.statechans.A.ioifaces;

import atm.ATM.roles.*;
import atm.ATM.statechans.A.*;

public interface Select_A_C_SendBankAddress_String<__Succ1 extends Succ_Out_C_SendBankAddress_String> extends Out_C_SendBankAddress_String<__Succ1>, Succ_In_C_Transfer {
	public static final Select_A_C_SendBankAddress_String<?> cast = null;

	@Override
	default Select_A_C_SendBankAddress_String<?> to(Select_A_C_SendBankAddress_String<?> cast) {
		return (Select_A_C_SendBankAddress_String<?>) this;
	}

	default ATM_A_5 to(ATM_A_5 cast) {
		return (ATM_A_5) this;
	}
}