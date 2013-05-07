       ******************************************************************
       * XTAR1001 - STORE DETAILS EXTRACT FILE                          *
       *                                                                *
       * AUTHOR BRUCE MARTIN   5 SEP 2003                               *
       *                                                                *
       * VERS   DATE   AUTHOR    PURPOSE                                *
       * -------------------------------------------------------------- *
       *  01  05/09/03 B MARTIN  INITIAL VERSION                        *
       *                                                                *
       *                                                                *
       ******************************************************************
                                                                         
        01  01TAR1000-REC.                                                
            10 00001000-STORE-NO       PIC S9(4) COMP.                   
            10 000R1000-REGION-NO      PIC S9(4) COMP.                   
            10 XTAR1000-STORE-NAME     PIC X(50).                        
            10 XTAR1000-NEW-STORE      PIC X(1).                         
            10 XTAR1000-ACTIVE-STORE   PIC X(1).                         
            10 XTAR1000-CLOSED-STORE   PIC X(1).                         
            10 XTAR1000-DC-TYPE        PIC X(1).                         
            10 XTAR1000-SRC-TYPE       PIC X(1). 
            10 XTAR1000-HO-TYPE        PIC X(1). 
            
000100*                                                                         
000200*   XTAR020 IS THE OUTPUT FROM DTAB020 FROM THE IML                       
000300*   CENTRAL REPORTING SYSTEM                                              
000400*                                                                         
000500*   CREATED BY BRUCE ARTHUR  19/12/90                                     
000600*                                                                         
000700*   RECORD LENGTH IS 27.                                                  
000800*                                                                         
       01  01TAR020-REC.                                                
000900        03  XTAR020-KCODE-STORE-KEY.                                      
001000            05 XTAR020-KEYCODE-NO      PIC X(08).                         
001100            05 XTAR020-STORE-NO        PIC S9(03)   COMP-3.               
001200        03  XTAR020-DATE               PIC S9(07)   COMP-3.               
001300        03  XTAR020-DEPT-NO            PIC S9(03)   COMP-3.               
001400        03  XTAR020-QTY-SOLD           PIC S9(9)    COMP-3.               
001500        03  XTAR020-SALE-PRICE         PIC S9(9)V99 COMP-3.               
            
