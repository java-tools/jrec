

        01  Product-Header.
           03 Record-Type                  PIC X.
              88 Header-Record                Value 'H'.
              88 Detail-Record                Value 'D'.
           03 Header-Details               PIC X(30).

        01  Product-Detail-1.
           03 Record-Type                  PIC X.
           03 Product-1                    PIC 9(8).
           03 Update-What                  PIC X.
              88 Update-Product-Details      Value 'P'.
              88 Update-Department-Details   Value 'D'.
           03 Product-Details              PIC X(40).

        01  Product-Detail-2.
           03 Record-Type                  PIC X.
           03 Product-2                    PIC 9(8).
           03 Update-What                  PIC X.
           03 Department-Details.
              05 Department-Number         PIC 9(4).
              05 Department-Name           PIC X(30).
