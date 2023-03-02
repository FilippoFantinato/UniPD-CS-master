/*********************************************
 * OPL 20.1.0.0 Model
 * Author: luigi
 * Creation Date: 19 nov 2021 at 13:03:04
 *********************************************/
 
 int m = 100;
 int n = 50*1000;
  
 setof(int) I = asSet(1..m);
 setof(int) J = asSet(1..n);
 
 int whatever=srand(5);
 execute { writeln("srand(1) gives ",whatever);}
 
 int A[i in I][ j in J] = rand(10);
 int b[i in I] = rand(1000);
 int c[j in J] = rand(10);
 
 dvar boolean x[J];
 
maximize sum(j in J) c[j]*x[j];
 
subject to{
  forall(i in I){
    resource: sum(j in J) A[i][j]*x[j] <= b[i];
  }
} 
  
