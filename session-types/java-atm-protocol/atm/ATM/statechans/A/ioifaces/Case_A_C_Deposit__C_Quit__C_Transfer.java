package atm.ATM.statechans.A.ioifaces;

import atm.ATM.ops.*;

public interface Case_A_C_Deposit__C_Quit__C_Transfer<__Succ1 extends Succ_In_C_Deposit, __Succ2 extends Succ_In_C_Quit, __Succ3 extends Succ_In_C_Transfer> extends In_C_Deposit<__Succ1>, In_C_Quit<__Succ2>, In_C_Transfer<__Succ3> {
	public static final Branch_A_C_Deposit__C_Quit__C_Transfer<?, ?, ?> cast = null;

	abstract Branch_A_C_Deposit__C_Quit__C_Transfer.Branch_A_C_Deposit__C_Quit__C_Transfer_Enum getOp();

	abstract public __Succ1 receive(Deposit op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;

	abstract public atm.ATM.statechans.A.EndSocket receive(Quit op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;

	abstract public __Succ3 receive(Transfer op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;
}