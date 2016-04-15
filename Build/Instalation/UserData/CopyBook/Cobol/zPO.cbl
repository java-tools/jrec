      ****************************
      
        01  PO-Record.
            03 Record-Type               pic x(3).
               88 PO-Rec  Value 'PO'.
            03 PO-Id                     pic x(14).
            03 Total-Cost                pic s9(9)V99.
            03 Po-Status                 pic 99.
               88 in-Creation        value 05.
               88 on-Order           value 10.
               88 In-DC              value 15.
               88 Being-Distributed  value 20.
               88 In-Stores          value 25.
               88 Finalised          value 99.
        
        01  Product-Record. 
            03 Record-Type               pic x(3).
               88 Product-rec Value 'PRD'.
            03 Product-Code              pic 9(8).
            03 Product-Name              pic X(20).
            03 Product-Type              pic x.
               88 Hardware   value 'H'.
               88 Food       value 'F'.
               88 Clothing   value 'C'.
               88 Footware   value 'S'.
            03 Product-Cost             pic s9(5)V99.
              
        01  Location-Record.
            03 Record-Type               pic x(3).
               88 Location-rec Value 'STR'.
            03 Location                 pic 0(4).
            03 Location-type            pic x.
               88 Is-Store     value 'S'.
               88 Is-DC        value 'D'.
               88 Is-New-Store value 'N'.
            03 Location-Name            pic x(30).
            03 Quantity                 pic s9(5).
            
        