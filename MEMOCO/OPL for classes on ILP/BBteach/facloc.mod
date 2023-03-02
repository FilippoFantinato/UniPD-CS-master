/*********************************************
 * OPL 20.1.0.0 Model
 * Author: luigi
 * Creation Date: 11 nov 2021 at 20:46:21
 *********************************************/

 int m = ...; //customer
 int n = ...; //facilities
 
 setof(int) I = asSet(1..n);
 setof(int) J = asSet(1..m);
 
 setof(int) I0 = {};	//set of y variables indexes to fix to 0
 setof(int) I1 = {};	//set of y variables indexes to fix to 1
 
 float f[I] = ...;
 float c[I][J] = ...;
 
 
 dvar float+	x[I][J];
 dvar float+	y[I];
 
 minimize sum(i in I) f[i]*y[i] + sum(i in I, j in J) c[i][j]*x[i][j];
 
 subject to {
   forall(j in J) sum(i in I) x[i][j] == 1;
   forall(i in I) {
     sum(j in J) x[i][j] <= m*y[i];
     //forall(j in J) x[i][j] <= y[i];     
   }
   forall(i in I) {
     y[i] <= 1;
     forall(j in J)
       x[i][j] <= 1;
   }
   forall(i in I0 ) y[i] == 0;
   forall(i in I1 ) y[i] == 1;
 }
