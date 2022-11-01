package atm.ATM.statechans.A.ioifaces;

import atm.ATM.ops.*;

public interface Handle_A_C_Deposit__C_Quit__C_Transfer<__Succ1 extends Succ_In_C_Deposit, __Succ2 extends Succ_In_C_Quit, __Succ3 extends Succ_In_C_Transfer> extends Callback_C_Deposit<__Succ1>, Callback_C_Quit<__Succ2>, Callback_C_Transfer<__Succ3> {

	abstract public void receive(__Succ1 schan, Deposit op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;

	abstract public void receive(__Succ2 schan, Quit op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;

	abstract public void receive(__Succ3 schan, Transfer op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;
}