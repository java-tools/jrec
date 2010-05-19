 
To Compile The recordEditor you will need

      hsqldbmain.jar   (lib/hsqldbmain.jar)   - there is specific coding for the HSQL DB
      jibx-run.jar     (lib/jibx-run.jar)     - XML bidings for saving / restoring parameter files
      jlibdiff.jar     (lib/jlibdiff.jar)     - File Compare
      TableLayout.jar  (lib/TableLayout.jar)  - Screen Layout
      velocity-1.4.jar (lib/velocity-1.4.jar) - Velocity 
      velocity-dep-1.4.jar (lib/velocity-dep-1.4.jar)

      ZCalanedar either    the source in src/ZCalendar                or lib/ZCalendar.jar - Displaying dates 
      cb2xml     either    the source in src/cb2xml/src directory     or lib/cb2xml.jar    - Cobol Copybooks code

      JRecord Source (in the src/JRecord/src directory)            - Low level access / IO Code
      RecordEditor  Source for the Record Editor

---------------------------------------------------------------------------
 Other Supplied Source

       src/re_utilities    holds some old utilities + JUnit Tests
       src/AvroEditor      holds code for Editing Avro Binary files, will become a seperate project at some stage