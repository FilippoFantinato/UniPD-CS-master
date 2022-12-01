/*********************************************
 * OPL 22.1.0.0 Model
 * Author: filippofantinato
 * Creation Date: Oct 15, 2022 at 4:42:50 PM
 *********************************************/

dvar float+ xv;
dvar float+ xm;
dvar float+ xf;

minimize (4*xv + 10*xm + 7*xf);
 
subject to {
  proteins: (5*xv + 15*xm + 4*xf) >= 20;
  iron: (6*xv + 10*xm + 5*xf) >= 30;
  calcium: (5*xv + 3*xm + 12*xf) >= 10;
}
