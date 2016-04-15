000033 01  WS-TRANSACTION-RECORD.
000034     05  TR-SOC-SEC-NUMBER                PIC X(9).
000035     05  TR-NAME.
000036         10  TR-LAST-NAME                 PIC X(15).
000037         10  TR-INITIALS                  PIC XX.
000038     05  TR-LOCATION-CODE                 PIC X(3).
000039     05  TR-COMMISSION-RATE               PIC 99.
000040     05  TR-SALES-AMOUNT                  PIC 9(5).
000041     05  TR-TRANSACTION-CODE              PIC X.
000042         88  ADDITION       VALUE 'A'.
000043         88  CORRECTION     VALUE 'C'.
000044         88  DELETION       VALUE 'D'.
000045         88  VALID-CODES    VALUES 'A', 'C', 'D'.
000046

