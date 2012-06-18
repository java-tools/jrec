
           01  Main.
               03 type   pic x.
               03 hf1    pic xxx.
               03 hf2    pic xx.
               03 rec1.
                  05 f1    pic xx.
                  05 f2    pic 999.
                  05 f3 redefines f2.
                     07 f4    pic x.
                  05 f5    pic x(20).
               03 rec2 redefines rec1.
                  05 r2f1   pic xxxxx.
                  05 r2f2   pic xxx.

               03 rec3 redefines rec1.
                  05 r3f1   pic 9(5).
                  05 r3f2   pic 9(4) comp.

               03 rec4 redefines rec1.
                  05 r4f1   pic 9(5).
                  05 r4f2   pic 9(4) comp.


