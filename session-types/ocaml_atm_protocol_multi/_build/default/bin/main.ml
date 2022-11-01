open Mpst.BasicCombinators
open Mpst.Unicast

[%%declare_roles_prefixed a, b, c] (* note that the order matters *)
[%%declare_labels msg]


let ring = (a --> b) msg @@ (b --> c) msg @@ (c --> a) msg finish
let (`cons(sa, `cons(sb, `cons(sc, _)))) = extract ring;;
(* Participant A *)
Thread.create (fun () ->
  let sa = select sa#role_B#msg in
  let `msg sa = branch sa#role_C in
  close sa
) ();;

(* Participant B *)
Thread.create (fun () ->
  let `msg sb = branch sb#role_A in
  let sb = select sb#role_C#msg in
  close sb
) ();;

(* Participant C *)
let `msg sc = branch sc#role_B in
let sc = select sc#role_A#msg in
close sc;
print_endline "Ring-based communication finished successfully!"