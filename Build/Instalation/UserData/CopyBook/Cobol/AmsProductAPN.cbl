        01  RD430-PROD-RECORD. 
            03  RD430-PROD-NO-X.                                      
                05 RD430-PROD-NO                 PIC 9(14).           
            03  RD430-PROD-BRAND-ID              PIC X(3).            
                88 RD430-PROD-BRAND-COLES        VALUE 'TAR'.
            03  RD430-PROD-QUAL                  PIC X(2).            
            03  RD430-PROD-DESC                  PIC X(40).           
            03  RD430-PROD-KEYCODE-X.                                 
                05  RD430-PROD-KEYCODE           PIC 9(8).            
            03  RD430-DANGR-GOODS-NO-X.                               
                05  RD430-DANGR-GOODS-NO         PIC 9(4).            
            03  RD430-DANGR-GOODS-CLS-X.                              
                05  RD430-DANGR-GOODS-CLS        PIC 9(4).            
            03  RD430-DANGR-GOODS-CLS-X-CMS      REDEFINES            
                RD430-DANGR-GOODS-CLS-X.                              
                05  RD430-DANGR-GOODS-CLS-CMS    PIC 9(3)V9.



