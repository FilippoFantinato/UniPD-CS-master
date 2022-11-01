package atm.ATM.statechans.A.ioifaces;

import atm.ATM.statechans.A.EndSocket;

public interface Succ_In_C_Quit {

	default EndSocket to(EndSocket cast) {
		return (EndSocket) this;
	}
}