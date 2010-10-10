 /* rexx */
 
/* return 1*/

     env = uname('S')
  
     if env = 'UNIX' | env = 'Linux' then do
  	  return 1
     end
     return 0
